package com.demo.e_commerce.service.cartService;

import com.demo.e_commerce.config.Exception.CustomeException.*;
import com.demo.e_commerce.dto.cartDto.AddProductCartRequest;
import com.demo.e_commerce.dto.cartDto.DeleteCartItemDto;
import com.demo.e_commerce.dto.cartDto.UpdateCartItemRequest;
import com.demo.e_commerce.dto.cartDto.response.CartItemsInCartDto;
import com.demo.e_commerce.dto.cartDto.response.CartResponseDto;
import com.demo.e_commerce.dto.cartDto.response.GetAllCartResponseDto;
import com.demo.e_commerce.dto.cartDto.response.ProductInCartDto;
import com.demo.e_commerce.dto.commonDto.CommonDto;
import com.demo.e_commerce.model.CartEntity;
import com.demo.e_commerce.model.CartItemEntity;
import com.demo.e_commerce.model.ProductEntity;
import com.demo.e_commerce.model.UserEntity;
import com.demo.e_commerce.repository.cartItemsrepo.CartItemsRepository;
import com.demo.e_commerce.repository.cartrepo.CartRepository;
import com.demo.e_commerce.repository.productrepo.ProductRepository;
import com.demo.e_commerce.repository.userrepo.UserRepository;
import com.demo.e_commerce.security.UserDetailsImpl;
import com.demo.e_commerce.service.cartService.helpers.HelperMethodCartService;
import com.demo.e_commerce.service.cartService.interfaces.CartService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private HelperMethodCartService  helperMethodCartService;

    @Override
    @Transactional
    public ResponseEntity<?> addProductIntoCart(AddProductCartRequest request) {

        try{
            if(request.getProductId()==null || request.getProductId()<=0){
                throw new MissingDataException("Product Id is missing or invalid.");
            }else if(request.getUserId()==null || request.getUserId()<=0){
                throw new MissingDataException("User Id is missing or invalid.");
            }else if(request.getQuantity() <= 0){
                throw new MissingDataException("Quantity cannot be 0 or negative.");
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(request.getUserId())){
                throw new UnauthorizedException("Unauthorized Access");
            }

            //Find User
            UserEntity user = userRepository.findByIdAndIsDeleted(request.getUserId(), 0)
                    .orElseThrow(() -> new NotFoundException("User with id " +  request.getUserId() + " not found."));

            //Find cart
            CartEntity cart = cartRepository.findByUserId(user.getId()).get();

            //Find product
            ProductEntity product = productRepository.findByIdAndIsDeleted(request.getProductId(), 0)
                    .orElseThrow(() -> new NotFoundException("Product with id " +  request.getProductId() + " not found."));

            if(product.getStockQuantity() < request.getQuantity()){
                throw new InvalidQunatityException("Quantity cannot be less than stock quantity.");
            }

            CartItemEntity cartItem = cartItemsRepository.findByCartIdAndProductId(cart.getId(), product.getId());
            if(cartItem != null){
                UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest();
                updateCartItemRequest.setCartItemId(cartItem.getId());
                updateCartItemRequest.setNewQuantity(cartItem.getQuantity() +  request.getQuantity());
                return updateCartItems(updateCartItemRequest);
            }

            //Making cart_items entity
            CartItemEntity cartItemEntity = new CartItemEntity();
            cartItemEntity.setCart(cart);
            cartItemEntity.setProduct(product);
            cartItemEntity.setQuantity(request.getQuantity());

            //Adding total price = Price of product * Quantity
            cartItemEntity.setPrice(product.getPrice().multiply(new BigDecimal(request.getQuantity())));

            cartItemsRepository.save(cartItemEntity);

            //Updating quantity
            product.setStockQuantity(product.getStockQuantity()- request.getQuantity());
            productRepository.save(product);

//            int updatedRow = cartRepository.updateTotalAmount(cart.getId());
//            if(updatedRow==0){
//                throw new DBError("Something went wrong while updating the total amount.");
//            }

            //Calculating total amount for cart
            BigDecimal totalAmount = helperMethodCartService.calculateTotalPrice(cart);
            cart.setTotalAmount(totalAmount);

            cartRepository.save(cart);

            CommonDto dto = new CommonDto();
            dto.setMessage("product added Successfully to cart.");
            dto.setSuccess(true);
            dto.setData(null);

            return  new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (MissingDataException | NotFoundException | DBError | InvalidQunatityException | UnauthorizedException e){
            throw e;
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateCartItems(UpdateCartItemRequest request) {
        try{

            if(request.getCartItemId()==null || request.getCartItemId()<=0){
                throw new MissingDataException("Cart Id is missing or invalid.");
            }else if (request.getNewQuantity() < 0){
                throw new MissingDataException("Quantity cannot be 0 or negative.");
            }

            if(request.getNewQuantity() == 0){
                return removeCartItem(request.getCartItemId());
            }

            CartItemEntity cartItem = cartItemsRepository.findById(request.getCartItemId())
                    .orElseThrow(() -> new NotFoundException("Cart Item with id " + request.getCartItemId() + " not found."));

            //Getting product
            ProductEntity product = cartItem.getProduct();
            if(product == null){
                throw new NotFoundException("Product is not available.");
            }

            if(product.getStockQuantity() < request.getNewQuantity()){
                throw new InvalidQunatityException("Quantity cannot be less than stock quantity.");
            }

            //Getting cart
            CartEntity cart = cartItem.getCart();
            if(cart == null){
                throw new NotFoundException("Cart is not available.");
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(cart.getUser().getId())){
                throw new UnauthorizedException("Unauthorized access to cart.");
            }

            Integer oldQuantity = cartItem.getQuantity();
            Integer newQuantity = request.getNewQuantity();

            //finding differnce
            Integer quantity = newQuantity - oldQuantity;

            //Calculating difference in amount
            BigDecimal differenceAmount = product.getPrice().multiply(new BigDecimal(quantity));

            //Making new Quantity = old + (new - old)
            cartItem.setQuantity(cartItem.getQuantity() + quantity);

            cartItem.setPrice(cartItem.getPrice().add(differenceAmount));

            cart.setTotalAmount(cart.getTotalAmount().add(differenceAmount));

            product.setStockQuantity(product.getStockQuantity() - quantity);

            productRepository.save(product);
            cartItemsRepository.save(cartItem);
            cartRepository.save(cart);

            //Packing DTO
            ProductInCartDto productInCartDto = new ProductInCartDto();
            productInCartDto.setProductId(product.getId());
            productInCartDto.setName(product.getName());
            productInCartDto.setPrice(product.getPrice());
            productInCartDto.setQuantity(product.getStockQuantity());
            productInCartDto.setCategoryName(product.getCategory().getName());

            CartItemsInCartDto  cartItemsInCartDto = new CartItemsInCartDto();
            cartItemsInCartDto.setCartItemId(cartItem.getId());
            cartItemsInCartDto.setTotalPrice(cartItem.getPrice());
            cartItemsInCartDto.setQuantity(cartItem.getQuantity());
            cartItemsInCartDto.setProduct(productInCartDto);

            CartResponseDto responseDto = new CartResponseDto();
            responseDto.setCartId(cart.getId());
            responseDto.setTotal(cart.getTotalAmount());
            responseDto.setCartItem(cartItemsInCartDto);

            CommonDto dto = new CommonDto();
            dto.setMessage("product updated successfully to cart.");
            dto.setSuccess(true);
            dto.setData(responseDto);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (MissingDataException | NotFoundException | DBError | InvalidQunatityException | UnauthorizedException e){
            throw e;
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> removeCartItem(Long cartItemId) {
        try{
            if(cartItemId == null || cartItemId<=0){
                throw new MissingDataException("Cart Id is missing or invalid.");
            }

            CartItemEntity cartItem = cartItemsRepository.findById(cartItemId)
                    .orElseThrow(() -> new NotFoundException("Cart Item with id " + cartItemId + " not found."));

            CartEntity cart = cartItem.getCart();
            if(cart == null){
                throw new NotFoundException("Cart is not available.");
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(cart.getUser().getId())){
                throw new UnauthorizedException("Unauthorized access to cart.");
            }

            ProductEntity  product = cartItem.getProduct();
            if(product == null){
                throw new NotFoundException("Product is not available.");
            }

            Integer quantity = -cartItem.getQuantity();

            //find difference
            BigDecimal differenceAmount = product.getPrice().multiply(new BigDecimal(quantity));

            //Add Differnce in Total AMount of cart
            cart.setTotalAmount(cart.getTotalAmount().add(differenceAmount));

            //SUB quantity from product to update
            product.setStockQuantity(product.getStockQuantity() - quantity);

            productRepository.save(product);
            cartItemsRepository.deleteById(cartItemId);
            cartRepository.save(cart);

            DeleteCartItemDto deleteCartItemDto = new DeleteCartItemDto();
            deleteCartItemDto.setCartId(cart.getId());
            deleteCartItemDto.setNewTotalAmount(cart.getTotalAmount());

            CommonDto dto = new CommonDto();
            dto.setMessage("product deleted successfully from cart.");
            dto.setSuccess(true);
            dto.setData(deleteCartItemDto);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (MissingDataException | NotFoundException | DBError | InvalidQunatityException | UnauthorizedException e){
            throw e;
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }

    @Override
    public ResponseEntity<?> getCart(Long userId) {
        try{
            if(userId == null || userId<=0){
                throw new MissingDataException("UserId is missing or invalid.");
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(userId)){
                throw new UnauthorizedException("You cannot create an order for another user!");
            }

            UserEntity user = userRepository.findByIdAndIsDeleted(userId, 0)
                    .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found."));

            CartEntity cart = user.getCart();
            List<CartItemEntity> cartItems = cart.getCartItems();
            if(cartItems == null || cartItems.isEmpty()){
                CommonDto dto = new CommonDto();
                dto.setMessage("Cart is Empty.");
                dto.setSuccess(false);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
            GetAllCartResponseDto responseDto = new GetAllCartResponseDto();
            responseDto.setCartId(cart.getId());
            responseDto.setTotal(cart.getTotalAmount());

            responseDto.setCartItem(
                cartItems.stream().map(cartItemEntity -> {
                    CartItemsInCartDto  cartItemsInCartDto = new CartItemsInCartDto();
                    cartItemsInCartDto.setCartItemId(cartItemEntity.getId());
                    cartItemsInCartDto.setTotalPrice(cartItemEntity.getPrice());
                    cartItemsInCartDto.setQuantity(cartItemEntity.getQuantity());

                    ProductEntity product =  cartItemEntity.getProduct();

                    ProductInCartDto productInCartDto = new ProductInCartDto();
                    productInCartDto.setProductId(product.getId());
                    productInCartDto.setName(product.getName());
                    productInCartDto.setPrice(product.getPrice());
                    productInCartDto.setQuantity(product.getStockQuantity());
                    productInCartDto.setCategoryName(product.getCategory().getName());

                    cartItemsInCartDto.setProduct(productInCartDto);

                    return cartItemsInCartDto;
                }).collect(Collectors.toList())
            );

            CommonDto dto = new CommonDto();
            dto.setMessage("cart fetched successfully to cart.");
            dto.setSuccess(true);
            dto.setData(responseDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (MissingDataException | NotFoundException | DBError | InvalidQunatityException | UnauthorizedException e){
            throw e;
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }
}

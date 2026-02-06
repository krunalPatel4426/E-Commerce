package com.demo.e_commerce.service.orderService.impl;

import com.demo.e_commerce.config.Exception.CustomeException.DBError;
import com.demo.e_commerce.config.Exception.CustomeException.MissingDataException;
import com.demo.e_commerce.config.Exception.CustomeException.NotFoundException;
import com.demo.e_commerce.config.Exception.CustomeException.UnauthorizedException;
import com.demo.e_commerce.dto.commonDto.CommonDto;
import com.demo.e_commerce.dto.commonDto.GetAllOrderDto;
import com.demo.e_commerce.dto.orderDto.MakeOrderRequestDto;
import com.demo.e_commerce.dto.orderDto.response.OrderItemInOrder;
import com.demo.e_commerce.dto.orderDto.response.OrderResponseDto;
import com.demo.e_commerce.dto.orderDto.response.ProductInOrderDto;
import com.demo.e_commerce.enums.OrderStatus;
import com.demo.e_commerce.model.*;
import com.demo.e_commerce.repository.cartItemsrepo.CartItemsRepository;
import com.demo.e_commerce.repository.cartrepo.CartRepository;
import com.demo.e_commerce.repository.orderitemsrepo.OrderItemsRepository;
import com.demo.e_commerce.repository.orderrepo.OrderRepository;
import com.demo.e_commerce.repository.productrepo.ProductRepository;
import com.demo.e_commerce.repository.userrepo.UserRepository;
import com.demo.e_commerce.security.UserDetailsImpl;
import com.demo.e_commerce.service.orderService.interfaces.OrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemsRepository  orderItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Transactional
    @Override
    public ResponseEntity<?> createOrder(MakeOrderRequestDto request) {
        try{
            if(request.getCartId() == null ||  request.getCartId() <= 0){
                throw new MissingDataException("Cart Id is missing or invalid");
            }
            if(request.getUserId() == null ||  request.getUserId() <= 0){
                throw new MissingDataException("User Id is missing or invalid");
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(request.getUserId())){
                throw new UnauthorizedException("Unauthorized Access");
            }

            UserEntity user = userRepository.findByIdAndIsDeleted(request.getUserId(), 0)
                    .orElseThrow(() -> new NotFoundException("User with id : " +  request.getUserId() + " not found."));

            if(user.getCart().getId() != request.getCartId()){
                throw new MissingDataException("Data Malfunctioned.");
            }

            CartEntity cart = cartRepository.findByUserId(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("Cart not found"));

            List<CartItemEntity> cartItems = cart.getCartItems();

            if(cartItems == null || cartItems.isEmpty()){
                CommonDto commonDto = new CommonDto();
                commonDto.setMessage("Cart is empty");
                commonDto.setSuccess(false);
                commonDto.setData(null);
                return new ResponseEntity<>(commonDto, HttpStatus.BAD_REQUEST);
            }

            //creats Order
            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setTotalAmount(cart.getTotalAmount());
            order.setOrderStatus(OrderStatus.CREATED);
            orderRepository.save(order);
            cartItems.stream()
                    .forEach(cartItem -> {
                        OrderItemEntity orderItem = new OrderItemEntity();
                        orderItem.setOrder(order);
                        orderItem.setProduct(cartItem.getProduct());
                        orderItem.setQuantity(cartItem.getQuantity());
                        orderItem.setPrice(cartItem.getPrice());
                        orderItemsRepository.save(orderItem);
                    });

//            int changes = cartItemsRepository.deleteAllByCartId(cart.getId());
//            if(changes == 0){
//
//                throw new DBError("Something went wrong. While creating order.");
//            }

            cart.getCartItems().clear();
            cart.setTotalAmount(BigDecimal.ZERO);
            cartRepository.save(cart);

            CommonDto dto = new CommonDto();
            dto.setData(null);
            dto.setMessage("Order created");
            dto.setSuccess(true);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (MissingDataException | DBError | NotFoundException | UnauthorizedException e){
            throw e;
        }
        catch (Exception e){
            logger.error("error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }

    @Override
    public ResponseEntity<?> getOrder(Long orderId) {
        try{
            if(orderId == null || orderId <= 0){
                throw new MissingDataException("Order Id is missing or invalid");
            }

            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("Order with id : " + orderId + " not found."));

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(order.getUser().getId())){
                throw new UnauthorizedException("Unauthorized Access");
            }

            List<OrderItemEntity> orderItem = order.getOrderItems();

            if(orderItem == null || orderItem.isEmpty()){
                CommonDto commonDto = new CommonDto();
                commonDto.setMessage("Order is empty");
                commonDto.setSuccess(false);
                commonDto.setData(null);
                return new ResponseEntity<>(commonDto, HttpStatus.BAD_REQUEST);
            }

            OrderResponseDto responseDto = new OrderResponseDto();
            responseDto.setOrderId(order.getId());
            responseDto.setTotalOrderPrice(order.getTotalAmount());
            responseDto.setOrderStatus(order.getOrderStatus());
            responseDto.setOrderDate(order.getOrderDate());

            responseDto.setOrderItem(
                    orderItem.stream().map(orderItemEntity -> {
                        OrderItemInOrder orderItemInOrder =  new OrderItemInOrder();
                        orderItemInOrder.setOrderItemId(orderItemEntity.getId());
                        orderItemInOrder.setQuantity(orderItemEntity.getQuantity());
                        orderItemInOrder.setOrderItemPrice(orderItemEntity.getPrice());

                        ProductEntity product = orderItemEntity.getProduct();
                        ProductInOrderDto productInOrderDto = new ProductInOrderDto();
                        productInOrderDto.setProductId(product.getId());
                        productInOrderDto.setName(product.getName());
                        productInOrderDto.setPrice(product.getPrice());
                        productInOrderDto.setCategory(product.getCategory().getName());
                        orderItemInOrder.setProduct(productInOrderDto);
                        return orderItemInOrder;
                    }).collect(Collectors.toList())
            );

            CommonDto dto = new CommonDto();
            dto.setData(responseDto);
            dto.setMessage("Order found");
            dto.setSuccess(true);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (MissingDataException | DBError | NotFoundException | UnauthorizedException e){
            throw e;
        }catch (Exception e){
            logger.error("error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }

    @Override
    public ResponseEntity<?> getAllOrderForUser(Long userId) {
        try{

            if(userId == null || userId <= 0){
                throw new MissingDataException("User Id is missing or invalid");
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assert userDetails != null;
            if(!userDetails.getId().equals(userId)){
                throw new UnauthorizedException("Unauthorized Access");
            }

            UserEntity user = userRepository.findByIdAndIsDeleted(userId, 0)
                    .orElseThrow(() -> new NotFoundException("User with id : " + userId + " not found."));


            List<OrderEntity> orders = user.getOrders();
            if(orders == null || orders.isEmpty()){
                CommonDto dto = new CommonDto();
                dto.setData(null);
                dto.setMessage("Orders not found");
                dto.setSuccess(true);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }

            List<OrderResponseDto> responseDtos = new ArrayList<>();
            for(OrderEntity order : orders){
                OrderResponseDto responseDto = new OrderResponseDto();
                responseDto.setOrderId(order.getId());
                responseDto.setTotalOrderPrice(order.getTotalAmount());
                responseDto.setOrderStatus(order.getOrderStatus());
                responseDto.setOrderDate(order.getOrderDate());

                List<OrderItemEntity> orderItems = order.getOrderItems();
                responseDto.setOrderItem(
                        orderItems.stream().map(orderItemEntity -> {
                            OrderItemInOrder orderItemInOrder =  new OrderItemInOrder();
                            orderItemInOrder.setOrderItemId(orderItemEntity.getId());
                            orderItemInOrder.setQuantity(orderItemEntity.getQuantity());
                            orderItemInOrder.setOrderItemPrice(orderItemEntity.getPrice());

                            ProductEntity product = orderItemEntity.getProduct();
                            ProductInOrderDto productInOrderDto = new ProductInOrderDto();
                            productInOrderDto.setProductId(product.getId());
                            productInOrderDto.setName(product.getName());
                            productInOrderDto.setPrice(product.getPrice());
                            productInOrderDto.setCategory(product.getCategory().getName());
                            orderItemInOrder.setProduct(productInOrderDto);
                            return orderItemInOrder;
                        }).collect(Collectors.toList())
                );
                responseDtos.add(responseDto);
            }

            GetAllOrderDto dto = new GetAllOrderDto();
            dto.setData(responseDtos);
            dto.setMessage("All orders found");
            dto.setSuccess(true);

            return ResponseEntity.ok(dto);
        }catch (MissingDataException | DBError | NotFoundException | UnauthorizedException e){
            throw e;
        }catch (Exception e){
            logger.error("error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }
}

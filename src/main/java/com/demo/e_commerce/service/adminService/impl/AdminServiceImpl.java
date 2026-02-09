package com.demo.e_commerce.service.adminService.impl;

import com.demo.e_commerce.config.Exception.CustomeException.CategoryNotFound;
import com.demo.e_commerce.config.Exception.CustomeException.MissingDataException;
import com.demo.e_commerce.config.Exception.CustomeException.NegativePriceException;
import com.demo.e_commerce.config.Exception.CustomeException.NotFoundException;
import com.demo.e_commerce.dto.categories.AddCategoryDto;
import com.demo.e_commerce.dto.categories.CategoryResponse;
import com.demo.e_commerce.dto.commonDto.CommonDto;
import com.demo.e_commerce.dto.products.AddProductDto;
import com.demo.e_commerce.dto.products.ProductResponseDto;
import com.demo.e_commerce.model.CategoryEntity;
import com.demo.e_commerce.model.ProductEntity;
import com.demo.e_commerce.repository.categoryrepo.CategoryRepository;
import com.demo.e_commerce.repository.productrepo.ProductRepository;
import com.demo.e_commerce.service.adminService.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdminServiceImpl implements AdminService {

    Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> createCategory(AddCategoryDto addCategoryDto) {
        try{
            CommonDto commonDto = new CommonDto();
            String name =  addCategoryDto.getName();
            boolean isCategoryPresent = categoryRepository.findByNameAndIsDeleted(name, 0).isPresent();
            CategoryEntity categoryEntity = new CategoryEntity();
            if(!isCategoryPresent){
                categoryEntity = new CategoryEntity();
                categoryEntity.setName(name);
                categoryRepository.save(categoryEntity);
                commonDto.setMessage("Category Saved Successfully");
                commonDto.setSuccess(true);
                CategoryResponse response = new CategoryResponse();
                response.setName(categoryEntity.getName());
                response.setId(categoryEntity.getId());
                response.setCreatedAt(categoryEntity.getCreatedAt());
                response.setUpdatedAt(categoryEntity.getUpdatedAt());
                commonDto.setData(response);
            }else {
                commonDto.setMessage("Category Already Exists");
                commonDto.setSuccess(false);
                commonDto.setData(null);
            }
            return ResponseEntity.ok(commonDto);
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong while creating the category");
        }
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, AddCategoryDto addCategoryDto) {
        try{
            CommonDto commonDto = new CommonDto();
            String name =  addCategoryDto.getName();
            CategoryEntity categoryEntity = categoryRepository.findByIdAndIsDeleted(id, 0)
                    .orElseThrow(() -> new NotFoundException("Category with id " + id + " Not Found"));

                categoryEntity.setName(name);
                categoryRepository.save(categoryEntity);
                commonDto.setMessage("Category Saved Successfully");
                commonDto.setSuccess(true);
                CategoryResponse response = new CategoryResponse();
                response.setName(categoryEntity.getName());
                response.setId(categoryEntity.getId());
                response.setCreatedAt(categoryEntity.getCreatedAt());
                response.setUpdatedAt(categoryEntity.getUpdatedAt());
                commonDto.setData(response);
            return ResponseEntity.ok(commonDto);
        }catch (NotFoundException e){
            throw e;
        }
        catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong while creating the category");
        }
    }

    @Override
    public ResponseEntity<?> createProduct(AddProductDto addProductDto) {
        try{
            if(addProductDto.getCategoryId() == null){
                throw new MissingDataException("Category Id is Empty");
            }

            if (addProductDto.getPrice().compareTo(BigDecimal.ZERO) <= 0){
                throw new NegativePriceException("Price is Negative or Zero");
            }

            if(addProductDto.getStockQuantity() <= 0){
                throw new NegativePriceException("Stock Quantity is Negative or Zero");
            }

            CategoryEntity  categoryEntity = categoryRepository.findByIdAndIsDeleted(addProductDto.getCategoryId(), 0)
                    .orElseThrow(() -> new CategoryNotFound("Category with id " + addProductDto.getCategoryId() + " Not Found"));
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(addProductDto.getName());
            productEntity.setCategory(categoryEntity);
            productEntity.setDescription(addProductDto.getDescription());
            productEntity.setPrice(addProductDto.getPrice());
            productEntity.setStockQuantity(addProductDto.getStockQuantity());

            productRepository.save(productEntity);

            CommonDto commonDto = new CommonDto();
            commonDto.setMessage("Product Saved Successfully");
            commonDto.setSuccess(true);

            ProductResponseDto  productResponseDto = new ProductResponseDto();
            productResponseDto.setId(productEntity.getId());
            productResponseDto.setName(productEntity.getName());
            productResponseDto.setDescription(productEntity.getDescription());
            productResponseDto.setPrice(productEntity.getPrice());
            productResponseDto.setQuantity(productEntity.getStockQuantity());
            productResponseDto.setDeleted(productEntity.getIsDeleted());
            productResponseDto.setCategory(categoryEntity.getName());
            productResponseDto.setCreatedAt(productEntity.getCreatedAt());
            productResponseDto.setUpdatedAt(productEntity.getUpdatedAt());

            commonDto.setData(productResponseDto);
            return ResponseEntity.ok(commonDto);

        }catch (MissingDataException | NegativePriceException | CategoryNotFound e){
            throw e;
        } catch (Exception e){
            logger.error("System Error: {}", e.getMessage());
            throw new RuntimeException("Something went wrong while creating the product");
        }
    }

    @Override
    public ResponseEntity<?> updateProduct(Long productId, AddProductDto addProductDto) {
        try{
            if(productId == null){
                throw new MissingDataException("Product Id is Empty");
            }

            if(addProductDto.getCategoryId() == null){
                throw new MissingDataException("Category Id is Empty");
            }

            if (addProductDto.getPrice().compareTo(BigDecimal.ZERO) < 0){
                throw new NegativePriceException("Price is Negative or Zero");
            }
            ProductEntity productEntity = productRepository.findByIdAndIsDeleted(productId, 0)
                    .orElseThrow(() -> new NotFoundException("Product with id " + productId + " Not found."));

            CategoryEntity  categoryEntity = categoryRepository.findByIdAndIsDeleted(addProductDto.getCategoryId(), 0)
                    .orElseThrow(() -> new CategoryNotFound("Category with id " + addProductDto.getCategoryId() + " Not Found"));
//            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(addProductDto.getName());
            productEntity.setCategory(categoryEntity);
            productEntity.setDescription(addProductDto.getDescription());
            productEntity.setPrice(addProductDto.getPrice());
            productEntity.setStockQuantity(addProductDto.getStockQuantity());

            productRepository.save(productEntity);

            CommonDto commonDto = new CommonDto();
            commonDto.setMessage("Product Saved Successfully");
            commonDto.setSuccess(true);

            ProductResponseDto  productResponseDto = new ProductResponseDto();
            productResponseDto.setId(productEntity.getId());
            productResponseDto.setName(productEntity.getName());
            productResponseDto.setDescription(productEntity.getDescription());
            productResponseDto.setPrice(productEntity.getPrice());
            productResponseDto.setQuantity(productEntity.getStockQuantity());
            productResponseDto.setDeleted(productEntity.getIsDeleted());
            productResponseDto.setCategory(categoryEntity.getName());
            productResponseDto.setCreatedAt(productEntity.getCreatedAt());
            productResponseDto.setUpdatedAt(productEntity.getUpdatedAt());

            commonDto.setData(productResponseDto);
            return ResponseEntity.ok(commonDto);

        }catch (NotFoundException | MissingDataException | NegativePriceException | CategoryNotFound e){
            throw e;
        } catch (Exception e){
            logger.error("System Error: {}", e.getMessage());
            throw new RuntimeException("Something went wrong while creating the product");
        }
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long productId) {
        try{
            if(productId == null){
                throw new MissingDataException("Product Id is Empty");
            }
            boolean isDeleted = productRepository.findByIdAndIsDeleted(productId, 0).isPresent();
            if(isDeleted){
               int change = productRepository.deleteProduct(productId, 1);
               if(change == 0){
                   throw new NotFoundException("Product with id " + productId + " Not Found");
               }
               CommonDto commonDto = new CommonDto();
               commonDto.setMessage("Product Deleted Successfully");
               commonDto.setSuccess(true);
               return ResponseEntity.ok(commonDto);
            }else{
                throw  new NotFoundException("Product with id " + productId + " Not Found");
            }
        }catch (MissingDataException | NotFoundException e){
            throw e;
        }catch (Exception e){
            logger.error("System Error: {}", e.getMessage());
            throw new RuntimeException("Something went wrong while deleting the product");
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        try{
            if(id == null || id <= 0){
                throw new MissingDataException("Category Id is Empty or invalid");
            }

            int change = categoryRepository.deleteCategoryById(id);
            if(change == 0){
                throw new NotFoundException("Category with id " + id + " Not Found");
            }

            CommonDto commonDto = new CommonDto();
            commonDto.setMessage("Category Deleted Successfully");
            commonDto.setSuccess(true);
            return ResponseEntity.ok(commonDto);

        }catch (MissingDataException | NotFoundException e){
            throw e;
        }catch (Exception e){
            logger.error("System Error: {}", e.getMessage());
            throw new RuntimeException("Something went wrong while deleting the category");
        }
    }


}

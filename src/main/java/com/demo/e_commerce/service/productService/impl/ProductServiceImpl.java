package com.demo.e_commerce.service.productService.impl;

import com.demo.e_commerce.config.Exception.CustomeException.MissingDataException;
import com.demo.e_commerce.config.Exception.CustomeException.NotFoundException;
import com.demo.e_commerce.dto.commonDto.CommonDto;
import com.demo.e_commerce.dto.products.GetAllProductResponseDto;
import com.demo.e_commerce.dto.products.GetProductResponseDto;
import com.demo.e_commerce.dto.products.projections.GetAllDataProjection;
import com.demo.e_commerce.dto.products.projections.GetProductDataProjection;
import com.demo.e_commerce.repository.productrepo.ProductRepository;
import com.demo.e_commerce.service.productService.interfaces.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository  productRepository;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ResponseEntity<?> getAllProducts() {
        try{
            List<GetAllDataProjection> projections = productRepository.getAllProducts();
            List<GetAllProductResponseDto> data =  projections
                    .stream()
                    .map(d -> {
                        GetAllProductResponseDto dto = new GetAllProductResponseDto();
                        dto.setId(d.getId());
                        dto.setCategoryName(d.getCategoryName());
                        dto.setName(d.getName());
                        dto.setPrice(d.getPrice());
                        dto.setAvailable(d.getQuantity() > 0 ? true : false);
                        return dto;
                    }).collect(Collectors.toList());

            CommonDto dto = new CommonDto();
            dto.setData(data);
            dto.setMessage("Success");
            dto.setSuccess(true);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again");
        }
    }

    @Override
    public ResponseEntity<?> getProduct(Long productId) {
        try{
            if(productId == null){
                throw new MissingDataException("Product ID is null");
            }
            GetProductDataProjection projection = productRepository.getProduct(productId)
                    .orElseThrow(() -> new NotFoundException("Product of Id " + productId + " not found."));

            GetProductResponseDto responseDto = new GetProductResponseDto();
            responseDto.setId(projection.getId());
            responseDto.setName(projection.getName());
            responseDto.setPrice(projection.getPrice());
            responseDto.setCategory(projection.getCategoryName());
            responseDto.setQuantity(projection.getQuantity());
            responseDto.setDescription(projection.getDescription());
            responseDto.setCreatedAt(projection.getCreatedAt());
            responseDto.setUpdatedAt(projection.getUpdatedAt());

            CommonDto dto = new CommonDto();
            dto.setData(responseDto);
            dto.setMessage("Success");
            dto.setSuccess(true);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (MissingDataException | NotFoundException e){
            throw e;
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again");
        }
    }
}

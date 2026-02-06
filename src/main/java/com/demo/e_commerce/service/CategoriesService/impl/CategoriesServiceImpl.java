package com.demo.e_commerce.service.CategoriesService.impl;

import com.demo.e_commerce.dto.categories.GetAllCategoriesResponseDto;
import com.demo.e_commerce.dto.categories.projections.GetAllCategoriesProjection;
import com.demo.e_commerce.dto.commonDto.CommonDto;
import com.demo.e_commerce.repository.categoryrepo.CategoryRepository;
import com.demo.e_commerce.service.CategoriesService.interfaces.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    @Autowired
    private CategoryRepository  categoryRepository;

    @Override
    public ResponseEntity<?> getAllCategories() {
        try{
            List<GetAllCategoriesProjection> projections = categoryRepository.getAllCategories();
            List<GetAllCategoriesResponseDto> responseDtos =
                    projections.stream()
                            .map(data -> {
                                GetAllCategoriesResponseDto dto = new GetAllCategoriesResponseDto();
                                dto.setId(data.getId());
                                dto.setName(data.getName());
                                return dto;
                            }).collect(Collectors.toList());
            CommonDto  commonDto = new CommonDto();
            commonDto.setData(responseDtos);
            commonDto.setMessage("Success");
            commonDto.setSuccess(true);
            return new ResponseEntity<>(commonDto, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Error : {}", e.getMessage());
            throw new RuntimeException("Something went wrong. Please try again later.");
        }
    }
}

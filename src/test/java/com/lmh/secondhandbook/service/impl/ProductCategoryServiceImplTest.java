package com.lmh.secondhandbook.service.impl;

import com.lmh.secondhandbook.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryService service;

    @Test
    void getAllProductCatgoryVO(){
        service.getAllProductCategoryVO();
    }


}
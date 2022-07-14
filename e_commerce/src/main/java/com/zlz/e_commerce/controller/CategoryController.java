package com.zlz.e_commerce.controller;

import com.zlz.e_commerce.service.ICategoryService;
import com.zlz.e_commerce.vo.CategoryVo;
import com.zlz.e_commerce.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll() {
        return categoryService.selectAll();
    }
}

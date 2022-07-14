package com.zlz.e_commerce.service;

import com.zlz.e_commerce.ECommerceApplicationTests;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.vo.CategoryVo;
import com.zlz.e_commerce.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Slf4j
public class CategoryServiceTest extends ECommerceApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void findSubcategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001, set);
        log.info("set={}", set);
    }
}
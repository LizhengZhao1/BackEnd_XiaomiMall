package com.zlz.e_commerce.service;

import com.github.pagehelper.PageInfo;
import com.zlz.e_commerce.ECommerceApplicationTests;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.vo.ProductDetailVo;
import com.zlz.e_commerce.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IProductServiceTest extends ECommerceApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void detail(){
        ResponseVo<ProductDetailVo> detailVoResponseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),detailVoResponseVo.getStatus());
    }
}
package com.zlz.e_commerce.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zlz.e_commerce.ECommerceApplicationTests;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.form.CartAddForm;
import com.zlz.e_commerce.form.CartUpdateForm;
import com.zlz.e_commerce.vo.CartVo;
import com.zlz.e_commerce.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ICartServiceTest extends ECommerceApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Integer productId = 26;

    private Integer uid = 1;

    @Before
    public void add() {
        log.info("Create a Cart......");
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(productId);
        cartAddForm.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.add(uid, cartAddForm);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo<CartVo> responseVo = cartService.list(uid);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update(){
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(5);
        cartUpdateForm.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.update(uid, productId, cartUpdateForm);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @After
    public void delete(){
        log.info("Delete a Cart......");
        ResponseVo<CartVo> responseVo = cartService.delete(uid, productId);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void sum(){
        ResponseVo<Integer> responseVo = cartService.sum(uid);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}
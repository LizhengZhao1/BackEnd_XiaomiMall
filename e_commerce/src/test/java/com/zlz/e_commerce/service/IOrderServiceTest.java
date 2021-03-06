package com.zlz.e_commerce.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zlz.e_commerce.ECommerceApplicationTests;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.form.CartAddForm;
import com.zlz.e_commerce.vo.CartVo;
import com.zlz.e_commerce.vo.OrderVo;
import com.zlz.e_commerce.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class IOrderServiceTest extends ECommerceApplicationTests {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    private Integer uid = 1;

    private Integer shippingId = 4;

    private Integer productId = 26;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void before(){
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(productId);
        cartAddForm.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.add(uid, cartAddForm);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void createTest(){
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    private ResponseVo<OrderVo> create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
        return responseVo;
    }

    @Test
    public void list(){
        ResponseVo<PageInfo> responseVo = orderService.list(uid, 1, 2);
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void detail(){
        ResponseVo<OrderVo> vo = create();
        ResponseVo<OrderVo> responseVo = orderService.detail(uid, vo.getData().getOrderNo());
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void cancel(){
        ResponseVo<OrderVo> vo = create();
        ResponseVo responseVo = orderService.cancel(uid, vo.getData().getOrderNo());
        log.info("result={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}
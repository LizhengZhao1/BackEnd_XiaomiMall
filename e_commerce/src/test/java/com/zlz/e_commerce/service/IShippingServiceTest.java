package com.zlz.e_commerce.service;

import com.zlz.e_commerce.ECommerceApplicationTests;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.form.ShippingForm;
import com.zlz.e_commerce.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class IShippingServiceTest extends ECommerceApplicationTests {

    @Autowired
    private IShippingService shippingService;

    private Integer uid = 1;

    private ShippingForm form;

    private Integer shippingId;
    @Before
    public void before(){
        ShippingForm form = new ShippingForm();
        form.setReceiverName("Mark");
        form.setReceiverAddress("2675 Park Ave");
        form.setReceiverCity("Santa Clara");
        form.setReceiverDistrict("SCU");
        form.setReceiverMobile("1234567899");
        form.setReceiverPhone("00903093");
        form.setReceiverProvince("CA");
        form.setReceiverZip("95050");
        this.form = form;

        add();
    }

    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("result={}", responseVo);
        this.shippingId = responseVo.getData().get("shippingId");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverCity("San Jose");
        ResponseVo responseVo = shippingService.update(uid, shippingId, form);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo responseVo = shippingService.list(uid, 1,10);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}
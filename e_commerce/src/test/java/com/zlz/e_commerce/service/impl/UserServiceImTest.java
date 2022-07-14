package com.zlz.e_commerce.service.impl;

import com.zlz.e_commerce.ECommerceApplicationTests;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.enums.RoleEnum;
import com.zlz.e_commerce.pojo.User;
import com.zlz.e_commerce.service.IUserService;
import com.zlz.e_commerce.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImTest extends ECommerceApplicationTests {

    private static final String USERNAME = "jack";
    private static final String PASSWORD = "123456";

    @Autowired
    private IUserService userService;

    @Before
    public void register() {
        User user = new User(USERNAME, PASSWORD, "1224@gmail.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);

    }

    @Test
    public void login() {
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}
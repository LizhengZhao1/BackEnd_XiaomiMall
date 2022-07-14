package com.zlz.e_commerce.service;

import com.zlz.e_commerce.pojo.User;
import com.zlz.e_commerce.vo.ResponseVo;

public interface IUserService {
    /**
     * 注册
     */
    ResponseVo<User> register(User user);

    /**
     * 登录
     */
    ResponseVo<User> login(String username, String password);
}

package com.zlz.e_commerce.service.impl;

import com.zlz.e_commerce.dao.UserMapper;
import com.zlz.e_commerce.enums.RoleEnum;
import com.zlz.e_commerce.pojo.User;
import com.zlz.e_commerce.service.IUserService;
import com.zlz.e_commerce.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.zlz.e_commerce.enums.ResponseEnum.*;

@Service
public class UserServiceIm implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        //Username不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(USERNAME_EXIT);
        }
        //email不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(EMAIL_EXIT);
        }
        //MD5摘要算法（Spring自带）
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(md5DigestAsHex);

        user.setRole(RoleEnum.CUSTOMER.getCode());

        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount < 0) {
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            //用户不存在(返回：用户名或密码输入错误)
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equalsIgnoreCase(md5DigestAsHex)) {
            //密码错误(返回：用户名或密码输入错误)
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResponseVo.success(user);
    }
}

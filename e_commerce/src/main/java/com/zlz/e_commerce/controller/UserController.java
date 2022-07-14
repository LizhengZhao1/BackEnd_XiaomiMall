package com.zlz.e_commerce.controller;

import com.zlz.e_commerce.consts.MallConst;
import com.zlz.e_commerce.form.UserLoginForm;
import com.zlz.e_commerce.form.UserRegisterForm;
import com.zlz.e_commerce.pojo.User;
import com.zlz.e_commerce.service.IUserService;
import com.zlz.e_commerce.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm) {

        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpSession httpSession) {
        ResponseVo<User> login = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置Session
        httpSession.setAttribute(MallConst.CURRENT_USER, login.getData());
        log.info("/login sessionId = {} ", httpSession.getId());
        return login;
    }

    //session保存在内存里 改进版：token+redis
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession httpSession) {
        log.info("/user sessionId = {} ", httpSession.getId());
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success(user);
    }
    //TODO 判断登录状态
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession httpSession) {
        log.info("/user/logout sessionId = {} ", httpSession.getId());
        httpSession.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}

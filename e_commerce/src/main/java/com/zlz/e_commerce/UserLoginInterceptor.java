package com.zlz.e_commerce;

import com.zlz.e_commerce.consts.MallConst;
import com.zlz.e_commerce.exception.UserLoginException;
import com.zlz.e_commerce.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * True:表示继续流程 False: 表示中断
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        log.info("preHandle......");
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            log.info("User == null");
            throw new UserLoginException();
        }
        return true;
    }
}

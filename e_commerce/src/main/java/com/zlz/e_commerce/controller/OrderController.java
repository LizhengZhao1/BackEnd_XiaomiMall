package com.zlz.e_commerce.controller;

import com.github.pagehelper.PageInfo;
import com.zlz.e_commerce.consts.MallConst;
import com.zlz.e_commerce.form.OrderCreateForm;
import com.zlz.e_commerce.pojo.User;
import com.zlz.e_commerce.service.IOrderService;
import com.zlz.e_commerce.vo.OrderVo;
import com.zlz.e_commerce.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<OrderVo> responseVo = orderService.create(user.getId(), form.getShippingId());
        return responseVo;
    }

    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestBody Integer pageNum,
                                     @RequestBody Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<PageInfo> responseVo = orderService.list(user.getId(), pageNum, pageSize);
        return responseVo;
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<OrderVo> responseVo = orderService.detail(user.getId(), orderNo);
        return responseVo;
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo responseVo = orderService.cancel(user.getId(), orderNo);
        return responseVo;
    }
}

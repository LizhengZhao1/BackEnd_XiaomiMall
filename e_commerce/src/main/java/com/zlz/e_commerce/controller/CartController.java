package com.zlz.e_commerce.controller;

import com.zlz.e_commerce.consts.MallConst;
import com.zlz.e_commerce.form.CartAddForm;
import com.zlz.e_commerce.form.CartUpdateForm;
import com.zlz.e_commerce.pojo.User;
import com.zlz.e_commerce.service.ICartService;
import com.zlz.e_commerce.vo.CartVo;
import com.zlz.e_commerce.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
            @Valid @RequestBody CartUpdateForm cartUpdateForm, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(), productId, cartUpdateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}

package com.zlz.e_commerce.service;

import com.zlz.e_commerce.form.CartAddForm;
import com.zlz.e_commerce.form.CartUpdateForm;
import com.zlz.e_commerce.pojo.Cart;
import com.zlz.e_commerce.vo.CartVo;
import com.zlz.e_commerce.vo.ResponseVo;

import java.util.List;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);
}

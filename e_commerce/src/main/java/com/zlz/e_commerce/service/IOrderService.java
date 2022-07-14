package com.zlz.e_commerce.service;

import com.github.pagehelper.PageInfo;
import com.zlz.e_commerce.vo.OrderVo;
import com.zlz.e_commerce.vo.ResponseVo;

public interface IOrderService {

    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    ResponseVo cancel(Integer uid, Long orderNo);

    void paid(Long orderNo);


}

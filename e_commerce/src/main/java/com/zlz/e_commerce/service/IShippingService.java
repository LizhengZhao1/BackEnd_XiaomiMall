package com.zlz.e_commerce.service;

import com.github.pagehelper.PageInfo;
import com.zlz.e_commerce.form.ShippingForm;
import com.zlz.e_commerce.vo.ResponseVo;

import java.util.Map;

public interface IShippingService {

    ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form);

    ResponseVo delete(Integer uid, Integer shippingId);

    ResponseVo update(Integer uid, Integer shippingId, ShippingForm form);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

}

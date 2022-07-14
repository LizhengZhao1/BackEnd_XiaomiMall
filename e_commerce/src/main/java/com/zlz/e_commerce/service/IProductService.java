package com.zlz.e_commerce.service;

import com.github.pagehelper.PageInfo;
import com.zlz.e_commerce.vo.ProductDetailVo;
import com.zlz.e_commerce.vo.ResponseVo;

public interface IProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);

}

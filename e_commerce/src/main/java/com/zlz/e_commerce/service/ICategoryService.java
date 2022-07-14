package com.zlz.e_commerce.service;

import com.zlz.e_commerce.vo.CategoryVo;
import com.zlz.e_commerce.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}

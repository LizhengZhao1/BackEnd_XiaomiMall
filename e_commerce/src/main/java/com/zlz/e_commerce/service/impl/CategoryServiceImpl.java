package com.zlz.e_commerce.service.impl;

import com.zlz.e_commerce.consts.MallConst;
import com.zlz.e_commerce.dao.CategoryMapper;
import com.zlz.e_commerce.pojo.Category;
import com.zlz.e_commerce.service.ICategoryService;
import com.zlz.e_commerce.vo.CategoryVo;
import com.zlz.e_commerce.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 耗时:http(请求微信API) > 磁盘 > 内存
     * mysql(内网+磁盘)
     * @return
     */

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
//        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();
        //查出parent_id = 0
//        for (Category category : categories) {
//            if (category.getParentId().equals(MallConst.ROOT_PARENT_ID)) {
//                CategoryVo categoryVo = new CategoryVo();
//                BeanUtils.copyProperties(category, categoryVo);
//                categoryVos.add(categoryVo);
//            }
//        }
        //lambda + stream
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        //查询子目录
        findSubCategories(categoryVoList, categories);
        return ResponseVo.success(categoryVoList);
    }

    private void findSubCategories(List<CategoryVo> categoryVoList, List<Category> categories) {

        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                //如果查到内容， 设置subCategories,然后继续往下查
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo categoryVo1 = category2CategoryVo(category);
                    subCategoryVoList.add(categoryVo1);
                }
            }
            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(subCategoryVoList);
            findSubCategories(subCategoryVoList, categories);
        }
    }

    private CategoryVo category2CategoryVo (Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id, resultSet, categories);
    }

    public void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {
        for (Category category : categories){
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(), resultSet, categories);
            }
        }
    }
}

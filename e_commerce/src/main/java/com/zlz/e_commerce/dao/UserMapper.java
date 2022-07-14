package com.zlz.e_commerce.dao;

import com.zlz.e_commerce.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUsername(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUsername(String username);

    int countByEmail(String email);
}
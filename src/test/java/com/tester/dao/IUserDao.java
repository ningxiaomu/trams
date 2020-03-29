package com.tester.dao;

import com.tester.model.user;

import java.util.List;


public interface IUserDao {

    /**
     * 查询所有操作
     * @return
     */
    List<user> findAll();
}

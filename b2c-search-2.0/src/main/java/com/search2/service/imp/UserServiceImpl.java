package com.search2.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search2.dao.jdbcTemplate.UserDao;
import com.search2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public void create(String name, Integer age) {
        dao.create(name, age);
    }

    @Override
    public void deleteByName(String name) {
        dao.deleteByName(name);
    }

    @Override
    public Integer getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public void deleteAllUsers() {
        dao.deleteAllUsers();
    }

}

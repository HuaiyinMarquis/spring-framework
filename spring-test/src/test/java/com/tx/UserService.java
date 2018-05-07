package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void saveUser() {
        userDao.insertUser();
        System.out.println("insert user Success...");
        System.out.println(10/0);
    }
}

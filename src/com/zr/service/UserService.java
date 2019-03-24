package com.zr.service;

import com.zr.dao.UserDao;
import com.zr.dao.daoImpl.UserDaoImpl;
import com.zr.entity.User;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 7:55
 */
public class UserService {
    private UserDao dao=new UserDaoImpl();
    public User queryOne(String username){
        return dao.queryOne(username);
    }
}

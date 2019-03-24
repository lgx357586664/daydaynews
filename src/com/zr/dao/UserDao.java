package com.zr.dao;

import com.zr.entity.User;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 7:48
 */
public interface UserDao {
    public User queryOne(String username);
}

package com.zr.dao.daoImpl;

import com.zr.dao.UserDao;
import com.zr.entity.User;
import com.zr.framework.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import sun.dc.pr.PRError;

import java.sql.SQLException;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 7:49
 */
public class UserDaoImpl implements UserDao {
    private QueryRunner qr=new QueryRunner();
    @Override
    public User queryOne(String username) {
        String  sql="select * from user where username = ?";
        try {
            User user = qr.query(JdbcUtils.getConnection(), sql, new BeanHandler<>(User.class), username);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return null;
    }
}

package com.zr.dao.daoImpl;

import com.zr.dao.NewsTypeDao;
import com.zr.entity.Comment;
import com.zr.entity.NewsType;
import com.zr.entity.PageBean;
import com.zr.framework.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public class NewsTypeDaoImpl implements NewsTypeDao {
    private QueryRunner qr=new QueryRunner();
    @Override
    public List<NewsType> findAll() {
        List<NewsType> list=new ArrayList<>();
        String sql="select *from news_type";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                NewsType newsType=new NewsType(typeId,typeName);
                list.add(newsType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null){
                   rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
        return list;
    }

    @Override
    public NewsType findTypeById(int typeId) {
        String sql="select *from news_type where newsdb.news_type.type_id=?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,typeId);
            rs = ps.executeQuery();
            while (rs.next()){
                String typeName = rs.getString("type_name");
                NewsType newsType=new NewsType(typeId,typeName);
                return newsType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
        return null;
    }

    @Override
    public int addNewsType(NewsType newsType) {
        String sql="insert into news_type (type_name) values(?)";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql, newsType.getTypeName());
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int updateNewsType(NewsType newsType) {
        String sql="update news_type set type_name=? where type_id=?";
        int i = 0;
        try {
            i = qr.update(JdbcUtils.getConnection(), sql, newsType.getTypeName(),newsType.getTypeId());
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int deleteNewsType(int typeId) {
        String sql="delete from news_type  where type_id=?";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql, typeId);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public List<NewsType> queryByPage(PageBean pageBean) {
        String sql="select * from news_type   limit ?,?";
        try {
            List<NewsType> newsTypeList = qr.query(JdbcUtils.getConnection(), sql, new BeanListHandler<>(NewsType.class), pageBean.getIndex(), pageBean.getPageCount());
            return newsTypeList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return null;
    }
}

package com.zr.dao.daoImpl;

import com.zr.dao.LinkDao;
import com.zr.entity.Link;
import com.zr.entity.PageBean;
import com.zr.framework.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public class LinkDaoImpl implements LinkDao {
    QueryRunner qr=new QueryRunner();
    @Override
    public List<Link> findAll() {
        List<Link> list =new ArrayList<>();
        String sql="select * from link order by link_order";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int linkId = rs.getInt("link_id");
                String linkName = rs.getString("link_name");
                String email = rs.getString("email");
                String linkUrl = rs.getString("link_url");
                int linkOrder = rs.getInt("link_order");
                Link link =  new Link(linkId,linkName,email,linkUrl,linkOrder);
                list.add(link);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null)
                    rs.close();
                if(ps!=null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
        return  list;
    }

    @Override
    public int  getCount() {
       String sql="select count(*) count from link";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int count = rs.getInt("count");
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null)
                    rs.close();
                if(ps!=null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public List<Link> queryLinkPage(PageBean pageBean) {
        String sql="select * from newsdb.link order by link_order limit ?,?";
        List<Link> list =new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBean.getIndex());
            ps.setInt(2,pageBean.getPageCount());
            rs = ps.executeQuery();
            while (rs.next()){
                int linkId = rs.getInt("link_id");
                String linkName = rs.getString("link_name");
                String email = rs.getString("email");
                String linkUrl = rs.getString("link_url");
                int linkOrder = rs.getInt("link_order");
                Link link =  new Link(linkId,linkName,email,linkUrl,linkOrder);
                list.add(link);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null)
                    rs.close();
                if(ps!=null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
        return list;
    }

    @Override
    public int addLink(Link link) {
        String sql="insert into link (link_name,email,link_url,link_order) values(?,?,?,?)";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql,
                    link.getLinkName(), link.getEmail(), link.getLinkUrl(), link.getLinkOrder());
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int update(Link link) {
        String sql="update link set link_name=?,email=?,link_url=?,link_order=? where link_id=?";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql,
                    link.getLinkName(), link.getEmail(), link.getLinkUrl(), link.getLinkOrder(),link.getLinkId());
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int delete(int linkId) {
        String sql="delete from link where link_id=?";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql, linkId);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public Link queryOne(int linkId) {
        String sql="select * from link where link_id=?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,linkId);
            rs = ps.executeQuery();
            while (rs.next()){
                String linkName = rs.getString("link_name");
                String email = rs.getString("email");
                String linkUrl = rs.getString("link_url");
                int linkOrder = rs.getInt("link_order");
                Link link =  new Link(linkId,linkName,email,linkUrl,linkOrder);
                return link;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null)
                    rs.close();
                if(ps!=null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
        return  null;
    }

}

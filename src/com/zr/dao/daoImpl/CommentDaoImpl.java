package com.zr.dao.daoImpl;

import com.zr.dao.CommentDao;
import com.zr.entity.Comment;
import com.zr.entity.PageBean;
import com.zr.framework.DataSourceUtil;
import com.zr.framework.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 1:48
 */
public class CommentDaoImpl implements CommentDao {
    private QueryRunner qr=new QueryRunner();
    @Override
    public int addComment(Comment comment) {
       String sql="insert into comment (news_id,content,ip_addr,comment_date) values(?,?,?,?)";
       Object[] objs={comment.getNewsId(),comment.getContent(),comment.getIpAddr(),comment.getCommentDate()};
        try {
            int i = qr.update(JdbcUtils.getConnection(),sql, objs);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public List<Comment> queryByNewsId(int newsId) {
        String sql="select * from comment where news_id=? order by comment_date desc";
        try {
            List<Comment> commentList = qr.query(JdbcUtils.getConnection(),sql, new BeanListHandler<>(Comment.class), newsId);
            return commentList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return null;
    }

    @Override
    public int getCount() {
        String sql="select count(*)count from comment ";
        try {
            Number count = qr.query(JdbcUtils.getConnection(), sql, new ScalarHandler<>());
            return count.intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public List<Comment> findAll() {
        String sql="select c.*,n.title title from comment c,news n where c.news_id=n.news_id";
        try {
            List<Comment> commentList = qr.query(JdbcUtils.getConnection(), sql, new BeanListHandler<>(Comment.class));
            return commentList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return null;
    }


    @Override
    public int deleteComment(int cId) {
        String sql="delete from comment where c_id=?";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql, cId);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int deleteComments(int newsId) {
        String sql="delete from comment where news_id=?";
        try {
            int i = qr.update(JdbcUtils.getConnection(), sql, newsId);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public List<Comment> queryByPage(PageBean pageBean) {
        String sql="select c.*,n.title title from comment c,news n where c.news_id=n.news_id  limit ?,?";
        try {
            List<Comment> commentList = qr.query(JdbcUtils.getConnection(), sql, new BeanListHandler<>(Comment.class), pageBean.getIndex(), pageBean.getPageCount());
            return commentList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return null;
    }

}

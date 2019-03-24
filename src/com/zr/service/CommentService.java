package com.zr.service;

import com.zr.dao.CommentDao;
import com.zr.dao.daoImpl.CommentDaoImpl;
import com.zr.entity.Comment;
import com.zr.entity.PageBean;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 3:50
 */
public class CommentService {
    private CommentDao dao = new CommentDaoImpl();

    public int addComment(Comment comment) {
        return dao.addComment(comment);
    }

    public List<Comment> queryByNewsId(int newsId) {
        return dao.queryByNewsId(newsId);
    }

    public int getCount(){return dao.getCount();}

    public List<Comment> findAll(){return dao.findAll();}

    public int deleteComment(int cId){
        return dao.deleteComment(cId);
    }
    public int deleteComments(int newsId){return dao.deleteComments(newsId);}

    public List<Comment> queryByPage(PageBean pageBean){return dao.queryByPage(pageBean);}
}

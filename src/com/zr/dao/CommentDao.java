package com.zr.dao;

import com.zr.entity.Comment;
import com.zr.entity.NewsType;
import com.zr.entity.PageBean;


import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 1:46
 */
public interface CommentDao {
    public int addComment(Comment comment);
    public List<Comment> queryByNewsId(int newsId);
    public int getCount();
    public List<Comment> findAll();
    public int deleteComment(int cId);
    public int deleteComments(int newsId);
    public List<Comment> queryByPage(PageBean pageBean);
}

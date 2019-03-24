package com.zr.dao;

import com.zr.entity.NewsType;
import com.zr.entity.PageBean;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public interface NewsTypeDao {
    public List<NewsType> findAll();
    public NewsType findTypeById(int typeId);
    public int addNewsType(NewsType newsType);
    public int updateNewsType(NewsType newsType);
    public int deleteNewsType(int typeId);
    public List<NewsType> queryByPage(PageBean pageBean);
}

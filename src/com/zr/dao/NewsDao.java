package com.zr.dao;

import com.zr.entity.News;
import com.zr.entity.PageBean;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public interface NewsDao {
    public List<News> findAll();
    public List<News> findImageNews();
    public News findHeadline();
    public List<News> findNewNews();
    public List<News> findHotNews();
    public List<News> findNewsByType(int typeId);
    public List<News> findClickNews();

    //通过类型查询新闻集合  获取每页的数据
    public List<News> findNewsListByType(int typeId, PageBean pageBean);
    //获取数据总条数
    public int findNewsCountByType(int typeId);
    // 联查写typeName的作用是写导航栏需要    通过newsId查询新闻
    public News findNewsById(int newsId);

    public News findUpNewsById(int newsId);
    public News findDownNewsById(int newsId);
    //访问次数
    public void addClick(int newsId);
    public List<News> queryByPage(PageBean pageBean);

    public int deleteNews(int newsId);

    public  int updateNews(News news);

    public  int addNews(News news);
}

package com.zr.dao.daoImpl;

import com.zr.dao.NewsDao;
import com.zr.entity.News;
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
public class NewsDaoImpl implements NewsDao {
    private QueryRunner queryRunner=new QueryRunner();
    @Override
    public List<News> findAll() {
        String sql ="select * from news order by publish_date desc";
        return getNewsList(sql);
    }

    @Override
    public List<News> findImageNews() {
        String sql ="select * from news where is_image=1 order by publish_date desc limit 0,4";
        return getNewsList(sql);
    }

    @Override
    public News findHeadline() {
        String sql ="select * from news order by publish_date desc";
        return getNewsList(sql).get(0);
    }

    @Override
    public List<News> findNewNews() {
        String sql ="select * from news  order by publish_date desc limit 0,8";
        return getNewsList(sql);
    }

    @Override
    public List<News> findHotNews() {
        String sql ="select * from news where is_hot=1 order by publish_date desc limit 0,8";
        return getNewsList(sql);
    }

    @Override
    public List<News> findNewsByType(int typeId) {
        String sql ="select * from news where type_id="+typeId+" order by publish_date desc limit 0,8";
        return getNewsList(sql);
    }


    @Override
    public List<News> findClickNews() {
        String sql ="select * from news  order by click desc limit 0,8";
        return getNewsList(sql);
    }

    /**
     * 通过类型查询新闻集合  获取每页的数据
     * @param typeId
     * @param pageBean
     * @return
     */
    @Override
    public List<News> findNewsListByType(int typeId, PageBean pageBean) {
        String sql ="select * from news where type_id="+typeId+" order by publish_date desc limit "+pageBean.getIndex()+","+pageBean.getPageCount();
        return getNewsList(sql);
    }

    /**
     * 获取新闻总条数
     * @param typeId
     * @return
     */
    @Override
    public int findNewsCountByType(int typeId) {
        String sql="select count(*)  count from news where type_id = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,typeId);
            rs = ps.executeQuery();
            while (rs.next()){
                int count = rs.getInt("count");
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return 0;
    }

    /**
     * 联查写typeName的作用是写导航栏需要
     * 通过newsId查询新闻
     * @param newsId
     * @return
     */
    @Override
    public News findNewsById(int newsId) {
        String sql ="select n.*,t.type_name from newsdb.news n,newsdb.news_type t  where t.type_id=n.type_id and news_id=?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,newsId);
            rs = ps.executeQuery();
            while (rs.next()){
                newsId = rs.getInt("news_id");
                String title = rs.getString("title");
                String context = rs.getString("context");
                String author = rs.getString("author");
                int typeId = rs.getInt("type_id");
                Date publishDate = rs.getDate("publish_date");
                int isImage = rs.getInt("is_image");
                String imageUrl = rs.getString("image_url");
                int click = rs.getInt("click");
                int isHot = rs.getInt("is_hot");
                String typeName = rs.getString("type_name");
                News news =new News(newsId,title,context,author,typeId,publishDate,isImage,imageUrl,click,isHot,typeName);
                return news;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    /**
     * 查询下一条 >升序 通过newsId查询
     * @param newsId
     * @return
     */
    @Override
    public News findUpNewsById(int newsId) {
        String sql ="select * from newsdb.news  where publish_date>(select publish_date from newsdb.news where news_id=?) order by publish_date asc limit 1";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        News news =new News();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,newsId);
            rs = ps.executeQuery();

            while (rs.next()){
                newsId = rs.getInt("news_id");
                String title = rs.getString("title");
                String context = rs.getString("context");
                String author = rs.getString("author");
                int typeId = rs.getInt("type_id");
                Date publishDate = rs.getDate("publish_date");
                int isImage = rs.getInt("is_image");
                String imageUrl = rs.getString("image_url");
                int click = rs.getInt("click");
                int isHot = rs.getInt("is_hot");
                news =new News(newsId,title,context,author,typeId,publishDate,isImage,imageUrl,click,isHot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return news;
    }

    /**
     * 查询下一条 <降序   通过newsId查询
     * @param newsId
     * @return
     */
    @Override
    public News findDownNewsById(int newsId) {
        String sql ="select * from newsdb.news  where publish_date<(select publish_date from newsdb.news where news_id=?) order by publish_date desc limit 1";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        News news =new News();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,newsId);
            rs = ps.executeQuery();
            while (rs.next()){
                newsId = rs.getInt("news_id");
                String title = rs.getString("title");
                String context = rs.getString("context");
                String author = rs.getString("author");
                int typeId = rs.getInt("type_id");
                Date publishDate = rs.getDate("publish_date");
                int isImage = rs.getInt("is_image");
                String imageUrl = rs.getString("image_url");
                int click = rs.getInt("click");
                int isHot = rs.getInt("is_hot");
                news =new News(newsId,title,context,author,typeId,publishDate,isImage,imageUrl,click,isHot);
                return news;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return news;
    }

    /**
     * 访问次数设置
     * @param newsId
     */
    @Override
    public void addClick(int newsId) {
        String sql="update news set click=click+1 where news_id=?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,newsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtils.close();
        }
    }
    @Override
    public List<News> queryByPage(PageBean pageBean) {
        String sql="select * from news n,news_type t where n.type_id=t.type_id limit ?,?";
        try {
            List<News> newsList = queryRunner.query(JdbcUtils.getConnection(), sql, new BeanListHandler<>(News.class), pageBean.getIndex(), pageBean.getPageCount());
            return newsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return null;
    }

    @Override
    public int deleteNews(int newsId) {
        String sql="delete from news where news_id=?";
        try {
            int i = queryRunner.update(JdbcUtils.getConnection(), sql, newsId);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int updateNews(News news) {
        String sql= "update news set title=?, context=?, author=?, type_id=?, publish_date=?," +
                "is_image=?, image_url=?,  is_hot=? where news_id=?";
        try {
            Object[] obj={news.getTitle(), news.getContext(), news.getAuthor(), news.getTypeId(),
                    news.getPublishDate(), news.getIsImage(), news.getImageUrl(),
                    news.getIsHot(), news.getNewsId()};
            int i = queryRunner.update(JdbcUtils.getConnection(), sql,obj );
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }

    @Override
    public int addNews(News news) {
        String sql="insert into news (title, context, author, type_id, publish_date, is_image, image_url, click, is_hot)" +
                "values(?,?,?,?,?,?,?,?,?)";
        try {
            Object[] obj={news.getTitle(), news.getContext(), news.getAuthor(), news.getTypeId(),
                    news.getPublishDate(), news.getIsImage(), news.getImageUrl(), news.getClick(),
                    news.getIsHot()};
            int i = queryRunner.update(JdbcUtils.getConnection(), sql,obj );
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close();
        }
        return 0;
    }
    /**
     * 对获取新闻集合分装
     * @param sql
     * @return
     */
    public List<News> getNewsList(String sql) {
        List<News> list=new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int newsId = rs.getInt("news_id");
                String title = rs.getString("title");
                String context = rs.getString("context");
                String author = rs.getString("author");
                int typeId = rs.getInt("type_id");
                Date publishDate = rs.getDate("publish_date");
                int isImage = rs.getInt("is_image");
                String imageUrl = rs.getString("image_url");
                int click = rs.getInt("click");
                int isHot = rs.getInt("is_hot");
                News news =new News(newsId,title,context,author,typeId,publishDate,isImage,imageUrl,click,isHot);
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
}

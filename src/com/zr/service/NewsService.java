package com.zr.service;

import com.zr.dao.NewsDao;
import com.zr.dao.daoImpl.NewsDaoImpl;
import com.zr.entity.News;
import com.zr.entity.NewsType;
import com.zr.entity.PageBean;
import com.zr.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-11 下午 3:12
 */
public class NewsService {
    private NewsDao dao=new NewsDaoImpl();

    public List<News> findImageNews(){
        return dao.findImageNews();
    }
    public News findHeadline(){
        News headline = dao.findHeadline();
        String context = headline.getContext();
        String text = StringUtil.html2Text(context);
        if(text.length()>=40){
            text = text.substring(0, 40);
        }
        headline.setContext(text);
        return  headline;
    }
    public List<News> findNewNews(){
        return  dao.findNewNews();
    }
    public List<News> findHotNews(){
        return  dao.findHotNews();
    }
    public List<News> findClickNews(){
        return  dao.findClickNews();
    }
    public List<List<News>> findNewsByType(){
        List<List<News>> listsnewsLists =  new ArrayList<>();
        NewsTypeService service =new NewsTypeService();
        List<NewsType> typeList = service.findAll();
        for (NewsType newsType:typeList) {
            List<News> newsList = dao.findNewsByType(newsType.getTypeId());
            listsnewsLists.add(newsList);
        }
        return listsnewsLists;
    }
    public List<News> findNewsListPage(int typeId, PageBean pageBean){
        List<News> newsList = dao.findNewsListByType(typeId, pageBean);
        return newsList;
    }
    public int findNewsCountByType(int typeId){
        return dao.findNewsCountByType(typeId);
    }
    public News findNewsById(int newsId){
        dao.addClick(newsId);
        return dao.findNewsById(newsId);
    }
    public News findNewsByNewsId(int newsId){
        return dao.findNewsById(newsId);
    }
    public List<News> getNewsUpAndDown(int newsId){
        List<News> list=new ArrayList<>();
        News upNews = dao.findUpNewsById(newsId);
        News downNews = dao.findDownNewsById(newsId);
        list.add(upNews);
        list.add(downNews);
        return list;
    }
    public List<News> findAll(){return dao.findAll();}

    public List<News> queryByPage(PageBean pageBean){return dao.queryByPage(pageBean);}

    public int deleteNews(int newsId){return dao.deleteNews(newsId);}

    public  int updateNews(News news){return dao.updateNews(news);}

    public  int addNews(News news){return dao.addNews(news);}
}

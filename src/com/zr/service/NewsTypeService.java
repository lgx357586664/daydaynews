package com.zr.service;

import com.zr.dao.NewsTypeDao;
import com.zr.dao.daoImpl.NewsTypeDaoImpl;
import com.zr.entity.NewsType;
import com.zr.entity.PageBean;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/11 
 */
public class NewsTypeService {
    private NewsTypeDao dao=new NewsTypeDaoImpl();
    public List<NewsType> findAll(){
        return dao.findAll();
    }
    public NewsType findTypeById(int typeId){
        return dao.findTypeById(typeId);
    }
    public int addNewsType(NewsType newsType){return dao.addNewsType(newsType);}
    public int updateNewsType(NewsType newsType){return dao.updateNewsType(newsType);}
    public int deleteNewsType(int typeId){return dao.deleteNewsType(typeId);}
    public List<NewsType> queryByPage(PageBean pageBean){return dao.queryByPage(pageBean);}
}

package com.zr.servlet;

import com.zr.entity.Link;
import com.zr.entity.News;
import com.zr.entity.NewsType;
import com.zr.service.LinkService;
import com.zr.service.NewsService;
import com.zr.service.NewsTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/14
 */
@WebServlet(name = "GoIndexServlet",urlPatterns = "/goIndex")
public class GoIndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsService newsService = new NewsService();
        NewsTypeService typeService = new NewsTypeService();
        LinkService linkService = new LinkService();

        List<NewsType> typeList = typeService.findAll();
        request.setAttribute("typeList",typeList);
        List<News> imageNewsList = newsService.findImageNews();
        request.setAttribute("imageNewsList",imageNewsList);
        News headline = newsService.findHeadline();
        request.setAttribute("headline",headline);
        List<News> newNewsList = newsService.findNewNews();
        request.setAttribute("newNewsList",newNewsList);
        List<News> hotNewsList = newsService.findHotNews();
        request.setAttribute("hotNewsList",hotNewsList);
        List<List<News>> newsByTypeList = newsService.findNewsByType();
        request.setAttribute("newsByTypeList",newsByTypeList);
        List<Link> linkList = linkService.findAll();
        request.setAttribute("linkList",linkList);
        request.getRequestDispatcher("index.jsp").forward(request,response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

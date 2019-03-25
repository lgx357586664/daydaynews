package com.zr.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.zr.entity.Comment;
import com.zr.entity.Link;
import com.zr.entity.News;
import com.zr.entity.NewsType;
import com.zr.service.CommentService;
import com.zr.service.LinkService;
import com.zr.service.NewsService;
import com.zr.service.NewsTypeService;
import com.zr.util.DateUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: LiGX
 * @Date: 2019/3/24
 */
@WebServlet(name="InitServlet",urlPatterns = "/InitServlet",loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    private NewsTypeService service = new NewsTypeService();
    private NewsService newsService = new NewsService();
    @Override
    public void init() throws ServletException {
        ServletContext application = this.getServletContext();
        // 新闻类别加载
        List<NewsType> typeList = service.findAll();
        application.setAttribute("typeList", typeList);

        // 最新新闻
        List<News> newNewsList = newsService.findNewNews();
        application.setAttribute("newNewsList", newNewsList);

        // 热门新闻
        List<News> clickNewsList = newsService.findClickNews();
        application.setAttribute("clickNewsList", clickNewsList);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 新闻类别加载
        ServletContext application = this.getServletContext();
        // 新闻类别加载
        List<NewsType> typeList = service.findAll();
        application.setAttribute("typeList", typeList);

        // 最新新闻
        List<News> newNewsList = newsService.findNewNews();
        application.setAttribute("newNewsList", newNewsList);

        // 热门新闻
        List<News> clickNewsList = newsService.findClickNews();
        application.setAttribute("clickNewsList", clickNewsList);
        loginInfo(request,response);
        PrintWriter out = response.getWriter();

        response.sendRedirect(request.getContextPath()+"/background/commons/backgroundIndex.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    public void setNews() {

    }
    private void loginInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建一次会话， 如果有会话就直接使用
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        NewsService newsService=new NewsService();
        List<News> newsList = newsService.findAll();

        NewsTypeService newsTypeService=new NewsTypeService();
        List<NewsType> newsTypeList = newsTypeService.findAll();

        LinkService linkService=new LinkService();
        List<Link> linkList = linkService.findAll();

        CommentService commentService=new CommentService();
        List<Comment> commentList = commentService.findAll();

        int sum=0;
        for (News news:newsList) {
            sum+=news.getClick();
        }
        Set set=new HashSet();
        for (Comment comment:commentList) {
            String ipAddr = comment.getIpAddr();
            set.add(ipAddr);
        }
        session.setAttribute("username",username);
        session.setAttribute("newsListCount",newsList.size());
        session.setAttribute("newsTypeListCount",newsTypeList.size());
        session.setAttribute("commentListCount",commentList.size());
        session.setAttribute("linkListCount",linkList.size());
        session.setAttribute("clickCount",sum);
        session.setAttribute("ipCount",set.size());

    }
}
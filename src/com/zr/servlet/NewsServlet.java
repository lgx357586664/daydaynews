package com.zr.servlet;

import com.alibaba.fastjson.JSONObject;
import com.zr.entity.Comment;
import com.zr.entity.News;
import com.zr.entity.NewsType;
import com.zr.entity.PageBean;
import com.zr.service.CommentService;
import com.zr.service.NewsService;
import com.zr.service.NewsTypeService;
import com.zr.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/17
 */
@WebServlet(name = "NewsServlet",urlPatterns = "/NewsServlet",initParams = {@WebInitParam(name="pageCount",value = "10")})
public class NewsServlet extends HttpServlet {

    private NewsService service=new NewsService();
    private NewsTypeService newsTypeService = new NewsTypeService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
        if("query".equals(action)){
            query(request, response);
        }else if("queryOne".equals(action)){
            queryOne(request, response);
        }else if("queryPage".equals(action)){
            queryPage(request, response);
        }else if("delete".equals(action)){
            delete(request, response);
        } else if("deleteAll".equals(action)){
            deleteAll(request, response);
        }else if("toadd".equals(action)){
            toadd(request, response);
        }else if("add".equals(action)){
            add(request, response);
        }else if("queryOneBack".equals(action)){
            queryOneBack(request, response);
        }else if("update".equals(action)){
            update(request, response);
        }
    }
    protected void queryOneBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newsId = request.getParameter("newsId");
        News news = service.findNewsByNewsId(Integer.parseInt(newsId));
        List<NewsType> typeList = newsTypeService.findAll();
        request.setAttribute("news",news);
        request.setAttribute("typeList",typeList);
        request.getRequestDispatcher("/background/news/newsAdd.jsp").forward(request,response);

    }
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newsId = request.getParameter("newsId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String typeId = request.getParameter("typeId");
        String hot = request.getParameter("hot");
        String isImage = request.getParameter("isImage");
        String publishDate = request.getParameter("publishDate");
        String content = request.getParameter("content");


        int ishot=0;
        if(hot!=null){
            ishot=1;
        }
        int isImg=0;
        if(isImage!=null){
            isImg=1;
        }

        String image = (String)request.getSession().getAttribute("image");
        request.getSession().removeAttribute("image");
        News news = new News(Integer.parseInt(newsId),title,content,author,
                Integer.parseInt(typeId), DateUtil.formatString(publishDate,"yyyy-MM-dd HH:mm:ss")
                ,isImg, image!=null?image:"",0,ishot);

        int i = service.updateNews(news);
        response.getWriter().print(i);
    }
    protected void toadd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<NewsType> typeList = newsTypeService.findAll();
        request.setAttribute("typeList",typeList);
        request.getRequestDispatcher("/background/news/newsAdd.jsp").forward(request,response);
    }
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String typeId = request.getParameter("typeId");
        String hot = request.getParameter("hot");
        String isImage = request.getParameter("isImage");
        String publishDate = request.getParameter("publishDate");
        String content = request.getParameter("content");

        int ishot=0;
        if(hot!=null){
            ishot=1;
        }
        int isImg=0;
        if(isImage!=null){
            isImg=1;
        }
        String image = (String)request.getSession().getAttribute("image");
        request.getSession().removeAttribute("image");
        News news=new News(title,content,author,Integer.parseInt(typeId),
                DateUtil.formatString(publishDate,"yyyy-MM-dd HH:mm:ss"),
                isImg,image!=null?image:"",0,ishot);
        int i = service.addNews(news);
        response.getWriter().print(i);

    }
    protected void deleteAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("ids");
        String[] array = ids.split(",");
        int sum=0;
        for (String newsId: array) {
            int i = service.deleteNews(Integer.parseInt(newsId));
            sum+=i;
        }
        response.getWriter().print(sum);

    }

    /**
     * 后台分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void queryPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page"); //页码
        String limit = request.getParameter("limit"); // 每页条数
        PageBean pageBean=new PageBean();
        pageBean.setPageIndex(Integer.parseInt(page));
        pageBean.setPageCount(Integer.parseInt(limit));
        pageBean.setCount(service.findAll().size());
        List<News> newsList = service.queryByPage(pageBean);
        JSONObject jsonObject = JsonUtil.getJsonObject(newsList, pageBean);
        response.getWriter().print(jsonObject);
    }
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newsId = request.getParameter("newsId");
        int i = service.deleteNews(Integer.parseInt(newsId));
        response.getWriter().print(i);
    }
    private void queryOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newsId = request.getParameter("newsId");
        News news = service.findNewsById(Integer.parseInt(newsId));
        //导航栏
        String newsNav = NavUtil.getNavNewsById(news.getTypeId(), news.getTypeName(), news.getTitle());
        //分篇查询
        List<News> newsUpAndDown = service.getNewsUpAndDown(Integer.parseInt(newsId));
        String upAndDown = NewsUpAndDownUtil.getNewsUpAndDown(newsUpAndDown);
        CommentService commentService=new CommentService();
        //评论查询
        List<Comment> commentList = commentService.queryByNewsId(Integer.parseInt(newsId));
        System.out.println(commentList);
        request.setAttribute("commentList",commentList);
        request.setAttribute("newsNav",newsNav);
        request.setAttribute("news",news);
        request.setAttribute("newsUpAndDown",upAndDown);
        //每条新闻mainJSP用newsInfo.jsp，用的都是一个模板newsModel.jsp
        request.setAttribute("mainJsp","newsInfo.jsp");
        request.getRequestDispatcher("/foreground/newsModel.jsp").forward(request,response);
    }

    /**
     * 前台分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        String pageIndex = request.getParameter("pageIndex");
        PageBean pageBean =new PageBean();
        //刚开始获取pageIndex为空，如果不为空，获取的是你读到的pageIndex
        if(!StringUtil.isEmpty(pageIndex)){
            //获取数据索引
            pageBean.setPageIndex(Integer.parseInt(pageIndex));
        }
        String pageCount = getInitParameter("pageCount");
        //获取每页条数
        pageBean.setPageCount(Integer.parseInt(pageCount));

        //获取总条数
        pageBean.setCount(service.findNewsCountByType(Integer.parseInt(typeId)));
        //一页的数据10条
        List<News> newsList = service.findNewsListPage(Integer.parseInt(typeId), pageBean);

        NewsTypeService typeService=new NewsTypeService();
        NewsType newsType = typeService.findTypeById(Integer.parseInt(typeId));
        // 导航条
        String newsListNav = NavUtil.getNavNewsListByType(newsType);
        //分页
        String newListPager = PageUtil.getPager(Integer.parseInt(typeId), pageBean);

        request.setAttribute("newListPager",newListPager);
        request.setAttribute("newsListNav",newsListNav);
        request.setAttribute("newsList",newsList);
        //每类新闻mainJSP用newsList.jsp，用的都是一个模板newsModel.jsp
        request.setAttribute("mainJsp","newsList.jsp");
        request.getRequestDispatcher("/foreground/newsModel.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

package com.zr.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.entity.Comment;
import com.zr.entity.NewsType;
import com.zr.entity.PageBean;
import com.zr.entity.ResultCode;
import com.zr.service.NewsService;
import com.zr.service.NewsTypeService;
import com.zr.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-21 上午 10:52
 */
@WebServlet(name = "NewsTypeServlet",urlPatterns = "/NewsTypeServlet")
public class NewsTypeServlet extends HttpServlet {
    private NewsTypeService service=new NewsTypeService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //防止页面乱码设置字符集
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
        if("query".equals(action)){
            query(request,response);
        }else if("add".equals(action)){
            add(request,response);
        }else if("update".equals(action)){
            update(request,response);
        }else if("delete".equals(action)){
            delete(request,response);
        }else if("queryOne".equals(action)){
            queryOne(request,response);
        }else if("deleteAll".equals(action)){
            deleteAll(request,response);
        }
    }
    protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page"); //页码
        String limit = request.getParameter("limit"); // 每页条数
        PageBean pageBean=new PageBean();
        pageBean.setPageIndex(Integer.parseInt(page));
        pageBean.setPageCount(Integer.parseInt(limit));
        pageBean.setCount(service.findAll().size());
        List<NewsType> newsTypeList = service.queryByPage(pageBean);
        JSONObject jsonObject = JsonUtil.getJsonObject(newsTypeList, pageBean);
        response.getWriter().print(jsonObject);
    }
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeName = request.getParameter("typeName");
        NewsType newsType=new NewsType(typeName);
        int i = service.addNewsType(newsType);
        response.getWriter().print(""+i);
    }
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        String typeName = request.getParameter("typeName");
        NewsType newsType=new NewsType(Integer.parseInt(typeId),typeName);
        int i = service.updateNewsType(newsType);
        response.getWriter().print(""+i);
    }
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        int id = Integer.parseInt(typeId);
        ResultCode resultCode=new ResultCode();
        NewsService newsService=new NewsService();
        int newsCount = newsService.findNewsCountByType(id);
        if(newsCount==0){
            int i = service.deleteNewsType(id);
            if(i>0){
                resultCode.setCode("2001");
                resultCode.setMessage("新闻类型删除成功");
            }else {
                resultCode.setCode("2002");
                resultCode.setMessage("新闻类型已删除或不存在");
            }
        }else {
            resultCode.setCode("2003");
            resultCode.setMessage("新闻类型下有新闻，不可删除");
        }
        String jsonString = JSONObject.toJSONString(resultCode);
        response.getWriter().print(jsonString);

    }
    protected void deleteAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("ids");
        String[] array = ids.split(",");
        int sum=0;
        for (String cId:array) {
            int i = service.deleteNewsType(Integer.parseInt(cId));
            sum+=i;
        }
        response.getWriter().print(sum);
    }
    protected void queryOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        NewsType newsType = service.findTypeById(Integer.parseInt(typeId));
        request.setAttribute("newsType",newsType);
        request.getRequestDispatcher("/background/newsType/newsTypeUpdate.jsp").forward(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}

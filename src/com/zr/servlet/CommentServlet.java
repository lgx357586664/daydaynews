package com.zr.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.entity.Comment;
import com.zr.entity.PageBean;
import com.zr.service.CommentService;
import com.zr.util.JsonUtil;
import com.zr.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 3:53
 */
@WebServlet(name = "CommentServlet",urlPatterns = "/CommentServlet",initParams = {@WebInitParam(name ="pageCount" ,value = "10")})
public class CommentServlet extends HttpServlet {
    private CommentService commentService=new CommentService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
        if("add".equals(action)){
            add(request, response);
        }else if("query".equals(action)){
            query(request, response);
        }else if("delete".equals(action)){
            delete(request, response);
        }else if("deleteAll".equals(action)){
            deleteAll(request, response);
        }

    }
    protected void deleteAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("ids");
        String[] array = ids.split(",");
        int sum=0;
        for (String cId:array) {
            int i = commentService.deleteComment(Integer.parseInt(cId));
            sum+=i;
        }
        response.getWriter().print(sum);
    }
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cId = request.getParameter("cId");
        int i = commentService.deleteComment(Integer.parseInt(cId));
        response.getWriter().print(i);
    }
    protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page"); //页码
        String limit = request.getParameter("limit"); // 每页条数
        PageBean pageBean=new PageBean();
        pageBean.setPageIndex(Integer.parseInt(page));
        pageBean.setPageCount(Integer.parseInt(limit));
        pageBean.setCount(commentService.getCount());
        List<Comment> commentList = commentService.queryByPage(pageBean);
        JSONObject jsonObject = JsonUtil.getJsonObject(commentList, pageBean);
        response.getWriter().print(jsonObject);
    }
    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newsId = request.getParameter("newsId");
        String content = request.getParameter("content");
        String ipAddr = request.getRemoteAddr();
        Comment comment=new Comment(Integer.parseInt(newsId),content,ipAddr,new Date(System.currentTimeMillis()));
        CommentService commentService =new CommentService();
        int i = commentService.addComment(comment);
        if(i>0){
            // 将对象转为json字符串
            String jsonString = JSONObject.toJSONString(comment);
            response.getWriter().print(jsonString);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}

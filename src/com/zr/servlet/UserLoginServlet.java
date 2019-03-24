package com.zr.servlet;

import com.alibaba.fastjson.JSONObject;
import com.zr.entity.*;
import com.zr.service.*;
import com.zr.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: LiGX
 * @Date: 2019/3/19
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService userService =new UserService();
        User user = userService.queryOne(username);
        ResultCode rc = null;
        if(user!=null){
            //用户名正确
            if(user.getPassword().equals(password)){
                //密码正确
                rc =new ResultCode("1001","登陆成功");
                request.getSession().setAttribute("currentDate",DateUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss"));
                loginInfo(request,response);
            }else {
                //密码错误
                rc =new ResultCode("1002","你是不是不知道密码都不知道？！！");
            }
        }else {
            rc =new ResultCode("1003","你是不是傻，连用户名都不知道？！！");
        }
        response.getWriter().print(JSONObject.toJSONString(rc));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    protected void loginInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        session.setAttribute("username",username);
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

        session.setAttribute("newsListCount",newsList.size());
        session.setAttribute("newsTypeListCount",newsTypeList.size());
        session.setAttribute("commentListCount",commentList.size());
        session.setAttribute("linkListCount",linkList.size());
        session.setAttribute("clickCount",sum);
        session.setAttribute("ipCount",set.size());

    }
}

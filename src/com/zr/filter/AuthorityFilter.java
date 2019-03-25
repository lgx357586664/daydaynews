package com.zr.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: LiGX
 * @Date: 2019-03-25 上午 11:20
 */
@WebFilter(filterName = "AuthorityFilter",urlPatterns = "/*")
public class AuthorityFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器来了");


    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //请求路径没有ip和端口号的
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        System.out.println("ip:"+ip);
        if(uri.contains("background")){// 如果访问后台不放行

            if(uri.contains("login")){
                chain.doFilter(req, resp);//放行
            }else {//没有去登录的页面
                // 如果有就使用，没有就创建
                HttpSession session = request.getSession();
                Object username = session.getAttribute("username");
                if(username!=null){
                    chain.doFilter(req, resp);//放行
                }else { //没有登录不放行
                    //重定向 去登陆
                    response.sendRedirect(request.getContextPath()+"/background/commons/login.jsp");
                }
            }
        }else {
            chain.doFilter(req, resp);//放行
        }
    }
    public void destroy() {

        System.out.println("过滤器走了");
    }


}

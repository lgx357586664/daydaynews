package com.zr.util;

import com.zr.entity.News;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-18 下午 6:28
 */
public class NewsUpAndDownUtil {
    /**
     * 在一条新闻的做分页：上下条新闻
     * @param list
     * @return
     */
    public static String getNewsUpAndDown(List<News> list){
        StringBuffer sb=new StringBuffer();
        sb.append("<ul class=\"pager\">");
        News upNews = list.get(0);
        News downNews = list.get(1);
        if(upNews.getNewsId()==0){
            sb.append("<li>上一篇：到顶了</li>");
        }else {
            sb.append("<li>上一篇: <a href='NewsServlet?action=queryOne&newsId=" + upNews.getNewsId() + "'>" + upNews.getTitle() + "</a></li>");
        }
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        if(downNews.getNewsId()==0){
            sb.append("<li>下一篇：到底了</li>");
        }else {
            sb.append("<li>下一篇: <a href='NewsServlet?action=queryOne&newsId=" + downNews.getNewsId() + "'>" + downNews.getTitle() + "</a></li>");
        }
        sb.append("</ul>");
        return sb.toString();

    }

}

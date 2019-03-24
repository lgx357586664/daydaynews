package com.zr.util;

import com.zr.entity.NewsType;

/**
 * @author: LiGX
 * @Date: 2019-03-18 上午 11:15
 */
public class NavUtil {
    /**
     * 一个类型的导航栏
     * @param newsType
     * @return
     */
    public static String getNavNewsListByType(NewsType newsType){
        StringBuffer sb =new StringBuffer();
        sb.append("<ul class=\"breadcrumb\">");
        sb.append("<li><a href=\"goIndex\">主页</a></li>");
        sb.append("<li><a href=\"NewsServlet?action=query&typeId="+newsType.getTypeId()+"\">"+newsType.getTypeName()+"</a></li>");
        sb.append("</ul>");
        return sb.toString();
    }

    /**
     * 一条新闻的导航栏
     * @param typeId
     * @param typeName
     * @param title
     * @return
     */
    public static  String getNavNewsById(int typeId,String typeName,String title){
        StringBuffer sb = new StringBuffer("<ul class=\"breadcrumb\">");
        sb.append("<li><a href=\"goIndex\">主页</a></li>");
        sb.append("<li><a href=\"NewsServlet?action=query&typeId="+typeId+"\">"+typeName+"</a></li>");
        sb.append("<li class=\"active\">"+title+"</li>");

        sb.append("</ul>");
        return sb.toString();
    }
}

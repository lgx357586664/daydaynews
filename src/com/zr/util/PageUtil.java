package com.zr.util;

import com.zr.entity.PageBean;

/**
 * @author: LiGX
 * @Date: 2019-03-18 下午 1:09
 */
public class PageUtil {
    /**
     * 一类新闻中做分页
     * @param typeId
     * @param pageBean
     * @return
     */
    public static String getPager(int typeId, PageBean pageBean){
        StringBuffer sb=new StringBuffer();
        sb.append("<ul class=\"pager\">");
        int pageIndex = pageBean.getPageIndex();
        int pages = pageBean.getPages();
        if(pageIndex<=1){
            sb.append("<li><a disabled=\"disabled\">上一页</a></li>");
        }else {
            sb.append("<li><a href=\"NewsServlet?action=query&typeId="+typeId+"&pageIndex="+(pageIndex-1)+"\">上一页</a></li>");
        }
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        if(pageIndex>=pages){
            sb.append("<li><a disabled=\"disabled\">下一页</a></li>");
        }else {
            sb.append("<li><a href=\"NewsServlet?action=query&typeId="+typeId+"&pageIndex="+(pageIndex+1)+"\">下一页</a></li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }
}

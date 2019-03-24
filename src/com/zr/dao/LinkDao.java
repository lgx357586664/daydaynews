package com.zr.dao;

import com.zr.entity.Link;
import com.zr.entity.PageBean;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public interface LinkDao {
    public List<Link> findAll();
    public int getCount();
    public List<Link> queryLinkPage(PageBean pageBean);
    public int addLink(Link link);
    public int update(Link link);
    public int delete(int linkId);
    public Link queryOne(int linkId);
}

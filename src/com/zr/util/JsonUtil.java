package com.zr.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.entity.Comment;
import com.zr.entity.PageBean;

import java.util.List;

/**
 * @author: LiGX
 * @Date: 2019-03-22 上午 10:31
 */
public class JsonUtil {
    public static <T> JSONObject getJsonObject(List<T> list, PageBean pageBean) {

        Object jsonArray = JSONArray.toJSON(list);
        JSONObject array=new JSONObject();
        array.put("code",0);
        array.put("msg","");
        array.put("count",pageBean.getCount());
        array.put("data",jsonArray);
        return array;
    }
}

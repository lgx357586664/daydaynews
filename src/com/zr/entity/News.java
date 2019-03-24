package com.zr.entity;

import java.util.Date;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public class News {
    private int newsId;
    private String title;
    private String context;
    private String author;
    private int typeId;
    private Date publishDate;
    private int isImage;
    private String imageUrl;
    private int click;
    private int isHot;
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setType_name(String typeName) {
        this.typeName = typeName;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNews_id(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setType_id(int typeId) {
        this.typeId = typeId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublish_date(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getIsImage() {
        return isImage;
    }

    public void setIs_image(int isImage) {
        this.isImage = isImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage_url(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIs_hot(int isHot) {
        this.isHot = isHot;
    }

    public News() {
    }

    public News(int newsId, String title, String context, String author, int typeId, Date publishDate, int isImage, String imageUrl, int click, int isHot) {
        this.newsId = newsId;
        this.title = title;
        this.context = context;
        this.author = author;
        this.typeId = typeId;
        this.publishDate = publishDate;
        this.isImage = isImage;
        this.imageUrl = imageUrl;
        this.click = click;
        this.isHot = isHot;
    }

    public News(int newsId, String title, String context, String author, int typeId, Date publishDate, int isImage, String imageUrl, int click, int isHot, String typeName) {
        this.newsId = newsId;
        this.title = title;
        this.context = context;
        this.author = author;
        this.typeId = typeId;
        this.publishDate = publishDate;
        this.isImage = isImage;
        this.imageUrl = imageUrl;
        this.click = click;
        this.isHot = isHot;
        this.typeName = typeName;
    }

    public News(String title, String context, String author, int typeId, Date publishDate, int isImage, String imageUrl, int click, int isHot) {
        this.title = title;
        this.context = context;
        this.author = author;
        this.typeId = typeId;
        this.publishDate = publishDate;
        this.isImage = isImage;
        this.imageUrl = imageUrl;
        this.click = click;
        this.isHot = isHot;
    }
}

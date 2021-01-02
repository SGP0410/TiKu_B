package com.example.tiku_b.bean;

import com.google.gson.annotations.SerializedName;

public class AllNews {

    /**
     * newsid : 1
     * newsType : 时政
     * picture : http://81.70.194.43:8080/mobileA/images/1.jpg
     * abstract : 10月1日，中华人民共和国成立71周年，天安门广场将举行2020年国庆升旗仪式。今年国庆的升旗仪式，有什么特殊的意义？又会有哪些新亮点？戳直播，看五星红旗在天安门广场冉冉升起，一起祝福祖国！
     * title : 天安门广场举行国庆升旗仪式
     * url : http://mbd.baidu.com/webpage?type=live&action=liveshow&source=search&room_id=3941827688
     */

    private String newsid;
    private String newsType;
    private String picture;
    @SerializedName("abstract")
    private String abstractX;
    private String title;
    private String url;

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

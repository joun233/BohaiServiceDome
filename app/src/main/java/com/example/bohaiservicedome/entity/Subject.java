package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/7 16
 */
public class Subject extends BmobObject {
    private String title;
    private String desc;
    private BmobFile images;
    private BmobFile pdfUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BmobFile getImages() {
        return images;
    }

    public void setImages(BmobFile images) {
        this.images = images;
    }

    public BmobFile getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(BmobFile pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}

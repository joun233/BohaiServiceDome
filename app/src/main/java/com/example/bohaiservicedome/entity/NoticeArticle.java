package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/4 12
 */
public class NoticeArticle extends BmobArticle {
    private BmobFile noticeImage;

    public BmobFile getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(BmobFile noticeImage) {
        this.noticeImage = noticeImage;
    }
}

package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/5 09
 */
public class ImageSlider extends BmobObject {
    String sliderTitle;
    BmobFile imageUrl;
    String webUrl;

    public String getSliderTitle() {
        return sliderTitle;
    }

    public void setSliderTitle(String sliderTitle) {
        this.sliderTitle = sliderTitle;
    }

    public BmobFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(BmobFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}

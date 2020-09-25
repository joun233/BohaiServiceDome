package com.example.bohaiservicedome.entity;

import java.util.List;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/6 12
 */
public class EnglishSentence {

    /**
     * code : 200
     * msg : success
     * newslist : [{"id":3723,"content":"The quality, not the longevity, of one's life is what is important.","note":"人一生最重要的是生命的质量而非寿命的长度。","tts":"https://edu-wps.ks3-cn-beijing.ksyun.com/audio/c4be43f2461011b787f3c10c93c2aaf2.mp3","imgurl":"https://edu-wps.ks3-cn-beijing.ksyun.com/image/b62b95c7d6c395a79987ba7aed019c46.png","date":"2020-04-05"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * id : 3723
         * content : The quality, not the longevity, of one's life is what is important.
         * note : 人一生最重要的是生命的质量而非寿命的长度。
         * tts : https://edu-wps.ks3-cn-beijing.ksyun.com/audio/c4be43f2461011b787f3c10c93c2aaf2.mp3
         * imgurl : https://edu-wps.ks3-cn-beijing.ksyun.com/image/b62b95c7d6c395a79987ba7aed019c46.png
         * date : 2020-04-05
         */

        private int id;
        private String content;
        private String note;
        private String tts;
        private String imgurl;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTts() {
            return tts;
        }

        public void setTts(String tts) {
            this.tts = tts;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}

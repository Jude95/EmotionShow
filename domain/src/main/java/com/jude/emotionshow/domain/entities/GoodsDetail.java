package com.jude.emotionshow.domain.entities;

import java.util.List;

/**
 * Created by mike on 2015/12/28.
 */
public class GoodsDetail extends Goods {
    private String intro;
    private String selled;
    private List<InfoItem> info;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSelled() {
        return selled;
    }

    public void setSelled(String selled) {
        this.selled = selled;
    }

    public List<InfoItem> getInfo() {
        return info;
    }

    public void setInfo(List<InfoItem> info) {
        this.info = info;
    }

    public class InfoItem {
        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}

package com.lennon.push.bean;

import java.io.Serializable;

/**
 * Created by lennon on 2017/8/22.
 */

public class JPushMessage implements Serializable {
    private String category;
    private String voice;
    private String url;
    private String provider;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

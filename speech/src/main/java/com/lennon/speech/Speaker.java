package com.lennon.speech;

import android.text.TextUtils;
import com.lennon.cn.utill.bean.StringBean;

public enum Speaker implements StringBean {
    XiaoYan("小燕", " 青年女声", "中英文（普通话）", "txiaoyan"),
    XiaoYu("小宇", "青年男声", "中英文（普通话）", "xiaoyu"),
    Catherine("凯瑟琳", "青年女声", "英文", "catherine"),
    Henry("亨利", "青年男声", "英文", "henry"),
    Mary("玛丽", "青年女声", "英文", "vimary"),
    Xiaoyan("小燕", " 青年女声", "中英文（普通话）", "vixy"),
    XiaoQi("小琪", " 青年女声", "中英文（普通话）", "xiaoqi"),
    XiaoFeng("小峰", " 青年男声", "中英文（普通话）", "vixf"),
    XiaoMei("小梅", " 青年女声", "中英文（粤语）", "xiaomei"),
    XiaoLi("小莉", " 青年女声", "中英文（台湾普通话）", "vixl"),
    XiaoLin("晓琳", " 青年女声", "中英文（台湾普通话）", "xiaolin"),
    XiaoRong("小蓉", " 青年女声", "汉语（四川话）", "xiaorong"),
    XiaoYun("小芸", "青年女声", "汉语（东北话）", "vixyun"),
    XiaoQian("小倩", "青年女声", "汉语（东北话）", "xiaoqian"),
    XiaoKun("小坤", "青年男声", "汉语（河南话）", "xiaokun"),
    XiaoQiang("小强", "青年男声", "汉语（湖北话）", "xiaoqiang"),
    XiaoYing("小莹", " 青年女声", "汉语（陕西话）", "vixying"),
    XiaoXin("小新", " 童年男声", "汉语（普通话）", "xiaoxin"),
    NanNan("楠楠", " 童年女声", "汉语（普通话）", "nannan"),
    LaoSun("老孙", " 老年男声", "汉语（普通话）", "vils");


    private String name;
    private String voice;
    private String language;
    private String value;

    Speaker(String name, String voice, String language, String value) {
        this.name = name;
        this.voice = voice;
        this.language = language;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public static Speaker getSpeaker(String value) {
        return getSpeaker(value,XiaoYan);
    }

    public static Speaker getSpeaker(String value, Speaker def) {
        if (TextUtils.isEmpty(value)) {
            return def;
        }
        Speaker[] speakers = values();
        for (Speaker s : speakers) {
            if (value.equals(s.value)) {
                return s;
            }
        }
        return def;
    }

    @Override
    public String getItemString() {
        return name + "(" + voice + "," + language + ")";
    }
}

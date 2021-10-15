package com.shuoye.video.pojo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AnimeDescDetailsBean implements Serializable {
    // 标题
    private String title;
    // 链接
    private String url;
    // 播放链接
    private String playerUrl;
    // 是否选中
    private boolean selected;

    public AnimeDescDetailsBean() {

    }

    public AnimeDescDetailsBean(String title, String url) {
        this.title = title;
        this.url = url;
        this.selected = false;
    }

    public AnimeDescDetailsBean(String title, String url, String playerUrl) {
        this.title = title;
        this.url = url;
        this.selected = false;
        if (playerUrl == null)
            return;
        //Url解码
        try {
            this.playerUrl = URLDecoder.decode(playerUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }
}

package com.shuoye.video.pojo;

import java.util.List;

public class PlayLine {
    private String from;
    private List<AnimeDescDetailsBean> AnimeDescDetailsList;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<AnimeDescDetailsBean> getAnimeDescDetailsList() {
        return AnimeDescDetailsList;
    }

    public void setAnimeDescDetailsList(List<AnimeDescDetailsBean> animeDescDetailsList) {
        this.AnimeDescDetailsList = animeDescDetailsList;
    }
}

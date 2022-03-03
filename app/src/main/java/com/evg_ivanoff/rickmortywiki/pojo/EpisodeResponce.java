package com.evg_ivanoff.rickmortywiki.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodeResponce {
    @SerializedName("info")
    @Expose
    private EpisodeInfo info;
    @SerializedName("results")
    @Expose
    private List<Episode> results = null;

    public EpisodeInfo getInfo() {
        return info;
    }

    public void setInfo(EpisodeInfo info) {
        this.info = info;
    }

    public List<Episode> getResults() {
        return results;
    }

    public void setResults(List<Episode> results) {
        this.results = results;
    }
}

package com.evg_ivanoff.rickmortywiki.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponce {
    @SerializedName("info")
    @Expose
    private DataInfo dataInfo;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public DataInfo getInfo() {
        return dataInfo;
    }

    public void setInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}

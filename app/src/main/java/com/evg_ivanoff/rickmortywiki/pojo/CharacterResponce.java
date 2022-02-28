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
    private List<Character> characters = null;

    public DataInfo getInfo() {
        return dataInfo;
    }

    public void setInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public List<Character> getResults() {
        return characters;
    }

    public void setResults(List<Character> characters) {
        this.characters = characters;
    }
}

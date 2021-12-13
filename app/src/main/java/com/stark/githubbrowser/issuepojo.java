package com.stark.githubbrowser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class issuepojo implements Serializable {
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("state")
    @Expose
    String state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

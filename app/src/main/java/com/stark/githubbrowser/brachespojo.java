package com.stark.githubbrowser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class brachespojo implements Serializable {
    @SerializedName("name")
    @Expose
    String name;

    public brachespojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

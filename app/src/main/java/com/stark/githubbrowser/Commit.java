package com.stark.githubbrowser;

import java.io.Serializable;

public class Commit implements Serializable {
//    public Author author;
    public Committer committer;
    public String message;
//    public Tree tree;
    public String url;
    public int comment_count;
//    public Verification verification;


    public Commit() {

    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
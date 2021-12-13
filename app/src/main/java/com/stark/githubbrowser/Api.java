package com.stark.githubbrowser;

import static android.content.Context.MODE_PRIVATE;

import android.app.job.JobScheduler;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.material.shadow.ShadowRenderer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stark.githubbrowser.models.Repomodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Api {
    String BASE_URL = "https://api.github.com";


    @GET("/users/{username}/repos")
    Call<List<Repomodel>> receivedata(@Path(value = "username", encoded = true) String usname);

    //dynamic url
    @GET("repos/{owner}/{reponame}/branches")
    Call<List<brachespojo>> branchpojo(@Path(value = "owner", encoded = true) String owner,@Path(value = "reponame", encoded = true) String repositoryname);

    @GET("repos/{owner}/{reponame}/issues")
    Call<List<issuepojo>> issuesdata(@Path(value = "owner", encoded = true) String owner,@Path(value = "reponame", encoded = true) String repositoryname);


    @GET("/users/{username}")
    Call<JsonObject> authenticate(@HeaderMap Map<String, String> headers, @Path(value = "username", encoded = true) String username);

    @GET("repos/{owner}/{repost}/commits/{branchname}")
    Call<JsonObject> Commits(@Path(value = "owner", encoded = true) String owner,@Path(value = "repost", encoded = true) String repname, @Path(value = "branchname", encoded = true) String branchname);

    //        @Headers({"Authorization: Bearer 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"})
    @DELETE("repos/{username}/{repository}")
    Call<JsonObject> deleterepository(@HeaderMap Map<String, String> tokens, @Path(value = "username", encoded = true) String userdelete, @Path(value = "repository", encoded = true) String reponame);

    @POST("/user/repos")
    Call<JsonObject> createRepository(@HeaderMap Map<String, String> token, @Body JsonObject jsonObject);
//    @GET("gohanSaki114/GitStyle/commits")
//    Call
}
//https://api.github.com/users/gohanSaki114/repos
//gohanSaki114/audioapp/branches
//repos/gohanSaki114/audioapp/issues
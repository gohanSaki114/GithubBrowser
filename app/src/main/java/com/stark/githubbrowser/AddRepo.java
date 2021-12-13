package com.stark.githubbrowser;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRepo extends DialogFragment {
    EditText repo, owner;
    Button addbutton;
    SharedPreferences sharedPreferences;
    static Activity activity;

    public static AddRepo newInstance(Activity mainActivity) {
        activity = mainActivity;
        return new AddRepo();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addrepo, container, false);
        owner = v.findViewById(R.id.owneredit);
        repo = v.findViewById(R.id.repoedit);
        addbutton = v.findViewById(R.id.add);
        sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!owner.getText().toString().isEmpty() && !repo.getText().toString().isEmpty()) {
                    createRepo(repo.getText().toString());
                }
            }
        });
        return v;
    }

    private void createRepo(String RepoName) {
        Map<String, String> header = new HashMap<>();
        String tok = sharedPreferences.getString("token", "");
        header.put("Authorization", "Bearer " + tok);
        header.put("Content-Type", "application/json");
        JsonObject obj = new JsonObject();
        obj.addProperty("name", RepoName);
        Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().createRepository(header, obj);
        call.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//          Log.e("addrepo",response.body().toString());
                Log.e("createresponse", "" + response.message());
                if (response.errorBody() == null) {
                    AddRepo.this.dismiss();

//                    RecyclerView rv = activity.findViewById(R.id.recycler);

                    mainrecylcer re = new mainrecylcer(activity);
re.notifyDataSetChanged();
//                    rv.setAdapter(null);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                    rv.setAdapter(re);
//                        }
//                    },1000);


//                    new mainrecylcer(getContext()).notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("addrepo", t.getMessage());
            }
        });

    }

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }


}

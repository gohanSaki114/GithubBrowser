package com.stark.githubbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stark.githubbrowser.models.CommitModal;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Commits extends AppCompatActivity {
    RecyclerView recyclerView;
    Bundle bundle;
    String username;
    String repname, branchname;
    SharedPreferences sharedPreferences;
    List<CommitModal> _commitModal = new ArrayList<>();
List<String> _shaList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        bundle = getIntent().getExtras();
        repname = bundle.getString("reponame");
        branchname = bundle.getString("name");
        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        recyclerView = findViewById(R.id.Commitrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(Commits.this));
        bringCommits(repname, branchname);

    }

    private void bringCommits(String repname, String branchname) {
        Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().Commits(username,repname, branchname);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject obj = response.body();
                if (obj != null && !_shaList.contains(obj.get("sha").getAsString())) {
                    CommitModal modal = new CommitModal();
                    modal.setSha(obj.get("sha").getAsString());
                    modal.setCommitMessage(obj.getAsJsonObject("commit").get("message").getAsString());
                    modal.setUsername(obj.getAsJsonObject("commit").getAsJsonObject("author").get("name").getAsString());
                    modal.setDate(obj.getAsJsonObject("commit").getAsJsonObject("author").get("date").getAsString());
                    modal.setAvatarUrl(obj.getAsJsonObject("author").get("avatar_url").getAsString());
                    _commitModal.add(modal);
                    _shaList.add(obj.get("sha").getAsString());
                    JsonArray _parents = obj.getAsJsonArray("parents");
                    if (_parents != null && _parents.size() > 0) {
                        for (int i = 0; i < _parents.size(); i++) {
                            String _sha = _parents.get(i).getAsJsonObject().get("sha").getAsString();
                            bringCommits(repname, _sha);

                        }
                    }
                    recyclerView.setAdapter(new adaptercommit(_commitModal,Commits.this));
                }


                Toast.makeText(Commits.this, "" + response.isSuccessful(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Commits.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Commits ", t.getMessage());
            }
        });
    }

    private void SetModal(JsonObject obj) {
        if (obj != null) {

        }

    }

}
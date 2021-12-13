package com.stark.githubbrowser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Details extends AppCompatActivity {
    TabLayout tabLayout;
    ImageView redirect ,trash;
    ViewPager viewPager;
    TextView nametext, descriptiontext;
    SharedPreferences sharedPreferences;
    Bundle bundle;
    String username;
    String name,description,url,_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        bundle = getIntent().getExtras();
        name = bundle.getString("name");
        description = bundle.getString("description");
        url = bundle.getString("url");
        _url = url.replace("api.","");
        _url = _url.replace("/repos","");
        tabLayout = findViewById(R.id.tabLayout);
        trash = findViewById(R.id.delete);
        redirect = findViewById(R.id.eyebutton);
        viewPager = findViewById(R.id.viewPager);
        nametext = findViewById(R.id.reponametext);
        descriptiontext = findViewById(R.id.descriptiontext);
        nametext.setText(name);
        sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRepos();
            }
        });
        descriptiontext.setText(description);
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.addTab(tabLayout.newTab().setText("Branches"));
        tabLayout.addTab(tabLayout.newTab().setText("Issues"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(_url));
                startActivity(i);

            }
        });
    }

    private void deleteRepos() {
        Map<String, String> tokens = new HashMap<>();
        String tok = sharedPreferences.getString("token","");
        tokens.put("Authorization","Bearer "+tok);
        Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().deleterepository(tokens,username,nametext.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Toast.makeText(Details.this, ""+response.body().toString(), Toast.LENGTH_SHORT).show();
                JsonObject delete = response.body();
                if (delete==null)
                {
                  Intent intent = new Intent(Details.this,MainActivity.class);
                  startActivity(intent);
                }
                Log.e("fasdfkljs","normal log");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Details.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG65",t.getMessage());
            }
        });
    }
}
//username,nametext.getText().toString()
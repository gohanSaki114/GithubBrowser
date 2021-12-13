package com.stark.githubbrowser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.stark.githubbrowser.models.Repomodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    Button addnow;

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String username;
    ImageView addrepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        recyclerView = findViewById(R.id.recycler);
        addrepo = findViewById(R.id.addrepo);

        addnow = findViewById(R.id.addnow);

        //getData(username);
        addrepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                @SuppressLint("ValidFragment")

                DialogFragment newFragment = AddRepo.newInstance(MainActivity.this);
                newFragment.show(ft, "dialog");

            }
        });
        addnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment newFragment = AddRepo.newInstance(MainActivity.this);
                newFragment.show(ft, "dialog");

            }
        });
        recyclerView = findViewById(R.id.recycler);
        mainrecylcer re=new mainrecylcer(MainActivity.this);
        recyclerView.setAdapter(re);


    }


}

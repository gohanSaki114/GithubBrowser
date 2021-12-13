package com.stark.githubbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    TextInputEditText username, token;
    Button login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences1 = getSharedPreferences("myPref",MODE_PRIVATE);
        String user = sharedPreferences1.getString("username","");
        String tok = sharedPreferences1.getString("token","");
        if (!user.isEmpty() && !tok.isEmpty())
        {
            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username = findViewById(R.id.username);
        token = findViewById(R.id.token);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginmethod(username.getText().toString(), token.getText().toString());
            }
        });
    }

    private void loginmethod(String username, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization",token);

        Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().authenticate(map, username);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() == null) {
//                    String res = response.body().toString();
                    Toast.makeText(MainActivity2.this, "invalid credantials", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                    sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("token",token);
                    editor.commit();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("tag45", t.getMessage().toString());
            }
        });
    }


}
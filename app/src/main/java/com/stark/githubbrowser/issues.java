package com.stark.githubbrowser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class issues extends Fragment {
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String repname;
    TextView nametext,random;
    String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_issues, container, false);
        sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        recyclerView = v.findViewById(R.id.issuesrecycler);
        random = v.findViewById(R.id.defaulttext);
        nametext = getActivity().findViewById(R.id.reponametext);
        getBranchApi();

        return v;
    }

    private void getBranchApi() {
        repname = nametext.getText().toString();
        Call<List<issuepojo>> call = RetrofitClient.getInstance().getMyApi().issuesdata(username,repname);
        call.enqueue(new Callback<List<issuepojo>>() {
            @Override
            public void onResponse(Call<List<issuepojo>> call, Response<List<issuepojo>> response) {
               if (response.body().isEmpty() || response.body()==null)
               {
                Log.e("Tag32","no data here");
                recyclerView.setVisibility(View.GONE);
                random.setVisibility(View.VISIBLE);
               }
               else
               {
                   recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                   recyclerView.setAdapter(new issuesadapter(response.body()));

               }
            }

            @Override
            public void onFailure(Call<List<issuepojo>> call, Throwable t) {
                Log.e("Tag32","ONfailed");
            }
        });
    }
}
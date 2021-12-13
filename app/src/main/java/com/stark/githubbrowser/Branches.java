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


public class Branches extends Fragment {
    RecyclerView recyclerView;
    String repname;
    TextView nametext, textbranch;
    SharedPreferences sharedPreferences;
    String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_branches, container, false);
        recyclerView = v.findViewById(R.id.branchesrecycler);
        textbranch = v.findViewById(R.id.branchtext);
        sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        nametext = getActivity().findViewById(R.id.reponametext);
        getBranchApi(nametext.getText().toString());
        return v;
    }

    private void getBranchApi(String s) {
        repname = s;
        Call<List<brachespojo>> call = RetrofitClient.getInstance().getMyApi().branchpojo(username,repname);
        call.enqueue(new Callback<List<brachespojo>>() {
            @Override
            public void onResponse(Call<List<brachespojo>> call, Response<List<brachespojo>> response) {
                if (response.body().isEmpty() || response.body() == null) {
//                Log.e("Tag13", "" + response.body().get(0).name);
                    recyclerView.setVisibility(View.GONE);
                    textbranch.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    recyclerView.setAdapter(new branchesadapter(response.body(),getActivity(),repname));
                }
            }

            @Override
            public void onFailure(Call<List<brachespojo>> call, Throwable t) {

            }
        });
    }
}
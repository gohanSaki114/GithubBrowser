package com.stark.githubbrowser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.stark.githubbrowser.models.Repomodel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainrecylcer extends RecyclerView.Adapter<mainrecylcer.ViewHolder> {
    Activity activity;
    List<Repomodel> listrepo;
    SharedPreferences sharedPreferences;

    public mainrecylcer(Activity activity) {

        this.activity = activity;
        listrepo = new ArrayList<>();
        sharedPreferences = activity.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        getData(sharedPreferences.getString("username", ""));

    }

    @NonNull
    @Override
    public mainrecylcer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainrecyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mainrecylcer.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(listrepo.get(position).name);
        holder.description.setText(listrepo.get(position).description);
        String _url = listrepo.get(position).url;
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, Details.class).putExtra("name", listrepo.get(position).name).putExtra("description", listrepo.get(position).description).putExtra("url", listrepo.get(position).url));
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                String url = _url.replace("api.", "");
                url = url.replace("/repos", "");
                i.putExtra(Intent.EXTRA_TEXT, "" + listrepo.get(position).name + "\r\n" + listrepo.get(position).description + "\r\n" + url + "");
                activity.startActivity(Intent.createChooser(i, "Send mail..."));

            }
        });

    }

    @Override
    public int getItemCount() {
        return listrepo == null ? 0 : listrepo.size();
    }

    public void getData(String username) {
        listrepo.clear();
        ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading repositories");
        pd.show();
        Call<List<Repomodel>> call = RetrofitClient.getInstance().getMyApi().receivedata(username);

        call.enqueue(new Callback<List<Repomodel>>() {
            @Override
            public void onResponse(Call<List<Repomodel>> call, Response<List<Repomodel>> response) {
//                Log.e("Tag", "onResponse: " + response.body().toString());
                if (response.body() == null || response.body().isEmpty()) {
                    pd.dismiss();
                    Toast.makeText(activity, "Response is null", Toast.LENGTH_SHORT).show();
                } else {

                    pd.dismiss();
                    listrepo.clear();
                    listrepo = response.body();

                    mainrecylcer.this.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Repomodel>> call, Throwable t) {
                Log.e("Tag", "onResponsefail: " + t.toString());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView name, description;
        ImageView share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.reponametext);
            description = itemView.findViewById(R.id.textdescription);
            share = itemView.findViewById(R.id.share);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintrow);
        }
    }
}

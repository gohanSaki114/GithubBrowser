package com.stark.githubbrowser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class issuesadapter extends RecyclerView.Adapter<issuesadapter.ViewHolder> {

    List<issuepojo> allissues;
    public issuesadapter(List<issuepojo> body) {
        allissues = body;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowissues, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull issuesadapter.ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {

      return allissues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

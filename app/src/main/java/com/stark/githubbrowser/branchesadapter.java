package com.stark.githubbrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class branchesadapter extends RecyclerView.Adapter<branchesadapter.ViewHolder> {
    List<brachespojo> allbranch;
    Context context;
    String repositoryname;
    public branchesadapter(List<brachespojo> body, FragmentActivity activity, String repname) {
        allbranch = body;
        context = activity;
        repositoryname = repname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowbranches, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.branchename.setText(allbranch.get(position).name);
        String name = allbranch.get(position).getName();
        holder.constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Commits.class);
                intent.putExtra("name", name);
                intent.putExtra("reponame",repositoryname);
                Log.e("branch",name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allbranch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView branchename;
        ConstraintLayout constraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraint = itemView.findViewById(R.id.constraintrow);
            branchename = itemView.findViewById(R.id.branchname);
        }
    }
}

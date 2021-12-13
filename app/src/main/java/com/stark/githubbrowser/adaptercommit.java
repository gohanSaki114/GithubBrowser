package com.stark.githubbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stark.githubbrowser.models.CommitModal;

import java.util.ArrayList;
import java.util.List;

public class adaptercommit extends RecyclerView.Adapter<adaptercommit.ViewModel> {

    List<CommitModal> _modalList;
     Context context;
    public adaptercommit(List<CommitModal> commitModal, Commits commits) {
        _modalList = commitModal;
        context =commits;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commitrowitems, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptercommit.ViewModel holder, int position) {
        holder.sha.setText(_modalList.get(position).getSha());
        holder.user.setText(_modalList.get(position).getUsername());
        holder.date.setText(_modalList.get(position).getDate());
        holder.message.setText(_modalList.get(position).getCommitMessage());
        Glide.with(context).load(_modalList.get(position).getAvatarUrl()).circleCrop().into(holder.useravatar);
    }

    @Override
    public int getItemCount() {
        return _modalList.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView sha, date, message, user;
        ImageView useravatar;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            sha = itemView.findViewById(R.id.sha);
            date = itemView.findViewById(R.id.datetext);
            user = itemView.findViewById(R.id.usertext);
            message = itemView.findViewById(R.id.commitreponame);
            useravatar = itemView.findViewById(R.id.userimage);

        }
    }
}

package com.alfiankarim.submission1_list.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.alfiankarim.submission1_list.R;
import com.alfiankarim.submission1_list.UserModel.UserRes;
import com.bumptech.glide.Glide;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowHolder> {

    Context context;
    List<UserRes> Followers;

    public FollowAdapter(Context context, List<UserRes> Followers) {
        this.context = context;
        this.Followers = Followers;
    }

    @Override
    public FollowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_follow, parent,false);
        return new FollowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowHolder vh, final int position) {
        vh.tv_nama.setText(Followers.get(position).getLogin());
        vh.tv_ket.setText(Followers.get(position).getNodeId());
        Glide.with(context).load(Followers.get(position).getAvatarUrl()).into(vh.iv_user);
    }

    @Override
    public int getItemCount() {
        return Followers.size();
    }

    public class FollowHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,tv_ket;
        ImageView iv_user;
        View v;

        public FollowHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tv_nama= itemView.findViewById(R.id.tv_user);
            tv_ket=itemView.findViewById(R.id.tv_ket);
            iv_user=itemView.findViewById(R.id.iv_user);
        }
    }
}


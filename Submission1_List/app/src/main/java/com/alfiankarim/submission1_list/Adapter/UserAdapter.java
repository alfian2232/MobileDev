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

import com.alfiankarim.submission1_list.DetailActivity;
import com.alfiankarim.submission1_list.R;
import com.alfiankarim.submission1_list.UserModel.UserRes;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    Context context;
    List<UserRes> users ;

    public UserAdapter(Context context, List<UserRes> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public UserHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder vh, final int position) {
        vh.tv_nama.setText(users.get(position).getLogin());
        vh.tv_ket.setText(users.get(position).getNodeId());
        Glide.with(context).load(users.get(position).getAvatarUrl()).into(vh.iv_user);
        vh.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("username",users.get(position).getLogin());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
       return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,tv_ket;
        ImageView iv_user;
        View v;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tv_nama= itemView.findViewById(R.id.tv_user);
            tv_ket=itemView.findViewById(R.id.tv_ket);
            iv_user=itemView.findViewById(R.id.iv_user);
        }
    }
}

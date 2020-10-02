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
import com.alfiankarim.submission1_list.UserModel.Item;
import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    Context context;
    List<Item> usersSearch;

    public SearchAdapter(Context context, List<Item> users) {
        this.context = context;
        this.usersSearch = users;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, parent,false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder vh, final int position) {
        vh.tv_nama.setText(usersSearch.get(position).getLogin());
        vh.tv_ket.setText(usersSearch.get(position).getNodeId());
        Glide.with(context).load(usersSearch.get(position).getAvatarUrl()).into(vh.iv_user);
        vh.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("username", usersSearch.get(position).getLogin());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersSearch.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,tv_ket;
        ImageView iv_user;
        View v;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tv_nama= itemView.findViewById(R.id.tv_user);
            tv_ket=itemView.findViewById(R.id.tv_ket);
            iv_user=itemView.findViewById(R.id.iv_user);
        }
    }
}

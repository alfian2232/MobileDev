package com.alfiankarim.submission1_list.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alfiankarim.submission1_list.Adapter.FollowAdapter;
import com.alfiankarim.submission1_list.R;
import com.alfiankarim.submission1_list.Retrofit.APIClient;
import com.alfiankarim.submission1_list.Retrofit.APIService;
import com.alfiankarim.submission1_list.UserModel.User;
import com.alfiankarim.submission1_list.UserModel.UserRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Follower extends Fragment {
    View v;
    RecyclerView rv_follower;
    List<UserRes> listFollowers= new ArrayList<>();
    FollowAdapter followAdapter;
    String User_Log;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_follower, container, false);
        rv_follower=v.findViewById(R.id.rv_followers);

        getFollowers(User_Log);

        return v;
    }

    public Follower(String user_Log) {
        User_Log = user_Log;
    }

    private void getFollowers(String username){
        APIService service = APIClient.getClient().create(APIService.class);
        Call<List<UserRes>> call = service.getFollowers(username);
        call.enqueue(new Callback<List<UserRes>>() {
            @Override
            public void onResponse(Call<List<UserRes>> call, Response<List<UserRes>> response) {
                if(response.code() == 200){
                    listFollowers=response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rv_follower.setLayoutManager(linearLayoutManager);
                    followAdapter = new FollowAdapter(getContext(),listFollowers);
                    rv_follower.setAdapter(followAdapter);
                }else{
                    Toast.makeText(getContext(), "Problem "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserRes>> call, Throwable t) {
                Toast.makeText(getContext(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
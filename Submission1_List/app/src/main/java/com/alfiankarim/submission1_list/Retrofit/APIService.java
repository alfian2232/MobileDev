package com.alfiankarim.submission1_list.Retrofit;

import com.alfiankarim.submission1_list.UserModel.DetailUserRes;
import com.alfiankarim.submission1_list.UserModel.SearchUserRes;
import com.alfiankarim.submission1_list.UserModel.UserRes;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("users/{username}")
    Call<DetailUserRes> getDetailUser (@Path("username") String username);

    @GET("search/users")
    Call<SearchUserRes> getUserFromSearch (@QueryMap Map<String,String> options);

    @GET("users")
    Call<List<UserRes>> getAllUser();

    @GET("users/{username}/followers")
    Call<List<UserRes>> getFollowers(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<UserRes>> getFollowing(@Path("username") String username);

}

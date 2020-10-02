package com.alfiankarim.submission1_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alfiankarim.submission1_list.Adapter.SearchAdapter;
import com.alfiankarim.submission1_list.Adapter.UserAdapter;
import com.alfiankarim.submission1_list.Retrofit.APIClient;
import com.alfiankarim.submission1_list.Retrofit.APIService;
import com.alfiankarim.submission1_list.UserModel.Item;
import com.alfiankarim.submission1_list.UserModel.SearchUserRes;
import com.alfiankarim.submission1_list.UserModel.UserRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserAdapter userAdapter;
    SearchAdapter searchAdapter;
    RecyclerView rv_item,rv_search;
    Toolbar toolbar;
    SearchView search_view;
    List<UserRes> listuser = new ArrayList<>();
    SearchUserRes searchUserRes;
    List<Item> list_item = new ArrayList<>();
    ProgressDialog progressDialog;
    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_item = findViewById(R.id.rv_item);
        toolbar = findViewById(R.id.toolbar);
        search_view = findViewById(R.id.search_view);
        rv_search = findViewById(R.id.rv_search);
        btn_cancel =  findViewById(R.id.btn_cancel);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        getUser();


        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getDataFromSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search_view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                rv_item.setVisibility(View.VISIBLE);
                rv_search.setVisibility(View.GONE);
                getUser();
                return true;
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_item.setVisibility(View.VISIBLE);
                rv_search.setVisibility(View.GONE);
                search_view.setQuery("",false);
                search_view.clearFocus();
                getUser();
            }
        });

    }

    void getUser() {
        progressDialog = new ProgressDialog(MainActivity.this,R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        APIService service = APIClient.getClient().create(APIService.class);
        Call<List<UserRes>> call = service.getAllUser();
        call.enqueue(new Callback<List<UserRes>>() {
            @Override
            public void onResponse(Call<List<UserRes>> call, Response<List<UserRes>> response) {
                if (response.code() == 200) {
                    listuser = response.body();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_item.setLayoutManager(layoutManager);
                    userAdapter = new UserAdapter(MainActivity.this, listuser);
                    rv_item.setAdapter(userAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Problem" + response.message(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<List<UserRes>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Eror" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });
    }

    private void getDataFromSearch(String log_username) {
        progressDialog = new ProgressDialog(MainActivity.this,R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Map<String, String> query = new HashMap<>();
        query.put("q", log_username);
        APIService service = APIClient.getClient().create(APIService.class);
        Call<SearchUserRes> call = service.getUserFromSearch(query);
        call.enqueue(new Callback<SearchUserRes>() {
            @Override
            public void onResponse(Call<SearchUserRes> call, Response<SearchUserRes> response) {
                if (response.code() == 200) {
                    searchUserRes = response.body();
                    list_item.clear();
                    rv_search.setVisibility(View.VISIBLE);
                    list_item = searchUserRes.getItems();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_search.setLayoutManager(layoutManager);
                    searchAdapter = new SearchAdapter(MainActivity.this, list_item);
                    rv_search.setAdapter(searchAdapter);
                    rv_item.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Problem " + response.message(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<SearchUserRes> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.setting_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
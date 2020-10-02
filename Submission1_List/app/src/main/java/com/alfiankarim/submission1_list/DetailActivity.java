package com.alfiankarim.submission1_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfiankarim.submission1_list.Adapter.FollowAdapter;
import com.alfiankarim.submission1_list.Fragment.Follower;
import com.alfiankarim.submission1_list.Fragment.Following;
import com.alfiankarim.submission1_list.Retrofit.APIClient;
import com.alfiankarim.submission1_list.Retrofit.APIService;
import com.alfiankarim.submission1_list.UserModel.DetailUserRes;
import com.alfiankarim.submission1_list.UserModel.User;
import com.alfiankarim.submission1_list.UserModel.UserRes;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tv_username,tv_name,tv_location,tv_following,tv_follower;
    ImageView iv_avatar;
    Toolbar toolbar;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    String Username;
    ProgressDialog pdialog;
    DetailUserRes detailUserRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_username= findViewById(R.id.tv_Username);
        tv_name= findViewById(R.id.tv_name);
        tv_location=findViewById(R.id.tv_location);
        tv_follower=findViewById(R.id.tv_follower);
        tv_following=findViewById(R.id.tv_following);
        iv_avatar=findViewById(R.id.iv_user);
        toolbar= findViewById(R.id.toolbar);
        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.detail_name));
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Username=getIntent().getStringExtra("username");
        getData(Username);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        int index= tabLayout.getSelectedTabPosition();
        ((TextView) tabLayout.getTabAt(index).getCustomView()).setTextColor(getResources().getColor(R.color.white));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.white));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new Following(Username),getString(R.string.following));
        viewPagerAdapter.addFrag(new Follower(Username),getString(R.string.followers));
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabIcons() {
        TextView Following = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        Following.setText(getString(R.string.following));
        Following.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_following, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(Following);

        TextView Follower = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        Follower.setText(getString(R.string.followers));
        Follower.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_followers, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(Follower);
    }

    private void getData(String username){
        pdialog = new ProgressDialog(DetailActivity.this,R.style.Theme_AppCompat_DayNight_Dialog);
        pdialog.setIndeterminate(true);
        pdialog.setMessage(getString(R.string.loading));
        pdialog.setCancelable(false);
        pdialog.show();
        APIService service= APIClient.getClient().create(APIService.class);
        Call<DetailUserRes> call = service.getDetailUser(username);
        call.enqueue(new Callback<DetailUserRes>() {
            @Override
            public void onResponse(Call<DetailUserRes> call, Response<DetailUserRes> response) {
                if(response.code() == 200){
                    detailUserRes = response.body();
                    tv_username.setText(detailUserRes.getLogin());
                    tv_name.setText(detailUserRes.getType());
                    tv_location.setText(": "+detailUserRes.getLocation());
                    tv_following.setText(": "+detailUserRes.getFollowing());
                    tv_follower.setText(": "+detailUserRes.getFollowers());
                    Glide.with(DetailActivity.this).load(detailUserRes.getAvatarUrl()).into(iv_avatar);
                }else{
                    Toast.makeText(DetailActivity.this, "Problem "+response.message(), Toast.LENGTH_SHORT).show();
                }
                pdialog.cancel();
            }

            @Override
            public void onFailure(Call<DetailUserRes> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                pdialog.cancel();
            }
        });
    }
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
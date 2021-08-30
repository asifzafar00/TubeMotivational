package com.player.tubemotivational.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.player.tubemotivational.R;
import com.player.tubemotivational.databinding.UserActivityBinding;
import com.player.tubemotivational.main.util.CommonFunction;
import com.player.tubemotivational.player.PlayerActivity;
import com.player.tubemotivational.user.adapter.UserAdapter;
import com.player.tubemotivational.user.interfaces.UserItemClickListener;
import com.player.tubemotivational.user.worker.DataWorker;

import static com.player.tubemotivational.util.AppConstants.USER_ITEMS;
import static com.player.tubemotivational.util.AppConstants.USER_ITEM_URL_KEY;
import static com.player.tubemotivational.util.AppConstants.USER_NAME_KEY;
import static com.player.tubemotivational.util.AppConstants.USER_URL;

public class UserActivity extends AppCompatActivity implements UserItemClickListener {
    private UserActivityBinding viewBinding;
    private UserAdapter adapter;
    private Context context;
    private UserItemClickListener listener;
    private String userName;

    private static final String TAG = "UserActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        listener = this;
        userName = getIntent().getStringExtra(USER_ITEMS);
        viewBinding = UserActivityBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());


        //
        setSupportActionBar(viewBinding.toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewBinding.toolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        viewBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
        getSupportActionBar().setTitle(userName);
        viewBinding.toolBar.setTitle(userName);
        getSupportActionBar().setLogo(R.drawable.ic_account_circle_black_24dp);

        viewBinding.toolBar.setTitleTextColor(getResources().getColor(R.color.colorGrey));
        //
        Log.e(TAG, "onCreate: "+userName );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewBinding.recyclerView.setLayoutManager(layoutManager);

        //


        adapter = new UserAdapter(CommonFunction.getAllNameOfUserList(context), context, listener);
        viewBinding.recyclerView.setAdapter(adapter);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        viewBinding.adView.loadAd(adRequest);

    }

    @Override
    public void userItemClick(String user) {

        Log.e(TAG, "userItemClick: "+user );
       // if (user != null) {
          //  Intent in = new Intent(UserActivity.this, PlayerActivity.class);
//            in.putExtra(USER_ITEM_URL_KEY, user);
//            startActivity(in);
//        }

        Data.Builder builder = new Data.Builder();
        builder.putString(USER_URL, "");
        builder.putString(USER_NAME_KEY, userName);
        Data data = builder.build();

        Constraints constraints = new Constraints.Builder()

                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DataWorker.class)
                .setConstraints(constraints)
                .setInputData(data)
                .build();

        WorkManager mWorkManager = WorkManager.getInstance(UserActivity.this);
        mWorkManager.enqueue(oneTimeWorkRequest);


        mWorkManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {

                    Log.e(TAG, "WorkInfo received: state: " + workInfo.getState());
                    String message = workInfo.getOutputData().getString("KEY_MESSAGE");
                    Log.e(TAG, "message: " + message);


                }
            }
        });
        //
    }
}

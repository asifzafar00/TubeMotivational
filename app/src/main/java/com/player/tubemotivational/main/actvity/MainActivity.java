package com.player.tubemotivational.main.actvity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.player.tubemotivational.R;
import com.player.tubemotivational.main.adapter.NameAdapter;
import com.player.tubemotivational.databinding.ActivityMainBinding;
import com.player.tubemotivational.main.interfaces.ItemClickListener;
import com.player.tubemotivational.main.util.CommonFunction;
import com.player.tubemotivational.user.activity.UserActivity;

import static com.player.tubemotivational.util.AppConstants.USER_ITEMS;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private ActivityMainBinding viewBinding;
    private NameAdapter nameAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Context context;
    ItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = this;
        context = getApplicationContext();
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new GridLayoutManager(context, 2);
        viewBinding.recyclerView.setLayoutManager(recyclerViewLayoutManager);
        nameAdapter = new NameAdapter(CommonFunction.getAllNameList(context), context, listener);
        viewBinding.recyclerView.setAdapter(nameAdapter);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        viewBinding.adView.loadAd(adRequest);


    }

    private static final String TAG = "MainActivity";

    @Override
    public void itemClick(String user) {
        Log.e(TAG, "itemClick: " + user);
        if (user != null) {
            Intent in = new Intent(MainActivity.this, UserActivity.class);
            in.putExtra(USER_ITEMS, user);
            startActivity(in);
        }
    }
}

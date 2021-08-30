package com.player.tubemotivational.player;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.player.tubemotivational.R;
import com.player.tubemotivational.databinding.YoutubeViewBinding;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;
import static com.player.tubemotivational.util.AppConstants.USER_ITEM_URL_KEY;

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {

    private YoutubeViewBinding viewBinding;
    private String userUrlKey = "murl";
    public static final String API_KEY = "AIzaSyBx7v0YOb1404fDO7EbfMx4l87raxezDWFw";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static final String VIDEO_ID = "-m3V8w_7vhk";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        userUrlKey = getIntent().getStringExtra(USER_ITEM_URL_KEY);
        viewBinding = YoutubeViewBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        viewBinding.adView.loadAd(adRequest);

        // Initializing YouTube player view

        viewBinding.youtubeplayer.initialize(API_KEY, this);


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (null == player) return;

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}

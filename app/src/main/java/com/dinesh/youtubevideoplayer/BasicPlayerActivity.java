package com.dinesh.youtubevideoplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.content.ContentValues.TAG;
import static com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import static com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import static com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import static com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import static com.google.android.youtube.player.YouTubePlayer.Provider;

public class BasicPlayerActivity extends YouTubeBaseActivity implements OnInitializedListener {
    public static final String API_KEY = "AIzaSyBWcCsiL0pyL0EYdEjotqmkBVp6R5bq5HE";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static final String VIDEO_ID = "-m3V8w_7vhk";
    private YouTubePlayer mPlayer;
    private boolean fullscreen;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // attaching layout xml
        setContentView(R.layout.activity_basic_player);
        mainActivity = new MainActivity();
        // Initializing YouTube player view
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(API_KEY, this);

    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if(null== player) return;

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }

        mPlayer = player;



        player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean _Fullscreen) {
                Log.i(TAG, "onFullscreen: ");
                fullscreen=_Fullscreen;
            }
        });

        player.addFullscreenControlFlag(0);

        // Add listeners to YouTubePlayer instance
        player.setPlayerStateChangeListener(new PlayerStateChangeListener() {
            @Override
            public void onAdStarted() { }
            @Override
            public void onError(ErrorReason arg0) { }
            @Override
            public void onLoaded(String arg0) { }
            @Override
            public void onLoading() { }
            @Override
            public void onVideoEnded() { }
            @Override
            public void onVideoStarted() { }
        });


        player.setPlaybackEventListener(new PlaybackEventListener() {
            @Override
            public void onBuffering(boolean arg0) { }
            @Override
            public void onPaused() { }
            @Override
            public void onPlaying() { }
            @Override
            public void onSeekTo(int arg0) { }
            @Override
            public void onStopped() { }
        });

    }

    @Override
    public void onBackPressed() {
        if (mPlayer != null && fullscreen){
            mPlayer.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mPlayer.setFullscreen(false);
//    }
}
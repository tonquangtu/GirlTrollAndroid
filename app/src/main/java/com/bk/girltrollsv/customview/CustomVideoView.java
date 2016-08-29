package com.bk.girltrollsv.customview;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bk.girltrollsv.model.Video;
import com.bk.girltrollsv.util.Utils;

import java.io.IOException;

/**
 * Created by Dell on 25-Aug-16.
 */
public class CustomVideoView {

    private Activity mActivity;

    SurfaceView mSurfaceView;

    MediaPlayer mPlayer;

    CustomMediaController mMediaController;

    FrameLayout frameContainer;

    Video mVideo;

    ProgressBar mProgress;

    public CustomVideoView(Activity activity, FrameLayout container) {

        this.mActivity = activity;

        this.frameContainer = container;

        mMediaController = new CustomMediaController(mActivity);

        initSurfaceView();

        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(callback);

    }

    private void initSurfaceView() {

        int width = Utils.getScreenWidth(mActivity);
        int height = 3 * width / 4;

        mSurfaceView = new SurfaceView(mActivity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        mSurfaceView.setLayoutParams(layoutParams);
        frameContainer.addView(mSurfaceView);

        mSurfaceView.setVisibility(View.VISIBLE);
    }

    public void initMediaPlayer(Video video) {

        mVideo = video;
        initProgressBar();
        mPlayer = new MediaPlayer();

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(mActivity, Uri.parse(mVideo.getUrlVideo()));
            mPlayer.setOnPreparedListener(preparedListener);

        } catch (IOException e) {
            // show error
        }

    }

    private void initProgressBar() {

        mProgress = new ProgressBar(mActivity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mProgress.setLayoutParams(layoutParams);
        frameContainer.addView(mProgress);

    }


    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            mPlayer.setDisplay(holder);
            mPlayer.prepareAsync();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {

            mProgress.setVisibility(View.GONE);
            mSurfaceView.setVisibility(View.VISIBLE);
            mMediaController.setMediaPlayer(mediaPlayerControl);
            mMediaController.setAnchorView(frameContainer);
            mPlayer.start();

        }
    };

    private CustomMediaController.MediaPlayerControl mediaPlayerControl = new CustomMediaController.MediaPlayerControl() {
        @Override
        public void start() {
            mPlayer.start();
        }

        @Override
        public void pause() {
            mPlayer.pause();
        }

        @Override
        public int getDuration() {
            return mPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return mPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int pos) {
            mPlayer.seekTo(pos);
        }

        @Override
        public boolean isPlaying() {
            return mPlayer.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return 0;
        }

        @Override
        public boolean canPause() {
            return true;
        }

        @Override
        public boolean canSeekBackward() {
            return true;
        }

        @Override
        public boolean canSeekForward() {
            return true;
        }

        @Override
        public boolean isFullScreen() {
            return false;
        }

        @Override
        public void toggleFullScreen() {

        }
    };

    public CustomMediaController getMediaController() {
        return mMediaController;
    }

}

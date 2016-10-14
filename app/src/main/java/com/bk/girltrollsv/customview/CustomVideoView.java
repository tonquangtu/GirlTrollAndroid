package com.bk.girltrollsv.customview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bk.girltrollsv.callback.OnFullScreenListener;
import com.bk.girltrollsv.model.Video;
import com.bk.girltrollsv.util.ScreenHelper;

import java.io.IOException;

/**
 * Created by Dell on 25-Aug-16.
 */
public class CustomVideoView {

    public static final int INIT = 0;
    public static final int PREPARING = 1;
    public static final int PREPARED = 2;
    public static final int NO_PLAY = 3;
    public static final int PLAYING = 4;

    private Activity mActivity;

    SurfaceView mSurfaceView;

    MediaPlayer mPlayer;

    CustomMediaController mMediaController;

    FrameLayout frameContainer;

    Video mVideo;

    ProgressBar mProgress;

    boolean mIsFullScreen = false;

    OnFullScreenListener mFullScreenListener;

    int mPreState;

    int mStatePrepare = INIT;

    public CustomVideoView(Activity activity, FrameLayout container, SurfaceView surfaceView, Video video) {

        this.mActivity = activity;
        this.frameContainer = container;
        this.mSurfaceView = surfaceView;
        this.mVideo = video;
        mMediaController = new CustomMediaController(mActivity);

        initMediaPlayer();
        initSurfaceView();
        initProgressBar();
    }

    private void initSurfaceView() {

        float ratio = 0.75f;
        mSurfaceView.getLayoutParams().height = (int) (ScreenHelper.getScreenWidthInPx() * ratio);
        mSurfaceView.getHolder().addCallback(callback);
    }

    public void initMediaPlayer() {

        mPlayer = new MediaPlayer();
        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(mActivity, Uri.parse(mVideo.getUrlVideo()));
            mPlayer.setOnPreparedListener(preparedListener);
            mPlayer.setOnInfoListener(infoListener);

            Log.e("tuton", mVideo.getUrlVideo());
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

    public CustomMediaController getMediaController() {
        return mMediaController;
    }

    public void handleToggleFullScreen() {

        int width;
        int height;

        if (!mIsFullScreen) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            width = ScreenHelper.getScreenWidthInPx();
            height = ScreenHelper.getScreenHeightInPx();
            changeSizeSurfaceView(width, height);

        } else {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            float ratio = 0.75f;
            width = ScreenHelper.getScreenWidthInPx();
            height = (int) (width * ratio);
            changeSizeSurfaceView(width, height);
        }
        mIsFullScreen = !mIsFullScreen;
        mFullScreenListener.onFullScreenChange(mIsFullScreen);
    }

    public void changeSizeSurfaceView(int width, int height) {

        ViewGroup.LayoutParams layoutParamsSurfaceView = mSurfaceView.getLayoutParams();
        layoutParamsSurfaceView.width = width;
        layoutParamsSurfaceView.height = height;

        ViewGroup.LayoutParams layoutParamsFrameContainer = frameContainer.getLayoutParams();
        layoutParamsFrameContainer.width = width;
        layoutParamsFrameContainer.height = height;
    }

    public void setFullScreenListener(OnFullScreenListener fullScreenListener) {
        this.mFullScreenListener = fullScreenListener;
    }

    public void release() {

        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
    }

    public void pause() {

        if (mPlayer.isPlaying()) {
            mPreState = PLAYING;
            mPlayer.pause();
        } else {
            mPreState = NO_PLAY;
        }
        mMediaController.show2();
    }

    public void play() {

        if (mPreState == PLAYING) {
            mPlayer.start();
        }
        mMediaController.show2();
        if (mStatePrepare != PREPARED) {
            updateProgressLoading(View.VISIBLE);
        }
    }


    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mPlayer.setDisplay(holder);
            if (mStatePrepare == INIT) {
                mPlayer.prepareAsync();
                mStatePrepare = PREPARING;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    };

    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {

        // call after preparation is complete
        @Override
        public void onPrepared(MediaPlayer mp) {

            mStatePrepare = PREPARED;
            mProgress.setVisibility(View.GONE);
            mMediaController.setMediaPlayer(mediaPlayerControl);
            mMediaController.setAnchorView(frameContainer);
            mPlayer.start();
        }
    };

    private MediaPlayer.OnInfoListener infoListener = new MediaPlayer.OnInfoListener() {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {

            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    updateProgressLoading(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    updateProgressLoading(View.GONE);
                    break;
            }
            return false;
        }
    };

    private CustomMediaController.MediaPlayerControl mediaPlayerControl =
            new CustomMediaController.MediaPlayerControl() {
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
            return mIsFullScreen;
        }

        @Override
        public void toggleFullScreen() {

            handleToggleFullScreen();
        }
    };

    public void updateProgressLoading(int visibilityProgress) {

        if (mProgress.getVisibility() != visibilityProgress) {
            mProgress.setVisibility(visibilityProgress);
        }
        if (visibilityProgress == View.VISIBLE) {
            updatePausePlayButton(View.GONE);
        } else {
            updatePausePlayButton(View.VISIBLE);
        }
    }

    public void updatePausePlayButton(int visibilityPausePlay) {
        mMediaController.visiblePausePlay(visibilityPausePlay);
    }


}

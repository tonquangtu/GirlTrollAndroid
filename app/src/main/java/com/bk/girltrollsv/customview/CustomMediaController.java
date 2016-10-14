package com.bk.girltrollsv.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.util.StringUtil;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Dell on 25-Aug-16.
 */
public class CustomMediaController extends FrameLayout {

    private CustomMediaController.MediaPlayerControl mPlayer;
    private Context mContext;
    private ViewGroup mAnchor;
    private View mRoot;
    private ProgressBar mProgress;
    private TextView mEndTime;
    private TextView mCurrentTime;
    private boolean mShowing;
    private boolean mDragging;
    private static final int sDefaultTimeout = 2000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;

    private ImageButton mPauseButton;
    private ImageButton mFullScreenButton;
    private Handler mHandler = new MessageHandler(this);

    public CustomMediaController(Context context) {
        super(context);
        mRoot = null;
        mContext = context;
    }

    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoot = null;
        mContext = context;

    }

    @Override
    public void onFinishInflate() {

        if (mRoot != null) {
            initControllerView(mRoot);
        }
        super.onFinishInflate();
    }

    public void setMediaPlayer(CustomMediaController.MediaPlayerControl player) {
        mPlayer = player;
        updatePausePlay();
        updateFullScreen();
    }

    public void setAnchorView(ViewGroup view) {

        mAnchor = view;
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        frameParams.gravity = Gravity.CENTER;
        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);

    }

    public View makeControllerView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflater.inflate(R.layout.media_controller_layout, null);

        initControllerView(mRoot);
        return mRoot;
    }

    public void initControllerView(View v) {

        mPauseButton = (ImageButton) v.findViewById(R.id.img_btn_pause);
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mFullScreenButton = (ImageButton) v.findViewById(R.id.img_btn_fullscreen);
        if (mFullScreenButton != null) {
            mFullScreenButton.requestFocus();
            mFullScreenButton.setOnClickListener(mFullScreenListener);
        }

        SeekBar seekBar = (SeekBar) v.findViewById(R.id.sb_media_controller_progress);
        mProgress = seekBar;

        if (mProgress != null) {

            seekBar.setOnSeekBarChangeListener(mSeekListener);
            mProgress.setMax(1000);
        }

        mEndTime = (TextView) v.findViewById(R.id.txt_time);
        mCurrentTime = (TextView) v.findViewById(R.id.txt_time_current);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    public void show() {

        if (!mShowing) {
            show(sDefaultTimeout);
        } else {
            hide();
        }
    }

    /**
     * Disable pause or seek buttons if the stream cannot be paused or seeked.
     * This requires the control interface to be a MediaPlayerControlExt
     */
    private void disableUnsupportedButtons() {

        if (mPlayer == null) {
            return;
        }
        try {
            if (mPauseButton != null && !mPlayer.canPause()) {
                mPauseButton.setEnabled(false);
            }
            if (mProgress != null && !mPlayer.canSeekBackward() && !mPlayer.canSeekForward()) {
                mProgress.setEnabled(false);
            }
        } catch (IncompatibleClassChangeError ex) {
            // We were given an old version of the interface, that doesn't have
            // the canPause/canSeekXYZ methods. This is OK, it just means we
            // assume the media can be paused and seeked, and so we don't disable
            // the buttons.
        }
    }

    public void show(int timeout) {

        if (!mShowing && mAnchor != null) {
            setProgress();
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            disableUnsupportedButtons();

            FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER
            );

            mAnchor.addView(this, tlp);
            mShowing = true;

        }

        updatePausePlay();
        updateFullScreen();
        mHandler.sendEmptyMessage(SHOW_PROGRESS);

        // cause the progress bar to be updated even if mShowing
        // was already true.  This happens, for example, if we're
        // paused with the progress bar showing the user hits play.
        Message msg = mHandler.obtainMessage(FADE_OUT);
        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(msg, timeout);
        }

    }

    public boolean isShowing() {
        return mShowing;
    }


    public void hide() {

        if (mAnchor == null) {
            return;
        }
        try {
            mAnchor.removeView(this);
            mHandler.removeMessages(SHOW_PROGRESS);
        } catch (IllegalArgumentException e) {
            Log.w("MediaController", "already removed");
        }
        mShowing = false;
    }

    public String stringForTime(int timeMs) {

        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();

        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    public int setProgress() {

        if (mPlayer == null || mDragging) {
            return 0;
        }

        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();

        if (mProgress != null) {

            if (duration > 0) {
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }

            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        StringUtil.displayText(stringForTime(duration), mEndTime);
        StringUtil.displayText(stringForTime(position), mCurrentTime);
        return position;
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            show();
        }
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            show();
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mPlayer == null) {
            return true;
        }

        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
                doPauseResume();
                show(sDefaultTimeout);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mPlayer.isPlaying()) {
                mPlayer.start();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
            // don't show the controls for volume adjustment
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
            }
            return true;
        }

        show(sDefaultTimeout);
        return super.dispatchKeyEvent(event);
    }

    private View.OnClickListener mPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    private View.OnClickListener mFullScreenListener = new View.OnClickListener() {
        public void onClick(View v) {

            doToggleFullscreen();
            show(sDefaultTimeout);
        }
    };

    public void updatePausePlay() {

        if (mRoot == null || mPauseButton == null || mPlayer == null) {
            return;
        }

        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(R.drawable.icon_pause);
        } else {
            mPauseButton.setImageResource(R.drawable.icon_play);
        }
    }

    public void updateFullScreen() {
        if (mRoot == null || mFullScreenButton == null || mPlayer == null) {
            return;
        }

        if (mPlayer.isFullScreen()) {
            mFullScreenButton.setImageResource(R.drawable.icon_shrink);
        } else {
            mFullScreenButton.setImageResource(R.drawable.icon_fullscreen);
        }
    }

    public void doPauseResume() {

        if (mPlayer == null) {
            return;
        }

        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }

        updatePausePlay();
    }

    private void doToggleFullscreen() {
        if (mPlayer == null) {
            return;
        }
        updateFullScreen();
        mPlayer.toggleFullScreen();
    }

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);

            mDragging = true;

            // By removing these pending progress messages we make sure
            // that a) we won't update the progress while the user adjusts
            // the seekbar and b) once the user is done dragging the thumb
            // we will post one of these messages to the queue again and
            // this ensures that there will be exactly one message queued up.
            mHandler.removeMessages(SHOW_PROGRESS);
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            if (mPlayer == null) {
                return;
            }

            if (!fromUser) {
                // We're not interested in programmatically generated changes to
                // the progress bar's position.
                return;
            }

            long duration = mPlayer.getDuration();
            long newPosition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newPosition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newPosition));
        }

        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            setProgress();
            updatePausePlay();
            show(sDefaultTimeout);

            // Ensure that progress is properly updated in the future,
            // the call to show() does not guarantee this because it is a
            // no-op if we are already showing.
            mHandler.sendEmptyMessage(SHOW_PROGRESS);
        }
    };

    @Override
    public void setEnabled(boolean enabled) {

        if (mPauseButton != null) {
            mPauseButton.setEnabled(enabled);
        }
        if (mProgress != null) {
            mProgress.setEnabled(enabled);
        }
        disableUnsupportedButtons();
        super.setEnabled(enabled);
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        int getDuration();

        int getCurrentPosition();

        void seekTo(int pos);

        boolean isPlaying();

        int getBufferPercentage();

        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        boolean isFullScreen();

        void toggleFullScreen();
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<CustomMediaController> mView;

        MessageHandler(CustomMediaController view) {
            mView = new WeakReference<CustomMediaController>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomMediaController view = mView.get();
            if (view == null || view.mPlayer == null) {
                return;
            }

            int pos;
            switch (msg.what) {
                case FADE_OUT:
                    view.hide();
                    break;
                case SHOW_PROGRESS:
                    pos = view.setProgress();
                    if (!view.mDragging && view.mShowing && view.mPlayer.isPlaying()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
            }
        }
    }

    public void show2() {
        show(sDefaultTimeout);
    }

    public void visiblePausePlay(int visibility) {
        if (mPauseButton != null && mPauseButton.getVisibility() != visibility) {
            mPauseButton.setVisibility(visibility);
        }
    }

}

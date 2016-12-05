package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ProgressBar;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.model.PostedFeed;

import butterknife.Bind;

/**
 * Created by Dell on 02-Dec-16.
 */
public class PostedFeedViewHolder extends FeedViewHolder {

    @Bind(R.id.pgb_uploading)
    ProgressBar mPgbUploading;

    @Bind(R.id.ll_pgb_uploading)
    View llPgbUpload;

    @Bind(R.id.view_reupload)
    View reUpload;

    @Bind(R.id.img_reupload)
    View imgReupload;
//
//    @Bind(R.id.ll_purdah)
//    LinearLayout llPurdah;

    @Bind(R.id.root_posted_feed)
    View mRootPostedFeedView;

    public PostedFeedViewHolder(View itemView, Activity activity) {
        super(itemView, activity);
        mPgbUploading.getIndeterminateDrawable().setColorFilter(
                activity.getResources().getColor(R.color.color_progress_bar),
                PorterDuff.Mode.MULTIPLY);
    }

    public void populatePost(PostedFeed postedFeed) {
        super.populate(postedFeed);

        int uploadState = postedFeed.getUploadState();
        switch (uploadState) {

            case PostedFeed.UPLOADING:
                setEnableClickView(false);
                setVisibilityProgress(true);
                setVisibilityReupload(false);
                break;

            case PostedFeed.UPLOAD_FAIL:
                setEnableClickView(false);
//                setVisibilityProgress(false);
                setProgressBarVisible(false);
                setVisibilityReupload(true);
                break;

            case PostedFeed.UPLOAD_SUCCESS:
                setEnableClickView(true);
                setVisibilityProgress(false);
                setVisibilityReupload(false);
                break;

            case PostedFeed.ACCEPTED:
                setEnableClickView(true);
                setVisibilityProgress(false);
                setVisibilityReupload(false);
                break;

            case PostedFeed.REJECT:
                setEnableClickView(false);
//                setVisibilityProgress(false);
                setProgressBarVisible(false);
                setVisibilityReupload(true);

        }

    }

    public void setEnableClickView(boolean enable) {
        llLike.setClickable(enable);
        llComment.setClickable(enable);
        imgBtnMore.setClickable(enable);
    }

    public void setVisibilityProgress(boolean visible) {

        if (visible) {
            if (llPgbUpload.getVisibility() != View.VISIBLE) {
                llPgbUpload.setVisibility(View.VISIBLE);
            }
        } else if (llPgbUpload.getVisibility() == View.VISIBLE) {
            llPgbUpload.setVisibility(View.GONE);
        }
    }

    public void setProgressBarVisible(boolean visible) {

        if (visible) {
            if (mPgbUploading.getVisibility() != View.VISIBLE) {
                mPgbUploading.setVisibility(View.VISIBLE);
            }
        } else if (mPgbUploading.getVisibility() == View.VISIBLE) {
            mPgbUploading.setVisibility(View.GONE);
        }
    }

    public void setVisibilityReupload(boolean visible) {

        if (visible) {
            if (reUpload.getVisibility() != View.VISIBLE) {
                reUpload.setVisibility(View.VISIBLE);
            }
        } else if (reUpload.getVisibility() == View.VISIBLE) {
            reUpload.setVisibility(View.GONE);
        }
    }
}

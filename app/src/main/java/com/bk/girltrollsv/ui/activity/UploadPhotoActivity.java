package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.util.AccountUtil;
import com.bk.girltrollsv.util.BitmapUtil;
import com.bk.girltrollsv.util.FileUtil;
import com.bk.girltrollsv.util.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Dell on 26-Oct-16.
 */
public class UploadPhotoActivity extends BaseActivity {

    public static final int MIX_PHOTO = 1;
    public static final int NORMAL_PHOTO = 0;
    public static final String NO_TITLE = "Bạn chưa thêm mô tả cho bài đăng";

    @Bind(R.id.img_preview_upload_photo)
    ImageView imgPreview;

    @Bind(R.id.edit_description_post)
    EditText editDescriptionPost;

    @Bind(R.id.spinner_school)
    Spinner spinnerSchool;

    private Uri mPhotoUri = null;
    private String mPhotoPath = null;
    private int mTypePhoto = NORMAL_PHOTO;
    private boolean isDecodePhotoComplete = false;

    @Override
    public void handleIntent(Intent intent) {
        Bundle data = intent.getBundleExtra(AppConstant.PACKAGE);
        mPhotoUri = data.getParcelable(AppConstant.URI_PHOTO_TAG);
        mPhotoPath = data.getString(AppConstant.PHOTO_PATH_TAG);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_upload_photo;
    }

    @Override
    public void initView() {

        initPreview();

        initSpinnerSchool();
    }

    @Override
    public void initData() {

    }

    public void initPreview() {
        new DecodePhotoTask().execute();
    }


    public void initSpinnerSchool() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schools, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchool.setAdapter(adapter);
        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.back_up_btn)
    public void backUpOnClick(View view) {
        this.finish();
    }

    @OnClick(R.id.img_preview_upload_photo)
    public void onClickImagePreview(View view) {
        launchingMixImageActivity();
    }

    @OnClick(R.id.btn_upload_photo)
    public void onClickButtonUpload(View view) {

        String title = editDescriptionPost.getText().toString();
        String school = spinnerSchool.getSelectedItem().toString();
        String accountId = AccountUtil.getAccountId();
        if (accountId != null && !accountId.isEmpty()) {
            if (!title.trim().isEmpty()) {

                if (isDecodePhotoComplete) {

                    ArrayList<String> photoPaths = new ArrayList<>();
                    photoPaths.add(mPhotoPath);
                    Bundle data = new Bundle();
                    data.putInt(AppConstant.FLAG_OPEN_POSTED_HISTORY, PostedHistoryActivity.UPLOAD_PHOTO);
                    data.putStringArrayList(AppConstant.PHOTO_PATH_TAG, photoPaths);
                    data.putString(AppConstant.TITLE_UPLOAD_TAG, title.trim());
                    data.putString(AppConstant.SCHOOL_TAG, school);
                    data.putString(AppConstant.ACCOUNT_ID_TAG, accountId);
                    Intent postedHistoryIntent = new Intent(this, PostedHistoryActivity.class);
                    postedHistoryIntent.putExtra(AppConstant.PACKAGE, data);
                    startActivityForResult(postedHistoryIntent, AppConstant.POSTED_HISTORY_REQUEST_CODE);
                }

            } else {
                Utils.toastShort(this, NO_TITLE);
            }
        } else {
            Utils.toastShort(this, R.string.message_confirm_login);
        }

    }


    public void launchingMixImageActivity() {

        Intent fixImageIntent = new Intent(this, MixImageActivity.class);
        Bundle sentData = new Bundle();
        sentData.putString(AppConstant.PHOTO_PATH_TAG, mPhotoUri.toString());
        fixImageIntent.putExtra(AppConstant.PACKAGE, sentData);
        this.startActivityForResult(fixImageIntent, AppConstant.MIX_IMAGE_REQUEST_CODE);
    }

    private class DecodePhotoTask extends AsyncTask<Void, Void, Void> {

        Bitmap photoBitmap;
        int targetW;
        int targetH;

        @Override
        protected void onPreExecute() {
            ViewGroup.LayoutParams params = imgPreview.getLayoutParams();
            targetW = params.width;
            targetH = params.height;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Log.e(AppConstant.LOG_TAG, mPhotoPath);
            photoBitmap = BitmapUtil.decodePhotoWithNewSize(mPhotoPath, targetW, targetH);
            publishProgress();
            FileUtil.saveImageLocal(photoBitmap, mPhotoPath);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            imgPreview.setImageBitmap(photoBitmap);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            isDecodePhotoComplete = true;
        }
    }

}

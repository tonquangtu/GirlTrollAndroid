package com.bk.girltrollsv.util.networkutil;

import android.util.Log;

import com.bk.girltrollsv.callback.UploadCallback;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.dataserver.MyResponse;
import com.bk.girltrollsv.model.dataserver.ServerResponse;
import com.bk.girltrollsv.networkconfig.ConfigNetwork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 26-Oct-16.
 */
public class UploadUtil {

    public static final String FILE1 = "file_0";
    public static final String FILE2 = "file_1";
    public static final String FILE3 = "file_2";
    public static final String FILE4 = "file_3";

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";


    /**
     * Upload one photo to remote
     * Use retrofit 2, read document of retrofit 2
     * RequestBody to sent data string and Multipart.Part to wrap file into part
     *
     * @param photoPaths
     * @param memberId
     * @param title
     * @param school
     * @param typePhoto  : 0 is anh che else 1 is normal photo
     */
    public static void uploadPhotos2(
            ArrayList<String> photoPaths,
            String memberId,
            String title,
            String school,
            int typePhoto,
            final long postedCode,
            final UploadCallback uploadCallback) {

        if (photoPaths == null || photoPaths.size() == 0) {
            return;
        }

        int totalFile = photoPaths.size();
        RequestBody memberIdBody = createPartString(memberId);
        RequestBody titleBody = createPartString(title);
        RequestBody schoolBody = createPartString(school);
        RequestBody totalFileBody = createPartString(String.valueOf(totalFile));
        RequestBody typeBody = createPartString(String.valueOf(typePhoto));
        String path = "/storage/emulated/0/Android/data/com.bk.girltrollsv/files/Pictures/logo.png";
        Call<MyResponse> call;
        if (totalFile == 1) {
            File photoFile1 = new File(path);
            Log.e("tuton", "file:" + photoPaths.get(0));
//            MultipartBody.Part partFile1 = createPartFile(photoFile1, FILE1);
            RequestBody file1Body = createPartFile(photoFile1);
            call = ConfigNetwork.serviceAPI.uploadSimplePhoto(
                    memberIdBody,
                    titleBody,
                    schoolBody,
                    typeBody,
                    totalFileBody,
                    file1Body
            );

        } else if (totalFile == 2) {

            File photoFile1 = new File(photoPaths.get(0));
            File photoFile2 = new File(photoPaths.get(1));

            MultipartBody.Part partFile1 = createPartFile(photoFile1, FILE1);
            MultipartBody.Part partFile2 = createPartFile(photoFile2, FILE2);
            call = ConfigNetwork.serviceAPI.uploadSimplePhoto(
                    memberIdBody,
                    titleBody,
                    schoolBody,
                    typeBody,
                    totalFileBody,
                    partFile1,
                    partFile2
            );
        } else if (totalFile == 3) {
            File photoFile1 = new File(photoPaths.get(0));
            File photoFile2 = new File(photoPaths.get(1));
            File photoFile3 = new File(photoPaths.get(2));

            MultipartBody.Part partFile1 = createPartFile(photoFile1, FILE1);
            MultipartBody.Part partFile2 = createPartFile(photoFile2, FILE2);
            MultipartBody.Part partFile3 = createPartFile(photoFile3, FILE3);
            call = ConfigNetwork.serviceAPI.uploadSimplePhoto(
                    memberIdBody,
                    titleBody,
                    schoolBody,
                    typeBody,
                    totalFileBody,
                    partFile1,
                    partFile2,
                    partFile3
            );
        } else {
            File photoFile1 = new File(photoPaths.get(0));
            File photoFile2 = new File(photoPaths.get(1));
            File photoFile3 = new File(photoPaths.get(2));
            File photoFile4 = new File(photoPaths.get(3));

            MultipartBody.Part partFile1 = createPartFile(photoFile1, FILE1);
            MultipartBody.Part partFile2 = createPartFile(photoFile2, FILE2);
            MultipartBody.Part partFile3 = createPartFile(photoFile3, FILE3);
            MultipartBody.Part partFile4 = createPartFile(photoFile4, FILE4);
            call = ConfigNetwork.serviceAPI.uploadSimplePhoto(
                    memberIdBody,
                    titleBody,
                    schoolBody,
                    typeBody,
                    totalFileBody,
                    partFile1,
                    partFile2,
                    partFile3,
                    partFile4
            );
        }

        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                try {
//                    Log.e(AppConstant.LOG_TAG, response.errorBody().string());
                    Log.e(AppConstant.LOG_TAG, response.message());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (uploadCallback != null) {
                    MyResponse body = response.body();
                    if (body != null) {
                        uploadCallback.uploadComplete(body.getSuccess(), body.getMessage(), postedCode);
                        Log.e(AppConstant.LOG_TAG, body.getMessage());
                    } else {
                        uploadCallback.uploadFail(postedCode);
                        Log.e(AppConstant.LOG_TAG, "vao null");
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                if (uploadCallback != null) {
                    uploadCallback.uploadFail(postedCode);
                }
            }
        });

    }


    public static void uploadPhotos3(
            ArrayList<String> photoPaths,
            String memberId,
            String title,
            String school,
            int typePhoto,
            final long postedCode,
            final UploadCallback uploadCallback) {

        String path = "/storage/emulated/0/Android/data/com.bk.girltrollsv/files/Pictures/demo.txt";
        if (photoPaths == null || photoPaths.size() == 0) {
            return;
        }

        int totalFile = photoPaths.size();
        File file = new File(path);

        RequestBody requestFile = createRequestFile(file);

        Call<MyResponse> call = ConfigNetwork.serviceAPI.uploadSimplePhoto(
                memberId,
                title,
                school,
                typePhoto + "",
                totalFile + "",
                requestFile
        );

        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                try {
                    Log.e(AppConstant.LOG_TAG, response.errorBody().string());
                    Log.e(AppConstant.LOG_TAG, response.message());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (uploadCallback != null) {
                    MyResponse body = response.body();
                    if (body != null) {
                        uploadCallback.uploadComplete(body.getSuccess(), body.getMessage(), postedCode);
                    } else {
                        uploadCallback.uploadFail(postedCode);
                        Log.e(AppConstant.LOG_TAG, "vao null");
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                if (uploadCallback != null) {
                    uploadCallback.uploadFail(postedCode);
                }
            }
        });

    }

    public static void uploadPhotos(
            ArrayList<String> photoPaths,
            String memberId,
            String title,
            String school,
            int typePhoto,
            final long postedCode,
            final UploadCallback uploadCallback) {

        Map<String, RequestBody> map = new HashMap<>();
        map.put("memberId", createPartString(memberId));
        map.put("title", createPartString(title));
        map.put("school", createPartString(school));
        map.put("type", createPartString(String.valueOf(typePhoto)));
        map.put("totalFile", createPartString(String.valueOf(1)));

        File file = new File(photoPaths.get(0));
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
        map.put("file\"; filename=\"" + file.getName() + "\"", requestFile);
        Call<ServerResponse> call = ConfigNetwork.serviceAPI.uploadSimplePhoto( "token", map);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (uploadCallback != null) {
                    ServerResponse body = response.body();
                    if (body != null) {
                        int success = 0;
                        if (body.getSuccess()) {
                            success = 1;
                        }
                        uploadCallback.uploadComplete(success, body.getMessage(), postedCode);
                    } else {
                        uploadCallback.uploadFail(postedCode);
                        Log.e(AppConstant.LOG_TAG, "vao null");
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if (uploadCallback != null) {
                    uploadCallback.uploadFail(postedCode);
                    t.printStackTrace();
                    Log.e("tuton", "vao");
                }
            }
        });

    }


    private static RequestBody createPartString(String value) {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value);
    }

    private static MultipartBody.Part createPartFile(File file, String tagName) {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(FILE1, file.getName(), requestFile);
    }

    public static RequestBody createPartFile(File file) {
        return RequestBody.create(MediaType.parse("*/*"), file);
    }

    public static RequestBody createRequestFile(File imageFile) {
        RequestBody fileBody = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), imageFile);
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.addFormDataPart("file_0", "file_0", fileBody);
        RequestBody fileRequestBody = multipartBuilder.build();
        return fileRequestBody;
    }

}

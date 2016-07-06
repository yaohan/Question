package com.ssdut411.app.questionanswer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetHeadReq;
import com.ssdut411.app.questionanswer.model.Resp.GetHeadResp;
import com.ssdut411.app.questionanswer.model.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yao_han on 2016/4/20.
 */
public class BitmapUtils {
    public static void setSelfHead(Context context, final ImageView imageView) {
        imageView.setImageResource(R.mipmap.role_student);
        final String head = MainApplication.getInstance().getUser().getHeadPortrait();
        L.i("head：" + head);
        if (head == null || head.length() == 0) {
            showHeadFromNet(context, imageView, MainApplication.getInstance().getUserId());

        }else{
            imageView.setImageBitmap(convertStringToIcon(head));
        }
    }

    public static void showHeadFromNet(Context context, final ImageView imageView, final String userId) {
        AppAction action = new AppActionImpl(context);
        GetHeadReq getHeadReq = new GetHeadReq();
        getHeadReq.setUserId(userId);
        action.getHead(getHeadReq, new ActionCallbackListener<GetHeadResp>() {
            @Override
            public void onSuccess(GetHeadResp data) {
                if (data.getResult() == GetHeadResp.RESULT_SUCCESS) {
                    if(data.getHead() == null || data.getHead().length() == 0){
                        imageView.setImageResource(R.mipmap.role_student);
                    }else{
                        imageView.setImageBitmap(convertStringToIcon(data.getHead()));
                        if(userId.equals(MainApplication.getInstance().getUserId())){
                            UserModel userModel = MainApplication.getInstance().getUser();
                            if (userModel != null) {
                                userModel.setHeadPortrait(data.getHead());
                                MainApplication.getInstance().setUser(userModel);
                            }
                        }
                    }
                } else {
                    imageView.setImageResource(R.mipmap.role_student);
                }
            }

            @Override
            public void onFailure(String message) {
                imageView.setImageResource(R.mipmap.role_student);
            }
        });
    }

    public static boolean saveBitmap(Bitmap bitmap, String fileName) {
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            L.i("保存成功");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 图片转成string
     *
     * @param filePath
     * @return
     */
    public static String convertIconToString(String filePath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
//        Bitmap bitmap = getSmallBitmap(filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        String icon = Base64.encodeToString(appicon, Base64.DEFAULT);
        L.i("icon:" + icon.length());
        return icon;
    }

    private static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, 30, 30);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        L.i("height:" + height + " width:" + width);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRadio = Math.round((float) height / (float) reqHeight);
            int widthRadio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRadio < widthRadio ? heightRadio : widthRadio;
        }
        L.i("inSampleSize:" + inSampleSize);
        return inSampleSize;
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
}

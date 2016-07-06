package com.ssdut411.app.questionanswer.model.model;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by yao_han on 2016/4/21.
 */
public class FormImage {
    //参数的名称
    private String mName = "uploadimg";
    //文件名
    private String mFileName ;
    //文件的 mime，需要根据文档查询
    private String mMime = "image/png";
    //需要上传的图片资源，因为这里测试为了方便起见，直接把 bigmap 传进来，真正在项目中一般不会这般做，而是把图片的路径传过来，在这里对图片进行二进制转换
    private Bitmap mBitmap ;

    public FormImage(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
    //对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        mBitmap.compress(Bitmap.CompressFormat.PNG,80,bos) ;
        return bos.toByteArray();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public String getmMime() {
        return mMime;
    }

    public void setmMime(String mMime) {
        this.mMime = mMime;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
}

package com.ssdut411.app.questionanswer.activity.person;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.MainPageActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.MainActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.FinishInfoReq;
import com.ssdut411.app.questionanswer.model.Req.GetInfoReq;
import com.ssdut411.app.questionanswer.model.Resp.FinishInfoResp;
import com.ssdut411.app.questionanswer.model.Resp.GetInfoResp;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.ActivityStackUtils;
import com.ssdut411.app.questionanswer.utils.BitmapUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.dialog.BaseDialog;
import com.ssdut411.app.questionanswer.widget.dialog.SimpleDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Created by LENOVO on 2016/1/13.
 */
public class InfoActivity extends BaseActivity {
    public static final int REQUEST_CODE = 0;
    public static final int REQUEST_PARENT = 1;
    public static final int REQUEST_PUPILS = 2;
    public static final int RESULT_LOAD_IMAGE = 3;
    public static final int IMAGE_CUT = 4;
    public static final int FLAG_CHOOSE_PHONE = 5;
    public static final int FLAG_CROP_PHONE = 6;
    public String photoImageUrl;
    private String gender = "";
    private String schoolId = "";
    private String classId = "";
    private String parentId = "";
    private String childId = "";
    private ImageView ivHead;
    private EditText etName;
    private Button btMale;
    private Button btFemale;
    private EditText etAge;
    private EditText etSchool;
    private EditText etClass;
    private EditText etParent;
    private EditText etSubject;
    private EditText etChild;
    private EditText etContent;
    private EditText etAddress;
    private int role;
    private BaseDialog headDialog;
    private String picturePath = "";
    private String icon;

    @Override
    protected String initTitle() {
        return "完善信息";
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_sure;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_finish_info;
    }

    @Override
    protected void initViews() {
        ivHead = getImageView(R.id.iv_info_head);
        etName = getEditText(R.id.et_info_name);
        btMale = getButton(R.id.bt_info_male);
        btFemale = getButton(R.id.bt_info_female);
        etAge = getEditText(R.id.et_info_age);
        etSchool = getEditText(R.id.et_info_school);
        etClass = getEditText(R.id.et_info_class);
        etParent = getEditText(R.id.et_info_parent);
        etSubject = getEditText(R.id.et_info_subject);
        etChild = getEditText(R.id.et_info_child);

        etContent = getEditText(R.id.et_info_contact);
        etAddress = getEditText(R.id.et_info_address);
        headDialog = initHeadDialog();
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headDialog.show();
            }
        });
        btMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
                btMale.setBackgroundResource(R.color.title_pressed);
                btFemale.setBackgroundResource(R.color.theme_green);
            }
        });
        btFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                btMale.setBackgroundResource(R.color.theme_green);
                btFemale.setBackgroundResource(R.color.title_pressed);
            }
        });

        etSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectSchoolActivity.class);
                intent.putExtra("target", "school");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        etClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schoolId.equals("")) {
                    T.showShort(context, "请先选择学校");
                } else {
                    Intent intent = new Intent(context, SelectSchoolActivity.class);
                    intent.putExtra("target", "class");
                    intent.putExtra("schoolId", schoolId);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        etParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchPersonActivity.class);
                intent.putExtra("role", ModelConfig.ROLE_PARENT);
                startActivityForResult(intent, REQUEST_PARENT);
            }
        });
        etChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchPersonActivity.class);
                intent.putExtra("role", ModelConfig.ROLE_PUPILS);
                startActivityForResult(intent, REQUEST_PUPILS);
            }
        });


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actions_sure:
                        sure();
                        break;
                }
                return true;
            }
        });
    }

    private BaseDialog initHeadDialog() {
        return new SimpleDialog.Builder(context, R.layout.dialog_easy)
                .setTitle("上传头像")
                .setDesc("请选择上传方式")
                .setPositiveButton("本地", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        local();
                    }
                }).setNegativeButton("拍照", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picture();
                    }
                }).getDialog();
    }

    private void picture() {
        File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yaohan/images/");
        if (!filePath.exists()) {
            L.i("create");
            filePath.mkdirs();
            L.i("create finish");
        }
        L.i("filePath:" + filePath);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String portraitName = generatePortraitName();
        photoImageUrl = portraitName;
//        setPhotoImageUrl(portraitName);
        // RegisteringData.setPhotoImageUrl(portraitName);
        File f = new File(filePath, portraitName);
        Uri u = Uri.fromFile(f);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        startActivityForResult(intent, FLAG_CHOOSE_PHONE);
        headDialog.dismiss();
    }

    private void local() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
        headDialog.dismiss();
    }

    @SuppressLint("SimpleDateFormat")
    private String generatePortraitName() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");// 设置日期格式
        String portrailName = "image_" + df.format(new Date()) + UUID.randomUUID();
        return portrailName;
    }

    private void sure() {
        if (check()) {
            AppAction action = new AppActionImpl(context);
            UserModel userModel = new UserModel();
            userModel.setId(MainApplication.getInstance().getUserId());
            userModel.setRole(MainApplication.getInstance().getRole());
//            userModel.setHeadPortrait(BitmapUtils.convertIconToString(picturePath));
            userModel.setRealName(etName.getText().toString());
            userModel.setGender(gender);
            userModel.setAge(Integer.parseInt(etAge.getText().toString()));
            userModel.setSchoolId(schoolId);
            userModel.setSchoolName(etSchool.getText().toString());
            userModel.setClassId(classId);
            userModel.setClassName(etClass.getText().toString());
            userModel.setParentId(parentId);
            userModel.setParentName(etParent.getText().toString());
            userModel.setChildId(childId);
            userModel.setChildName(etChild.getText().toString());
            userModel.setContact(etContent.getText().toString());
            userModel.setAddress(etAddress.getText().toString());
            userModel.setHeadPortrait(icon);
            MainApplication.getInstance().setUser(userModel);
            FinishInfoReq finishInfoReq = new FinishInfoReq();
            finishInfoReq.setUserModel(userModel);
            action.finishInfo(finishInfoReq, new ActionCallbackListener<FinishInfoResp>() {
                @Override
                public void onSuccess(FinishInfoResp data) {
                    T.showShort(context, data.getDesc());
                    if (data.getResult() == FinishInfoResp.RESULT_SUCCESS) {
                        if (getIntent().getStringExtra("target") != null && getIntent().getStringExtra("target").equals("register")) {
                            Intent intent;
                            L.i("");
                            if (MainApplication.getInstance().getRole() == ModelConfig.ROLE_PUPILS) {
                                intent = new Intent(context, MainPageActivity.class);
                            } else if (MainApplication.getInstance().getRole() == ModelConfig.ROLE_TEACHER) {
                                intent = new Intent(context, com.ssdut411.app.questionanswer.activity.teacher.MainPageActivity.class);
                            } else if (MainApplication.getInstance().getRole() == ModelConfig.ROLE_PARENT) {
                                intent = new Intent(context, com.ssdut411.app.questionanswer.activity.parent.MainPageActivity.class);
                            } else {
                                intent = new Intent(context, MainActivity.class);
                            }
                            startActivity(intent);
                        }
                        finish();
                    }
                }

                @Override
                public void onFailure(String message) {
                    T.showShort(context, getString(R.string.error_message));
                }
            });
        }
    }

    private boolean check() {
        if (etName.getText().toString().equals(getString(R.string.empty))) {
            T.showShort(context, "姓名不能为空");
            return false;
        } else if (gender.equals(getString(R.string.empty))) {
            T.showShort(context, "请选择性别");
            return false;
        } else if (etAge.getText().toString().equals(getString(R.string.empty))) {
            T.showShort(context, "年龄不能为空");
            return false;
        }
        if (role == ModelConfig.ROLE_PUPILS) {
            if (schoolId.equals(getString(R.string.empty))) {
                T.showShort(context, "请选择学校");
                return false;
            } else if (classId.equals(getString(R.string.empty))) {
                T.showShort(context, "请选择班级");
                return false;
            }
        } else if (role == ModelConfig.ROLE_TEACHER) {
            if (schoolId.equals(getString(R.string.empty))) {
                T.showShort(context, "请选择学校");
                return false;
            } else if (classId.equals(getString(R.string.empty))) {
                T.showShort(context, "请选择班级");
                return false;
            }
        } else if (role == ModelConfig.ROLE_PARENT) {
//            if(etChild.getText().toString().equals(getString(R.string.empty))){
//                T.showShort(context,"请选择孩子");
//                return false;
//            }
        }
        return true;
    }

    @Override
    protected void loadData() {
        role = getIntent().getIntExtra("role", ModelConfig.ROLE_NULL);
        L.i("role：" + role);
        if (role == ModelConfig.ROLE_PUPILS) {
            getLinearLayout(R.id.layout_parent).setVisibility(View.GONE);
            getRelativeLayout(R.id.rl_info_subject).setVisibility(View.GONE);
        } else if (role == ModelConfig.ROLE_PARENT) {
            getLinearLayout(R.id.layout_student).setVisibility(View.GONE);
        } else if (role == ModelConfig.ROLE_TEACHER) {
            getLinearLayout(R.id.layout_parent).setVisibility(View.GONE);
            getRelativeLayout(R.id.rl_info_parent).setVisibility(View.GONE);
        } else {
            T.showShort(context, "role error");
            ActivityStackUtils.getInstance().exit();
        }
    }

    @Override
    protected void showView() {
        setCanBack();
        setView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (data.getStringExtra("target").equals("school")) {
                        etSchool.setText(data.getStringExtra("schoolName"));
                        schoolId = data.getStringExtra("schoolId");
                    } else {
                        etClass.setText(data.getStringExtra("className"));
                        classId = data.getStringExtra("classId");
                    }
                    break;
                case REQUEST_PARENT:
                    etParent.setText(data.getStringExtra("name"));
                    parentId = data.getStringExtra("id");
                    break;
                case REQUEST_PUPILS:
                    etChild.setText(data.getStringExtra("name"));
                    childId = data.getStringExtra("id");
                    break;
                // 加载图片 = 2
                case RESULT_LOAD_IMAGE:
                    L.i("RESULT_LOAD_IMAGE");
                    if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                        String picturePath = "";
                        if (data.getData().toString().startsWith("content://")) {
                            picturePath = changeUriToFilePath(data.getData());
                        } else if (data.getData().toString().startsWith("file://")) {
                            picturePath = data.getData().toString().substring("file://".length(), data.getData().toString().length());
                        } else {
                            picturePath = data.getData().toString();
                        }
                        Intent intent = new Intent();
                        intent.setAction("com.android.camera.action.CROP");
                        intent.setDataAndType(Uri.fromFile(new File(picturePath)), "image/*");// mUri是已经选择的图片 Uri
                        L.i("path:" + picturePath);
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1); // 裁剪框比例
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 310); // 输出图片大小
                        intent.putExtra("outputY", 310);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, FLAG_CROP_PHONE);
                    }
                    break;
                // 图片剪切 = 3
                case IMAGE_CUT:

                    L.i("IMAGE_CUT");
                    if (resultCode == RESULT_OK && null != data) {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        uploadPictureTo7niu(bitmap);
                    }
                    break;
                // 这是动用系统相机后调用的-----4
                case FLAG_CHOOSE_PHONE:
                    L.i("FLAG_CHOOSE_PHONE");
                    L.i("resultCode:" + resultCode);
                    if (resultCode == RESULT_OK) {
                        File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yaohan/images/");
                        if (!filePath.exists()) {
                            filePath.mkdirs();
                        }
                        File f = new File(filePath, photoImageUrl);
                        if (requestCode == FLAG_CHOOSE_PHONE) {
                            String path = f.getAbsolutePath();
                            L.i("path:" + path);
                            Intent intent = new Intent();
                            intent.setAction("com.android.camera.action.CROP");
                            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");// mUri是已经选择的图片 Uri
                            intent.putExtra("crop", "true");
                            intent.putExtra("aspectX", 1); // 裁剪框比例
                            intent.putExtra("aspectY", 1);
                            intent.putExtra("outputX", 310); // 输出图片大小
                            intent.putExtra("outputY", 310);
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, FLAG_CROP_PHONE);
                        }
                    }
                    break;
                // 这是是系统相机返回的 = 5
                case FLAG_CROP_PHONE:
                    L.i("FLAG_CROP_PHONE");
                    if (resultCode == RESULT_OK && null != data) {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        uploadPictureTo7niu(bitmap);
                    }
                    break;
            }
        } else {
//            T.showShort(context,"resultCode: "+resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPictureTo7niu(Bitmap bitmap) {
//        ivHead.setImageBitmap(bitmap);
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yaohan/images/" + generatePortraitName();
        if (BitmapUtils.saveBitmap(bitmap, filePath)) {
            icon = BitmapUtils.convertIconToString(filePath);
            L.i("icon：" + icon);
            ivHead.setImageBitmap(BitmapUtils.convertStringToIcon(icon));
        }
    }

    ;

    private String changeUriToFilePath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) {
            throw new NullPointerException("reader file field");
        }
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    private void setView() {

        UserModel userModel = MainApplication.getInstance().getUser();
        if(userModel != null){
            etName.setText(userModel.getRealName());
            gender = userModel.getGender();
            if (gender.equals("male")) {
                btMale.setBackgroundResource(R.color.title_pressed);
            } else {
                btFemale.setBackgroundResource(R.color.title_pressed);
            }
            etAge.setText(userModel.getAge() + "");
            schoolId = userModel.getSchoolId();
            etSchool.setText(userModel.getSchoolName());
            classId = userModel.getClassId();
            etClass.setText(userModel.getClassName());
            etContent.setText(userModel.getContact());
            etAddress.setText(userModel.getAddress());
            icon = userModel.getHeadPortrait();
            BitmapUtils.showHeadFromNet(context, ivHead, userModel.getId());
            if (userModel.getChildName() != null) {
                etChild.setText(userModel.getChildName());
            }
            if (userModel.getParentName() != null) {
                etParent.setText(userModel.getParentName());
            }
        }


//        AppAction action = new AppActionImpl(context);
//        GetInfoReq getInfoReq = new GetInfoReq();
//        getInfoReq.setId(MainApplication.getInstance().getUserId());
//        action.getInfo(getInfoReq, new ActionCallbackListener<GetInfoResp>() {
//            @Override
//            public void onSuccess(GetInfoResp data) {
//                if (data.getResult() == GetInfoResp.RESULT_SUCCESS) {
//                    UserModel userModel = data.getUserModel();
//                    etName.setText(userModel.getRealName());
//                    gender = userModel.getGender();
//                    if (gender.equals("male")) {
//                        btMale.setBackgroundResource(R.color.title_pressed);
//                    } else {
//                        btFemale.setBackgroundResource(R.color.title_pressed);
//                    }
////                    if(userModel.getHeadPortrait() != null){
////                        ivHead.setImageBitmap(BitmapUtils.convertStringToIcon(userModel.getHeadPortrait()));
////                    }
//                    etAge.setText(userModel.getAge() + "");
//                    schoolId = userModel.getSchoolId();
//                    etSchool.setText(userModel.getSchoolName());
//                    classId = userModel.getClassId();
//                    etClass.setText(userModel.getClassName());
//                    etContent.setText(userModel.getContact());
//                    etAddress.setText(userModel.getAddress());
//                    icon = userModel.getHeadPortrait();
//                    BitmapUtils.showHead(context, ivHead, icon);
//                    if (userModel.getChildName() != null) {
//                        etChild.setText(userModel.getChildName());
//                    }
//                    if (userModel.getParentName() != null) {
//                        etParent.setText(userModel.getParentName());
//                    }
//                } else {
////                    T.showShort(context, data.getDesc());
//                }
//            }
//
//            @Override
//            public void onFailure(String message) {
////                T.showShort(context, message);
//            }
//        });
    }

}
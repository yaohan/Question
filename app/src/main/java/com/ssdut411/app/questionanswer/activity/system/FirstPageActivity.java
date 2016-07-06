package com.ssdut411.app.questionanswer.activity.system;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.BaseAdapter;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.MainPageActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.RegisterReq;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.Resp.RegisterResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.SPUtils;
import com.ssdut411.app.questionanswer.utils.T;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 引导页，应用首次进入，2秒后根据本地SP记录的role跳转到对应主页
 * Created by yao_han on 2016/3/2.
 *
 */
public class FirstPageActivity extends BaseActivity {
    private String userId;

    @Override
    protected String initTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_firstpage;
    }

    @Override
    protected void initViews() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        startMainActivity();
    }

    private void startMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int role= MainApplication.getInstance().getRole();
                Intent intent;
                if(role == ModelConfig.ROLE_PUPILS){//学生主页
                    intent = new Intent(context,MainPageActivity.class);
                }else if(role == ModelConfig.ROLE_PARENT){//家长主页
                    intent = new Intent(context, com.ssdut411.app.questionanswer.activity.parent.MainPageActivity.class);
                }else if(role == ModelConfig.ROLE_TEACHER){//老师主页
                    intent = new Intent(context, com.ssdut411.app.questionanswer.activity.teacher.MainPageActivity.class);
                }else{
                    intent = new Intent(context,MainActivity.class);
                    MainApplication.getInstance().setUserId(getUniqueId());
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    public String getUniqueId() {
        TelephonyManager TelephonyMgr = (TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        L.i("TelephonyMgr = null?" + (TelephonyMgr == null));
        String m_szImei = TelephonyMgr.getDeviceId();
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI

                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits
        String m_szAndroidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        WifiManager wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String m_szBTMAC = m_BluetoothAdapter.getAddress();
        String m_szLongID = m_szImei + m_szDevIDShort
                + m_szAndroidID+ m_szWLANMAC + m_szBTMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(),0,m_szLongID.length());
// get md5 bytes
        byte p_md5Data[] = m.digest();
// create a hex string
        String m_szUniqueID = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b =  (0xFF & p_md5Data[i]);
// if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID+="0";
// add number to string
            m_szUniqueID+=Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }

}

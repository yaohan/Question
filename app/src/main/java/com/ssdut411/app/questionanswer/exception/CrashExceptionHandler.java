package com.ssdut411.app.questionanswer.exception;

import android.content.Context;
import android.os.Looper;

import com.ssdut411.app.questionanswer.AppConfig;
import com.ssdut411.app.questionanswer.utils.AppUtils;
import com.ssdut411.app.questionanswer.utils.DeviceUtils;
import com.ssdut411.app.questionanswer.utils.FileUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Created by yao_han on 2015/12/22.
 */
public class CrashExceptionHandler extends BaseExceptionHandler {

    private static final String TAG = "CrashExceptionHandler";

    private Context context;

    public CrashExceptionHandler(Context context) {
        this.context = context;
    }

    /**
     * 自定义异常处理逻辑
     */
    @Override
    protected boolean exceptionHandle(Throwable ex) {
        if (null == ex) {
            return false;
        }

        new Thread() {
            public void run() {
                Looper.prepare();
                T.showShort(context, "很抱歉,程序出现异常,正在从错误中恢复");
                Looper.loop();
            }

            ;
        }.start();

        // 保存异常日志信息
        this.saveLog(ex);

        // 打印异常到控制台
        ex.printStackTrace();

        return true;
    }

    /**
     * 保存异常日志信息
     *
     * @param ex
     */
    private void saveLog(Throwable ex) {
        OutputStream os = null;
        PrintStream ps = null;
        try {
            String logPath = FileUtils.getDiskCacheDir(context) + AppConfig.LOG_PATH;

            // 创建日志目录
            File logDir = new File(logPath);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            String errorFilePath = logPath + AppConfig.LOG_CRASH_FILE;
            L.d("APP崩溃日志文件目录：" + errorFilePath);

            // 创建错误日志文件
            File errorFile = new File(errorFilePath);
            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }

            os = new FileOutputStream(errorFile, true);
            os.write(("\n应用程序名称：" + AppUtils.getAppName(context) + "\n").getBytes());
            os.write(("搜集日期：" + dateFormat.format(new Date()) + "\n").getBytes());

            StringBuffer deviceInfo = new StringBuffer();
            deviceInfo.append("\n----------设备信息：----------\n");
            deviceInfo.append("手机品牌：" + DeviceUtils.getDeviceBrand() + "\n");
            deviceInfo.append("手机型号：" + DeviceUtils.getDeviceModle() + "\n");
            deviceInfo.append("系统版本：" + DeviceUtils.getSystemVersion() + "\n");
            deviceInfo.append("屏幕分辨率：" + DeviceUtils.getScreenSize(context) + "\n");

            os.write(deviceInfo.toString().getBytes());
            os.write(("\n----------错误日志信息：----------\n").getBytes());

            ps = new PrintStream(os);
            ex.printStackTrace(ps);

            ps.flush();
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

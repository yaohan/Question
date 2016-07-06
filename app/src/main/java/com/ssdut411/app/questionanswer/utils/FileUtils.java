package com.ssdut411.app.questionanswer.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by yao_han on 2015/12/22.
 */
public class FileUtils {

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 递归删除指定文件夹下的所有文件（包括该文件夹）
     *
     * @param file
     */
    public static void deleteAll(File file) {
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteAll(f);// 递归删除每一个文件
                f.delete();// 删除该文件夹
            }
            file.delete();
        }
    }

    /**
     * 获取磁盘缓存的路径地址
     *
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }

    /**
     * 在SDCard里创建目录或文件
     * <p/>
     * 如果存在同名文件则删除文件后再创建,同名文件夹不做任何操作
     *
     * @param path
     * @return
     */
    public static String createFileOnSDCard(String path) {
        if (!SDCardUtils.isSDCardEnable()) {
            throw new com.ssdut411.app.questionanswer.exception.BaseException("SDCard不可用");
        }

        if (!path.startsWith(File.separator)) {
            path = File.separator + path;
        }

        File file = new File(SDCardUtils.getSDCardPath() + path);

        if (file.isFile() && file.exists()) {
            file.delete();
        }

        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new com.ssdut411.app.questionanswer.exception.BaseException("文件/文件夹" + path + "创建失败");
            }
        }

        return SDCardUtils.getSDCardPath() + path;
    }

    /**
     * 删除SDCard里的文件或文件夹
     *
     * @param path 文件路径
     */
    public static void deleteFileOnSDCard(String path) {
        if (!SDCardUtils.isSDCardEnable()) {
            throw new com.ssdut411.app.questionanswer.exception.BaseException("SDCard不可用");
        }

        if (!path.startsWith(File.separator)) {
            path = File.separator + path;
        }

        File file = new File(SDCardUtils.getSDCardPath() + path);

        if (file.isFile() && file.exists()) {
            if (!file.delete()) {
                throw new com.ssdut411.app.questionanswer.exception.BaseException("删除文件" + path + "失败");
            }
        }
    }

    /**
     * 获取data/data/package/files目录
     * <p/>
     * 该目录在卸载程序时自动删除
     *
     * @param context
     * @return
     */
    public static String getDataFolderPath(Context context) {
        String sdcard = context.getFilesDir().getAbsolutePath();
        return sdcard;
    }

    /**
     * 在data/data/package/files创建目录或文件
     * <p/>
     * 如果存在同名文件则删除文件后再创建,同名文件夹不做任何操作
     * <p/>
     * 该目录在卸载程序时自动删除
     *
     * @param path 文件夹绝对路径
     * @throws com.ssdut411.exception.BaseException
     */
    public static String createFileOnDataFolder(Context contenxt, String path) {
        if (!path.startsWith(File.separator)) {
            path = File.separator + path;
        }

        File file = new File(getDataFolderPath(contenxt) + path);

        if (file.isFile() && file.exists()) {
            file.delete();
        }

        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new com.ssdut411.app.questionanswer.exception.BaseException("文件/文件夹" + path + "创建失败");
            }
        }

        return getDataFolderPath(contenxt) + path;
    }

    /**
     * 删除data/data/package/files文件或文件夹
     * <p/>
     * 该目录在卸载程序时自动删除
     *
     * @param path 文件路径
     */
    public static void deleteFileOnDataFolder(String path) {
        if (!path.startsWith(File.separator)) {
            path = File.separator + path;
        }

        File file = new File(SDCardUtils.getSDCardPath() + path);

        if (file.isFile() && file.exists()) {
            if (!file.delete()) {
                throw new com.ssdut411.app.questionanswer.exception.BaseException("删除文件" + path + "失败");
            }
        }
    }
}

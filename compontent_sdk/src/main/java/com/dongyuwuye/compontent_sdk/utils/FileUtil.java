package com.dongyuwuye.compontent_sdk.utils;

import android.graphics.Bitmap;
import android.os.Environment;


import com.dongyuwuye.compontent_sdk.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chao on 2016/10/19.
 * 文件保存相关工具
 */

public class FileUtil {

    public static final String ROOT = BuildConfig.APPLICATION_ID + "/voiceCache/";
    public static final String PIC_DIR = "picCache/";

    public static String saveFile(String fileName, Bitmap bitmap) {
        return saveFile("", fileName, bitmap);
    }

    public static String saveFile(String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    //生成文件路径 格式 data/data/UniteRoute/20161019/
    public static String saveFile(String filePath, String fileName, byte[] bytes) {
        String fileFullName;
        FileOutputStream fos = null;
        try {
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = ROOT + PIC_DIR;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName);
            if (fullFile.exists()) {//如果有当前文件，先删除
                fullFile.delete();
            }
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(fullFile);
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    public static File setDir(String filePath, String fileName) {
        String fileFullName;
        if (filePath == null || filePath.trim().length() == 0) {
            filePath = ROOT + PIC_DIR;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File fullFile = new File(filePath, fileName);
        return fullFile;
    }
}

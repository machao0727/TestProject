package com.dongyuwuye.compontent_sdk.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * create by：mc on 2021/4/2 15:32
 * email:
 * 文件存储
 */
public class FileSaveUtils {
    public static void save(String data, Context context, String filename) {
        FileOutputStream output = null;
        try {
            output = context.openFileOutput(filename, Context.MODE_PRIVATE);
            output.write(data.getBytes());  //将String字符串以字节流的形式写入到输出流中
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 这里定义的是文件读取的方法
     * */
    public static String read(String filename, Context context) {
        String result = "";
        try {
            FileInputStream fis = context.openFileInput(filename);
            //获取文件长度
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            //将byte数组转换成指定格式的字符串
            result = new String(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}

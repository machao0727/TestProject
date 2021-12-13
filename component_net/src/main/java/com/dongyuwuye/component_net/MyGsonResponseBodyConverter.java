package com.dongyuwuye.component_net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private Annotation[] annotation;
    private static final int DATA_EXCEPTION = 1000;//默认数据异常code值

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Annotation[] annotation) {
        this.gson = gson;
        this.adapter = adapter;
        this.annotation = annotation;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        if (annotation != null) {
//            for (Annotation item : annotation) {
//                if (item instanceof ResponseProcess) {
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        if (!object.get(((ResponseProcess) item).typeName()).equals(((ResponseProcess) item).typeValue())) {//键值对不相等
//                            String msg = "Date is null or empty";
//                            if (!object.isNull("data") && object.get("data") instanceof String) {
//                                msg = object.getString("data");
//                            }
//                            int code = DATA_EXCEPTION;
//                            if (!object.isNull("code") && object.get("code") instanceof Integer) {
//                                code = object.getInt("code");
//                            }
//                            throw new DataException(msg, code);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
        try {
            JSONObject object = new JSONObject(response);
            if (!object.getBoolean("Result")) {//如果result返回为false，证明是天问接口，并且请求出现错误
                if (object.get("data") instanceof String) {
                    throw new DataException((String) object.get("data"), 100);
                } else {
                    JSONObject error = (JSONObject) object.get("data");
                    throw new DataException((String) error.get("ErrorMsg"), 100);
                }
            }
        } catch (JSONException e) {
            Log.i("tag", "天问接口异常");
        }
        try {
            JSONObject object = new JSONObject(response);
            if (object.getInt("code") != 200 && object.getInt("code") != 0 && object.getInt("code") != 1) {//如果result返回为false，证明是天问接口，并且请求出现错误
                throw new DataException((String) object.get("msg"), object.getInt("code"));
            }
        } catch (JSONException e) {
            Log.i("tag", "code 500异常");
        }
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}

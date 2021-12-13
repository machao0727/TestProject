package com.dongyuwuye.compontent_sdk.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

public class EntityUtils {

    /**
     * 实体类转map
     *
     * @param obj
     * @return
     */
    public static HashMap<String, String> entityToMap(Object obj) {
        HashMap<String, String> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        try {
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object objField = field.get(obj);
                if (objField != null && StringUtils.isNotNullOrEmpty(objField.toString())) {
                    map.put(field.getName(), objField.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

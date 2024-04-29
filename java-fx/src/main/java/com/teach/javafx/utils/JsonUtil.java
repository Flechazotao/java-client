package com.teach.javafx.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class JsonUtil {
    public static <T> T parse(Object object, Class<T> tClass){
        LinkedHashMap<?,?> o=new LinkedHashMap<>();
        if(object instanceof LinkedHashMap<?,?>){
            o = (LinkedHashMap<?, ?>) object;
        }
        T t = JSONObject.parseObject(JSON.toJSONString(o), tClass);
        Field[] fields = tClass.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            if(o.containsKey(field.getName())){
                if (o.get(field.getName()).getClass()==o.getClass()){
                    try {
                        field.set(t,parse(o.get(field.getName()),field.getType()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }
}

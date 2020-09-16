package com.zc.resulthelperlib.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zc.resulthelperlib.IntentValue;
import com.zc.resulthelperlib.OnResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Project: ActivityResultHelper
 * ClassName: ActivityResultHelper
 * Date: 2020/9/16 10:20
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is ActivityResultHelper description !
 */
public class ActivityResultHelper {

    /**
     * 注解处理activity返回数据, 需要在onActivityResult中调用该方法
     * @param activity activity
     * @param requestCode 请求码
     * @param resultCode 结果
     * @param data 数据
     */
    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data){
        Method[] methods = activity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            OnResult annotation = method.getAnnotation(OnResult.class);
            if (annotation == null){
                continue;
            }
            if (annotation.requestCode() != requestCode ||
                    resultCode != annotation.resultCode()){
                continue;
            }
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 0){
                try {
                    method.setAccessible(true);
                    method.invoke(activity);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                continue;
            }
            ArrayList<Object> objects = new ArrayList<>();
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0; i < types.length; i++) {
                Class<?> type = types[i];
                //获取参数注解数组
                Annotation[] paramAnnotations = annotations[i];
                Object object = null;
                if (paramAnnotations.length == 0) {
                    if (type.equals(Intent.class)){
                        object = data;
                    }
                } else {
                    for (Annotation paramAnnotation : paramAnnotations) {
                        if (paramAnnotation instanceof IntentValue) {
                            IntentValue intentKey = (IntentValue) paramAnnotation;
                            String key = intentKey.key();
                            if (data != null && data.hasExtra(key)) {
                                if (data.getExtras() != null) {
                                    object = data.getExtras().get(key);
                                }
                            }
                            break;
                        }
                    }
                }
                if (object == null && type.isPrimitive()){
                    if (type.getName().endsWith("boolean")){
                        object = false;
                    }else {
                        object = 0;
                    }
                }
                objects.add(object);
            }
            try {
                method.setAccessible(true);
                method.invoke(activity, objects.toArray(new Object[]{}));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将 intent 中的值注入到activity中
     * @param activity activity
     */
    public static void injectIntentValue(Activity activity){
        Intent intent = activity.getIntent();
        if (intent == null){
            return;
        }
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            IntentValue annotation = field.getAnnotation(IntentValue.class);
            if (annotation == null) continue;
            Bundle intentExtras = intent.getExtras();
            if (intentExtras == null) continue;
            String key = annotation.key();
            if (! intentExtras.containsKey(key)) continue;
            Object o = intentExtras.get(key);
            try {
                field.setAccessible(true);
                field.set(activity, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

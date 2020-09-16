package com.zc.resulthelperlib;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: ActivityResultHelper
 * ClassName: OnResult
 * Date: 2020/9/16 09:00
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is OnResult description !
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public  @interface OnResult {
    int requestCode();
    int resultCode() default Activity.RESULT_OK;
}

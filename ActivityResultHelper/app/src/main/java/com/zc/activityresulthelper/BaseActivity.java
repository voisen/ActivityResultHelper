package com.zc.activityresulthelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.zc.resulthelperlib.helper.ActivityResultHelper;

/**
 * Project: ActivityResultHelper
 * ClassName: BaseActivity
 * Date: 2020/9/16 09:10
 * Creator: wuzhicheng
 * Email: voisen@icloud.com
 * Version: 1.0
 * Description:  this is BaseActivity description !
 */
public class BaseActivity extends ComponentActivity {
    public final String TAG = this.getClass().getSimpleName();
    private Toast toast;

    protected synchronized void showToast(String msg){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultHelper.injectIntentValue(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultHelper.onActivityResult(this, requestCode, resultCode, data);
    }
}

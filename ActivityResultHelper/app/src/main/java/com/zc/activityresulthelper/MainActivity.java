package com.zc.activityresulthelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zc.resulthelperlib.IntentValue;
import com.zc.resulthelperlib.OnResult;

import java.util.Set;

public class MainActivity extends BaseActivity {

    private String name;
    private String nickname;
    private int age = 0;
    private TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 需要取出intent内的值
     * 注意类型一定要匹配, 否则将抛出异常
     * 其中intent无需使用注解@IntentValue
     * @param intent intent
     * @param name name
     * @param nickname nickname
     * @param age age
     */
    @OnResult(requestCode = 100)
    void onResult(Intent intent,
                  @IntentValue(key = "name") String name,
                  @IntentValue(key = "nickname") String nickname,
                  @IntentValue(key = "age") int age) {
        this.name = name;
        this.nickname = nickname;
        this.age = age;

        tvDesc.setText(String.format("\t姓名: %s\n\t昵称: %s\n\t年龄: %s", name, nickname, age));
    }

    @OnResult(requestCode = 101)
    private void onNoArgsResult() {
        showToast("onNoArgsResult");
    }

    @OnResult(requestCode = 100)
    private void onOnlyIntentResult(Intent intent) {
        showToast("Intent:");
        Set<String> keySet = intent.getExtras().keySet();
        for (String s : keySet) {
            Log.i(TAG, "onOnlyIntentResult: "+s+" : "+intent.getExtras().get(s));
        }
    }

    public void inputClick(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("nickname", nickname);
        intent.putExtra("age", age);
        startActivityForResult(intent,
                100);
    }

    private void initView() {
        tvDesc = findViewById(R.id.tv_desc);
    }
}
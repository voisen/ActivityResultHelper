package com.zc.activityresulthelper;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zc.resulthelperlib.IntentValue;

public class TestActivity extends BaseActivity {

    private EditText etName;
    private EditText etNickname;
    private EditText etAge;

    @IntentValue(key = "name")
    private String name;
    @IntentValue(key = "nickname")
    private String nickname;
    @IntentValue(key = "age")
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        etName.setText(name);
        etNickname.setText(nickname);
        etAge.setText(age+"");
    }

    public void inputFinish(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", etName.getText().toString());
        intent.putExtra("nickname", etNickname.getText().toString());
        intent.putExtra("age", Integer.parseInt(etAge.getText().toString()));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etNickname = findViewById(R.id.et_nickname);
        etAge = findViewById(R.id.et_age);
    }
}
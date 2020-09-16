# ActivityResultHelper
一个Activity跳转间的帮助类， 方便参数传递与接收

### 使用

- 导入项目下的`resulthelperlib`到你的项目中或下载[resulthelperlib.aar](https://github.com/voisen/ActivityResultHelper/blob/master/ActivityResultHelper/aar/resulthelperlib.aar)
- 在`activity`中加入以下代码

````java

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注入参数
        ActivityResultHelper.injectIntentValue(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调处理
        ActivityResultHelper.onActivityResult(this, requestCode, resultCode, data);
    }

````

假如从`A`页面跳转到`B`页面， `A`页面需要传递参数到`B`页面， `B`页面需要`finish`时需要传递参数到`A`页面，两个页面配置如下:

- A

````

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
    }

public void toB() {
        Intent intent = new Intent(this, B.class);
        intent.putExtra("name", name);
        intent.putExtra("nickname", nickname);
        intent.putExtra("age", age);
        startActivityForResult(intent,
                100);
    }

````


- B

````
    @IntentValue(key = "name")
    private String name;
    @IntentValue(key = "nickname")
    private String nickname;
    @IntentValue(key = "age")
    private int age;

	public void finishB() {
        Intent intent = new Intent();
        intent.putExtra("name", "姓名");
        intent.putExtra("nickname", "昵称");
        intent.putExtra("age", 23);
        setResult(RESULT_OK, intent);
        finish();
    }

````


### 注意

返回参数以及接收参数的类型一定要匹配， 否则会导致异常发生 ！

### 作者语

如果您有好的方案，欢迎提出 ~

package pr.tongson.train_okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pr.tongson.train_okhttp.mine.Call2;
import pr.tongson.train_okhttp.mine.Callback2;
import pr.tongson.train_okhttp.mine.OkHttpClient2;
import pr.tongson.train_okhttp.mine.Request2;
import pr.tongson.train_okhttp.mine.Response2;

public class MainActivity extends AppCompatActivity {

    private final String PATH = "https://api.github.com/";
    private Button mBtnOkHttp;
    private Button mBtnMyOkHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnOkHttp = (Button) findViewById(R.id.btn_okHttp);
        mBtnMyOkHttp = (Button) findViewById(R.id.btn_myOkHttp);
        mBtnOkHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useOkHttp();
            }
        });

        mBtnMyOkHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useMyOkHttp();
            }
        });
    }

    public void useOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        final Request request = new Request.Builder().url(PATH).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Tongson", "onFailure:" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Tongson", "onResponse:" + response.body().string());
            }
        });
    }

    public void useMyOkHttp() {
        OkHttpClient2 okHttpClient2 = new OkHttpClient2.Builder().build();
        final Request2 request = new Request2.Builder().url(PATH).build();

        Call2 call = okHttpClient2.newCall(request);
        call.enqueue(new Callback2() {
            @Override
            public void onFailure(Call2 call, IOException e) {
                Log.i("Tongson", "自定义OKHTTP请求失败...自定義onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(Call2 call, Response2 response) throws IOException {
                Log.i("Tongson", "OKHTTP请求成功.... result:" + response.getBody() + " 请求结果码：" + response.getStatusCode());

            }
        });
    }


}

package pr.tongson.train_okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pr.tongson.train_okhttp.demo.Api;
import pr.tongson.train_okhttp.demo.Bean;
import pr.tongson.train_okhttp.demo.Repos;
import pr.tongson.train_okhttp.mine.Call2;
import pr.tongson.train_okhttp.mine.Callback2;
import pr.tongson.train_okhttp.mine.OkHttpClient2;
import pr.tongson.train_okhttp.mine.Request2;
import pr.tongson.train_okhttp.mine.Response2;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private final String PATH = "https://api.github.com/";
    private Button mBtnOkHttp;
    private Button mBtnMyOkHttp;
    private String TAG="Tongson";

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



    private void requsetTest() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://api.github.com/").
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build();

        Api api = retrofit.create(Api.class);
        api.getRepos("gepriniusce").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<List<Repos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("正在请求");
                    }

                    @Override
                    public void onSuccess(List<Repos> repos) {
                        System.out.println("onSuccess");
                        System.out.println(repos.get(0).getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("onError");
                        System.out.println(e.getMessage());
                    }
                });
    }

    interface Service {
        @GET("openapi.do")
        retrofit2.Call<Bean> getCall(@Query("keyfrom") String keyFrom,
                                     @Query("key") String key,
                                     @Query("type") String type,
                                     @Query("doctype") String docType,
                                     @Query("version") String version,
                                     @Query("q") String q
        );
    }

    /**
     * https://fanyi.youdao.com/openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car
     */
    private void requestGet() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://fanyi.youdao.com/").
                addConverterFactory(GsonConverterFactory.create()).
                build();


        Service service = retrofit.create(Service.class);

        service.getCall("Yanzhikai",
                "2032414398",
                "data",
                "json",
                "1.1",
                "car").enqueue(new retrofit2.Callback<Bean>() {
            @Override
            public void onResponse(retrofit2.Call<Bean> call, retrofit2.Response<Bean> response) {
                Log.i(TAG, "response:" + response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<Bean> call, Throwable t) {
                Log.i(TAG, "t:" + t.getMessage());

            }
        });
    }

}

package pr.tongson.train_zip;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Tongson Retrofit";
    private TextView mBtnDo;

    private String path;
    private String descDir;
    private Button mBtnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean sdCardExist = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //为真则SD卡已装入，
            sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();
            descDir = sdDir.getAbsolutePath() + "/share/";
            path = sdDir.getAbsolutePath() + "/share/压缩.zip";
        }

        initView();
    }

    private void initView() {
        mBtnDo = (TextView) findViewById(R.id.btn_do);
        mBtnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ZipUtil.unZipFiles(path, descDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGet();
            }


        });
    }


    interface Service {
        @Headers({"Content-Type:application/json",
                "Accept-Encoding:application/json",
                "User-Agent:MoonRetrofit"
        })
        @GET
        Call<RequestBody> getCall();
    }



    private void requestGet() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://fanyi.youdao.com/").
                addConverterFactory(GsonConverterFactory.create()).
                build();


        Service service = retrofit.create(Service.class);

        service.getCall().enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.i(TAG, "response:" + response.body());
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.i(TAG, "t:" + t.getMessage());

            }
        });

    }


}

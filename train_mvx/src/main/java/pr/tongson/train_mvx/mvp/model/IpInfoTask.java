package pr.tongson.train_mvx.mvp.model;

import android.util.Log;

import pr.tongson.train_mvx.mvp.model.retrofit.IpService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * https://blog.csdn.net/hongyuancao/article/details/84963526
 *
 * @author tongson
 */
public class IpInfoTask implements NetTask<String> {

    private static IpInfoTask INSTANCE = null;

    /**
     * https://ip.taobao.com/service/getIpInfo.php?ip=103.27.25.105
     *
     * https://apis.map.qq.com/ws/location/v1/ip?ip=61.135.17.68&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77
     */
    private static final String HOST = "http://ip.taobao.com/service/getIpInfo.php";
    private static final String URL = "https://apis.map.qq.com/ws/location/v1/";

    private LoadTasksCallback mLoadTasksCallback;

    private IpInfoTask() {

    }

    public static IpInfoTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IpInfoTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(String ip, final LoadTasksCallback callback) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        IpService ipService = retrofit.create(IpService.class);
        Call<IpInfo> call = ipService.getIpMsg();

        call.enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                IpInfo ipInfo = response.body();
                callback.onSuccess(ipInfo);
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                Log.e("Tongson's Log", t.getMessage());
                callback.onFailed();
            }
        });
    }
}

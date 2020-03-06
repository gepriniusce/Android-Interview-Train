package pr.tongson.train_okhttp.mine.chain;

import android.util.Log;

import java.io.IOException;

import pr.tongson.train_okhttp.mine.OkHttpClient2;
import pr.tongson.train_okhttp.mine.RealCall2;
import pr.tongson.train_okhttp.mine.Response2;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ReRequestInterceptor implements Interceptor2 {

    @Override
    public Response2 intercept(Chain2 chain) throws IOException {
        Log.d("Tongson ,", "我是重試攔截器，執行了");
        ChainManager chainManager = (ChainManager) chain;

        RealCall2 realCall2 = chainManager.getCall();
        OkHttpClient2 client2 = realCall2.getClient();

        if (client2.getRecount() != 0) {
            for (int i = 0; i < client2.getRecount(); i++) {
                try {
                    Log.d("Tongson ,", "我是重試攔截器，我要return Response2");
                    Response2 response2 = chain.proceed(chain.request());
                    return response2;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d("Tongson ,", "沒有配置");
        }
        return chainManager.proceed(chain.request());
    }
}

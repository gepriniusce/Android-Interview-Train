package pr.tongson.train_okhttp.mine.chain;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import pr.tongson.train_okhttp.mine.Request2;
import pr.tongson.train_okhttp.mine.RequestBody2;
import pr.tongson.train_okhttp.mine.Response2;
import pr.tongson.train_okhttp.mine.SocketRequestServer;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * 请求头拦截器处理
 *
 * @author tongson
 */
public class RequestHeaderInterceptor implements Interceptor2 {


    @Override
    public Response2 intercept(Chain2 chain) throws IOException {
        Log.d("Tongson ,", "我是請求頭攔截器，執行了");

        ChainManager chainManager = (ChainManager) chain;

        Request2 request2 = chain.request();

        Map<String, String> mHeaderList = request2.getHeaderList();
        mHeaderList.put("Host", new SocketRequestServer().getHost(request2));

        if ("POST".equalsIgnoreCase(request2.getRequestMethod())) {
            mHeaderList.put("Content-Length", request2.getRequestBody2().getBody().length() + "");
            mHeaderList.put("Content-Type", RequestBody2.TYPE);
        }

        Log.d("Tongson ,", "我是請求頭攔截器，chain-index:" + chainManager.getIndex());
        // 执行下一个拦截器（任务节点）
        return chainManager.proceed(request2);
    }
}

package pr.tongson.train_okhttp.mine.chain;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import pr.tongson.train_okhttp.mine.Call2;
import pr.tongson.train_okhttp.mine.RealCall2;
import pr.tongson.train_okhttp.mine.Request2;
import pr.tongson.train_okhttp.mine.Response2;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ChainManager implements Chain2 {

    private final List<Interceptor2> interceptors;
    private int index;
    private final Request2 request;
    private final RealCall2 call;

    public ChainManager(List<Interceptor2> interceptors, int index, Request2 request, RealCall2 call) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
        this.call = call;
    }

    @Override
    public Response2 proceed(Request2 request) throws IOException {
        if (index >= interceptors.size()) {
            throw new AssertionError();
        }

        if (interceptors.isEmpty()) {
            throw new IOException("interceptors is empty!");
        }
        //取出 index 攔截器
        Interceptor2 interceptor2 = interceptors.get(index);

        //new index++ ChainManager
        ChainManager manager = new ChainManager(interceptors, ++index, request, call);

        //執行Interceptor2攔截器
        Response2 response2 = interceptor2.intercept(manager);
        return response2;
    }

    public int getIndex() {
        return index;
    }

    public RealCall2 getCall() {
        return call;
    }

    @Override
    public Request2 request() {
        return request;
    }
}

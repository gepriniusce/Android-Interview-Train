package pr.tongson.train_okhttp.mine.chain;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.Connection;
import pr.tongson.train_okhttp.mine.Call2;
import pr.tongson.train_okhttp.mine.Request2;
import pr.tongson.train_okhttp.mine.Response2;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface Chain2 {

    Request2 request();

    Response2 proceed(Request2 request) throws IOException;

//    /**
//     * Returns the connection the request will be executed on. This is only available in the chains
//     * of network interceptors; for application interceptors this is always null.
//     */
//    @Nullable
//    Connection connection();
//
//    Call2 call();
//
//    int connectTimeoutMillis();
//
//    Chain2 withConnectTimeout(int timeout, TimeUnit unit);
//
//    int readTimeoutMillis();
//
//    Chain2 withReadTimeout(int timeout, TimeUnit unit);
//
//    int writeTimeoutMillis();
//
//    Chain2 withWriteTimeout(int timeout, TimeUnit unit);
}

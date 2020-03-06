package pr.tongson.train_okhttp.mine.chain;

import java.io.IOException;

import pr.tongson.train_okhttp.mine.Response2;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface Interceptor2 {

    Response2 intercept(Chain2 chain) throws IOException;

}

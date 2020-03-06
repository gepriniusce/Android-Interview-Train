package pr.tongson.train_okhttp.mine;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * <b>Create Date:</b> 2020-03-04<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface Callback2 {

    void onFailure(Call2 call, IOException e);


    void onResponse(Call2 call, Response2 response) throws IOException;
}

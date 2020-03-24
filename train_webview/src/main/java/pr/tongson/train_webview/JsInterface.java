package pr.tongson.train_webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * <b>Create Date:</b> 2020-03-20<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class JsInterface {

    private Context mContext;

    public JsInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void hello() {
        Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void hello(String params) {
        Toast.makeText(mContext, params, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getAndroid() {
        Toast.makeText(mContext, "getAndroid", Toast.LENGTH_SHORT).show();
        return "Android data";
    }

    @JavascriptInterface
    public String getAndroidTime() {
        Toast.makeText(mContext, "getAndroidTime：" + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
        return String.valueOf(System.currentTimeMillis());
    }
}

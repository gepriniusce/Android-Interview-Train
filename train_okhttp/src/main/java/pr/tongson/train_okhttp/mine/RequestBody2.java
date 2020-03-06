package pr.tongson.train_okhttp.mine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Create Date:</b> 2020-03-06<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class RequestBody2 {

    /**
     * 表单提交Type application/x-www-form-urlencoded
     */
    public static final String TYPE = "application/x-www-form-urlencoded";
    private final String ENC = "utf-8";
    Map<String, String> bodys = new HashMap<>();


    public void addBody(String key, String value) {
        try {
            bodys.put(URLEncoder.encode(key, ENC), URLEncoder.encode(value, ENC));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getBody() {
        StringBuffer stringBuffer = new StringBuffer();

        for (Map.Entry<String, String> stringStringEntry : bodys.entrySet()) {
            stringBuffer.
                    append(stringStringEntry.getKey()).
                    append("=").
                    append(stringStringEntry.getValue()).
                    append("&");
        }

        if (stringBuffer.length() != 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        return stringBuffer.toString();
    }
}

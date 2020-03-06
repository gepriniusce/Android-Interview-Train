package pr.tongson.train_okhttp.mine;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class SocketRequestServer {
    private final String K = " ";
    private final String VIERSION = "HTTP/1.1";
    private final String GRGN = "\r\n";

    /**
     * 找到域名HOST
     *
     * @param request2
     * @return
     */
    public String getHost(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 獲取端口
     *
     * @param request2
     * @return
     */
    public int getPort(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            int port = url.getPort();
            return port == -1 ? url.getDefaultPort() : port;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 獲取請求透的所有信息
     *
     * @param request2
     * @return
     */
    public String getRequestHeaderAll(Request2 request2) {
        // 得到请求方式
        URL url = null;
        try {
            url = new URL(request2.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String file = url.getFile();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request2.getRequestMethod()).
                append(K).
                append(file).
                append(K).
                append(VIERSION).
                append(GRGN);


        // TODO 获取请求集 进行拼接
        /**
         * Content-Length: 48\r\n
         * Host: api.guihub.com\r\n
         * Content-Type: application/x-www-form-urlencoded\r\n
         */
        if (!request2.getHeaderList().isEmpty()) {
            Map<String, String> mapList = request2.getHeaderList();
            for (Map.Entry<String, String> entry : mapList.entrySet()) {
                stringBuffer.
                        append(entry.getKey()).
                        append(":").
                        append(K).
                        append(entry.getValue()).
                        append(GRGN);
            }
            // 拼接空行，代表下面的POST，请求体了
            stringBuffer.append(GRGN);
        }

        // TODO POST请求才有 请求体的拼接
        if ("POST".equalsIgnoreCase(request2.getRequestMethod())) {
            stringBuffer.append(request2.getRequestBody2().getBody()).append(GRGN);
        }
        Log.d("Tongson ,", "-->getRequestHeaderAll:" + stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * HTTP還是HTTPS
     *
     * @param urlString
     * @return
     */
    public String queryHttpOrHttps(String urlString) {
        try {
            URL url = new URL(urlString);

            return url.getProtocol();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

package pr.tongson.train_okhttp.mine.chain;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import pr.tongson.train_okhttp.mine.Request2;
import pr.tongson.train_okhttp.mine.Response2;
import pr.tongson.train_okhttp.mine.SocketRequestServer;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ConnectionServerInterceptor implements Interceptor2 {
    @Override
    public Response2 intercept(Chain2 chain) throws IOException {
        SocketRequestServer srs = new SocketRequestServer();
        Request2 request2 = chain.request();
        Socket socket = new Socket(srs.getHost(request2), srs.getPort(request2));
        String result = srs.queryHttpOrHttps(request2.getUrl());
        Log.d("Tongson ,", "-->getHost:" + srs.getHost(request2));
        Log.d("Tongson ,", "-->getPort:" + srs.getPort(request2));

        if (result != null) {
            if ("HTTP".equalsIgnoreCase(result)) {
                //只能訪問HTTP，不能訪問HTTPS S SSL 握手
                socket = new Socket(srs.getHost(request2), srs.getPort(request2));
            } else if ("HTTPS".equalsIgnoreCase(result)) {
                //HTTPS
                socket = SSLSocketFactory.getDefault().createSocket(srs.getHost(request2), srs.getPort(request2));
            }
        }

        //請求
        OutputStream os = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        String requestAll = srs.getRequestHeaderAll(request2);
        Log.d("Tongson-->", "-->requestAll:" + requestAll);

        // 给服务器发送请求 --- 请求头信息 所有的
        bw.write(requestAll);
        //真正的寫出去...
        bw.flush();

        //響應
        final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Response2 response2 = new Response2();
        //todo 取出響應碼
        //讀取第一條 響應頭信息
        String readLine = br.readLine();
        // 服务器响应的:HTTP/1.1 200 OK
        String[] strings = readLine.split(" ");
        response2.setStatusCode(Integer.parseInt(strings[1]));

        //todo  取出響應體，只要是空行下面的，就是響應體
        StringBuffer sb=new StringBuffer();
        while (true) {
            String readLineAll = br.readLine();
            if (readLineAll != null) {
                // 读到空行了，就代表下面就是 响应体了
                Log.d("Tongson-->", "-->響應的數據:" + readLineAll);
                sb.append(readLineAll);
            } else {
                break;
            }
        }
        response2.setBody(sb.toString());
        Log.d("Tongson-->", "return");
        return response2;
    }
}

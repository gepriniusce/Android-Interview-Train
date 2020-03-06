package pr.tongson.train_okhttp.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class Test01 {

    private static final String PATH = "https://api.github.com";
    private static final String PATH2 = "https://192.168.1.1:8080";

    public static void main(String[] args) {
        //urlAction();
        socketHTTPS();
    }

    private static void urlAction() {
        try {
            //統一資源定位符
            URL url = new URL(PATH);

            System.out.println("" + url.getProtocol());
            System.out.println("" + url.getHost());
            System.out.println("" + url.getFile());
            System.out.println("" + url.getQuery());
            System.out.println(url.getPort() + "---" + url.getDefaultPort());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 傳輸層
     */
    private static void socketHTTPS() {
        //https:440 http:80
        try {
            //            Socket socket = new Socket("www.baidu.com/", 443);

            //
            Socket socket = SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443);

            //寫出去 請求
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            /**
             * GET / HTTP/1.1
             * Host: api.github.com
             */
            bw.write("GET / HTTP/1.1\r\n");
            bw.write("Host: www.baidu.com\r\n\n");
            bw.flush();


            //讀取數據 響應

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String readLine = br.readLine();
                if (readLine != null) {
                    System.out.println("響應的數據：" + readLine);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void socketHTTP() {
        //https:440 http:80
        try {
            //            Socket socket = new Socket("www.baidu.com/", 443);

            //
            Socket socket = SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443);

            //寫出去 請求
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            /**
             * GET / HTTP/1.1
             * Host: www.baidu.com
             */
            bw.write("GET / HTTP/1.1\r\n");
            bw.write("Host: www.baidu.com\r\n\n");
            bw.flush();


            //讀取數據 響應

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String readLine = br.readLine();
                if (readLine != null) {
                    System.out.println("響應的數據：" + readLine);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void socketHTTPPost() {

    }

    private static void queryHttpOrHttps() {

    }


}

package pr.tongson.train_okhttp.mine;


import android.util.Log;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import pr.tongson.train_okhttp.mine.chain.ChainManager;
import pr.tongson.train_okhttp.mine.chain.ConnectionServerInterceptor;
import pr.tongson.train_okhttp.mine.chain.Interceptor2;
import pr.tongson.train_okhttp.mine.chain.ReRequestInterceptor;
import pr.tongson.train_okhttp.mine.chain.RequestHeaderInterceptor;


/**
 * <b>Create Date:</b> 2020-03-04<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class RealCall2 implements Call2 {
    private boolean executed;


    OkHttpClient2 client;

    Request2 originalRequest;
    boolean forWebSocket;

    private RealCall2(OkHttpClient2 client, Request2 originalRequest, boolean forWebSocket) {
        this.client = client;
        this.originalRequest = originalRequest;
        this.forWebSocket = forWebSocket;
    }

    public static Call2 newRealCall(OkHttpClient2 client, Request2 originalRequest, boolean forWebSocket) {
        RealCall2 call = new RealCall2(client, originalRequest, forWebSocket);
        return call;
    }

    @Override
    public void enqueue(Callback2 responseCallback) {
        //不能被重複的執行 enqueue
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("Already Executed");
            }
            executed = true;
        }


        client.dispatcher().enqueue(new RealCall2.AsyncCall(responseCallback));
    }


    final class AsyncCall implements Runnable {
        private final Callback2 responseCallback;
        private volatile AtomicInteger callsPerHost = new AtomicInteger(0);

        AsyncCall(Callback2 responseCallback) {
            this.responseCallback = responseCallback;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response2 response = getResponseWithInterceptorChain();
                signalledCallback = true;
                responseCallback.onResponse(RealCall2.this, response);
            } catch (IOException e) {
                if (signalledCallback) {
                    // Do not signal the callback twice!
                    //Platform.get().log(INFO, "Callback failure for " + toLoggableString(), e);
                    Log.i("Tongson", "用戶在使用過程中出錯：" + e.getMessage());
                } else {
                    responseCallback.onFailure(RealCall2.this, e);
                }
            } catch (Throwable t) {
                if (!signalledCallback) {
                    IOException canceledException = new IOException("canceled due to " + t);
                    canceledException.addSuppressed(t);
                    responseCallback.onFailure(RealCall2.this, canceledException);
                }
                throw t;
            } finally {
                client.dispatcher().finished(this);
            }
        }

        AtomicInteger callsPerHost() {
            return callsPerHost;
        }


        void executeOn(ExecutorService executorService) {
            assert (!Thread.holdsLock(client.dispatcher()));
            boolean success = false;
            try {
                executorService.execute(this);
                success = true;
            } catch (RejectedExecutionException e) {
                InterruptedIOException ioException = new InterruptedIOException("executor rejected");
                ioException.initCause(e);
                //transmitter.noMoreExchanges(ioException);
                responseCallback.onFailure(RealCall2.this, ioException);
            } finally {
                if (!success) {
                    client.dispatcher().finished(this); // This call is no longer running!
                }
            }
        }

    }

    public OkHttpClient2 getClient() {
        return client;
    }


    /**
     * 從這裏還是弄響應流程
     */
    //    Response2 getResponseWithInterceptorChain() throws IOException {
    //        Response2 response2 = new Response2();
    //        response2.setBody("流程走通");
    //        return response2;
    //    }


    Response2 getResponseWithInterceptorChain() throws IOException {
        // Build a full stack of interceptors.
        List<Interceptor2> interceptors = new ArrayList<>();
        // 重试拦截器 Response
        interceptors.add(new ReRequestInterceptor());
        // 请求头拦截器 Response
        interceptors.add(new RequestHeaderInterceptor());
        // 连接服务器的拦截器 Response
        interceptors.add(new ConnectionServerInterceptor());
        ChainManager chainManager = new ChainManager(interceptors, 0, originalRequest, RealCall2.this);

        return chainManager.proceed(originalRequest);
    }


}

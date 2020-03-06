package pr.tongson.train_okhttp.mine;


import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

/**
 * <b>Create Date:</b> 2020-03-04<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class Dispatcher2 {

    /**
     * 同時訪問任務，最大限制64個
     */
    private int maxRequests = 64;
    /**
     * 同時訪問同一個服務器域名，最大限制5個
     */
    private int maxRequestsPerHost = 5;

    private @Nullable
    ExecutorService executorService;

    /**
     * 存儲等待的隊列
     */
    /**
     * Ready async calls in the order they'll be run.
     */
    private final Deque<RealCall2.AsyncCall> readyAsyncCalls = new ArrayDeque<>();

    /**
     * 存儲運行的隊列
     */
    /**
     * Running asynchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<RealCall2.AsyncCall> runningAsyncCalls = new ArrayDeque<>();

    /**
     * Running synchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<RealCall2> runningSyncCalls = new ArrayDeque<>();


    void enqueue(RealCall2.AsyncCall call) {
        synchronized (this) {
            readyAsyncCalls.add(call);

            // Mutate the AsyncCall so that it shares the AtomicInteger of an existing running call to
            // the same host.
//            if (!call.get().forWebSocket) {
//                RealCall2.AsyncCall existingCall = findExistingCallWithHost(call.host());
//                if (existingCall != null)
//                    call.reuseCallsPerHostFrom(existingCall);
//            }
        }
        promoteAndExecute();
    }

    private boolean promoteAndExecute() {
        assert (!Thread.holdsLock(this));

        List<RealCall2.AsyncCall> executableCalls = new ArrayList<>();
        boolean isRunning;
        synchronized (this) {


            for (Iterator<RealCall2.AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
                RealCall2.AsyncCall asyncCall = i.next();

                if (runningAsyncCalls.size() >= maxRequests) {
                    break; // Max capacity.
                }
                if (asyncCall.callsPerHost().get() >= maxRequestsPerHost) {
                    continue; // Host max capacity.
                }

                i.remove();
                //++
                asyncCall.callsPerHost().incrementAndGet();
                executableCalls.add(asyncCall);
                runningAsyncCalls.add(asyncCall);
            }
            isRunning = runningCallsCount() > 0;
        }


        for (int i = 0, size = executableCalls.size(); i < size; i++) {
            RealCall2.AsyncCall asyncCall = executableCalls.get(i);
            asyncCall.executeOn(executorService());
        }

        return isRunning;
    }


    /**
     * 緩存方案的線程池
     *
     * @return
     */
    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("自定義的線程...");
                    //不是守護線程
                    thread.setDaemon(false);
                    return thread;
                }
            });
        }
        return executorService;
    }

    public synchronized int runningCallsCount() {
        return runningAsyncCalls.size() + runningSyncCalls.size();
    }

    void finished(RealCall2.AsyncCall call) {
        call.callsPerHost().decrementAndGet();
        finished(runningAsyncCalls, call);
    }
    private @Nullable Runnable idleCallback;
    public synchronized void setIdleCallback(@Nullable Runnable idleCallback) {
        this.idleCallback = idleCallback;
    }
    private <T> void finished(Deque<T> calls, T call) {
        Runnable idleCallback;
        synchronized (this) {
            if (!calls.remove(call)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            idleCallback = this.idleCallback;
        }

        boolean isRunning = promoteAndExecute();

        if (!isRunning && idleCallback != null) {
            idleCallback.run();
        }
    }
}

package pr.tongson.train_okhttp.mine;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pr.tongson.train_okhttp.R;

/**
 * <b>Create Date:</b> 2020-03-05<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class MyThreadPool {

    public static void main2(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                System.out.println("我是线程" + r);
                return t;
            }
        });    //用lambda表达式编写方法体中的逻辑
        Runnable run = () -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "正在执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 5; i++) {
            service.submit(run);
        }    //这里一定要做关闭
        service.shutdown();
    }


    public static void main(String[] args) {


        //        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        //        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());


        //        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        //        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        //
        //        for (int i = 0; i < 20; i++) {
        //            executorService.execute(new Runnable() {
        //                @Override
        //                public void run() {
        //                    try {
        //                        Thread.sleep(1000);
        //                        System.out.println("當前線程，執行耗時任務，線程是：" + Thread.currentThread().getName());
        //                    } catch (InterruptedException e) {
        //                        e.printStackTrace();
        //                    }
        //                }
        //            });
        //        }

        //        ExecutorService executorService = Executors.newCachedThreadPool();
        //        ExecutorService executorService = Executors.newSingleThreadExecutor();


        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("MyOkHttop Dispatcher");
                thread.setDaemon(false);

                try {
                    Thread.sleep(1000);
                    System.out.println("線程工程線程名字：" + thread.getName());
                    System.out.println("當前線程是：" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return thread;
            }
        });

        //用lambda表达式编写方法体中的逻辑
        Runnable run = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("當前線程，執行耗時任務，線程是：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        for (int i = 0; i < 20; i++) {
            executorService.execute(run);
        }

        executorService.shutdown();
    }


}

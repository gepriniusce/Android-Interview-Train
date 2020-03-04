package pr.tongson.demo_framework;

import org.junit.Test;

import androidx.annotation.Nullable;

/**
 * <b>Create Date:</b> 2020-02-17<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ThreadLocalTest {
    @Test
    public void test() {
        //創建本地線程（主線程）
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
            @Nullable
            @Override
            protected String initialValue() {
                //重寫初始化方法，默認返回null，如果ThreadLocalMap拿不到值再用初始化方法。
                return "Tongson";
            }
        };

        System.out.println("主線程ThreadLocal" + threadLocal.get());

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //
                String value1 = threadLocal.get();
                System.out.println("Thread >>>" + value1);

                threadLocal.set("TongsonReset");
                String value2 = threadLocal.get();
                System.out.println("Thread set >>>" + value2);

                //使用完成建議Remove，避免大量無意義的內存佔用
                threadLocal.remove();

            }
        });
        thread.start();

    }

}

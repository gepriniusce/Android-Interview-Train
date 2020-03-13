package pr.tongson.train_java;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

public class MyClass {


    public static void main(String[] args) {

        soft();
        phantom();

        _3();
    }

    /**
     * 可以看到虚引用和弱引用被回收掉。。。
     */
    private static void _3() {
        HashSet<SoftReference<Store>> hs1 = new HashSet<SoftReference<Store>>();
        HashSet<WeakReference<Store>> hs2 = new HashSet<WeakReference<Store>>();


        //创建 10 个软引用
        for (int i = 1; i <= 10; i++) {
            SoftReference<Store> soft = new SoftReference<Store>(new Store("soft" + i), queue);
            System.out.println("create soft" + soft.get());
            hs1.add(soft);
        }

        System.gc();
        checkQueue();


        //创建 10 个弱引用
        for (int i = 1; i <= 10; i++) {
            WeakReference<Store> weak = new WeakReference<Store>(new Store("weak" + i), queue);
            System.out.println("create weak" + weak.get());
            hs2.add(weak);
        }
        System.gc();
        checkQueue();
        HashSet<PhantomReference<Store>> h3 = new HashSet<PhantomReference<Store>>();

        //创建 10 个虚引用
        for (int i = 1; i <= 10; i++) {
            PhantomReference<Store> phantom = new PhantomReference<Store>(new Store("weak" + i), queue);
            System.out.println("create phantom" + phantom.get());
            h3.add(phantom);
        }
        System.gc();
        checkQueue();

    }


    /**
     * 如果内存空间足够，垃圾回收器就不会回收它，如果内存空间不足了，就会回收这些对象的内存。
     * 只要垃圾回收器没有回收它，该对象就可以被程序使用。
     */
    private static void soft() {
        //这就是一个强引用
        String str = "hello";
        //现在我们由上面的强引用创建一个软引用,所以现在 str 有两个引用指向它
        SoftReference<String> soft = new SoftReference<String>(str);
        str = null;

        //可以使用 get()得到引用指向的对象
        //输出 hello
        System.out.println(soft.get());
    }


    /**
     * 虚引用必须和引用队列(ReferenceQueue)联合使用
     */
    private static void phantom() {
        //这就是一个强引用
        String str = "hello";
        ReferenceQueue<? super String> q = new ReferenceQueue<String>();
        //现在我们由上面的强引用创建一个虚引用,所以现在 str 有两个引用指向它
        PhantomReference<String> p = new PhantomReference<String>(str, q);
        //可以使用 get()得到引用指向的对象
        //输出 null
        System.out.println(q.poll());
    }


    public static ReferenceQueue<Store> queue = new ReferenceQueue<Store>();

    public static void checkQueue() {
        if (queue != null) {
            @SuppressWarnings("unchecked") Reference<Store> ref = (Reference<Store>) queue.poll();
            if (ref != null) {
                System.out.println(ref + "......" + ref.get());
            }
        }
    }


}

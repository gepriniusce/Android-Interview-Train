package pr.tongson.train_rxjava.diy;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <b>Create Date:</b> 2020-03-07<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * <p>
 * 所有上游處理，異步線程
 *
 * @author tongson
 */
public class ObservableOnIO2<T> implements ObservableOnSubscribe2<T> {

    /**
     * 線程池
     */
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private ObservableOnSubscribe2<T> source;

    public ObservableOnIO2(ObservableOnSubscribe2<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(final Observer2 emitter) {
        EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                //上游
                source.subscribe(emitter);
            }
        });
    }
}

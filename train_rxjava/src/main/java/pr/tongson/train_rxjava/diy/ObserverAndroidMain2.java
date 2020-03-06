package pr.tongson.train_rxjava.diy;

import android.os.Handler;
import android.os.Looper;

/**
 * <b>Create Date:</b> 2020-03-07<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ObserverAndroidMain2<T> implements ObservableOnSubscribe2<T> {

    private ObservableOnSubscribe2<T> source;


    public ObserverAndroidMain2(ObservableOnSubscribe2<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer2<? super T> emitter) {
        PackageObserver<T> packageObserver = new PackageObserver<T>(emitter);
        source.subscribe(packageObserver);
    }

    class PackageObserver<T> implements Observer2<T> {
        private Observer2<? super T> emitter;

        public PackageObserver(Observer2<? super T> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onSubscribe() {
            emitter.onSubscribe();
        }

        @Override
        public void onNext(final T t) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    emitter.onNext(t);
                }
            });
        }

        @Override
        public void onError(final Throwable e) {

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    emitter.onError(e);
                }
            });
        }

        @Override
        public void onComplete() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    emitter.onComplete();
                }
            });
        }
    }

}

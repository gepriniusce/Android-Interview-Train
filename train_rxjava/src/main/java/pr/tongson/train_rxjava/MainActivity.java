package pr.tongson.train_rxjava;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import pr.tongson.train_rxjava.diy.Function2;
import pr.tongson.train_rxjava.diy.Observable2;
import pr.tongson.train_rxjava.diy.ObservableOnSubscribe2;
import pr.tongson.train_rxjava.diy.Observer2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cllick01(View view) {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                                    emitter.onNext(i);
                                }

                                emitter.onComplete();
                            }
                        },
                //todo 上游不停發射大量事件，下游阻塞了，處理不過來，放入緩存池，如果池子滿了，就會拋出異常
                BackpressureStrategy.ERROR
                //todo 上游不停發射大量事件，下游阻塞了，處理不過來，放入緩存池，"等待"下游來接收事件處理
                //BackpressureStrategy.BUFFER
                //todo 上游不停發射大量事件，下游阻塞了，處理不過來，放入緩存池，如果池子滿了，就會把後面發射的事件
                //BackpressureStrategy.DROP
                //todo 上游不停發射大量事件，下游阻塞了，處理不過來，只存儲128個事件
                //BackpressureStrategy.LATEST
                //todo 上游不停發射大量事件，下游阻塞了，處理不過來，
                // BackpressureStrategy.MISSING


        ).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void cllick02(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

            }
        }).
                map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return null;
                    }
                }).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cllick03(View view) {
        Observable2.create(new ObservableOnSubscribe2<Integer>() {
            @Override
            public void subscribe(Observer2<? super Integer> emitter) {
                emitter.onNext(10);
                emitter.onComplete();
            }
        }).
                subscribe(new Observer2<Integer>() {
                    @Override
                    public void onSubscribe() {
                        Log.i("Tongson ", "onSubscribe:");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("Tongson ", "onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("Tongson ", "onComplete:");

                    }
                });

    }

    public void cllick04(View view) {
        Observable2.just("a", "b", "c").
                subscribe(new Observer2<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.i("Tongson ", "onSubscribe:");

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("Tongson ", "onNext:" + s);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("Tongson ", "onComplete:");
                    }
                });
    }

    public void cllick05(View view) {

        Observable2.create(new ObservableOnSubscribe2<Integer>() {
            @Override
            public void subscribe(Observer2<? super Integer> emitter) {
                emitter.onNext(10);
                emitter.onComplete();
            }
        }).
                map(new Function2<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return "<" + integer + ">";
                    }
                }).
                subscribe(new Observer2<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.i("Tongson ", "onSubscribe:");
                    }

                    @Override
                    public void onNext(String integer) {
                        Log.i("Tongson ", "onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("Tongson ", "onComplete:");

                    }
                });
    }

    public void cllick06(View view) {
        Observable2.create(new ObservableOnSubscribe2<Integer>() {
            @Override
            public void subscribe(Observer2<? super Integer> emitter) {
                Log.i("Tongson ", "subscribe:");
                Log.i("Tongson ", "1:" + Thread.currentThread().getName());
                emitter.onNext(10);
                Log.i("Tongson ", "2:" + Thread.currentThread().getName());
                emitter.onComplete();
                Log.i("Tongson ", "3:" + Thread.currentThread().getName());

            }
        }).
                map(new Function2<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        Log.i("Tongson ", "apply:");
                        Log.i("Tongson ", "apply:" + Thread.currentThread().getName());
                        return "<" + integer + ">";
                    }
                }).
                subscribeOn().
                observeOn().
                subscribe(new Observer2<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.i("Tongson ", "onSubscribe:");
                        Log.i("Tongson ", "onSubscribe:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String integer) {
                        Log.i("Tongson ", "onNext:" + integer);
                        Log.i("Tongson ", "onNext:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Tongson ", "onError:");
                        Log.i("Tongson ", "onError:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Tongson ", "onComplete:");
                        Log.i("Tongson ", "onComplete:" + Thread.currentThread().getName());

                    }
                });
    }
}

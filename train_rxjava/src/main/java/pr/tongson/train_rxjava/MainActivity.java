package pr.tongson.train_rxjava;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

import android.os.Bundle;
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
    }

    public void cllick03(View view) {
    }

    public void cllick04(View view) {
    }

    public void cllick05(View view) {
    }

    public void cllick06(View view) {
    }
}

package pr.tongson.train_rxjava.diy;



/**
 * <b>Create Date:</b> 2020-03-06<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * 被觀察者上游
 *
 * @author tongson
 */
public class Observable2<T> {

    private ObservableOnSubscribe2<T> source;

    public Observable2(ObservableOnSubscribe2<T> source) {
        this.source = source;
    }

    public static <T> Observable2<T> create(ObservableOnSubscribe2<T> source) {
        return new Observable2<T>(source);
    }

    public static <T> Observable2<T> just(final T... t) {
        return new Observable2<T>(new ObservableOnSubscribe2<T>() {
            @Override
            public void subscribe(Observer2<? super T> emitter) {
                for (T t1 : t) {
                    emitter.onNext(t1);
                }
                emitter.onComplete();
            }
        });
    }

    public void subscribe(Observer2<? super T> observer) {
        //開始之前調用
        observer.onSubscribe();
        //Observer2接口回調
        source.subscribe(observer);
    }

    public <R> Observable2<R> map(Function2<? super T, ? extends R> fun) {
        ObservableMap2 observableMap = new ObservableMap2(source, fun);
        return new Observable2<R>(observableMap);
    }

    /**
     * 給所有上游分配異步線程
     *
     * @return
     */
    public Observable2<T> subscribeOn() {
        return create(new ObservableOnIO2<T>(source));
    }

    /**
     * 給下游分配Android主線程
     *
     * @return
     */
    public Observable2<T> observeOn() {
        ObserverAndroidMain2<T> observerAndroidMain2 = new ObserverAndroidMain2<T>(source);
        return create(observerAndroidMain2);
    }


}

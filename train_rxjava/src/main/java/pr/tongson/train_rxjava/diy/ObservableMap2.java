package pr.tongson.train_rxjava.diy;


/**
 * <b>Create Date:</b> 2020-03-06<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ObservableMap2<T, R> implements ObservableOnSubscribe2<T> {

    /**
     * 獲取到上一層的source
     */
    private ObservableOnSubscribe2<T> source;


    private Function2<? super T, ? extends R> function2;

    /**
     * 下一層的發射器
     */
    private Observer2<? super T> observable2Emitter;

    public ObservableMap2(ObservableOnSubscribe2<T> source, Function2<? super T, ? extends R> function2) {
        this.source = source;
        this.function2 = function2;
    }

    @Override
    public void subscribe(Observer2<? super T> emitter) {
        this.observable2Emitter = emitter;
        //創建新的發射器
        ObserverMap2<T> observerMap2 = new ObserverMap2(function2, observable2Emitter);
        source.subscribe(observerMap2);
    }


    class ObserverMap2<T> implements Observer2<T> {

        private Function2<? super T, ? extends R> function2;

        private Observer2<? super R> observable2Emitter;

        public ObserverMap2(Function2<? super T, ? extends R> function2, Observer2<? super R> observable2Emitter) {
            this.function2 = function2;
            this.observable2Emitter = observable2Emitter;
        }

        @Override
        public void onSubscribe() {
            observable2Emitter.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            //t-->r
            R r = function2.apply(t);
            observable2Emitter.onNext(r);
        }

        @Override
        public void onError(Throwable e) {
            observable2Emitter.onError(e);
        }

        @Override
        public void onComplete() {
            observable2Emitter.onComplete();
        }
    }


}

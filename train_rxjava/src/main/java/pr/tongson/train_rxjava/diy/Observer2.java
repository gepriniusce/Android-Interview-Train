package pr.tongson.train_rxjava.diy;


/**
 * <b>Create Date:</b> 2020-03-06<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * <p>
 * 觀察者，下游
 *
 * @author tongson
 */
public interface Observer2<T> {

    void onSubscribe();

    void onNext(T t);

    void onError(Throwable e);

    void onComplete();
}

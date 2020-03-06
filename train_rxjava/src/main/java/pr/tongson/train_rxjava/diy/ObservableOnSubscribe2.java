package pr.tongson.train_rxjava.diy;

/**
 * <b>Create Date:</b> 2020-03-06<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface ObservableOnSubscribe2<T> {

    void subscribe(Observer2<? super T> emitter);

}

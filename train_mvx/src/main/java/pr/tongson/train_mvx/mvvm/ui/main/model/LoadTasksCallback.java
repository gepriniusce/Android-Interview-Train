package pr.tongson.train_mvx.mvvm.ui.main.model;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface LoadTasksCallback<T> {
    void onSuccess(T t);

    void onStart();

    void onFailed();

    void onFinish();
}

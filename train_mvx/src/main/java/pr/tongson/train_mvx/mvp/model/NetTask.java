package pr.tongson.train_mvx.mvp.model;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface NetTask<T> {

    void execute(T data, LoadTasksCallback callback);
}

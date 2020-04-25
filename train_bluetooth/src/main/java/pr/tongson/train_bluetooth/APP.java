package pr.tongson.train_bluetooth;

import android.app.Application;

import pr.tongson.train_bluetooth.helper.BleManager;

/**
 * @Email:289286298@qq.com
 * @Author tongson
 * @Date 2020/4/2
 * @Version
 * @Since
 * @Description
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BleManager.getInstance().init(this);
    }
}

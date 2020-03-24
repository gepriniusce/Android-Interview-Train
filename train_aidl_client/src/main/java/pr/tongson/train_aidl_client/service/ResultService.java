package pr.tongson.train_aidl_client.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import pr.tongson.binder.ILoginAidlInterface;

public class ResultService extends Service {
    public ResultService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginAidlInterface.Stub() {
            @Override
            public void login() throws RemoteException {
                Log.d("Tongson", "login=>train_aidl_client=>");

            }

            @Override
            public void loginCallback(boolean loginStatus, String loginUser) throws RemoteException {
                Log.d("Tongson ", "loginCallback=>train_aidl_client=>loginStatus:" + loginStatus + ",loginUser:" + loginUser);
            }
        };
    }
}

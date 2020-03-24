package pr.tongson.train_aidl_server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import pr.tongson.binder.ILoginAidlInterface;
import pr.tongson.train_aidl_server.MainActivity;

public class LoginService extends Service {
    public LoginService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginAidlInterface.Stub() {
            @Override
            public void login() throws RemoteException {
                Log.d("Tongson", "login=>train_aidl_server");
                //单向通信，真是项目中，跨进程都是双向通信的，双向服务绑定的
                serviceStartActivity();
            }


            @Override
            public void loginCallback(boolean loginStatus, String loginUser) throws RemoteException {
                Log.d("Tongson ", "loginCallback=>train_aidl_client=>loginStatus:" + loginStatus + ",loginUser:" + loginUser);
            }
        };
    }

    private void serviceStartActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}

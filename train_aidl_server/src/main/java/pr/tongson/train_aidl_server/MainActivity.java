package pr.tongson.train_aidl_server;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import pr.tongson.binder.ILoginAidlInterface;

/**
 * Server
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 是否开启AIDL-跨进程通信
     */
    private boolean isStartRemote;
    /**
     * AIDL定义的接口
     */
    private ILoginAidlInterface iLogin;
    private TextView tvLoginTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBindService();
        initView();
    }

    /**
     * 绑定服务
     */
    private void initBindService() {
        Intent intent = new Intent();
        //设置Server应用Action(服务的唯一标示)
        intent.setAction("Train_Binder_Client_Action");

        //设置Server应用包名
        intent.setPackage("pr.tongson.train_aidl_client");

        //开启绑定服务
        bindService(intent, conn, BIND_AUTO_CREATE);

        //标示跨进程绑定
        isStartRemote = true;
    }

    /**
     * 服务连接
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //使用Server提供的功能（方法）
            iLogin = ILoginAidlInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void login() {
        if (iLogin != null) {
            boolean loginStatus = true;
            try {
                iLogin.loginCallback(loginStatus, "Tongson");
                if (loginStatus) {
                    finish();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务，一定要记得，服务连接资源异常
        if (isStartRemote) {
            unbindService(conn);
        }
    }

    private void initView() {
        tvLoginTest = (TextView) findViewById(R.id.tv_login_test);
        tvLoginTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
}

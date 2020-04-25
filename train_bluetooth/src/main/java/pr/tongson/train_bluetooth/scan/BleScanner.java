package pr.tongson.train_bluetooth.scan;

import android.util.Log;

import java.util.List;

import pr.tongson.train_bluetooth.helper.BleManager;
import pr.tongson.train_bluetooth.model.BleDevice;

/**
 * @Email:289286298@qq.com
 * @Author tongson
 * @Date 2020/4/2
 * @Version
 * @Since
 * @Description
 */
public class BleScanner {
    private static BleScanner ourInstance;
    private BleScanPresenter mBleScanPresenter=new BleScanPresenter() {
        @Override
        public void onScanStarted(boolean success) {
            Log.i("Tongson", " onScanStarted:" + success);
        }

        @Override
        public void onLeScan(BleDevice bleDevice) {
            Log.i("Tongson", " onLeScan:" + bleDevice.getName());
        }

        @Override
        public void onScanning(BleDevice bleDevice) {
            Log.i("Tongson", " onScanning:" + bleDevice.getName());
        }

        @Override
        public void onScanFinished(List<BleDevice> bleDeviceList) {
            Log.i("Tongson", " onScanFinished:" + bleDeviceList.size());
        }
    };

    synchronized public static BleScanner getInstance() {
        if (ourInstance == null) {
            ourInstance = new BleScanner();
        }
        return ourInstance;
    }

    private BleScanner() {
    }

    public void scan(long timeOut) {
        startLeScan(timeOut);
    }

    private synchronized void startLeScan(long timeOut) {
        mBleScanPresenter.prepare(timeOut);
        boolean success = BleManager.
                getInstance().
                getBluetoothAdapter().
                startLeScan(null, mBleScanPresenter);
        mBleScanPresenter.notifyScanStarted(success);
    }

    public synchronized void stopLeScan() {
        BleManager.getInstance().getBluetoothAdapter().stopLeScan(mBleScanPresenter);
        mBleScanPresenter.notifyScanStopped();
    }

}

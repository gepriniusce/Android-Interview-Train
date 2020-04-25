package pr.tongson.train_bluetooth.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import pr.tongson.train_bluetooth.model.BleDevice;

/**
 * @Email:289286298@qq.com
 * @Author tongson
 * @Date 2020/4/2
 * @Version
 * @Since
 * @Description
 */
public abstract class BleScanPresenter implements BluetoothAdapter.LeScanCallback {
    public static final int MSG_SCAN_DEVICE = 0X00;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private boolean mHandling;
    private List<BleDevice> mBleDeviceList = new ArrayList<>();
    private long mScanTimeout;

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (device == null) {
            return;
        }
        Log.i("Tongson", " onLeScan:" + device.getName());
        if (!mHandling) {
            return;
        }

        Message message = mHandler.obtainMessage();
        message.what = MSG_SCAN_DEVICE;
        message.obj = new BleDevice(device, rssi, scanRecord, System.currentTimeMillis());
        mHandler.sendMessage(message);
    }

    /**
     * 准备
     *
     * @param timeOut
     */
    public void prepare(long timeOut) {
        mScanTimeout = timeOut;
        mHandlerThread = new HandlerThread(BleScanPresenter.class.getSimpleName());
        mHandlerThread.start();
        mHandler = new ScanHandler(mHandlerThread.getLooper(), this);
        mHandling = true;
    }

    /**
     * 接收返回的message，接收扫描得到的设备
     */
    private static final class ScanHandler extends Handler {

        private final WeakReference<BleScanPresenter> mBleScanPresenter;

        ScanHandler(Looper looper, BleScanPresenter bleScanPresenter) {
            super(looper);
            mBleScanPresenter = new WeakReference<>(bleScanPresenter);
        }

        @Override
        public void handleMessage(Message msg) {
            BleScanPresenter bleScanPresenter = mBleScanPresenter.get();
            if (bleScanPresenter != null) {
                if (msg.what == MSG_SCAN_DEVICE) {
                    final BleDevice bleDevice = (BleDevice) msg.obj;
                    if (bleDevice != null) {
                        bleScanPresenter.handleResult(bleDevice);
                    }
                }
            }
        }
    }

    private void handleResult(final BleDevice bleDevice) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onLeScan(bleDevice);
            }
        });
        checkDevice(bleDevice);
    }

    private void checkDevice(final BleDevice bleDevice) {
        {
            AtomicBoolean hasFound = new AtomicBoolean(false);
            for (BleDevice result : mBleDeviceList) {
                if (result.getDevice().equals(bleDevice.getDevice())) {
                    hasFound.set(true);
                }
            }
            if (!hasFound.get()) {
                mBleDeviceList.add(bleDevice);
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onScanning(bleDevice);
                    }
                });
            }
        }

    }

    public final void notifyScanStarted(final boolean success) {
        mBleDeviceList.clear();
        removeHandlerMsg();
        if (success && mScanTimeout > 0) {
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BleScanner.getInstance().stopLeScan();
                }
            }, mScanTimeout);
        }

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onScanStarted(success);
            }
        });
    }

    public final void notifyScanStopped() {
        mHandling = false;
        mHandlerThread.quit();
        removeHandlerMsg();
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onScanFinished(mBleDeviceList);
            }
        });
    }

    public final void removeHandlerMsg() {
        mMainHandler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
    }

    public abstract void onScanStarted(boolean success);

    public abstract void onLeScan(BleDevice bleDevice);

    public abstract void onScanning(BleDevice bleDevice);

    public abstract void onScanFinished(List<BleDevice> bleDeviceList);
}

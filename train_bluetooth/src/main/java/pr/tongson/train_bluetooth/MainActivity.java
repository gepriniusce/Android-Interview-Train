package pr.tongson.train_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 9527;
    private static final long INTERVAL_TIME = 3000;
    private static final String TAG = "BLE";

    private Handler mHandler = new Handler();
    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    private List<BluetoothDevice> mDevices = new ArrayList<>();
    private String OYGEN_DEVICE_NAME;

    private BluetoothAdapter mBluetoothAdapter;
    private Button mOpenBle;
    private Button mScanBle;
    private Button mConnectBle;
    private Button mShowScannedBle;
    private Button mShowConnectedBle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.i(TAG, "ble not supported");
            finish();
        }

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "没有发现蓝牙模块");
            return;
        }

        initView();
    }

    private void initView() {
        mOpenBle = (Button) findViewById(R.id.open_ble);
        mOpenBle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open2();
            }
        });
        mScanBle = (Button) findViewById(R.id.scan_ble);
        mScanBle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address

                        Log.i(TAG, "deviceName:" + deviceName);
                        Log.i(TAG, "deviceHardwareAddress:" + deviceHardwareAddress);

                    }
                }


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    initCallBack();
//                    scanLeDevice(true);
//                }
            }
        });
        mConnectBle = (Button) findViewById(R.id.connect_ble);
        mConnectBle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mShowScannedBle = (Button) findViewById(R.id.show_scanned_ble);
        mShowScannedBle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "mDevices:" + mDevices.size());

                for (BluetoothDevice device : mDevices) {
                    Log.i(TAG, "device:" + device.getAddress());
                }
            }
        });
        mShowConnectedBle = (Button) findViewById(R.id.show_connected_ble);
        mShowConnectedBle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     *
     */
    public void open1() {
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }

    /**
     *
     */
    public void open2() {
        if (mBluetoothAdapter == null) {
            return;
        }
        if (mBluetoothAdapter.isEnabled()) {
            Log.i(TAG, "已打开" + mBluetoothAdapter);
        }
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback mScanCallback;

    /**
     * 扫描设备
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
            mBluetoothLeScanner.startScan(mScanCallback);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothLeScanner.stopScan(mScanCallback);
                }
            }, INTERVAL_TIME);

        } else {
            //停止扫描
            mBluetoothLeScanner.stopScan(mScanCallback);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCallBack() {
        // 扫描Callback
        mScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Log.i(TAG, "ScanResult:" + result);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        };
    }

    BluetoothGatt mBluetoothGatt;

    private String address;

    public boolean connect() {
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        return true;
    }

    /**
     * 这里有9个要实现的方法，看情况要实现那些，用到那些就实现那些
     */
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        /**
         * 当连接状态发生改变的时候
         */
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
        }

        /**
         * 当服务被发现的时候回调的结果
         * @param gatt
         * @param status
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        /**
         * 回调响应特征读操作的结果。
         * @param gatt
         * @param characteristic
         * @param status
         */
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        /**
         * 回调响应特征写操作的结果。
         * @param gatt
         * @param characteristic
         * @param status
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        /**
         *
         * @param gatt
         * @param characteristic
         */
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        /**
         * 当连接能被被读的操作
         * @param gatt
         * @param descriptor
         * @param status
         */
        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "requestCode:" + requestCode + "\nresultCode:" + resultCode + "\ndata:" + data, Toast.LENGTH_SHORT).show();

    }

}

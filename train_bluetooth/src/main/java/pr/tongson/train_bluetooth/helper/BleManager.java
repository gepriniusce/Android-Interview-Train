package pr.tongson.train_bluetooth.helper;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import pr.tongson.train_bluetooth.scan.BleScanRuleConfig;
import pr.tongson.train_bluetooth.scan.BleScanner;

/**
 * @Email:289286298@qq.com
 * @Author tongson
 * @Date 2020/4/2
 * @Version
 * @Since
 * @Description
 */
public class BleManager {

    private Application context;
    private BleScanRuleConfig mBleScanRuleConfig;
    private BluetoothAdapter mBluetoothAdapter;
    //private MultipleBluetoothController multipleBluetoothController;
    private BluetoothManager mBluetoothManager;
    private static BleManager ourInstance;

    synchronized public static BleManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new BleManager();
        }
        return ourInstance;
    }

    public BleManager(Application context) {
        this.context = context;
    }

    private BleManager() {
    }

    public void init(Application context) {
        if (context == null) {
            throw new RuntimeException("Application context must be not null");
        }
        this.context = context;
        if (isSupportBle()) {
            mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        }
        //// Get the default adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //multipleBluetoothController = new MultipleBluetoothController();
        mBleScanRuleConfig = new BleScanRuleConfig();
    }

    /**
     * is support ble?
     *
     * @return
     */
    public boolean isSupportBle() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && context.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /**
     * 配置扫描
     *
     * @param scanRuleConfig
     */
    public void initScanRule(BleScanRuleConfig scanRuleConfig) {
        this.mBleScanRuleConfig = scanRuleConfig;
    }

    /**
     * 扫描
     */
    public void scan() {
        long timeOut = mBleScanRuleConfig.getScanTimeOut();
        BleScanner.getInstance().scan(timeOut);
    }

    /**
     * 利用 BluetoothAdapter，您可以通过设备发现或查询配对设备的列表来查找远程蓝牙设备。
     * @return
     */
    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

}

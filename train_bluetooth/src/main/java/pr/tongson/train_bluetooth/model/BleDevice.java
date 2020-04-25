package pr.tongson.train_bluetooth.model;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Email:289286298@qq.com
 * @Author tongson
 * @Date 2020/4/2
 * @Version
 * @Since
 * @Description
 */
public class BleDevice implements Parcelable {

    private BluetoothDevice mDevice;
    private byte[] mScanRecord;
    private int mRssi;
    private long mTimestampNanos;


    public BleDevice(BluetoothDevice device) {
        mDevice = device;
    }

    public BleDevice(BluetoothDevice device, int rssi, byte[] scanRecord, long timestampNanos) {
        mDevice = device;
        mScanRecord = scanRecord;
        mRssi = rssi;
        mTimestampNanos = timestampNanos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mDevice, flags);
        dest.writeByteArray(this.mScanRecord);
        dest.writeInt(this.mRssi);
        dest.writeLong(this.mTimestampNanos);
    }

    protected BleDevice(Parcel in) {
        this.mDevice = in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.mScanRecord = in.createByteArray();
        this.mRssi = in.readInt();
        this.mTimestampNanos = in.readLong();
    }

    public static final Creator<BleDevice> CREATOR = new Creator<BleDevice>() {
        @Override
        public BleDevice createFromParcel(Parcel source) {
            return new BleDevice(source);
        }

        @Override
        public BleDevice[] newArray(int size) {
            return new BleDevice[size];
        }
    };


    public String getName() {
        if (mDevice != null) {
            return mDevice.getName();
        }
        return null;
    }

    public String getMac() {
        if (mDevice != null) {
            return mDevice.getAddress();
        }
        return null;
    }

    public String getKey() {
        if (mDevice != null) {
            return mDevice.getName() + mDevice.getAddress();
        }
        return "";
    }


    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public void setDevice(BluetoothDevice device) {
        mDevice = device;
    }

    public byte[] getScanRecord() {
        return mScanRecord;
    }

    public void setScanRecord(byte[] scanRecord) {
        mScanRecord = scanRecord;
    }

    public int getRssi() {
        return mRssi;
    }

    public void setRssi(int rssi) {
        mRssi = rssi;
    }

    public long getTimestampNanos() {
        return mTimestampNanos;
    }

    public void setTimestampNanos(long timestampNanos) {
        mTimestampNanos = timestampNanos;
    }
}

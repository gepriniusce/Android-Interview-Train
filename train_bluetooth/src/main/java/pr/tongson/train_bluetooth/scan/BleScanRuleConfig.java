package pr.tongson.train_bluetooth.scan;

import java.util.UUID;

/**
 * @Email:289286298@qq.com
 * @Author tongson
 * @Date 2020/4/2
 * @Version
 * @Since
 * @Description
 */
public class BleScanRuleConfig {
    private static final int DEFAULT_SCAN_TIME = 10000;

    private UUID[] mServiceUuids = null;
    private String[] mDeviceNames = null;
    private String mDeviceMac = null;
    private boolean mAutoConnect = false;
    private boolean mFuzzy = false;
    private long mScanTimeOut = DEFAULT_SCAN_TIME;

    public UUID[] getServiceUuids() {
        return mServiceUuids;
    }

    public String[] getDeviceNames() {
        return mDeviceNames;
    }

    public String getDeviceMac() {
        return mDeviceMac;
    }

    public boolean isAutoConnect() {
        return mAutoConnect;
    }

    public boolean isFuzzy() {
        return mFuzzy;
    }

    public long getScanTimeOut() {
        return mScanTimeOut;
    }

    public static class Builder {

        private UUID[] mServiceUuids = null;
        private String[] mDeviceNames = null;
        private String mDeviceMac = null;
        private boolean mAutoConnect = false;
        private boolean mFuzzy = false;
        private long mTimeOut = DEFAULT_SCAN_TIME;

        public Builder setServiceUuids(UUID[] uuids) {
            this.mServiceUuids = uuids;
            return this;
        }

        public Builder setDeviceName(boolean fuzzy, String... name) {
            this.mFuzzy = fuzzy;
            this.mDeviceNames = name;
            return this;
        }

        public Builder setDeviceMac(String mac) {
            this.mDeviceMac = mac;
            return this;
        }

        public Builder setAutoConnect(boolean autoConnect) {
            this.mAutoConnect = autoConnect;
            return this;
        }

        public Builder setScanTimeOut(long timeOut) {
            this.mTimeOut = timeOut;
            return this;
        }

        void applyConfig(BleScanRuleConfig config) {
            config.mServiceUuids = this.mServiceUuids;
            config.mDeviceNames = this.mDeviceNames;
            config.mDeviceMac = this.mDeviceMac;
            config.mAutoConnect = this.mAutoConnect;
            config.mFuzzy = this.mFuzzy;
            config.mScanTimeOut = this.mTimeOut;
        }

        public BleScanRuleConfig build() {
            BleScanRuleConfig config = new BleScanRuleConfig();
            applyConfig(config);
            return config;
        }
    }
}

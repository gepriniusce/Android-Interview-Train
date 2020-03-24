package pr.tongson.train_webview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <b>Create Date:</b> 2020-03-20<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class WebIntentParams implements Parcelable {
    private String mUrl;

    public static String Key = "asdf";

    public WebIntentParams() {
    }

    protected WebIntentParams(Parcel in) {
        mUrl = in.readString();
    }

    public static final Creator<WebIntentParams> CREATOR = new Creator<WebIntentParams>() {
        @Override
        public WebIntentParams createFromParcel(Parcel in) {
            return new WebIntentParams(in);
        }

        @Override
        public WebIntentParams[] newArray(int size) {
            return new WebIntentParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}

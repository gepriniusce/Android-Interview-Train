package pr.tongson.train_webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * <b>Create Date:</b> 2020-03-20<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class SafeWebViewClient extends WebViewClient {

    private static final int FILECHOOSER_REQUESTCODE = 132;
    private WebViewEventCallBack mWebViewEventCallBack;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMsg;
    private Activity mContext;

    /**
     * 当WebView得页面Scale值发生改变时回调
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    /**
     * 是否在 WebView 内加载页面
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    /**
     * WebView 加载页面资源时会回调，每一个资源产生的一次网络加载，除非本地有当前 url 对应有缓存，否则就会加载。
     *
     * @param view WebView
     * @param url  url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * WebView 可以拦截某一次的 request 来返回我们自己加载的数据，这个方法在后面缓存会有很大作用。
     *
     * @param view    WebView
     * @param request 当前产生 request 请求
     * @return WebResourceResponse
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    /**
     * WebView 访问 url 出错
     *
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }


    public SafeWebViewClient(Activity context, WebViewEventCallBack callback) {
        mContext = context;
        mWebViewEventCallBack = callback;
    }

    //    @Override
    //    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
    //        quotaUpdater.updateQuota(5 * 1024 * 1024);
    //    }

    /**
     * 3.0 + 调用这个方法
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        openImageChooserActivity();
    }

    /**
     * Android < 3.0 调用这个方法
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        openImageChooserActivity();
    }

    /**
     * Android  >= 4.1
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        openImageChooserActivity();
    }

    //    /**
    //     * Android >= 5.0 api21
    //     */
    //    @TargetApi(21)
    //    @Override
    //    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
    //        mUploadMsg = filePathCallback;
    //        openImageChooserActivity();
    //        return true;
    //    }


    protected void openImageChooserActivity() {
        if (mWebViewEventCallBack == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mWebViewEventCallBack.startActivityForResult(Intent.createChooser(intent, "请选择需要上传的文件"), FILECHOOSER_REQUESTCODE);
    }

    /**
     * 捕获上传文件的事件
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_REQUESTCODE) {
            return;
        }
        if (null == mUploadMessage && null == mUploadMsg) {
            return;
        }
        Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
        if (mUploadMsg != null) {
            onActivityResultAboveL(requestCode, resultCode, data);
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    @TargetApi(21)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILECHOOSER_REQUESTCODE || mUploadMsg == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        mUploadMsg.onReceiveValue(results);
        mUploadMsg = null;
    }

    public interface WebViewEventCallBack {
        void startActivityForResult(Intent intent, int requestCode);
    }

    private boolean loadFailture = false;

    /**
     * 是否加载成功，true成功，false失败
     */
    public boolean getLoadState() {
        return !loadFailture;
    }

    /**
     * WebView 开始加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        loadFailture = false;
        //        mProgressBar.setVisibility(View.VISIBLE);
        //        mWebView.setVisibility(View.VISIBLE);
        //        mWebReloadLayout.setVisibility(View.GONE);
    }

    /**
     * WebView 完成加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //        mProgressBar.setVisibility(View.GONE);
        //        if (loadFailture) {
        //            mWebView.setVisibility(View.GONE);
        //            mWebReloadLayout.setVisibility(View.VISIBLE);
        //        } else {
        //            mWebView.setVisibility(View.VISIBLE);
        //            mWebReloadLayout.setVisibility(View.GONE);
        //            mWebView.loadUrl("javascript:MMCReady()");
        //        }
    }

    /**
     * WebView ssl 访问证书出错，
     * handler.cancel()取消加载，
     * handler.proceed()对然错误也继续加载
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {



        // https安全认证问题，暂时忽略
        // handler.proceed();
        //        if (mIntentParams.isgm()) {
        //            showProcessDialog(handler);
        //        } else {
        //            handler.proceed();
        //        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        loadFailture = true;
        //        mWebView.setVisibility(View.GONE);
        //        mProgressBar.setVisibility(View.GONE);
        //        mWebReloadLayout.setVisibility(View.VISIBLE);
    }

        /*
        @TargetApi(23)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            loadFailture = true;
            mWebView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mWebReloadLayout.setVisibility(View.VISIBLE);
        }*/

}

package pr.tongson.train_webview;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    private ViewGroup mRoot;
    protected WebView mWebView;
    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;
    protected WebSettings mSettings;
    private String url;

    public static WebViewFragment newInstance(WebIntentParams params) {
        WebViewFragment contentFragment = new WebViewFragment();
        Bundle data = new Bundle();
        if (params != null) {
            data.putParcelable(WebIntentParams.Key, params);
            contentFragment.setArguments(data);
        }
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) {
            return;
        }
        Bundle bundle = getArguments();
        if (bundle == null) {
            getActivity().finish();
            return;
        }
        WebIntentParams params = bundle.getParcelable(WebIntentParams.Key);
        if (params == null) {
            getActivity().finish();
            return;
        }
        url = params.getUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = (ViewGroup) inflater.inflate(R.layout.fragment_web_view, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mWebView = mRoot.findViewById(R.id.web_view);

        setupWebView();
        setupWebSettings();
        //要在setupWebSettings以后
        //setUserAgent();
        addJavascriptInterface(new JsInterface(getActivity()), "AndroidNative");

        extraMethod();
        if (!TextUtils.isEmpty(url)) {
            loadUrl(url);
        }
    }

    /**
     * 额外附加的方法
     */
    public void extraMethod() {

    }

    /**
     * 转载WebView
     */
    public void setupWebView() {
        mWebViewClient = new SafeWebViewClient(getActivity(), null);
        setWebViewClient(mWebViewClient);
        mWebChromeClient = new SafeWebChromeClient(getActivity());
        setWebChromeClient(mWebChromeClient);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // 向 Web端注入 java 对象
            mWebView.removeJavascriptInterface("AndroidNative");
        }
    }


    /**
     * @param webViewClient
     */
    public void setWebViewClient(WebViewClient webViewClient) {
        if (webViewClient != null) {
            mWebView.setWebViewClient(webViewClient);
        }
    }

    /**
     * @param webChromeClient
     */
    public void setWebChromeClient(WebChromeClient webChromeClient) {
        if (webChromeClient != null) {
            mWebView.setWebChromeClient(webChromeClient);
        }
    }

    public void addJavascriptInterface(JsInterface jsInterface, String name) {
        mWebView.addJavascriptInterface(jsInterface, name);
    }

    /**
     * 设置 UserAgent 属性
     */
    public void setUserAgent(String userAgent) {
        String origin = mSettings.getUserAgentString();
        mSettings.setUserAgentString(origin + " " + userAgent);
    }

    /**
     * "http://www.jianshu.com/u/fa272f63280a"
     * "file:///android_asset/百度一下，你就知道.htm"
     *
     * @param url
     */
    public void loadUrl(String url) {
        // 加载url，也可以执行js函数
        mWebView.loadUrl(url);
    }

    public void loadBlank() {
        // 清空当前加载
        mWebView.loadUrl("about:blank");

    }

    protected void stopLoading() {
        mWebView.stopLoading();
    }

    private void clear() {
        // 清除网页查找的高亮匹配字符。
        mWebView.clearMatches();
        // 清除当前 WebView 访问的历史记录
        mWebView.clearHistory();
        //清除ssl信息
        mWebView.clearSslPreferences();
        //清空网页访问留下的缓存数据。
        //需要注意的时，由于缓存是全局的，所以只要是WebView用到的缓存都会被清空，即便其他地方也会使用到。
        //该方法接受一个参数，从命名即可看出作用。
        //若设为false，则只清空内存里的资源缓存，而不清空磁盘里的。
        mWebView.clearCache(true);
    }

    public void setupWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        if (webSettings == null) {
            return;
        }
        mSettings = webSettings;
        // 支持 Js 使用
        webSettings.setJavaScriptEnabled(true);
        // 开启DOM缓存,默认状态下是不支持LocalStorage的
        webSettings.setDomStorageEnabled(true);
        // 开启数据库缓存
        webSettings.setDatabaseEnabled(true);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(hasKitkat());
        // 设置 WebView 的缓存模式
        //        if(debug){
        //            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //        }
        //        if (NetUtil.hasNetWorkStatus(getActivity(), false)) {
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //        } else {
        //            mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //        }
        // 支持启用缓存模式
        webSettings.setAppCacheEnabled(true);
        // 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
        webSettings.setAppCacheMaxSize(8 * 1024 * 1024);
        // Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
        webSettings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());
        // 数据库路径
        if (!hasKitkat()) {
            webSettings.setDatabasePath(getContext().getDatabasePath("html").getPath());
        }
        // 关闭密码保存提醒功能
        webSettings.setSavePassword(false);
        // 支持缩放设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置默认缩放方式尺寸是far
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);

        // 允许加载本地 html 文件/false
        webSettings.setAllowFileAccess(true);
        // 允许通过 file url 加载的 Javascript 读取其他的本地文件,Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        webSettings.setAllowFileAccessFromFileURLs(false);
        // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源，
        // Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        // 如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setSaveFormData(false);
        //设置使用广角端口
        //        webSettings.setUseWideViewPort(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // WebView自适应屏幕大小
        webSettings.setDefaultTextEncodingName("UTF-8");
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 不然5.0以后http和https混合的页面会加载不出来
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }


    //        public boolean onBackPressed() {
    //
    //            if (mWebView.canGoBack()) {
    //                mWebView.goBack();
    //                return true;
    //            }
    //            return false;
    //        }


    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (mRoot != null) {
            mRoot.removeView(mWebView);
        }
        if (mWebView != null) {
            mWebView.setVisibility(View.GONE);
            //清空子View
            mWebView.removeAllViews();
        }
        super.onDestroy();
        if (mWebView != null) {
            try {
                mWebView.stopLoading();
                mWebView.clearMatches();
                mWebView.clearHistory();
                mWebView.clearSslPreferences();
                mWebView.clearCache(true);
                mWebView.loadUrl("about:blank");
                mWebView.removeAllViews();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    mWebView.removeJavascriptInterface("AndroidNative");
                }
                mWebView.destroy();
            } catch (Throwable t) {
                // ignore
            }
            if (mWebView != null) {
                mWebView = null;
            }
        }
    }
}

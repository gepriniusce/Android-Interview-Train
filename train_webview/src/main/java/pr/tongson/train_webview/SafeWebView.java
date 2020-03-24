package pr.tongson.train_webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebView;

import java.lang.reflect.Method;

/**
 * <b>Create Date:</b> 2020-03-20<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class SafeWebView extends WebView {
    public SafeWebView(Context context) {
        super(context);
        removeSearchBox();
    }

    public SafeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        removeSearchBox();
    }

    public SafeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        removeSearchBox();
    }

    /**
     * 移除系统注入的对象，避免js漏洞
     * <p>
     * https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-1939
     * https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-7224
     */
    private void removeSearchBox() {
        super.removeJavascriptInterface("searchBoxJavaBridge_");
        super.removeJavascriptInterface("accessibility");
        super.removeJavascriptInterface("accessibilityTraversal");
    }

    public void disableAccessibility(Context context) {
        if (Build.VERSION.SDK_INT == 17/*4.2 (Build.VERSION_CODES.JELLY_BEAN_MR1)*/) {
            if (context != null) {
                try {
                    AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
                    if (!am.isEnabled()) {
                        //Not need to disable accessibility
                        return;
                    }
                    /**{@link AccessibilityManager#STATE_FLAG_ACCESSIBILITY_ENABLED}*/
                    Method setState = am.getClass().getDeclaredMethod("setState", int.class);
                    setState.setAccessible(true);
                    setState.invoke(am, 0);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setOverScrollMode(int mode) {
        //        try {
        //                    super.setOverScrollMode(mode);
        //        } catch (Throwable e) {
        //            e.printStackTrace();
        //        }

        try {
            super.setOverScrollMode(mode);
        } catch (Throwable e) {
            String trace = Log.getStackTraceString(e);
            if (trace.contains("android.content.pm.PackageManager$NameNotFoundException") || trace.contains("java.lang.RuntimeException: Cannot load WebView") || trace.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean isPrivateBrowsingEnabled() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && getSettings() == null) {
            return false;
        } else {
            return super.isPrivateBrowsingEnabled();
        }
    }

}
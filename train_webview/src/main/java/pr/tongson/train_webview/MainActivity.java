package pr.tongson.train_webview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private pr.tongson.train_webview.IMyAidlInterface mIMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebIntentParams params = new WebIntentParams();
        //https://18.197.159.153:28100/privacy.html
        //file:///android_asset/test.html
        params.setUrl("https://18.197.159.153:28100/privacy.html");

        Fragment fragment = WebViewFragment.newInstance(params);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
        }
    }
}

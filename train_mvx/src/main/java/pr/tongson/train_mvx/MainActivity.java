package pr.tongson.train_mvx;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import pr.tongson.train_mvx.mvp.IpInfoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, IpInfoFragment.newInstance()).
                    commitNow();
        }
    }
}

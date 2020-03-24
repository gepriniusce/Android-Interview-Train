package pr.tongson.train_apt_javapoet;

import androidx.appcompat.app.AppCompatActivity;
import pr.tongson.train_annotation.ARouter;

import android.os.Bundle;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

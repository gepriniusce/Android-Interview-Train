package pr.tongson.train_diy_view;

import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.img_anim);
        imageView.setFocusable(true);
        imageView.setFocusableInTouchMode(true);
        imageView.requestFocus();
        imageView.requestFocusFromTouch();
        EditText editText = findViewById(R.id.et);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(MainActivity.this, R.drawable.animated_vecotr_search);
                        imageView.setImageDrawable(animatedVectorDrawableCompat);
                        ((Animatable) (imageView.getDrawable())).start();
                    }
                }
            }
        });
    }
}

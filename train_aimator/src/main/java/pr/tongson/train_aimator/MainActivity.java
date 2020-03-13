package pr.tongson.train_aimator;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import pr.tongson.train_aimator.animator.LineInterpolator;
import pr.tongson.train_aimator.animator.MyObjectAnimator;

public class MainActivity extends AppCompatActivity {

    private TextView mTvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTvHello = (TextView) findViewById(R.id.tv_hello);
        mTvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyObjectAnimator objectAnimator=MyObjectAnimator.ofFloat(mTvHello,"scaleX",1f,2f);
                objectAnimator.setInterpolator(new LineInterpolator());
                objectAnimator.setDeration(3000);
                objectAnimator.start();

            }
        });
    }
}

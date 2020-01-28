package com.example.templet_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lib_component.TempletConfig;
import com.xiaojinzi.component.impl.Router;
import com.xiaojinzi.component.impl.RouterErrorResult;
import com.xiaojinzi.component.impl.RouterResult;
import com.xiaojinzi.component.support.CallbackAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvClick = findViewById(R.id.tv_click);
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity();
            }
        });
    }

    private void startActivity() {
        Router
                .with(MainActivity.this)
                .host(TempletConfig.TempletActivity.NAME)
                .path(TempletConfig.Business.LIST_ACTIVITY)
                .putString("data", "normalJump")
                .putString("name", "cxj")
                .putInt("age", 25)
                .forward(new CallbackAdapter() {
                    @Override
                    public void onSuccess(@NonNull RouterResult result) {
                    }

                    @Override
                    public void onError(@NonNull RouterErrorResult errorResult) {
                    }
                });
    }
}

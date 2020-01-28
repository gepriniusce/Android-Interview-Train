package com.example.templet_activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lib_component.TempletConfig;
import com.example.templet_activity.R;
import com.example.templet_activity.activity.dummy.DummyContent;
import com.xiaojinzi.component.anno.RouterAnno;

@RouterAnno(
        path = TempletConfig.Business.LIST_ACTIVITY,
        desc = "业务组件1的测试界面"
)
public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ItemFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}

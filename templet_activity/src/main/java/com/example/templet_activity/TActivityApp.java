package com.example.templet_activity;

import android.app.Application;

import com.example.lib_component.TempletConfig;
import com.xiaojinzi.component.Component;
import com.xiaojinzi.component.Config;
import com.xiaojinzi.component.impl.application.ModuleManager;
import com.xiaojinzi.component.support.RxErrorIgnoreUtil;

public class TActivityApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化组件化相关
        Component.init(
                BuildConfig.DEBUG, Config.with(this)
                        .build()
        );

        // 忽略一些不想处理的错误
        RxErrorIgnoreUtil.ignoreError();

        // 装载各个业务组件
        ModuleManager.getInstance().registerArr("TempletActivity", TempletConfig.TempletActivity.NAME);

        if (BuildConfig.DEBUG) {
            ModuleManager.getInstance().check();
        }
    }
}

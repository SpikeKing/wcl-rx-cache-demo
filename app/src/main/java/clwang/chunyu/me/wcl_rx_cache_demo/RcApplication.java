package clwang.chunyu.me.wcl_rx_cache_demo;

import android.app.Application;

import clwang.chunyu.me.wcl_rx_cache_demo.di.ApiComponent;
import clwang.chunyu.me.wcl_rx_cache_demo.di.ApiModule;
import clwang.chunyu.me.wcl_rx_cache_demo.di.DaggerApiComponent;

/**
 * 应用
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public class RcApplication extends Application {
    private ApiComponent mApiComponent;

    @Override public void onCreate() {
        super.onCreate();
        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule(this)).build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}

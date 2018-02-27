package org.wangchenlong.wcl_rx_cache_demo.di;

import javax.inject.Singleton;

import org.wangchenlong.wcl_rx_cache_demo.uis.CacheActivity;
import org.wangchenlong.wcl_rx_cache_demo.uis.NocacheActivity;
import dagger.Component;

/**
 * 组件
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
@Singleton @Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(NocacheActivity activity);

    void inject(CacheActivity activity);
}

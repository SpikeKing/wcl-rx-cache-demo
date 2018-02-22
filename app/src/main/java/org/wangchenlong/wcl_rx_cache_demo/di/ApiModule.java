package org.wangchenlong.wcl_rx_cache_demo.di;

import android.app.Application;

import javax.inject.Singleton;

import org.wangchenlong.wcl_rx_cache_demo.data.ObservableRepoDb;
import org.wangchenlong.wcl_rx_cache_demo.networks.GitHubClient;
import dagger.Module;
import dagger.Provides;

/**
 * 模块
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
@Module
public class ApiModule {
    private Application mApplication;

    public ApiModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton GitHubClient provideGitHubClient() {
        return new GitHubClient();
    }

    @Provides ObservableRepoDb provideObservableRepoDb() {
        return new ObservableRepoDb(mApplication);
    }
}

package org.wangchenlong.wcl_rx_cache_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wangchenlong.wcl_rx_cache_demo.uis.CacheActivity;
import org.wangchenlong.wcl_rx_cache_demo.uis.NocacheActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 跳转无缓存
    public void gotoNoCache(View view) {
        startActivity(new Intent(this, NocacheActivity.class));
    }

    // 跳转有缓存
    public void gotoCache(View view) {
        startActivity(new Intent(this, CacheActivity.class));
    }
}

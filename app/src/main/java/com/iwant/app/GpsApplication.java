package com.iwant.app;

import com.iwant.oogpstest.LocationService;

import android.app.Application;

/**
 * @Description:
 * @author: whsgzcy
 * @date: 2016-8-3 下午1:14:08
 *
 */
public class GpsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 加载定位
//		LocationService.getInstance(this);
    }

}

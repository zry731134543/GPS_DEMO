package com.iwant.oogpstest;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * @Description:
 * @author: whsgzcy
 * @date: 2016-7-26 上午10:25:09
 * 
 */
public class GpsGetService extends Service {

	private GPSCallBack mGpsCallBack;
	private Timer mTimer = new Timer();
	private GpsGetBinder mGpsGetBinder = new GpsGetBinder();

	public class GpsGetBinder extends Binder {
		void setGpsCallBack(GPSCallBack gpsCallBack) {
			mGpsCallBack = gpsCallBack;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mGpsGetBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				GPSRecongizeFilter gf = new GPSRecongizeFilter();
				try {
					GPSFilterResult gr = gf.gpsFilterResult();
					// 赋值为null在此截断
					if (gr == null) {
						return;
					}
					Message message = new Message();
					message.obj = gr;
					mHandler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1000, 3000);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			GPSFilterResult gr = (GPSFilterResult) msg.obj;
			mGpsCallBack.onState(gr.getState());
			mGpsCallBack.onDeviceUtcDate(gr.getDeviceUtcDate());
			mGpsCallBack.onSpeed(gr.getSpeed());
			mGpsCallBack.onCourse(gr.getCourse());
			mGpsCallBack.onIsStop(gr.getIsStop());
			mGpsCallBack.onIncon(gr.getIcon());
			mGpsCallBack.onDistance(gr.getDistance());
			mGpsCallBack.onGpsLatLonitude(gr.getGpslatitude(), gr.getGpslongitude());
			mGpsCallBack.onBaiduLatLonitude(gr.getLatitude(), gr.getLongitude());
		}
	};

}

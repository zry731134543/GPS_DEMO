package com.iwant.oogpstest;

/**
 * @Description:
 * @author: whsgzcy
 * @date: 2016-7-26 上午10:19:05
 * 
 */
public interface GPSCallBack {

	void onState(String state);

	void onDeviceUtcDate(String deviceUtcDate);

	void onBaiduLatLonitude(String latitude, String longitude);

	void onSpeed(String speed);

	void onCourse(String course);

	void onIsStop(String isStop);

	void onIncon(String incon);

	void onDistance(String distance);

	void onGpsLatLonitude(String gpsLatitude, String gpsLongitude);

}

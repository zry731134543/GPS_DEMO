package com.iwant.oogpstest;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

/**
 * @author zyzhang
 * @date 2015-12-4 下午7:32:06
 */
public class LocationService {

	private static String TAG = "LocationService";

	// 声明AMapLocationClient类对象
	public static AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;

	// 声明定位回调监听器

	private Context mContext;
	public static LocationService mInstance;

	private double mLatitude; // 纬度
	private double mLongitude;// 经度
	private AMapLocation mlocation;//
	private OnLocationGetListener mOnLocationGetListener;
	private boolean isOnceLocation = false;// 一次定位还是多次定位

	LocationService(Context context) {
		mContext = context;
		initBaiDu();
	}

	public static LocationService getInstance(Context context) {

		if (mInstance == null) {
			mInstance = new LocationService(context);
		}
		return mInstance;
	}

	public static LocationService getInstance() {
		return mInstance;
	}

	public void onlocationgetlistener(
			OnLocationGetListener onLocationGetListener) {
		mOnLocationGetListener = onLocationGetListener;
	}

	/**
	 * 初始化
	 */
	public void initBaiDu() {

		locationClient = new AMapLocationClient(mContext);
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为低功耗模式/高
		locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		// 设置定位回调监听
		locationClient.setLocationListener(mLocationListener);

		// 设置是否返回地址信息（默认返回地址信息）
		locationOption.setNeedAddress(true);
		/**
		 * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位 注意：只有在高精度模式下的单次定位有效，其他方式无效
		 */
		// locationOption.setGpsFirst(true);
		// 设置是否只定位一次,默认为false
		// locationOption.setOnceLocation(true);
		// 设置是否强制刷新WIFI，默认为强制刷新
		locationOption.setWifiActiveScan(true);
		// 设置是否允许模拟位置,默认为false，不允许模拟位置
		locationOption.setMockEnable(true);
		// 设置定位间隔,单位毫秒,默认为2000ms
		locationOption.setInterval(2000);
		// 给定位客户端对象设置定位参数
		// locationClient.setLocationOption(locationOption);
		// 启动定位
		// locationClient.startLocation();

		initLocation();
	}

	public void initLocation() {
		if (locationClient != null) {
			// 设置是否只定位一次,默认为false
			Log.d("zyzhang", "isOnceLocation:" + isOnceLocation);
			locationOption.setOnceLocation(isOnceLocation());
			// 给定位客户端对象设置定位参数
			locationClient.setLocationOption(locationOption);
			// 启动定位
			locationClient.startLocation();
		} else {
			initBaiDu();
		}

	}

	public boolean isOnceLocation() {
		return isOnceLocation;
	}

	public void setOnceLocation(boolean isOnceLocation) {
		this.isOnceLocation = isOnceLocation;
	}

	/**
	 * 定位
	 * 
	 * @author zyzhang
	 * 
	 */
	AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onLocationChanged(AMapLocation location) {

			if (null != location) {
				if (location.getErrorCode() == 0) {
					// 定位成功回调信息，设置相关消息
					mLatitude = location.getLatitude();// 获取纬度
					mLongitude = location.getLongitude();// 获取经度
					mlocation = location;
					PositionEntity entity = new PositionEntity();
					entity.setmLatitude(mLatitude);
					entity.setmLongitude(mLongitude);
					entity.setMlocation(mlocation);
					mOnLocationGetListener.onLocationGet(entity);
				} else {
					mOnLocationGetListener.onGetLocationFail();
				}
			} else {
				mOnLocationGetListener.onGetLocationFail();
			}
		}
	};

	public double getmLatitude() {
		return mLatitude;
	}

	public double getmLongitude() {
		return mLongitude;
	}

	public AMapLocation getMlocation() {
		return mlocation;
	}

	public void setMlocation(AMapLocation mlocation) {
		this.mlocation = mlocation;
	}
}

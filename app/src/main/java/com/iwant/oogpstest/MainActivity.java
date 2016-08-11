package com.iwant.oogpstest;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.iwant.util.CoordinateUtil;

public class MainActivity extends Activity implements GPSCallBack,
		LocationSource, AMapLocationListener, OnMarkerDragListener,
		OnMapLoadedListener {

	private Intent mGpsIntent;
	private GpsGetService.GpsGetBinder mGpsBinder;

	private MapView mapView;
	private AMap aMap;

	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	// 当前位置的LatLon
	private LatLng mLocationLatLng;
	private Polyline mPolyline;

	ServiceConnection mGpsConn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mGpsBinder = (GpsGetService.GpsGetBinder) service;
			mGpsBinder.setGpsCallBack(MainActivity.this);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGpsIntent = new Intent(this, GpsGetService.class);
		bindService(mGpsIntent, mGpsConn, Service.BIND_AUTO_CREATE);

		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写

		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		/*
		 * // 自定义系统定位小蓝点 myLocationStyle = new MyLocationStyle();
		 * myLocationStyle.myLocationIcon(BitmapDescriptorFactory
		 * .fromResource(R.drawable.map_gps));// 设置小蓝点的图标
		 * myLocationStyle.strokeColor(Color.argb(225, 0, 100, 225));//
		 * 设置圆形的边框颜色 myLocationStyle.radiusFillColor(Color.argb(50, 0, 100,
		 * 225));// 设置圆形的填充颜色 myLocationStyle.strokeWidth(1f);// 设置圆形的边框粗细
		 */

	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		aMap.setOnMarkerDragListener(this);
		aMap.setOnMapLoadedListener(this);// 设置点击marker事件监听器
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.amap_car));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
		myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setMyLocationRotateAngle(180);
	}

//
//	  // 自定义系统定位小蓝点 MyLocationStyle myLocationStyle = new MyLocationStyle();
//	  myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//	  .fromResource(R.drawable.transdrawable));// 设置小蓝点的图标
//	  myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
//	  myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
//	  myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
//	  aMap.setMyLocationStyle(myLocationStyle);
//	  aMap.setMyLocationRotateAngle(180); aMap.setLocationSource(this);//
//	  设置定位监听 mUiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮
//	  aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
//	  aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//	  mUiSettings.setTiltGesturesEnabled(false);// 设置地图是否可以倾斜
//	  mUiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
//	  mUiSettings.setZoomControlsEnabled(false); initMapListener();
//
//	  myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
//	  myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				mLocationLatLng = new LatLng(amapLocation.getLatitude(),
						amapLocation.getLongitude());
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode() + ": "
						+ amapLocation.getErrorInfo();
			}
		}
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			// 设置是否返回地址信息（默认返回地址信息）
			mLocationOption.setNeedAddress(true);
			// 设置是否只定位一次,默认为false
			mLocationOption.setOnceLocation(false);
			// 设置定位监听
			mlocationClient.setLocationListener(this);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

			// 设置是否强制刷新WIFI，默认为强制刷新
			mLocationOption.setWifiActiveScan(true);
			// 设置是否允许模拟位置,默认为false，不允许模拟位置
			mLocationOption.setMockEnable(false);
			// 设置定位间隔,单位毫秒,默认为2000ms
			mLocationOption.setInterval(2000);
			// 给定位客户端对象设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 启动定位
			mlocationClient.startLocation();
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	/****************************** 添加marker的回调事件 *************************/
	@Override
	public void onMapLoaded() {

	}

	@Override
	public void onMarkerDrag(Marker arg0) {
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
	}

	/****************************** GPS 数据返回值 ******************************/
	@Override
	public void onState(String state) {
	}

	@Override
	public void onDeviceUtcDate(String deviceUtcDate) {
	}

	@Override
	public void onSpeed(String speed) {
	}

	@Override
	public void onCourse(String course) {
	}

	@Override
	public void onIsStop(String isStop) {
	}

	@Override
	public void onIncon(String incon) {
	}

	@Override
	public void onDistance(String distance) {
	}

	@Override
	public void onGpsLatLonitude(String gpsLatitude, String gpsLongitude) {

		// LatLng ll = new LatLng(Double.parseDouble(gpsLatitude),
		// Double.parseDouble(gpsLongitude));
		//
		// addMarkersToMap(CoordinateUtil.transformFromWGSToGCJ(ll));

	}

	@Override
	public void onBaiduLatLonitude(String latitude, String longitude) {
		if (marker != null) {
			marker.remove();
		}
		if (mPolyline != null) {
			mPolyline.remove();
		}
		// 画出直线
		if (mLocationLatLng != null) {
			PolylineOptions polylineOptions = new PolylineOptions()
					.add(CoordinateUtil.Convert_BD09_To_GCJ02(
							Double.parseDouble(latitude),
							Double.parseDouble(longitude)), mLocationLatLng)
					.width(10).color(Color.GREEN);
			mPolyline = aMap.addPolyline(polylineOptions);
		}
		addMarkersToMap(CoordinateUtil.Convert_BD09_To_GCJ02(
				Double.parseDouble(latitude), Double.parseDouble(longitude)));
	}

	private MarkerOptions markerOption;

	/**
	 * 在地图上添加marker
	 */
	Marker marker;

	private void addMarkersToMap(LatLng ll) {
		// TO DO 这必须有一个状态的判断
		marker = aMap.addMarker(new MarkerOptions().position(ll).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
	}

	/****************************** 周期函数销毁对象 ******************************/
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		unbindService(mGpsConn);
		if (null != mlocationClient) {
			mlocationClient.onDestroy();
		}
	}
}

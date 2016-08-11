package com.iwant.oogpstest;

import java.io.Serializable;

import com.amap.api.location.AMapLocation;

/**
 * ClassName:PositionEntity <br/>
 * Function: 封装的关于位置的实体 <br/>
 * 
 */
public class PositionEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double mLatitude; // 纬度
	private double mLongitude;// 经度
	public String mCity = null;// 城市名
	public String myLocation = null;// 我的位置
	private String mDistrict;// 城区信息
	private String mStreet;// 街道
	private String mStreetNUm;// 街道门牌号信息
	private String mDetailLocation;// mDistrict + mStreet + mStreetNUm
	private AMapLocation mlocation;//
	private String mCityDistrictLocation;// 城市+区或县

	public double getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public double getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmCity() {
		return mCity;
	}

	public void setmCity(String mCity) {
		this.mCity = mCity;
	}

	public String getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(String myLocation) {
		this.myLocation = myLocation;
	}

	public String getmDistrict() {
		return mDistrict;
	}

	public void setmDistrict(String mDistrict) {
		this.mDistrict = mDistrict;
	}

	public String getmStreet() {
		return mStreet;
	}

	public void setmStreet(String mStreet) {
		this.mStreet = mStreet;
	}

	public String getmStreetNUm() {
		return mStreetNUm;
	}

	public void setmStreetNUm(String mStreetNUm) {
		this.mStreetNUm = mStreetNUm;
	}

	public String getmDetailLocation() {
		return mDetailLocation;
	}

	public void setmDetailLocation(String mDetailLocation) {
		this.mDetailLocation = mDetailLocation;
	}

	public AMapLocation getMlocation() {
		return mlocation;
	}

	public void setMlocation(AMapLocation mlocation) {
		this.mlocation = mlocation;
	}

	public String getmCityDistrictLocation() {
		return mCityDistrictLocation;
	}

	public void setmCityDistrictLocation(String mCityDistrictLocation) {
		this.mCityDistrictLocation = mCityDistrictLocation;
	}

}

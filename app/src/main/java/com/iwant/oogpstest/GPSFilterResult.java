package com.iwant.oogpstest;

import android.os.Parcel;
import android.os.Parcelable;

public class GPSFilterResult implements Parcelable {

	private String state;
	private String deviceUtcDate;
	private String latitude;
	private String longitude;
	private String speed;
	private String course;
	private String isStop;
	private String icon;
	private String distance;
	private String gpslatitude;
	private String gpslongitude;

	public GPSFilterResult() {
		state = "";
		deviceUtcDate = "";
		latitude = "";
		longitude = "";
		speed = "";
		course = "";
		isStop = "";
		icon = "";
		distance = "";
		gpslatitude = "";
		gpslongitude = "";
	}

	public GPSFilterResult(Parcel in) {
		state = in.readString();
		deviceUtcDate = in.readString();
		latitude = in.readString();
		longitude = in.readString();
		speed = in.readString();
		course = in.readString();
		isStop = in.readString();
		icon = in.readString();
		distance = in.readString();
		gpslatitude = in.readString();
		gpslongitude = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(state);
		dest.writeString(deviceUtcDate);
		dest.writeString(latitude);
		dest.writeString(longitude);
		dest.writeString(speed);
		dest.writeString(course);
		dest.writeString(isStop);
		dest.writeString(icon);
		dest.writeString(distance);
		dest.writeString(gpslatitude);
		dest.writeString(gpslongitude);
	}

	public static final Creator<GPSFilterResult> CREATOR = new Creator<GPSFilterResult>() {

		public GPSFilterResult createFromParcel(Parcel in) {
			return new GPSFilterResult(in);
		}

		public GPSFilterResult[] newArray(int size) {
			return new GPSFilterResult[size];
		}
	};

	/********************* getter and setter ***********************/

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeviceUtcDate() {
		return deviceUtcDate;
	}

	public void setDeviceUtcDate(String deviceUtcDate) {
		this.deviceUtcDate = deviceUtcDate;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getIsStop() {
		return isStop;
	}

	public void setIsStop(String isStop) {
		this.isStop = isStop;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getGpslatitude() {
		return gpslatitude;
	}

	public void setGpslatitude(String gpslatitude) {
		this.gpslatitude = gpslatitude;
	}

	public String getGpslongitude() {
		return gpslongitude;
	}

	public void setGpslongitude(String gpslongitude) {
		this.gpslongitude = gpslongitude;
	}

}

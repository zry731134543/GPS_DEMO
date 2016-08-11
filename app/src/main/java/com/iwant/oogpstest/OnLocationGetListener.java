package com.iwant.oogpstest;


/**
 * ClassName:OnLocationGetListener <br/>
 * Function: 定位完成后回调接口<br/>
 * Date: 2015年4月2日 下午6:17:17 <br/>
 */
public interface OnLocationGetListener {

	public void onLocationGet(PositionEntity entity);
	public void onGetLocationFail();

}

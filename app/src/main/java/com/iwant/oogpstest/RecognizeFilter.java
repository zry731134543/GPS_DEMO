package com.iwant.oogpstest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

/**
 * @Description: 解析父类(WeatherFilterResult、MinuteFilterResult、HourFilterResult、
 *               DailyFilterResult)
 * @author cyzheng
 * @date 2016年06月28日
 * 
 *       TAG = RecognizeFilter
 */
public abstract class RecognizeFilter {

	protected static final String TAG = "RecognizeFilter";

	/**
	 * 获取json
	 * 
	 * @param urlStr
	 * @return
	 */
	// TODO
	public static String getJsonContent(String urlStr) {
		String value = "fail";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(8000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				value = ConvertStream2Json(httpConn.getInputStream());
				return value;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Byte[] 转化成String
	 * 
	 * @param inputStream
	 * @return
	 */
	// TODO
	private static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	public abstract GPSFilterResult gpsFilterResult() throws Exception;

}

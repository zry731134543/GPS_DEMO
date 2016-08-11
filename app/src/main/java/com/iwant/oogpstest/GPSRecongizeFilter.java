package com.iwant.oogpstest;

import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import com.google.gson.Gson;

/**
 * @Description:
 * @author: whsgzcy
 * @date: 2016-7-26 上午11:18:49
 * 
 */
public class GPSRecongizeFilter extends RecognizeFilter {

	private String xmlData;

	public GPSRecongizeFilter() {
		super();
		//4456=2783 4455=2782
		//http://gps.gps66.com:8585/OpenAPIV2.asmx/GetTracking2?DeviceID=545&TimeZone=China Standard Time&MapType=Baidu
		String url = "http://gps.gps66.com:8585/OpenAPIV2.asmx/GetTracking2?DeviceID=2782&TimeZone=ChinaStandardTime&MapType=Baidu";
		xmlData = getJsonContent(url);
	}

	@Override
	public GPSFilterResult gpsFilterResult() throws Exception {
		
		if(xmlData.equals("fail")){
			return null;
		}
		
		GPSFilterResult gr = new GPSFilterResult();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlData));
			int eventType = xmlPullParser.getEventType();
			String value = "";
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				if (nodeName != null)
					switch (eventType) {
					case XmlPullParser.START_TAG: {
						if (nodeName.equals("string")) {
							value = xmlPullParser.nextText();
							Gson gson = new Gson();
							gr = gson.fromJson(value, GPSFilterResult.class);
						}
						break;
					}
					case XmlPullParser.END_TAG: {
						break;
					}
					default:
						break;
					}
				eventType = xmlPullParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gr;
	}
}

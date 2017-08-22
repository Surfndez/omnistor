package net.yostore.aws.api.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.BaseApi;
import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.sax.MediaInfo;

import org.xml.sax.SAXException;

import android.os.Build;
import android.util.Log;
import android.util.Xml;

public class MediaInfoHelper extends BaseHelper{
	private long fileId = -999;
	public MediaInfoHelper(long fileid){
		this.fileId=fileid;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		MediaInfo handler = new MediaInfo();
		
		String urlStr = "https://"+apicfg.webRelay+"/webrelay/directdownload/"+apicfg.token+"/?fi="+String.valueOf(fileId);
		URL url = new URL(urlStr);
		Log.d(TAG, urlStr);

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(BaseApi.TIMEOUT); // 30 sec
		conn.setReadTimeout(BaseApi.TIMEOUT);
		conn.setRequestMethod("GET");

		ApiCookies apicookies = new ApiCookies();
		String cookieStr = "sid="+apicookies.getSid()+";c="+apicookies.getC_ClientType()+";v="+apicookies.getV_ClientVersion()+";x-v="+BaseApi.clientversion+";EEE_MANU_Maunfactory="+apicookies.getEEE_MANU_Maunfactory()+";EEE_PROD_ProductModal="+apicookies.getEEE_PROD_ProductModal()+";OS_VER="+Build.VERSION.SDK+";";
		conn.setRequestProperty("extension-pragma", cookieStr);
		conn.setRequestProperty("Cookie", cookieStr);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();

		InputStream in = conn.getInputStream();
		Xml.parse(in, Xml.Encoding.UTF_8, handler);

		conn.disconnect();
		ApiResponse response = handler.getResponse();
		Log.d(TAG, String.valueOf(response.getStatus()));
		return response;
	}
}

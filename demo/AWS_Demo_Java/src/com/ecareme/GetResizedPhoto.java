package com.ecareme;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;

import asuswebstorage.user.info.userInfo;
import entity.AcquireTokenResponse;

public class GetResizedPhoto 
{
	
	static String API = "/webrelay/getresizedphoto/";// Service api
	
	public static void main(String[] args) throws Exception
	{		
		/* Fetching token from RequestServiceGateway by userid & pwd. Then you will get token, Inforelay and Webrelay domain/IP */
		AcquireToken at = new AcquireToken();
		AcquireTokenResponse acquireTokenResponse = null;
		try {
			acquireTokenResponse = at.getResponse(userInfo.userid, userInfo.pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// The correct AcquireToken response payload's status value must equals "0
		if (acquireTokenResponse == null || !"0".equals(acquireTokenResponse.getStatus())) {
			throw new Exception("Error : You have to check the AcquireToken api response");
		}
		
		String token = acquireTokenResponse.getToken();// Token value
		String server = acquireTokenResponse.getWebRelay();// Connection server
		
		long photoFileID = 123455555;// Resizing the specify photo's id
		int type = 0;// Resized photo SizeType: 0 = 1024X768 | 1 = 100X100 | 2 = 50X50 | 3 = 640X480 | 4 = 150X150		
		boolean preview = false;// Preview file			
		int ecd = 1;// The fixed parameter
		
		String headerString = "pfd=" + photoFileID + ",st=" + type + ",pv=" + preview ;
		//String headerStringToBase64 = URLEncoder.encode(Base64.encodeToBase64String(headerString), "UTF-8");// The query string, it needs to be encode by Base64 and urlencode	
		String headerStringToBase64 = URLEncoder.encode(new sun.misc.BASE64Encoder().encode(headerString.getBytes()), "UTF-8");// The query string, it needs to be encode by Base64 and urlencode	
		
		String urlstr = server + API + token + "/" + headerStringToBase64 + ".jpg?dis=" +userInfo.sid + "&ecd=" + ecd ;	
				
		URL url = new URL(urlstr);
		
		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(60 * 1000); 
		connection.setReadTimeout(60 * 1000);
		
		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(userInfo.sid).append(";"); // Cookies have to add the value of SID
		
		connection.addRequestProperty("cookie", cookie.toString());// User must add the cookies in the header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);			
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		}

		/* Saving subsidiary photo to a file */
		String name = "downloadphoto.jpg";// Resized photo's name
		
		FileOutputStream fo = new FileOutputStream("D:\\"+name);
		try {
			IOUtils.copy(connection.getInputStream(), fo);
			fo.close();
			System.out.println(" --- FINISH GETRESIZEDPHOTO --- \n");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("GetResizedPhoto Error:" + e.getMessage());
		} finally {
			IOUtils.closeQuietly(fo);
			connection.disconnect();
		}
	}	
}


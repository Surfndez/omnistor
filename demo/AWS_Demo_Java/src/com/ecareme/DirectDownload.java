package com.ecareme;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;

import asuswebstorage.user.info.userInfo;
import entity.AcquireTokenResponse;

public class DirectDownload 
{	
	
	static String API = "/webrelay/directdownload/";// Service api
	public static void main(String[] args) throws Exception
	{		
		/* Fetching token from RequestServiceGateway by userid & pwd. Then you will get token, Inforelay and webrelay domain/IP */
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
		
		long fileID = 384022180;// Directdownloading the specify file's id
		boolean preview = false;// Preview file

		String urlstr = server + API + token + "/?dis=" + userInfo.sid + "&fi=" + fileID + "&pv=" + preview;// Compose the query string				
		URL url = new URL(urlstr);
		
		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(60 * 1000); 
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(userInfo.sid).append(";");// Cookies have to add the value of SID

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

		/* Downloading file to computer */
		String name = connection.getHeaderField("Content-Disposition").substring(22, connection.getHeaderField("Content-Disposition").length()-1);// Get downloading filename
		
		FileOutputStream fo = new FileOutputStream("D:\\" + name);
		try {
			IOUtils.copy(connection.getInputStream(), fo);
			fo.close();
			System.out.println(" --- FINISH DOWNLOAD FILE --- \n");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Get Download File Error:" + e.getMessage());
		} finally {
			connection.disconnect();
		}
	}	
}

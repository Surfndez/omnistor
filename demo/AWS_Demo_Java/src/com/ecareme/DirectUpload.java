package com.ecareme;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

import asuswebstorage.user.info.userInfo;
import sax.MultiPart;
import entity.AcquireTokenResponse;

public class DirectUpload {

	static String API = "/webrelay/directupload/";// Service api

	public static void main(String[] args) throws Exception {
		/*
		 * Fetching token from RequestServiceGateway by userid & pwd. Then you
		 * will get token, Inforelay and Webrelay domain/IP
		 */
		AcquireToken at = new AcquireToken();
		AcquireTokenResponse acquireTokenResponse = null;
		try {
			acquireTokenResponse = at.getResponse(userInfo.userid, userInfo.pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// The correct AcquireToken response payload's status value must equals
		// "0
		if (acquireTokenResponse == null
				|| !"0".equals(acquireTokenResponse.getStatus())) {
			throw new Exception(
					"Error : You have to check the AcquireToken api response");
		}
		String token = acquireTokenResponse.getToken();// Token value
		String server = acquireTokenResponse.getWebRelay();// Connection server

		String urlstr = server + API;
		URL url = new URL(urlstr);

		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setConnectTimeout(60 * 1000);
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(userInfo.sid).append(";");// Cookies have to add the
														// value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must
																	// add the
																	// cookies
																	// in the
																	// header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		}

		String parentID = "42157194";// Parent folder's id
		
		String Parent_folder="testupload";
		String folderDisplay = new sun.misc.BASE64Encoder().encode( Parent_folder.getBytes()); // Parent folder's name

		long progressID = 123;// Check the status of the upload

		// Directuploading the specify file attribute, it needs to be encoded
		String attribute = URLEncoder
				.encode("<creationtime>1313054123</creationtime><lastaccesstime>1313054123</lastaccesstime><lastwritetime>1313054123</lastwritetime>",
						"UTF-8");

		int fileSize = 4 * 1024; // Size of uploaded file in one time

		long olderFileID = 20090123;// If you want to overwrite the original
									// file in the server, you need to specify
									// this parameter

		boolean autoRename = false;// If the upload file already exists in the
									// server, this parameter means auto rename
									// this file ???

		String urlQuery = token + "/?dis=" + userInfo.sid;
		String urlStrDirectupload = server + API + urlQuery;//D:\\testuploadfile\\log.txt

		File file = new File("D:\\testuploadfile\\", "log.txt");// The path of
															// uploading file
		String filename = "log.txt";// Directuploading the specify file's
										// name

		/* Doing MultiPart to upload file */
		URL urlDirectupload = new URL(urlStrDirectupload);
		HttpsURLConnection connectionDirectupload = (HttpsURLConnection) urlDirectupload
				.openConnection();

		// MultiPart form
		try {
			MultiPart multipart = new MultiPart(connectionDirectupload);
			multipart.setParameter("pa", parentID);
			multipart.setParameter("d", folderDisplay);
			multipart.setParameter("pr", progressID);
			multipart.setParameter("at", attribute);
			multipart.setParameter("fs", fileSize);
			multipart.setParameter("fi", olderFileID);
			multipart.setParameter("ar", autoRename);
			multipart.setParameter(filename, file);

			multipart.post();

		} catch (IOException e) {
			throw new Exception(
					"Error : You have to check MultiPart when doing DirectUpload api");
		}
	}
}

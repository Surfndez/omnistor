package com.ecareme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import asuswebstorage.user.info.userInfo;
import sax.ByteUtils;
import entity.AcquireTokenResponse;
import entity.FinishBinaryUploadResponse;
import entity.InitbinaryUploadResponse;
import entity.ResumeBinaryUploadResponse;

public class BinaryUpload 
{
	static String initbinaryuploadAPI = "/webrelay/initbinaryupload/";// InitbinaryUpload service api
	static String resumebinaryUploadAPI = "/webrelay/resumebinaryupload/";// ResumebinaryUpload service api
	static String finishbinaryUploadAPI = "/webrelay/finishbinaryupload/";// FinishbinaryUpload service api
	
	static String filepath="D:\\testuploadfile\\log.txt";
	static String file_name="log.txt";

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
		
		/** initbinaryUpload */
		
		String filename=new sun.misc.BASE64Encoder().encode( file_name.getBytes());
		
		System.out.println(filename);
		filename = URLEncoder.encode(filename, "UTF-8");
		
		long parentFolderID = 42157194;// Uploading the specify file's id	
				
		/* Hash the file context using the SHA512 hashing algorithm then get the result - fileChecksum */
		String fileChecksum ;
		int BUFFER_SIZE = 1024 * 1024;		
		FileInputStream uploadFile = new FileInputStream(new File(filepath));
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		DigestInputStream digestInputstream = new DigestInputStream(uploadFile, messageDigest);
		
		byte[] buff = new byte[BUFFER_SIZE];
		long count = 0 ;
		int read = 0;
		while ((read = digestInputstream.read(buff, 0, BUFFER_SIZE)) != -1) {
			count += read;
		}
		uploadFile.close();
		digestInputstream.close();
		byte[] checksum = messageDigest.digest();
		fileChecksum = ByteUtils.getHexString(checksum);// Get the uploading file's fileChecksum
		
		// The uploading file's attribute
		String attribute = URLEncoder.encode("<creationtime>1313054123</creationtime><lastaccesstime>1313054123</lastaccesstime><lastwritetime>1313054123</lastwritetime>", "UTF-8");      
		
		int FILESIZE_UPLOAD = 4*1024 ;// File can be uploaded size each time
		
		/* Get the transid value from latest initbinaryupload response */
		InitbinaryUploadResponse latestInitbinaryuploadresponse = getLatestInitResponse();			
		String transid = latestInitbinaryuploadresponse.getTransid();			
		
		long topFolderID = 42157194;// Upload file to file's root directory, this parameter will saving your time of doing upload (optional parameter)
				
		// The fixed query string
		String fixedQuery = "?dis=" + userInfo.sid + "&tk=" + token + "&na="
				+ filename + "&pa=" + parentFolderID + "&sg="
				+ fileChecksum + "&at=" + attribute + "&fs="
				+ FILESIZE_UPLOAD;
		
		String queryInitbinaryUpload;// Query string
		
		// If the value of "transid" exists. Then you can get the uploaded total size in last time. Using this information can continue uploading since the last time
		if (transid != null) {
			queryInitbinaryUpload = fixedQuery + "&tx=" + transid + "&sc=" + topFolderID;// Compose the query string
		} else {
			queryInitbinaryUpload = fixedQuery + "&sc=" + topFolderID;// Compose the query string
		}
				
		String urlstr = server + initbinaryuploadAPI + queryInitbinaryUpload;
		
		
		HttpsURLConnection connectionInitbinaryUpload = connection(urlstr);// The HttpsURLConnection
		InitbinaryUploadResponse initbinaryuploadresponse = getInitResponse(connectionInitbinaryUpload);// Get the response from the server and parse it
		
		// Printout the response payload
		try {
			if (!"0".equals(initbinaryuploadresponse.getStatus())) {
				System.out.println(initbinaryuploadresponse.toString());
				
				throw new Exception("Error : You have to check the InitbinaryUpload api response");
			} else {
				System.out.println(initbinaryuploadresponse.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/* Get the "transid" from initbinaryupload response now */
		transid = initbinaryuploadresponse.getTransid();

		/** resumeBinaryUpload */	
		String queryResumeBinaryUpload;// Query string
		queryResumeBinaryUpload = "?dis=" + userInfo.sid + "&tk=" + token + "&tx=" + transid;// Compose the query string
		
		String urlStrResumeBinaryUpload = server + resumebinaryUploadAPI + queryResumeBinaryUpload;
		
		HttpsURLConnection connectionResumeBinaryUpload = connection(urlStrResumeBinaryUpload, filepath);// The HttpsURLConnection
		ResumeBinaryUploadResponse resumebinaryuploadresponse = getResumeResponse(connectionResumeBinaryUpload);// Get the response from the server and parse it
		
		// Printout the response payload
		try {
			if (!"0".equals(resumebinaryuploadresponse.getStatus())) {
				throw new Exception("Error : You have to check the ResumebinaryUpload api response");
			} else {
				System.out.println(resumebinaryuploadresponse.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}	
		
		/** finishBinaryUpload */
		String queryFinishBinaryUpload;// Query string
		
		/* Get the latest checksum value from latest initbinaryupload response */
		String latestchecksum = latestInitbinaryuploadresponse.getLatestchecksum();		
		
		// If the value of "latestchecksum" exists. Then you have to check this file's version
		if (latestchecksum != null) {
			queryFinishBinaryUpload = "?dis="+ userInfo.sid +"&tk="+ token + "&tx=" + transid + "&lsg=" + latestchecksum;// Compose the query string
		}else{
			queryFinishBinaryUpload = "?dis="+ userInfo.sid +"&tk="+ token + "&tx=" + transid;// Compose the query string
		}
		
		String urlStrFinishBinaryUpload = server + finishbinaryUploadAPI + queryFinishBinaryUpload;
		
		HttpsURLConnection connectionFinishBinaryUpload = connection(urlStrFinishBinaryUpload);// The HttpsURLConnection
		FinishBinaryUploadResponse finishbinaryuploadresponse = getFinishResponse(connectionFinishBinaryUpload);// Get the response from the server and parse it

		// Printout the response payload
		try {
			if (!"0".equals(finishbinaryuploadresponse.getStatus())) {
				throw new Exception("Error : You have to check the FinishbinaryUpload api response");
			} else {
				System.out.println(finishbinaryuploadresponse.toString());
				System.out.println("THE UPLOAD FILE'S ID IS : " + finishbinaryuploadresponse.getFileid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static HttpsURLConnection connection(String urlstr) throws IOException 
	{
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

		return connection;
	}

	public static HttpsURLConnection connection(String urlstr, String FilePath) throws IOException 
	{
		return connection(urlstr, FilePath, 0);
	}
	
	public static HttpsURLConnection connection(String urlstr, String FilePath, long offset) throws IOException 
	{
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

		/* Doing the file inputstream for uploading file */
		FileInputStream baf = null;
		try {
			baf = new FileInputStream(FilePath);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Please check out the uploading file");
			e.printStackTrace();
			return null;
		}
		baf.skip(offset);// "Offset" represents the finished uploading file's size in last time. You can use this parameter to saving your time of doing upload
		OutputStream os = connection.getOutputStream();
		int BUFFER_FILESIZE = 1024 * 1024;
		byte[] buff = new byte[BUFFER_FILESIZE];
		int read = 0;
		while ((read = baf.read(buff)) != -1) {
			os.write(buff, 0, read);
		}
		os.flush();
		os.close();
		baf.close();

		return connection;
	}

	public static InitbinaryUploadResponse getInitResponse(HttpsURLConnection connection) throws ParserConfigurationException, SAXException, IOException
	{
		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		InitbinaryUploadResponse initbinaryuploadresponse = new InitbinaryUploadResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if(nodelist.item(j).getNodeName().equals("status")){
				initbinaryuploadresponse.setStatus(nodelist.item(j).getTextContent());
			}	
			if(nodelist.item(j).getNodeName().equals("transid")){
				initbinaryuploadresponse.setTransid(nodelist.item(j).getTextContent());
			}	
			if(nodelist.item(j).getNodeName().equals("offset")){
				initbinaryuploadresponse.setOffset(Long.parseLong(nodelist.item(j).getTextContent()));
			}	
			if(nodelist.item(j).getNodeName().equals("latestchecksum")){
				initbinaryuploadresponse.setLatestchecksum(nodelist.item(j).getTextContent());
			}
			if(nodelist.item(j).getNodeName().equals("fileid")){
				initbinaryuploadresponse.setFileid(nodelist.item(j).getTextContent());				
			}
		}
		
		return initbinaryuploadresponse;
	}
	
	/* Get latest initbinaryupload response */
	public static InitbinaryUploadResponse getLatestInitResponse() 
	{		
		InitbinaryUploadResponse initbinaryuploadresponse = new InitbinaryUploadResponse();
		return initbinaryuploadresponse;
	}

	public static ResumeBinaryUploadResponse getResumeResponse(HttpsURLConnection connection) throws ParserConfigurationException, SAXException, IOException 
	{
		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		ResumeBinaryUploadResponse resumebinaryuploadresponse = new ResumeBinaryUploadResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if(nodelist.item(j).getNodeName().equals("status")){
				resumebinaryuploadresponse.setStatus(nodelist.item(j).getTextContent());
			}						
		}
		
		return resumebinaryuploadresponse;
	}

	public static FinishBinaryUploadResponse getFinishResponse(HttpsURLConnection connection) throws ParserConfigurationException, SAXException, IOException
	{
		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		FinishBinaryUploadResponse finishbinaryuploadresponse = new FinishBinaryUploadResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if(nodelist.item(j).getNodeName().equals("status")){
				finishbinaryuploadresponse.setStatus(nodelist.item(j).getTextContent());
			}	
			if(nodelist.item(j).getNodeName().equals("fileid")){
				finishbinaryuploadresponse.setFileid(nodelist.item(j).getTextContent());
			}	
			if(nodelist.item(j).getNodeName().equals("logmessage")){
				finishbinaryuploadresponse.setLogmessage(nodelist.item(j).getTextContent());
			}						
		}
		
		return finishbinaryuploadresponse;	
	}
}
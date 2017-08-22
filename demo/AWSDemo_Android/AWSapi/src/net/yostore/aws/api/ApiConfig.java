package net.yostore.aws.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ApiConfig 
{
	/**
	 * Service Portal Domain = asuscloudportal01.asuswebstorage.com//sp.yostore.net
	 */	
	public static String SERVICEPORTAL = "asuscloudportal01.asuswebstorage.com";//"sp.yostore.net";
	 
	/**
	 * ServiceGateway Domain is fetched by UserId & HashedPassword from ServicePortal 
	 */	
	public String ServiceGateway = "";
	
	public final String SYNCROOTID	   = "-5";
	public final String SYNCFOLDERNAME = "MySyncFolder";

	/***
	 * MySyncFolder Real FolderId
	 */
	public String mySyncFolderId  = "";
	public String parentFolderId  = SYNCROOTID;
	public String parentName	  = "";
	public String currentFolderId = "";
	public String folderName	  = SYNCFOLDERNAME;

	public String userid = "";

	/**
	 * Hashed Password (lower case) by MD5 
	 */	
	public String password = "";
	
	/**
	 * InfoRelay Domain (IP) 
	 */	
	public String infoRelay = "";
	/**
	 * Web Relay Domain (IP) 
	 */	
	public String webRelay = "";

	public String token = "";
	
	/**
	 * Account Package Name 
	 */	
	public String packageDisplay = "";

	public String capacity = "0";
	
	public String usedquota = "";
	
	public String expireDate = "";
	
	
	public static final String SEPARATOR = "\n";

	public static ApiConfig getFromString(String ss){
		ApiConfig apicfg = null;

		if (ss!=null){
			String str = null;
			BufferedReader reader = new BufferedReader(new StringReader(ss));
			apicfg = new ApiConfig();

			try {

				if ((str = reader.readLine()) != null) apicfg.userid 		= str;
				if ((str = reader.readLine()) != null) apicfg.password 		= str;
				if ((str = reader.readLine()) != null) apicfg.token 		= str;
				if ((str = reader.readLine()) != null) apicfg.ServiceGateway= str;
				if ((str = reader.readLine()) != null) apicfg.infoRelay 	= str;
				if ((str = reader.readLine()) != null) apicfg.webRelay 		= str;
				if ((str = reader.readLine()) != null) apicfg.packageDisplay= str;
				if ((str = reader.readLine()) != null) apicfg.capacity		= str;
				if ((str = reader.readLine()) != null) apicfg.expireDate	= str;
//				if ((str = reader.readLine()) != null) apicfg.mEarMeta 		= str;
				if ((str = reader.readLine()) != null) apicfg.mySyncFolderId= str;
//				if ((str = reader.readLine()) != null) apicfg.pixWeAlbum 	= str;

			} catch(IOException e) {
				e.printStackTrace();
			}


		}

		return apicfg;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userid);
		sb.append(SEPARATOR);
		sb.append(password);
		sb.append(SEPARATOR);
		sb.append(token);
		sb.append(SEPARATOR);
		sb.append(ServiceGateway);
		sb.append(SEPARATOR);
		sb.append(infoRelay);
		sb.append(SEPARATOR);
		sb.append(webRelay);
		sb.append(SEPARATOR);
		sb.append(packageDisplay);
		sb.append(capacity);
		sb.append(expireDate);
//		sb.append(mEarMeta);
		sb.append(SEPARATOR);
		sb.append(mySyncFolderId);
		sb.append(SEPARATOR);
//		sb.append(pixWeAlbum);

		return sb.toString();
	}
}

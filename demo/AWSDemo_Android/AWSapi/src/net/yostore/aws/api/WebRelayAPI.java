package net.yostore.aws.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.DirectUploadRequest;
import net.yostore.aws.api.entity.DirectUploadResponse;
import net.yostore.aws.api.entity.FinishBinaryUploadRequest;
import net.yostore.aws.api.entity.FinishBinaryUploadResponse;
import net.yostore.aws.api.entity.GetFullTextCompanionRequest;
import net.yostore.aws.api.entity.GetResizedPhotoRequest;
import net.yostore.aws.api.entity.GetVideoSnapshotRequest;
import net.yostore.aws.api.entity.GetVideoconvertprogressResponse;
import net.yostore.aws.api.entity.InitBinaryUploadRequest;
import net.yostore.aws.api.entity.InitBinaryUploadResponse;
import net.yostore.aws.api.entity.ResumeBinaryUploadRequest;
import net.yostore.aws.api.entity.ResumeBinaryUploadResponse;
import net.yostore.aws.api.sax.DirectUpload;
import net.yostore.aws.api.sax.FinishBinaryUpload;
import net.yostore.aws.api.sax.GetVideoconvertprogress;
import net.yostore.aws.api.sax.InitBinaryUpload;
import net.yostore.aws.api.sax.ResumeBinaryUpload;
import net.yostore.utility.Base64;
import net.yostore.utility.HttpsUtil;

//import org.xml.sax.SAXException;

public class WebRelayAPI extends BaseApi{	
	
	private static final String TAG = "WebRelayAPI";

	public WebRelayAPI(String ServerDomain) {
		super(ServerDomain);
	}

	public DirectUploadResponse directDataUpload(DirectUploadRequest request, ApiConfig apicfg){
		return (DirectUploadResponse)super.dataUpload(
				"https://" + apicfg.webRelay + "/webrelay/directupload/" + apicfg.token + "/",
				request.getFileName(),
				request.getData(),
				request.getParentid(),
				new DirectUpload());
	}

	public DirectUploadResponse directFileUpload(DirectUploadRequest request, ApiConfig apicfg){
		return (DirectUploadResponse)super.fileUpload(
				"https://" + apicfg.webRelay + "/webrelay/directupload/" + apicfg.token + "/", 
				request.getFile(),
				request.getParentid(),
				new DirectUpload());
		
	}
	
	public InitBinaryUploadResponse initBinaryUpload(InitBinaryUploadRequest request, ApiConfig apicfg) throws Exception
	{		
		StringBuilder url = new StringBuilder("/webrelay/initbinaryupload/");
		url.append("?tk=").append(request.getToken())
		   .append("&pa=").append(request.getParent())
		   .append("&fs=").append(request.getFileSize())
		   .append("&sg=").append(request.getChecksum())
		   .append("&at=").append(request.getAttribute())
		   .append("&dis=").append(request.getSid());
		
		if ( request.getName() != null && request.getName().trim().length() > 0 )
			url.append("&na=").append(request.getName().trim());
		if ( request.getTransactionId() != null && request.getTransactionId().trim().length() > 0 )
			url.append("&tx=").append(request.getTransactionId().trim());
		if ( request.getFileId() != null && request.getFileId() > 0 )
			url.append("&fi=").append(request.getFileId());
		if ( request.getSyncFolderId() != null )
			url.append("&sc=").append(request.getSyncFolderId());
		
		return (InitBinaryUploadResponse)super.BinaryUpload(url.toString(), new InitBinaryUpload());
	}
	
	public ResumeBinaryUploadResponse resumeBinaryUpload(ResumeBinaryUploadRequest request, ApiConfig apicfg, String file) throws Exception
	{
		StringBuilder url = new StringBuilder("/webrelay/resumebinaryupload/");
		url.append("?tk=").append(request.getToken());
		url.append("&tx=").append(request.getTransactionId());
		url.append("&dis=").append(request.getSID());
		
		return (ResumeBinaryUploadResponse)super.BinaryUpload(url.toString(), new ResumeBinaryUpload(), file);
	}
	
	public FinishBinaryUploadResponse finishBinaryUpload(FinishBinaryUploadRequest request, ApiConfig apicfg) throws Exception
	{
		StringBuilder url = new StringBuilder("/webrelay/finishbinaryupload/");
		url.append("?tk=").append(request.getToken())
		   .append("&tx=").append(request.getTransactionId())
		   .append("&dis=").append(request.getSID());
		
		if ( request.getLatestChecksum() != null && request.getLatestChecksum().trim().length() > 0 )
			url.append("&lsg=").append(request.getLatestChecksum().trim());
		return (FinishBinaryUploadResponse)super.BinaryUpload(url.toString(), new FinishBinaryUpload());
	}
	
	public byte[] getFullTextCompanion(GetFullTextCompanionRequest request, ApiConfig apicfg) throws Exception
	{
		StringBuilder msg = new StringBuilder();		
		StringBuilder para = new StringBuilder();
		
		para.append("fi=").append(request.getFileId())
			.append(",k=").append(request.getKind())
			.append(",pv=").append(request.isPreview() ? 1 : 0);
		
		String hashPara = null;
		try
		{
			hashPara = Base64.encodeToBase64String(para.toString(), "UTF-8");
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}

		StringBuilder urlStr = new StringBuilder();
		urlStr.append("https://").append(apicfg.webRelay)
		   .append("/webrelay/getfulltextcompanion/")
		   .append(apicfg.token).append("/").append(hashPara)
		   .append(".jpg?dis=").append(ApiCookies.sid)
		   .append("&ecd=1");
		
		try
		{
			URL url = new URL(urlStr.toString());
			Log.d(TAG, urlStr.toString());
			
			SSLContext sc = null;
			try
			{
				sc = SSLContext.getInstance("SSL");
				sc.init(null, HttpsUtil.getTrustAllCertsTrustManager(), new java.security.SecureRandom());
			}
			catch ( Exception e )
			{
				msg.delete(0, msg.length());
				msg.append("Initial SSL error:").append(e.getMessage());
				Log.e(TAG, msg.toString(), e);
				
				throw e;
			}
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(HttpsUtil.getverifyAlHostnameVerifier());
			conn.setConnectTimeout(TIMEOUT); // 60 sec
			conn.setReadTimeout(TIMEOUT);
			conn.setRequestMethod("POST");

			//Compose Developer Authorization String
			String authorization = null;
			try
			{
				authorization = composeAuthorizationHeader();
			}
			catch ( Exception e )
			{
				msg.delete(0, msg.length());
				msg.append("Composing developer authorization string error:").append(e.getMessage());
				Log.e(TAG, msg.toString(), e);
				
				throw e;
			}
			
			//Setting developer authorization string into header
			conn.addRequestProperty("Authorization", authorization);
			
			StringBuilder cookie = new StringBuilder();
			cookie.append("sid=").append(ApiCookies.sid).append(";")
				  .append("c=").append(ApiCookies.c_ClientType).append(";")
				  .append("v=").append(ApiCookies.v_ClientVersion).append(";")
				  .append("EEE_MANU=").append(ApiCookies.EEE_MANU_Maunfactory).append(";")
				  .append("EEE_PROD=").append(ApiCookies.EEE_PROD_ProductModal).append(";")
				  .append("OS_VER=").append(Build.VERSION.SDK).append(";")
				  ;
			
			conn.addRequestProperty("cookie", cookie.toString());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try
			{
				conn.connect();
			}
			catch ( IOException ioe )
			{
				Log.e(TAG, "Get Connection Error:" + ioe.getMessage(), ioe);
				throw ioe;
			}

			InputStream in = conn.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(in, bos);
			in.close();
			return bos.toByteArray();
		}
		catch ( Exception e )
		{
			msg.append("BinaryUpload Error:").append(e.getMessage());
			Log.e(TAG, msg.toString(), e);
			
			throw e;
		}
	}
	
	public Bitmap getVideoSnapshot(GetVideoSnapshotRequest request, ApiConfig apicfg) throws Exception
	{
		StringBuilder para = new StringBuilder();
		
		para.append("fi=").append(request.getFileId())
		    .append(",pv=").append(request.isPreview() ? 1 : 0);
		
		return getSubsidiaryPhoto("/webrelay/getvideosnapshot/", para.toString(), apicfg);
	}
	
	public Bitmap getResizedPhoto(GetResizedPhotoRequest request, ApiConfig apicfg) throws Exception
	{
		StringBuilder para = new StringBuilder();

		para.append("pfd=").append(request.getFileId())
	    	.append(",st=").append(request.getSizeType())
	    	.append(",preview=").append(request.isPreview() ? 1 : 0);
		
		return getSubsidiaryPhoto("/webrelay/getresizedphoto/", para.toString(), apicfg);
	}
	
	public Bitmap getSubsidiaryPhoto(String api, String para, ApiConfig apicfg) throws Exception
	{
		StringBuilder msg = new StringBuilder();		
		
		String hashPara = null;
		try
		{
			hashPara = Base64.encodeToBase64String(para, "UTF-8");
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}

		StringBuilder urlStr = new StringBuilder();
		urlStr.append("https://").append(apicfg.webRelay).append(api)
		   .append(apicfg.token).append("/").append(hashPara)
		   .append(".jpg?dis=").append(ApiCookies.sid)
		   .append("&ecd=1");
		
		try
		{
			URL url = new URL(urlStr.toString());
			Log.d(TAG, urlStr.toString());
			
			SSLContext sc = null;
			try
			{
				sc = SSLContext.getInstance("SSL");
				sc.init(null, HttpsUtil.getTrustAllCertsTrustManager(), new java.security.SecureRandom());
			}
			catch ( Exception e )
			{
				msg.delete(0, msg.length());
				msg.append("Initial SSL error:").append(e.getMessage());
				Log.e(TAG, msg.toString(), e);
				
				throw e;
			}
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(HttpsUtil.getverifyAlHostnameVerifier());
			conn.setConnectTimeout(TIMEOUT); // 60 sec
			conn.setReadTimeout(TIMEOUT);
			conn.setRequestMethod("POST");

			//Compose Developer Authorization String
			String authorization = null;
			try
			{
				authorization = composeAuthorizationHeader();
			}
			catch ( Exception e )
			{
				msg.delete(0, msg.length());
				msg.append("Composing developer authorization string error:").append(e.getMessage());
				Log.e(TAG, msg.toString(), e);
				
				throw e;
			}
			
			//Setting developer authorization string into header
			conn.addRequestProperty("Authorization", authorization);
			
			StringBuilder cookie = new StringBuilder();
			cookie.append("sid=").append(ApiCookies.sid).append(";")
				  .append("c=").append(ApiCookies.c_ClientType).append(";")
				  .append("v=").append(ApiCookies.v_ClientVersion).append(";")
				  .append("EEE_MANU=").append(ApiCookies.EEE_MANU_Maunfactory).append(";")
				  .append("EEE_PROD=").append(ApiCookies.EEE_PROD_ProductModal).append(";")
				  .append("OS_VER=").append(Build.VERSION.SDK).append(";")
				  ;
			
			conn.addRequestProperty("cookie", cookie.toString());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try
			{
				conn.connect();
			}
			catch ( IOException ioe )
			{
				Log.e(TAG, "Get Connection Error:" + ioe.getMessage(), ioe);
				throw ioe;
			}

			InputStream in = conn.getInputStream();
			Bitmap bm = BitmapFactory.decodeStream(in);
			in.close();
			
			return bm;
		}
		catch ( Exception e )
		{
			msg.append("BinaryUpload Error:").append(e.getMessage());
			Log.e(TAG, msg.toString(), e);
			
			throw e;
		}
	}
	public GetVideoconvertprogressResponse getvideoconVertprogress(ApiConfig apicfg,String fileID) throws IOException, SAXException{
		
		String url="/webrelay/getvideoconvertprogress/"+"?tk="+apicfg.token +"&fi="+fileID;
		
		return (GetVideoconvertprogressResponse)super.getResponse(url, "", new GetVideoconvertprogress());
	}
}

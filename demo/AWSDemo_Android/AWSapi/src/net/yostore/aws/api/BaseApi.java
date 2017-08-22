
package net.yostore.aws.api;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.DirectUploadResponse;
import net.yostore.aws.api.exception.APIException;
import net.yostore.aws.api.sax.BaseSaxHandler;
import net.yostore.aws.model.http.AWSHttpUploader;
import net.yostore.utility.Base64;
import net.yostore.utility.HttpsUtil;

import org.xml.sax.SAXException;

import android.os.Build;
//import android.util.Base64;
import android.util.Log;
import android.util.Xml;

public abstract class BaseApi
{
	private static final String SIGNATURE_METHOD = "HMAC-SHA1";
	
	private static final String TAG = "BaseApi";
	public static final int TIMEOUT = 60 * 1000;	//1min
	private String apiSvr;
	
	public static String clientversion="";
	
	protected BaseApi(String ServerDomain)
	{
		this.apiSvr = ServerDomain;
	}
	protected ApiResponse getResponse(String api, String params, BaseSaxHandler handler) throws MalformedURLException, ProtocolException, IOException, SAXException
	{
		String urlStr = "https://" + this.apiSvr + api;
		URL url = new URL(urlStr);
		Log.d(TAG, urlStr);
		Log.d(TAG, "SID:[" + ApiCookies.sid + "], ProgKey:[" + ApiCookies.progKey + "]");
		
		SSLContext sc = null;
		try
		{
			sc = SSLContext.getInstance("SSL");
			sc.init(null, HttpsUtil.getTrustAllCertsTrustManager(), new java.security.SecureRandom());
		}
		catch ( Exception e )
		{
			throw new IOException(e);
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
			StringBuilder msg = new StringBuilder();
			msg.append("Composing developer authorization string error:").append(e.getMessage());
			throw new MalformedURLException(msg.toString());
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
		
		// OUT
		 OutputStream out = conn.getOutputStream();
		 byte[] bytes = params.getBytes("UTF8");
		 out.write(bytes);
		 out.flush();
		 out.close();

		InputStream in = conn.getInputStream();
		
		Xml.parse(in, Xml.Encoding.UTF_8, handler);
			
//		conn.disconnect();
		ApiResponse response = handler.getResponse();
		Log.d(TAG, String.valueOf(response.getStatus()));
		return response;
	}
	
	public ApiResponse fileUpload(String url, File file, String parentid, BaseSaxHandler handler)
	{
		return fileUpload(url, file, file.getName(), parentid, handler);
	}
	
	public ApiResponse fileUpload(String url, File file, String fileName, String parentid, BaseSaxHandler handler)
	{
		return directIsUpload(url, fileName, file, parentid, handler);
	}

	public ApiResponse dataUpload(String url, String fileName, byte[] data, String parentid, BaseSaxHandler handler)
	{
		return directIsUpload(url, fileName, new ByteArrayInputStream(data), parentid, handler);
	}

	private static final String end = "\r\n";
	private static final String twoHyphens = "--";
	private static final String boundary = "*****";

	protected ApiResponse directIsUpload(String urlstr, String fileName, InputStream input, String parentid, BaseSaxHandler handler)
	{
		try
		{

			URL url = new URL(urlstr);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();

			//Compose Developer Authorization String
			String authorization = null;
			try
			{
				authorization = composeAuthorizationHeader();
			}
			catch ( Exception e )
			{
				StringBuilder msg = new StringBuilder();
				msg.append("Composing developer authorization string error:").append(e.getMessage());
				throw new MalformedURLException(msg.toString());
			}
			
			//Setting developer authorization string into header
			con.setRequestProperty("Authorization", authorization);
			
			// TODO: Read ATDL...
//			ApiCookies apicookies = new ApiCookies();
			con.setRequestProperty("cookie", "sid="+ApiCookies.sid+";c="+ApiCookies.c_ClientType+";v="+ApiCookies.v_ClientVersion+";x-v="+BaseApi.clientversion+";EEE_MANU_Maunfactory="+ApiCookies.EEE_MANU_Maunfactory+";EEE_PROD_ProductModal="+ApiCookies.EEE_PROD_ProductModal+";OS_VER="+Build.VERSION.SDK+";");
			/* ���\Input�BOutput�A���ϥ�Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			// con.setUseCaches(false);
			/* �]�w�ǰe��method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			con.connect();

			/* �]�wDataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());

			addMultiPartString(ds, "pa", parentid);
			addMultiPartString(ds, "d", "");
			addMultiPartString(ds, "p", "0");
			addMultiPartString(ds, "pr", String.valueOf(System.currentTimeMillis()));

			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"");
			ds.write(fileName.getBytes("utf-8"));
			ds.writeBytes("\"" + end);
			ds.writeBytes(end);

			/* �]�w�C���g�J1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];

			int length = -1;
			/* �q�ɮ�Ū���Ʀܽw�İ� */

			while ( ( length = input.read(buffer) ) != -1 )
			{
				ds.write(buffer, 0, length);
				ds.flush();
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

			/* close streams */
			input.close();
			ds.flush();

			/* ��oResponse���e */
			InputStream is = con.getInputStream();
			Xml.parse(is, Xml.Encoding.UTF_8, handler);
			// con.disconnect();
			ds.close();

			return handler.getResponse();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("finally")
	protected ApiResponse directIsUpload(String url, String fileName, File input, String parentid, BaseSaxHandler handler)
	{
		DirectUploadResponse response = null;
		try
		{
			response = (DirectUploadResponse)AWSHttpUploader.UploadArtifact(url, parentid, fileName, input.getAbsolutePath());
		}
		catch ( APIException e )
		{
			Log.d(TAG, e.toString());
		}
		catch ( Exception e )
		{
			Log.d(TAG, e.toString());
		}
		finally
		{
			return response;
		}
	}
	
	public ApiResponse BinaryUpload(String api, BaseSaxHandler handler) throws Exception
	{
		return BinaryUpload(api, null, handler);
	}
	
	public ApiResponse BinaryUpload(String api, BaseSaxHandler handler, String file) throws Exception
	{
		FileInputStream fi = new FileInputStream(file);
		try
		{
			return BinaryUpload(api, fi, handler);
		}
		finally
		{
			try { fi.close(); } catch ( Exception e ) {}			
		}
	}
	
	public ApiResponse BinaryUpload(String api, InputStream input, BaseSaxHandler handler) throws Exception
	{
		StringBuilder msg = new StringBuilder();
		
		String urlStr = "https://" + this.apiSvr + api;
		try
		{
			URL url = new URL(urlStr);
			Log.d(TAG, urlStr);
			
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
			conn.setChunkedStreamingMode(0);

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
			
			if ( input != null )
			{
				Log.d(TAG, "Uploading file to server...");
				// OUT
				OutputStream out = conn.getOutputStream();
				int buflen = 16*1024;
				byte[] buff = new byte[buflen];
				int eof = 0;
				try
				{
//					IOUtils.copy(input, out);
					while ( -1 != (eof = input.read(buff, 0, buflen))  )
					{
						out.write(buff, 0, eof);
					}
				}
				catch ( IOException ie )
				{
					msg.delete(0, msg.length());
					msg.append("Uploading file to server error:").append(ie.getMessage());
					Log.e(TAG, msg.toString(), ie);
					
					ie.printStackTrace();
					
					throw ie;
				}
			}

			InputStream in = conn.getInputStream();		
			Xml.parse(in, Xml.Encoding.UTF_8, handler);
			
			ApiResponse response = handler.getResponse();
			Log.d(TAG, String.valueOf(response.getStatus()));
			return response;
		}
		catch ( Exception e )
		{
			msg.append("BinaryUpload Error:").append(e.getMessage());
			Log.e(TAG, msg.toString(), e);
			
			throw e;
		}
	}

	private void addMultiPartString(DataOutputStream ds, String name, String value) throws IOException
	{
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"" + end);
		ds.writeBytes("Content-Type: text/plain; charset=UTF-8" + end);
		ds.writeBytes("Content-Transfer-Encoding: 8bit" + end);
		ds.writeBytes(end);
		ds.writeBytes(value);
		ds.writeBytes(end);
	}
	
	public String composeAuthorizationHeader() throws Exception
	{
		if ( ApiCookies.progKey == null || ApiCookies.progKey.trim().length() == 0 )
		{
			throw new Exception("There's no program key!");
		}
		
		StringBuilder authorization = new StringBuilder();
		
		String nonce = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamp = String.valueOf((long)Calendar.getInstance().getTimeInMillis());
		String signature = null;
		
		//Step 1, Compose signature string
		StringBuilder signaturePre = new StringBuilder();
		signaturePre.append("nonce=").append(nonce)
				 .append("&signature_method=").append(SIGNATURE_METHOD)
				 .append("&timestamp=").append(timestamp);

		//Step 2, Doing urlencode before doing hash
		String signatureURLEn = URLEncoder.encode(signaturePre.toString(), "UTF-8");
		
		//Java Only Support HMACSHA1
		String signMethod = SIGNATURE_METHOD.replaceAll("-", "");
		
		//Step 3, Doing hash signature string by HMAC-SHA1
		SecretKey sk = new SecretKeySpec(ApiCookies.progKey.getBytes("UTF-8"), signMethod);
		Mac m = Mac.getInstance(signMethod);
		m.init(sk);
		byte[] mac = m.doFinal(signatureURLEn.getBytes("UTF-8"));
		
		//Step 4, Doing base64 encoding & doing urlencode again 
		signature = URLEncoder.encode(new String(Base64.encodeToByte(mac), "UTF-8"), "UTF-8");
		
		//Final step, Put all parameters to be authorization header string
		authorization.append("signature_method=\"").append(SIGNATURE_METHOD).append("\",")
					 .append("timestamp=\"").append(timestamp).append("\",")
					 .append("nonce=\"").append(nonce).append("\",")
					 .append("signature=\"").append(signature).append("\"");
		
		return authorization.toString();
	}
}
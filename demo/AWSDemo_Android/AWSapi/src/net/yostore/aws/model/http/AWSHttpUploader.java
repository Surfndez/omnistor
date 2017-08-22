/*  
 *  MaharaDroid -  Artefact uploader
 * 
 *  This file is part of MaharaDroid.
 * 
 *  Copyright [2010] [Catalyst IT Limited]  
 *  
 *  This file is free software: you may copy, redistribute and/or modify it  
 *  under the terms of the GNU General Public License as published by the  
 *  Free Software Foundation, either version 3 of the License, or (at your  
 *  option) any later version.  
 *  
 *  This file is distributed in the hope that it will be useful, but  
 *  WITHOUT ANY WARRANTY; without even the implied warranty of  
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU  
 *  General Public License for more details.  
 *  
 *  You should have received a copy of the GNU General Public License  
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.  
 */

package net.yostore.aws.model.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.exception.AAAException;
import net.yostore.aws.api.exception.AAAFreezeException;
import net.yostore.aws.api.exception.APIException;
import net.yostore.aws.api.exception.BackupPCNotExistException;
import net.yostore.aws.api.exception.CIDCountException;
import net.yostore.aws.api.exception.CaptchaException;
import net.yostore.aws.api.exception.FileExistedException;
import net.yostore.aws.api.exception.HttpException;
import net.yostore.aws.api.exception.NoMoreWSSpaceException;
import net.yostore.aws.api.exception.OTPAuthException;
import net.yostore.aws.api.exception.OTPCidException;
import net.yostore.aws.api.exception.OTPLockException;
import net.yostore.aws.api.exception.RegisterException;
import net.yostore.aws.api.exception.UrlException;
import net.yostore.aws.api.sax.DirectUpload;
import net.yostore.utility.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xml.sax.SAXException;

import android.util.Log;
import android.util.Xml;

/*
 * The RestClient class is taken from the RestClient class
 * written by Russel Stewart (rnstewart@gmail.com) as part of the Flickr Free
 * Android application. Changes were made to reduce support to simple HTTP POST
 * upload of content only.
 *
 * @author	Alan McNatty (alan.mcnatty@catalyst.net.nz)
 */

public class AWSHttpUploader {
	static final String TAG = "AWSHttpUploader";
	// whether DEBUG level logging is enabled (whether globally, or explicitly for this log tag)
	static final boolean DEBUG = false;
	// whether VERBOSE level logging is enabled
//	static final boolean VERBOSE = LogConfig.VERBOSE;
    private static final int CONNECTION_TIMEOUT = 15000000;
    
    private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

    // TODO: change this to be a hash of post variables
	public static ApiResponse UploadArtifact(String url, String uploadFolder, String uploadFileName, String filePath) throws APIException{
		Vector<String> pNames = new Vector<String>();
		Vector<String> pVals = new Vector<String>();
//		File file = new File(uitem.path);
		pNames.add("pa");
		pVals.add(uploadFolder);
		pNames.add("rn");
		
		pVals.add(URLEncoder.encode(new String(Base64.encodeToBase64String(uploadFileName))));
		pNames.add("filename");
		pVals.add(filePath);
		

		String [] paramNames, paramVals;
		paramNames = paramVals = new String[]{};
		paramNames = pNames.toArray(paramNames);
		paramVals = pVals.toArray(paramVals);
		
		return CallFunction(url, paramNames, paramVals);
	}

	public static ApiResponse CallFunction(String url, String[] paramNames, String[] paramVals) throws APIException
	{
//		JSONObject json = new JSONObject();
		DirectUpload handler = new DirectUpload();
		SchemeRegistry supportedSchemes = new SchemeRegistry();
		
		// Register the "http" and "https" protocol schemes, they are
		// required by the default operator to look up socket factories.
		
		//TODO we make assumptions about ports.
		supportedSchemes.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		supportedSchemes.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		
		HttpParams http_params = new BasicHttpParams();
		ClientConnectionManager ccm = new ThreadSafeClientConnManager(http_params, supportedSchemes);
		
		//HttpParams http_params = httpclient.getParams();
	    http_params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	    
	    HttpConnectionParams.setConnectionTimeout(http_params, CONNECTION_TIMEOUT);
	    HttpConnectionParams.setSoTimeout(http_params, CONNECTION_TIMEOUT);

		DefaultHttpClient httpclient = new DefaultHttpClient(ccm, http_params);
    
	    if (paramNames == null) {
			paramNames = new String[0];
		}
		if (paramVals == null) {
			paramVals = new String[0];
		}
		
		if (paramNames.length != paramVals.length) {
			Log.w(TAG, "Incompatible nuber of param names and values, bailing on upload!");
			return null;
		}
		
		SortedMap<String,String> sig_params = new TreeMap<String,String>();
		
		HttpResponse response = null;

		try {
		    File file = null;
		    // If this is a POST call, then it is a file upload. Check to see if a
		    // filename is given, and if so, open that file.
	    	// Get the title of the photo being uploaded so we can pass it into the
	    	// MultipartEntityMonitored class to be broadcast for progress updates.
//	    	String upid = "";
	    	for (int i = 0; i < paramNames.length; ++i) {
//	    		if (paramNames[i].equals("upid")) {
//	    			upid = paramVals[i];
//	    		}
//	    		else 
	    			if (paramNames[i].equals("filename")) {
		    		file = new File(paramVals[i]);
		    		continue;
		    	}
	    		sig_params.put(paramNames[i], paramVals[i]);
	    	}
	    	
		    HttpPost httppost = new HttpPost(url);
		    
		    HttpMultipartEntityMonitored mp_entity = new HttpMultipartEntityMonitored();

			for (Map.Entry<String,String> entry : sig_params.entrySet()) {
				mp_entity.addPart(entry.getKey(), new StringBody(entry.getValue()));
			}
			mp_entity.addPart("userfile", new FileBody(file));
		    httppost.setEntity(mp_entity);
			
	    	response = httpclient.execute(httppost);
	    	HttpEntity resEntity = response.getEntity();
	    	
		    if (resEntity != null) {
		    	String content = convertStreamToString(resEntity.getContent());
		    	
		    	if ( response.getStatusLine().getStatusCode() == 200 ) {
//					try {
//		    			json = new JSONObject(content.toString());
//					} catch (JSONException e1) { 
//						Log.w(TAG, "Response 200 received but invalid JSON.");
//						json.put("fail", e1.getMessage());
//						if ( DEBUG) Log.d(TAG, "HTTP POST returned status code: " + response.getStatusLine());
//					}
		    		
		    		InputStream is = new ByteArrayInputStream(content.toString().getBytes("UTF-8"));
					
					try {
						Xml.parse(is, Xml.Encoding.UTF_8, handler);
						ApiResponse upresponse = handler.getResponse();
						switch(upresponse.getStatus()){
						case 0:
							return upresponse;
						case 2:
							throw new AAAException(TAG);
						case 214:
							throw new FileExistedException(TAG);
						case 218:
							throw new BackupPCNotExistException(TAG);
						case 224:
//							AWSService.isSpaceEnough = false;
//							UploadQueueHelper.changeAllItemStatus(ASUSWebstorage.applicationContext, apicfg.userid, upresponse.getStatus());
							throw new NoMoreWSSpaceException(TAG);
						case 226:
//							UploadQueueHelper.changeAllItemStatus(ASUSWebstorage.applicationContext, apicfg.userid, upresponse.getStatus());
							throw new AAAFreezeException(TAG);
						case 234:
							throw new RegisterException(TAG);
						case 504:
							throw new OTPAuthException(TAG);
						case 505:
							throw new OTPLockException(TAG);
						case 506:
							throw new CIDCountException(TAG);
						case 507:
							throw new OTPCidException(TAG);
						case 508:
							throw new CaptchaException(TAG,null);	
						default:
							//general exception, retry
							break;
						}
					}
					  catch (MalformedURLException e){
						Log.w(TAG, e.getMessage());
						throw new UrlException(e.getMessage());
					} catch (ProtocolException e){
						Log.w(TAG, e.getMessage());
						throw new HttpException(e.getMessage());
					} catch (IOException e) {
						Log.w(TAG, e.getMessage());
						throw new HttpException(e.getMessage());
					} catch (SAXException e) {
						Log.e(TAG, e.getMessage());
						//throw new SaxException(TAG);
					}
		    	} else {
//					Log.w(TAG, "File upload failed with response code:" + response.getStatusLine().getStatusCode());
//					json.put("fail", response.getStatusLine().getReasonPhrase());
//					if ( DEBUG) Log.d(TAG, "HTTP POST returned status code: " + response.getStatusLine());
		    		return null;
		    	}
	    	} else {
//				Log.w(TAG, "Response does not contain a valid HTTP entity.");
//				if ( DEBUG ) Log.d(TAG, "HTTP POST returned status code: " + response.getStatusLine());
	    		return null;
	    	}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
//			try {
//				json.put("fail", e.getMessage());
//			} catch (JSONException e1) { }
			e.printStackTrace();
		} catch (IllegalStateException e) {
//			try {
//				json.put("fail", e.getMessage());
//			} catch (JSONException e1) { }
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
//			try {
//				json.put("fail", e.getMessage());
//			} catch (JSONException e1) { }
			e.printStackTrace();
		} 
		
		httpclient.getConnectionManager().shutdown();

		return null;
	}
	
	/*
	private static String getUploadAttribute(){
		String dateStr = String.valueOf(new Date().getTime()/1000);
		Attribute at = new Attribute();
		at.setCreationtime(dateStr);
		at.setLastaccesstime(dateStr);
		at.setLastwritetime(dateStr);
		at.setClienttype(ApiCookies.sid);
		return at.toXml();
	}
	*/
}

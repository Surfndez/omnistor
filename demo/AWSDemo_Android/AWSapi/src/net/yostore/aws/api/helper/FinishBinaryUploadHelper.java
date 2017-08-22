package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FinishBinaryUploadRequest;

import org.xml.sax.SAXException;

import android.util.Log;

public class FinishBinaryUploadHelper extends BaseHelper
{
	private String _token;
	private String _transId;
	private String _latestChecksum;
	
	public FinishBinaryUploadHelper(String token, String transId)
	{
		this(token, transId, null);
	}
	
	public FinishBinaryUploadHelper(String token, String transId, String latestChecksum)
	{
		this._token			 = token;
		this._transId		 = transId; 
		this._latestChecksum = latestChecksum;
	}

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException, IOException, SAXException
	{
		FinishBinaryUploadRequest request = new FinishBinaryUploadRequest();
		request.setToken(_token);
		request.setTransactionId(_transId);
		request.setLatestChecksum(_latestChecksum);
		request.setSID(ApiCookies.sid);
		
		
		WebRelayAPI wr = new WebRelayAPI(apicfg.webRelay);
		try
		{
			return wr.finishBinaryUpload(request, apicfg);
		}
		catch ( Exception e )
		{
			Log.e(TAG, e.getMessage(), e);
			
			throw new IOException(e.getMessage(), e);
		}
	}

}

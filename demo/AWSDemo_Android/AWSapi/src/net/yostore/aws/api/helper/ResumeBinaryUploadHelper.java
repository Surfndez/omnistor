package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.ResumeBinaryUploadRequest;

import org.xml.sax.SAXException;

import android.util.Log;

public class ResumeBinaryUploadHelper extends BaseHelper
{
	private static final String TAG = "ResumeBinaryUploadHelper";
	private String _token;
	private String _transId;
	
	private String _file;
	
	public ResumeBinaryUploadHelper(String token, String transId, String file)
	{
		this._token   = token;
		this._transId = transId;
		this._file	  = file;
	}

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException, IOException, SAXException
	{
		ResumeBinaryUploadRequest request = new ResumeBinaryUploadRequest();
		request.setToken(_token);
		request.setTransactionId(_transId);
		request.setSID(ApiCookies.sid);

		WebRelayAPI wr = new WebRelayAPI(apicfg.webRelay);
		try
		{
			return wr.resumeBinaryUpload(request, apicfg, _file);
		}
		catch ( Exception e )
		{
			Log.e(TAG, e.getMessage(), e);
			
			throw new IOException(e.getMessage(), e);
		}
	}

}

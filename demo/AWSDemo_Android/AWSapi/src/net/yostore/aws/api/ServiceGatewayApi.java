package net.yostore.aws.api;

import java.io.IOException;

import org.xml.sax.SAXException;

import net.yostore.aws.api.entity.AcquireTokenRequest;
import net.yostore.aws.api.entity.AcquireTokenResponse;
import net.yostore.aws.api.sax.AcquireToken;
import android.util.Log;

public class ServiceGatewayApi extends BaseApi {
	private static final String TAG = "ServiceGatewayApi";
	
	public ServiceGatewayApi(String Url) {
		super(Url);
	}

	public AcquireTokenResponse acquireToken( AcquireTokenRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (AcquireTokenResponse)super.getResponse("/member/acquiretoken/", params, new AcquireToken());
	}
}

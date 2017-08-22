package net.yostore.aws.api;

import java.io.IOException;

import org.xml.sax.SAXException;

import net.yostore.aws.api.entity.RequestServiceGatewayRequest;
import net.yostore.aws.api.entity.RequestServiceGatewayResponse;
import net.yostore.aws.api.sax.RequestServiceGateway;
import android.util.Log;

public class ServicePortalApi extends BaseApi {
	private static final String TAG = "ServicePortalApi";
	
	public ServicePortalApi(String Url) {
		super(Url);
	}

	public RequestServiceGatewayResponse requestServiceGateway( RequestServiceGatewayRequest request ) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (RequestServiceGatewayResponse)super.getResponse("/member/requestservicegateway/", params, new RequestServiceGateway());
	}
}

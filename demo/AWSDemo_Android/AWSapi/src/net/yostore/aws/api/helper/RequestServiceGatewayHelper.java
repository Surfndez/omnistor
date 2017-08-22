package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.ServicePortalApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.RequestServiceGatewayRequest;
import org.xml.sax.SAXException;

public class RequestServiceGatewayHelper extends BaseHelper{

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException, IOException, SAXException {
		
		RequestServiceGatewayRequest request = new RequestServiceGatewayRequest(apicfg.userid, apicfg.password);
		ServicePortalApi spl = new ServicePortalApi(ApiConfig.SERVICEPORTAL);
		return  spl.requestServiceGateway(request);

	}
	
	
}

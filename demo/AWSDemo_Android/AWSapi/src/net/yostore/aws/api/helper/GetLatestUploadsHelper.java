package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.GetLatestUploadsRequest;

import org.xml.sax.SAXException;

public class GetLatestUploadsHelper extends BaseHelper{
	int top;
	String targetroot;
	int sortdirection;
	
	public GetLatestUploadsHelper(int top, String targetroot, int sortdirection){
		this.top = top;
		this.targetroot = targetroot;
		this.sortdirection = sortdirection;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		GetLatestUploadsRequest request = new GetLatestUploadsRequest(
				apicfg.userid,
				apicfg.token,
				top, 
				targetroot, 
				sortdirection
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.getLatestUploads(request);

	}
	
	
}

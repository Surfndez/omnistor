package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.GetLatestChangeFilesRequest;

import org.xml.sax.SAXException;

public class GetLatestChangeFilesHelper extends BaseHelper{
	int top;
	String targetroot;
	int sortdirection;
	
	public GetLatestChangeFilesHelper(int top, String targetroot, int sortdirection){
		this.top = top;
		this.targetroot = targetroot;
		this.sortdirection = sortdirection;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		GetLatestChangeFilesRequest request = new GetLatestChangeFilesRequest(
				apicfg.userid,
				apicfg.token,
				top, 
				targetroot, 
				sortdirection
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.getLatestChangeFiles(request);

	}
	
	
}

package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FindPropfindRequest;

import org.xml.sax.SAXException;


public class FindPropfindHelper extends BaseHelper{

	private String parent;
	private String display;
	private String type;
	public FindPropfindHelper(String parent, String display, String type){
		this.parent = parent;
		this.display = display;
		this.type = type;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		FindPropfindRequest request = new FindPropfindRequest(
				apicfg.userid,
				apicfg.token,
				this.parent,
				this.type,
				this.display
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.findPropfind(request);

	}
	
	
}

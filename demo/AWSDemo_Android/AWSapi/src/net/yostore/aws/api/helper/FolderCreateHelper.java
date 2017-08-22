package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FolderCreateRequest;

import org.xml.sax.SAXException;

public class FolderCreateHelper extends BaseHelper{

	private String parent;
	private String display;
	private String attr;
	public FolderCreateHelper(String parent, String display, String attr){
		this.parent = parent;
		this.display = display;
		this.attr = attr;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		FolderCreateRequest request = new FolderCreateRequest(
				apicfg.userid,
				apicfg.token,
				this.parent,
				this.display,
				this.attr
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.folderCreate(request);

	}
	
	
}

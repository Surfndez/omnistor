package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.UpdateFolderAttributeRequest;

import org.xml.sax.SAXException;

public class UpdateFolderAttributeHelper extends BaseHelper{

	private String parent;
	private String display;
	private String attr;
	private long folderId;
	
	public UpdateFolderAttributeHelper(String parent, String display, long folderId, String attr){
		this.parent = parent;
		this.display = display;
		this.attr = attr;
		this.folderId = folderId;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		UpdateFolderAttributeRequest request = new UpdateFolderAttributeRequest(
				apicfg.userid,
				apicfg.token,
				this.parent,
				this.display,
				this.attr,
				this.folderId
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.updateFolderAttribute(request);

	}
	
	
}

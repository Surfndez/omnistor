package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FolderRemoveRequest;

import org.xml.sax.SAXException;


public class FolderRemoveHelper extends BaseHelper{

	private String folderid;

	public FolderRemoveHelper(String folderid){
		this.folderid = folderid;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		FolderRemoveRequest request = new FolderRemoveRequest(
				apicfg.userid,
				apicfg.token,
				this.folderid
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.folderRemove(request);
	}
	
	
}

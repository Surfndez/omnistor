package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FileRemoveRequest;

import org.xml.sax.SAXException;


public class FileRemoveHelper extends BaseHelper{

	private String fileid;

	public FileRemoveHelper(String fileid){
		this.fileid = fileid;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		FileRemoveRequest request = new FileRemoveRequest(
				apicfg.userid,
				apicfg.token,
				this.fileid);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.fileRemove(request);
	}
	
	
}

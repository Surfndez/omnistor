package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FileRenameRequest;

import org.xml.sax.SAXException;


public class FileRenameHelper extends BaseHelper{

	private String fileid;
	private boolean isencrypted;
	private boolean issharing;
	private String display;
	
	public FileRenameHelper(String fileid, boolean isencrypted, boolean issharing, String display){
		this.fileid = fileid;
		this.isencrypted = isencrypted;
		this.issharing = issharing;
		this.display = display;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		FileRenameRequest request = new FileRenameRequest(
				apicfg.userid,
				apicfg.token,
				this.fileid,
				this.isencrypted,
				this.issharing,
				this.display
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.fileRename(request);
	}
	
	
}

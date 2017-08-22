package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FolderRenameRequest;

import org.xml.sax.SAXException;


public class FolderRenameHelper extends BaseHelper{

	private String folderid;
	private boolean isencrypted;
	private boolean issharing;
	private String display;
	
	public FolderRenameHelper(String folderid, boolean isencrypted, boolean issharing, String display){
		this.folderid = folderid;
		this.isencrypted = isencrypted;
		this.issharing = issharing;
		this.display = display;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		FolderRenameRequest request = new FolderRenameRequest(
				apicfg.userid,
				apicfg.token,
				this.folderid,
				this.isencrypted,
				this.issharing,
				this.display
		);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.folderRename(request);
	}
	
	
}

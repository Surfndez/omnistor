package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.xml.sax.SAXException;
import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.helper.BaseHelper;
import android.util.Log;

public class GetvideoconVertprogressHelp extends BaseHelper {

	private String TAG = "GetvideoconVertprogressHelp";
	private String fileID="";

	public GetvideoconVertprogressHelp(String fileID) {
		this.fileID = fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getFileID() {
		return fileID;
	}


	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException,
			ProtocolException, IOException, SAXException {
		
		Log.d(TAG, "fileID:" + this.fileID);
		WebRelayAPI wr = new WebRelayAPI(apicfg.webRelay);
		return wr.getvideoconVertprogress(apicfg,fileID);

	}

}

package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.GetMySyncFolderRequest;

import org.xml.sax.SAXException;

public class GetMySyncFolderHelper extends BaseHelper
{

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException, IOException, SAXException
	{
		GetMySyncFolderRequest request = new GetMySyncFolderRequest(apicfg.token, apicfg.userid);
		
		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.getMySyncFolder(request);
	}

}

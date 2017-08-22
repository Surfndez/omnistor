package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.ServiceGatewayApi;
import net.yostore.aws.api.entity.AcquireTokenRequest;
import net.yostore.aws.api.entity.ApiResponse;

import org.xml.sax.SAXException;

public class AcquireTokenHelper extends BaseHelper{
	private String auxpassword = null;
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		AcquireTokenRequest request;
		if(this.auxpassword==null || this.auxpassword.trim().length()==0)
			request = new AcquireTokenRequest(apicfg.userid, apicfg.password);
		else
			request = new AcquireTokenRequest(apicfg.userid, apicfg.password, this.auxpassword);

		ServiceGatewayApi sgw = new ServiceGatewayApi(apicfg.ServiceGateway);
		return sgw.acquireToken(request);
	}
	
	public String getAuxpassword()
	{
		return auxpassword;
	}
	public void setAuxpassword(String auxpassword)
	{
		this.auxpassword = auxpassword;
	}
	
	
}

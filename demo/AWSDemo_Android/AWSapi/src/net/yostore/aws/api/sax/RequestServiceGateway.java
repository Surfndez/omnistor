package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.RequestServiceGatewayResponse;

import org.xml.sax.SAXException;

public class RequestServiceGateway extends BaseSaxHandler{

	private RequestServiceGatewayResponse response = new RequestServiceGatewayResponse();
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("servicegateway")){
        	response.setServicegateway(builder.toString().trim());
        } else if (localName.equalsIgnoreCase("time")){
        	response.setTime(builder.toString().trim());
        }
        
        builder.setLength(0);    
	
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

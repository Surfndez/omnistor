package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.GetShareCodeResponse;

import org.xml.sax.SAXException;

public class GetShareCode extends BaseSaxHandler{

	private GetShareCodeResponse response = new GetShareCodeResponse();
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
        if (localName.equals("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
		} else if (localName.equals("scrip")){
			response.setScrip(builder.toString().trim());
		} else if (localName.equals("uri")){
			response.setUri(builder.toString().trim());
		} else if (localName.equals("ispasswordneeded")){
			response.setIspasswordneeded(builder.toString().trim());
		}
        
        
        builder.setLength(0);    
	
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

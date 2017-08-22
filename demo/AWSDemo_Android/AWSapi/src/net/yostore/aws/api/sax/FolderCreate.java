package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FolderCreateResponse;

import org.xml.sax.SAXException;

public class FolderCreate extends BaseSaxHandler{

	private FolderCreateResponse response = new FolderCreateResponse();
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
		} else if (localName.equalsIgnoreCase("scrip")){
			response.setScrip(builder.toString().trim());
		} else if (localName.equalsIgnoreCase("id")){
			if(!builder.toString().trim().equalsIgnoreCase("null") && !builder.toString().trim().equalsIgnoreCase("") && !builder.toString().trim().equalsIgnoreCase("undefine"))
				response.setId(Long.parseLong(builder.toString().trim()));
		} 
        
        builder.setLength(0);    
	
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

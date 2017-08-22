package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FolderRemoveResponse;

import org.xml.sax.SAXException;

public class FolderRemove extends BaseSaxHandler{

	private FolderRemoveResponse response = new FolderRemoveResponse();
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
		} else if (localName.equalsIgnoreCase("scrip")){
			response.setScrip(builder.toString().trim());
		} 
        
        builder.setLength(0);    
	
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

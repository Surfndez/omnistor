package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FolderRenameResponse;

import org.xml.sax.SAXException;

public class FolderRename extends BaseSaxHandler{

	private FolderRenameResponse response = new FolderRenameResponse();
	
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

package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.DirectUploadResponse;

import org.xml.sax.SAXException;

public class DirectUpload extends BaseSaxHandler{

	private DirectUploadResponse response = new DirectUploadResponse();
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
        if (localName.equals("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equals("fileid")){
			response.setFileId(builder.toString().trim());
		} else if (localName.equals("rawfilename")){
			response.setRawFileName(builder.toString().trim());
		} 
        
        builder.setLength(0);    
	
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

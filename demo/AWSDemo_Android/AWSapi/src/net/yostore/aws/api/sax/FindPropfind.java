package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FindPropfindResponse;

import org.xml.sax.SAXException;

public class FindPropfind extends BaseSaxHandler{

	private FindPropfindResponse response = new FindPropfindResponse();
//	private static final String TAG = "FindPropfind";
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
//    	Log.d(TAG, localName + "-->" + builder.toString().trim());
		
        if (localName.equalsIgnoreCase("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("isencrypted")){
        	response.setIsencrypted("1".equalsIgnoreCase(builder.toString().trim())?true:false);
        } else if (localName.equalsIgnoreCase("size")){
        	response.setSize(Long.parseLong(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("scrip")){
        	response.setScrip(builder.toString().trim());
        } else if (localName.equalsIgnoreCase("type")){
        	response.setType(builder.toString().trim());
        } else if (localName.equalsIgnoreCase("id")){
        	response.setId(builder.toString().trim());
        } else if (localName.equalsIgnoreCase("attribute")){
        	response.setAttribute(builder.toString().trim());
        }
        
        builder.setLength(0);    
	
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

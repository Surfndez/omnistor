package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.Attribute;
import net.yostore.aws.api.entity.BaseEntry;
import net.yostore.aws.api.entity.GetLatestUploadsResponse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LatestUploads extends BaseSaxHandler{

	private GetLatestUploadsResponse response = new GetLatestUploadsResponse();
	boolean isLatestUploads = false;
	boolean isAttr = false;
	BaseEntry lcf;
	Attribute at;
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (this.isLatestUploads){
			if(this.isAttr){
				if (localName.equalsIgnoreCase("attribute")){
					lcf.setAttribute(at);
					this.isAttr = false;
				} else if (localName.equalsIgnoreCase("creationtime")){
					at.setCreationtime(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("lastaccesstime")){
					at.setLastaccesstime(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("lastwritetime")){
					at.setLastwritetime(builder.toString().trim());
				} 
			}else{
				if (localName.equalsIgnoreCase("entry")){
					response.getLLastestUploadsList().add(lcf);
					this.isLatestUploads = false;
				} else if (localName.equalsIgnoreCase("id")){
					lcf.setId(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("parent")){
					lcf.setParent(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("rawfilename")){
					lcf.setRawfilename(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("isbackup")){
					lcf.setIsBackup("1".equalsIgnoreCase(builder.toString().trim())?true:false);
				} else if (localName.equalsIgnoreCase("isorigdeleted")){
					lcf.setIsorigdeleted("1".equalsIgnoreCase(builder.toString().trim())?true:false);
				} else if (localName.equalsIgnoreCase("marks")){
					lcf.setMarks(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("createdtime")){
					lcf.setCreatedtime(builder.toString().trim());
				} else if (localName.equalsIgnoreCase("lastchangetime")){
					lcf.setLastchangetime(Long.valueOf(builder.toString().trim()));
				} else if (localName.equalsIgnoreCase("isinfected")){
					lcf.setIsinfected("1".equalsIgnoreCase(builder.toString().trim())?true:false);
				} else if (localName.equalsIgnoreCase("size")){
					lcf.setSize(Long.valueOf(builder.toString().trim()));
				} else if (localName.equalsIgnoreCase("ispublic")){
					lcf.setIspublic("1".equalsIgnoreCase(builder.toString().trim())?true:false);
				}
			}
		}else{
			if (localName.equals("status")){
	        	response.setStatus(Integer.parseInt(builder.toString().trim()));
			} 
		}
        
        
        builder.setLength(0);    
	
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("entry")){
			this.isLatestUploads=true;
			lcf = new BaseEntry();
		}
		
		if (localName.equalsIgnoreCase("attribute")){
			this.isAttr=true;
			at = new Attribute();
		}
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
}

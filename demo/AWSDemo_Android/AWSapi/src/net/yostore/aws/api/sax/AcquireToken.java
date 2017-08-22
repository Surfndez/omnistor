package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.PackageInfo;
import net.yostore.aws.api.entity.AcquireTokenResponse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class AcquireToken extends BaseSaxHandler{

	private AcquireTokenResponse response = new AcquireTokenResponse();
	boolean isFeatureList = false;
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		if (this.isFeatureList){
			if (localName.equalsIgnoreCase("featurelist")){
	        	this.isFeatureList = false;
			}
		}else{
	        if (localName.equalsIgnoreCase("status")){
	        	int status = -999;
	        	try{
	        		status = Integer.parseInt(builder.toString().trim());
	        	}
	        	catch(Exception e){
	        		e.printStackTrace();
	        	}
	        	response.setStatus(status);
	        } else if (localName.equalsIgnoreCase("credential")) {
	        	response.setCredential(builder.toString().trim());
	        } else if (localName.equalsIgnoreCase("credentialstate")) {
	        	response.setCredentialState(builder.toString().trim());
	        } else if (localName.equalsIgnoreCase("token")){
	        	response.setToken(builder.toString().trim());
	        } else if (localName.equalsIgnoreCase("inforelay")){
	        	response.setInforelay(builder.toString().trim());
	        } else if (localName.equalsIgnoreCase("webrelay")){
	        	response.setWebrelay(builder.toString().trim());
	        } else if (localName.equalsIgnoreCase("time")){
	        	response.setTime(builder.toString().trim());
	        } else if (localName.equalsIgnoreCase("id")){
				response.getPackageinfo().setId(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("display")){
				response.getPackageinfo().setDisplay(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("capacity")){
				response.getPackageinfo().setCapacity(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("maxfilesize")){
				response.getPackageinfo().setMaxfilesize(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("expire")){
				response.getPackageinfo().setExpire(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("auxpasswordurl")){
				response.setAuxpasswordurl(builder.toString().trim());
			} 
		}
        builder.setLength(0);    
	
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("package")){
			response.setPackageinfo(new PackageInfo());
		}else if (localName.equalsIgnoreCase("feature") && attributes.getValue("name").equalsIgnoreCase("mear") && attributes.getValue("enable").equalsIgnoreCase("1")){
			this.isFeatureList = true;
//			response.setMearFeatureList(new MearFeatureList());
		}if(this.isFeatureList && localName.equalsIgnoreCase("property") && attributes.getValue("name").equalsIgnoreCase("BlockFreeAccFirstGate")){
			response.getPackageinfo().setMearBlockFreeAccFirstGate(Integer.valueOf(attributes.getValue("value")).intValue());
		}if(this.isFeatureList && localName.equalsIgnoreCase("property") && attributes.getValue("name").equalsIgnoreCase("BlockFreeAccAfterFGate")){
			response.getPackageinfo().setMearBlockFreeAccAfterFGate(Integer.valueOf(attributes.getValue("value")).intValue());
		}
	}
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
	
	

}

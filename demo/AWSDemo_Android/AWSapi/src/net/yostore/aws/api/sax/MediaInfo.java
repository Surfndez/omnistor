package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.MediaInfoEntity;
import net.yostore.aws.api.entity.MediaInfoResponse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MediaInfo extends BaseSaxHandler
{
	
	private MediaInfoResponse response = new MediaInfoResponse();
	//private static final String TAG = "MediaInfo";
	private boolean isNewEntity = false;
	private MediaInfoEntity mie;
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
        if (localName.equalsIgnoreCase("i")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setI(Long.valueOf(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("da")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setDa(Long.valueOf(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("d")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && !builder.toString().trim().equalsIgnoreCase("undefined") && builder.toString().trim().length()>0)
        		mie.setD(builder.toString().trim());
        } else if (localName.equalsIgnoreCase("ty")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setTy(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("r")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setR(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("no")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setNo(builder.toString().trim());
        } else if (localName.equalsIgnoreCase("en")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setEn(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("us")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setUs(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("fr")){
        	if(!builder.toString().trim().equalsIgnoreCase("null") && builder.toString().trim().length()>0)
        		mie.setFr(Integer.parseInt(builder.toString().trim()));
        	else
        		mie.setFr(1);
        } else if (localName.equalsIgnoreCase("f")){
        	response.getMieList().add(mie);
        	this.isNewEntity = false;
        }
        
        builder.setLength(0);    
	
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("f")){
			this.isNewEntity = true;
			mie = new MediaInfoEntity();
		}
	}
	
	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
}

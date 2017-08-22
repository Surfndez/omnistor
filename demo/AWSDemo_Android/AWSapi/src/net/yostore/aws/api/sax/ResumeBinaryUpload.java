package net.yostore.aws.api.sax;

import org.xml.sax.SAXException;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.ResumeBinaryUploadResponse;

public class ResumeBinaryUpload extends BaseSaxHandler
{
	private ResumeBinaryUploadResponse response = new ResumeBinaryUploadResponse();

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		super.endElement(uri, localName, qName);
        if ( localName.equals("status") )
        {
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
        }
        
        builder.setLength(0);    
	}

	@Override
	public ApiResponse getResponse()
	{
		return this.response;
	}

}

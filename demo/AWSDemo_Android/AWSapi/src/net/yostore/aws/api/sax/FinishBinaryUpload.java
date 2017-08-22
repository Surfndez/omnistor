package net.yostore.aws.api.sax;

import org.xml.sax.SAXException;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.FinishBinaryUploadResponse;

public class FinishBinaryUpload extends BaseSaxHandler
{
	private FinishBinaryUploadResponse response = new FinishBinaryUploadResponse();

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		super.endElement(uri, localName, qName);
        if ( localName.equals("status") )
        {
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
        }
        else if ( localName.equals("fileid") )
        {
        	if ( builder.toString().trim().length() > 0 )
        		response.setFileId(Long.parseLong(builder.toString().trim()));
        }
       
        builder.setLength(0);    
	}

	@Override
	public ApiResponse getResponse()
	{
		return this.response;
	}
}

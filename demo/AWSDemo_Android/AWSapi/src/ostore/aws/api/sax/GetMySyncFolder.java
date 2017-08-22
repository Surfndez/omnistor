package ostore.aws.api.sax;

import org.xml.sax.SAXException;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.GetMySyncFolderResponse;
import net.yostore.aws.api.sax.BaseSaxHandler;

public class GetMySyncFolder extends BaseSaxHandler
{
	private GetMySyncFolderResponse response = new GetMySyncFolderResponse();

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
//    	Log.d(TAG, localName + "-->" + builder.toString().trim());
		
        if (localName.equalsIgnoreCase("status")){
        	response.setStatus(Integer.parseInt(builder.toString().trim()));
        } else if (localName.equalsIgnoreCase("id")){
        	response.setId(builder.toString().trim());
        }
        
        builder.setLength(0);    	
	}
	@Override
	public ApiResponse getResponse()
	{
		return this.response;
	}

}

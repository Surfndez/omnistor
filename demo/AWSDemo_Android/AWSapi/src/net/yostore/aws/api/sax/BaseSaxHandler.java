package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseSaxHandler extends DefaultHandler{
	public abstract ApiResponse getResponse();
	protected StringBuilder builder;
	    
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
	    builder.append(ch, start, length);
//	    Log.d("BaseSaxHandler", builder.toString());
	}


	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	    builder = new StringBuilder();
	}

}

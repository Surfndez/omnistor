package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.GetVideoconvertprogressResponse;
import net.yostore.aws.api.entity.VideoInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

public class GetVideoconvertprogress extends BaseSaxHandler {

	private GetVideoconvertprogressResponse response = new GetVideoconvertprogressResponse();
	boolean isVideo = false;
	VideoInfo vi;
	
	private String TAG = "GetVideoconvertprogress";

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);

		if (this.isVideo) {
			if (localName.equalsIgnoreCase("video")) {
				response.getVideoList().add(vi);

				this.isVideo = false;
			} else if (localName.equalsIgnoreCase("type")) {
				
				vi.setType(builder.toString().trim());
				
				Log.d(TAG, "--"+builder.toString().trim());
			} else if (localName.equalsIgnoreCase("progressstate")) {
				vi.setProgressstate(builder.toString().trim());
			}	else if (localName.equalsIgnoreCase("resolution")) {
				vi.setResolution(builder.toString().trim());
			}else if (localName.equalsIgnoreCase("abstractstate")) {
				vi.setAbstractstate(builder.toString().trim());
			}
		} else {
			if (localName.equalsIgnoreCase("status")) {
				response.setStatus(Integer.parseInt(builder.toString().trim()));
				
			} else if (localName.equalsIgnoreCase("progressstate")) {
				response.setProgressstate(builder.toString().trim());
				Log.d(TAG, "--"+builder.toString().trim());
			} else if (localName.equalsIgnoreCase("abstractstate")) {
				response.setAbstractstate(builder.toString().trim());
			} 
		}

		builder.setLength(0);

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("video")) {
			this.vi = new VideoInfo();
			this.isVideo = true;
		} 

	}

	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
}
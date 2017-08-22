package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.SetAdvancedSharecodeResponse;

import org.xml.sax.SAXException;

public class SetAdvancedSharecode extends BaseSaxHandler {

	private SetAdvancedSharecodeResponse response = new SetAdvancedSharecodeResponse();

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (localName.equals("status")) {
			response.setStatus(Integer.parseInt(builder.toString().trim()));
		} else if (localName.equals("scrip")) {
			response.setScrip(builder.toString().trim());
		} else if (localName.equals("sharecode")) {
			response.setSharecode((builder.toString().trim()));
		} else if (localName.equals("ispasswordneeded")) {
			response.setIspasswordneeded(builder.toString().trim());
		} else if (localName.equals("isgroupaware")) {
			response.setIsgroupaware(builder.toString().trim());
		} else if (localName.equals("expiredtime")) {
			response.setExpiredtime(builder.toString().trim());
		} else if (localName.equals("folderquota")) {
			response.setFolderquota(builder.toString().trim());
		}

		builder.setLength(0);

	}

	@Override
	public ApiResponse getResponse() {
		return this.response;
	}

}

package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class GetMySyncFolderResponse extends ApiResponse
{
	private String _id;

	public String getId()
	{
		return _id;
	}

	public void setId(String _id)
	{
		this._id = _id;
	}
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "getmysyncfolder");
			serializer.startTag("", "status");
			serializer.text(String.valueOf(this._status));
			serializer.endTag("", "status");
			serializer.startTag("", "id");
			serializer.text(String.valueOf(this._id));
			serializer.endTag("", "id");
			serializer.endTag("", "getmysyncfolder");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

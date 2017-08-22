package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class FolderCreateResponse extends ApiResponse{

	private String _scrip;
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }

	private long _id;
	public long getId(){ return this._id; }
	public void setId(long value){ this._id = value; }
	
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "create");
			serializer.startTag("", "status");
			serializer.text(String.valueOf(this._status));
			serializer.endTag("", "status");
			serializer.startTag("", "scrip");
			serializer.text(this._scrip);
			serializer.endTag("", "scrip");
			serializer.startTag("", "id");
			serializer.text(String.valueOf(this._id));
			serializer.endTag("", "id");
			serializer.endTag("", "create");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}// end class 

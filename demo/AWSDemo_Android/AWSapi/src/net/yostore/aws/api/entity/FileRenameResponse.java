package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class FileRenameResponse extends ApiResponse{

	private String _scrip;
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "rename");
			serializer.startTag("", "status");
			serializer.text(String.valueOf(this._status));
			serializer.endTag("", "status");
			serializer.startTag("", "scrip");
			serializer.text(this._scrip);
			serializer.endTag("", "scrip");
			serializer.endTag("", "rename");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

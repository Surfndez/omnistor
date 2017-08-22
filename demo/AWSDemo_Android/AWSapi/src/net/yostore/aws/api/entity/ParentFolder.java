package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class ParentFolder {
	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }

	private String _name;
	public String getName(){ return this._name; }
	public void setName(String value){ this._name = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "parentfolder");
			serializer.startTag("", "name");
			serializer.text(this._name);
			serializer.endTag("", "name");
			serializer.startTag("", "id");
			serializer.text(this._id);
			serializer.endTag("", "id");
			serializer.endTag("", "parentfolder");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

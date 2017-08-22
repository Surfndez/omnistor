package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class FindPropfindResponse extends ApiResponse{

	private boolean _isencrypted;
	public boolean getIsencrypted(){ return this._isencrypted; }
	public void setIsencrypted(boolean value){ this._isencrypted = value; }

	private long _size;
	public long getSize(){ return this._size; }
	public void setSize(long value){ this._size = value; }

	private String _scrip;
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }

	private String _type;
	public String getType(){ return this._type; }
	public void setType(String value){ this._type = value; }

	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }

	private String _attribute;
	public String getAttribute(){ return this._attribute; }
	public void setAttribute(String value){ this._attribute = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "propfind");
			serializer.startTag("", "status");
			serializer.text(String.valueOf(this._status));
			serializer.endTag("", "status");
			serializer.startTag("", "isencrypted");
			serializer.text(this._isencrypted?"1":"0");
			serializer.endTag("", "isencrypted");
			serializer.startTag("", "size");
			serializer.text(String.valueOf(this._size));
			serializer.endTag("", "size");
			serializer.startTag("", "scrip");
			serializer.text(this._scrip);
			serializer.endTag("", "scrip");
			serializer.startTag("", "type");
			serializer.text(this._type);
			serializer.endTag("", "type");
			serializer.startTag("", "id");
			serializer.text(String.valueOf(this._id));
			serializer.endTag("", "id");
			serializer.startTag("", "attribute");
			serializer.text(this._attribute);
			serializer.endTag("", "attribute");
			serializer.endTag("", "propfind");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

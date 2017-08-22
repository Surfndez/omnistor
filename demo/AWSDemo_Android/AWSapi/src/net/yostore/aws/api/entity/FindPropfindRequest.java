package net.yostore.aws.api.entity;

import java.io.StringWriter;

import net.yostore.utility.Base64;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class FindPropfindRequest {
	public static final String TYPE_FILE = "system.file";
	public static final String TYPE_FOLDER = "system.folder";
	public static final String TYPE_UNKNOWN = "system.unknown";

	public FindPropfindRequest(){}
	public FindPropfindRequest(String uid, String token, String parent, String type, String find){
		this._userid = uid;
		this._token = token;
		this._parent = parent;
		this._type = type;
		this._find = find;
	}
	private String _token;
	public String getToken(){ return this._token; }
	public void setToken(String value){ this._token = value; }

	private String _scrip=String.valueOf(System.currentTimeMillis());
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }

	private String _userid;
	public String getUserid(){ return this._userid; }
	public void setUserid(String value){ this._userid = value; }

	private String _parent;
	public String getParent(){ return this._parent; }
	public void setParent(String value){ this._parent = value; }

	private String _find;
	public String getFind(){ return this._find; }
	public void setFind(String value){ this._find = value; }

	private String _type=TYPE_UNKNOWN;
	public String getType(){ return this._type; }
	public void setType(String value){ this._type = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "propfind");
			serializer.startTag("", "token");
			serializer.text(this._token);
			serializer.endTag("", "token");
			serializer.startTag("", "scrip");
			serializer.text(this._scrip);
			serializer.endTag("", "scrip");
			serializer.startTag("", "userid");
			serializer.text(this._userid);
			serializer.endTag("", "userid");
			serializer.startTag("", "parent");
			serializer.text(this._parent);
			serializer.endTag("", "parent");
			serializer.startTag("", "find");
			serializer.text(Base64.encodeToBase64String(this._find));
			serializer.endTag("", "find");
			serializer.startTag("", "type");
			serializer.text(this._type);
			serializer.endTag("", "type");
			serializer.endTag("", "propfind");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

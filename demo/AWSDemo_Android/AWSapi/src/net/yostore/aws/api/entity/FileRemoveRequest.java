package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class FileRemoveRequest {


	private String _token;
	public FileRemoveRequest(String uid, String token, String fileid) {
		this._userid = uid;
		this._token = token;
		this._id = fileid;
	}
	public String getToken(){ return this._token; }
	public void setToken(String value){ this._token = value; }

	private String _scrip = String.valueOf(System.currentTimeMillis());
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }

	private String _userid;
	public String getUserid(){ return this._userid; }
	public void setUserid(String value){ this._userid = value; }

	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "remove");
			serializer.startTag("", "token");
			serializer.text(this._token);
			serializer.endTag("", "token");
			serializer.startTag("", "scrip");
			serializer.text(this._scrip);
			serializer.endTag("", "scrip");
			serializer.startTag("", "userid");
			serializer.text(this._userid);
			serializer.endTag("", "userid");
			serializer.startTag("", "id");
			serializer.text(this._id);
			serializer.endTag("", "id");
			serializer.endTag("", "remove");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

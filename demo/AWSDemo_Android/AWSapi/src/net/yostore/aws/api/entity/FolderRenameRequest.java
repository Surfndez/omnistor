package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import net.yostore.utility.Base64;

import android.util.Xml;

public class FolderRenameRequest {
	
	public FolderRenameRequest(){}
	public FolderRenameRequest(String uid, String token, String folderid, boolean isencrypted, boolean issharing, String display){
		this._userid = uid;
		this._token = token;
		this._id = folderid;
		this._isencrypted = isencrypted;
		this._issharing = issharing;
		this._display = display;
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

	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }

	private boolean _isencrypted=false;
	public boolean getIsencrypted(){ return this._isencrypted; }
	public void setIsencrypted(boolean value){ this._isencrypted = value; }
	
	private boolean _issharing=false;
	public boolean getIssharing(){ return this._issharing; }
	public void setIssharing(boolean value){ this._issharing = value; }
	
	private String _display;
	public String getDisplay(){ return this._display; }
	public void setDisplay(String value){ this._display = value; }
	
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "rename");
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
			serializer.startTag("", "isencrypted");
			serializer.text(this._isencrypted?"1":"0");
			serializer.endTag("", "isencrypted");
			serializer.startTag("", "issharing");
			serializer.text(this._issharing?"1":"");
			serializer.endTag("", "issharing");
			serializer.startTag("", "display");
			serializer.text(Base64.encodeToBase64String(this._display));
			serializer.endTag("", "display");
			serializer.endTag("", "rename");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

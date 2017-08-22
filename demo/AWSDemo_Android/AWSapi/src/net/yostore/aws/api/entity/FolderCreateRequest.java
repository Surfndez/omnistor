package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import net.yostore.utility.Base64;

import android.util.Xml;

public class FolderCreateRequest {
	public FolderCreateRequest(){}
	
	/**
	 * 
	 * @param uid user id of AWS Service
	 * @param token cloud area token
	 * @param parent The Folder to Create to
	 * @param display The Folder Display
	 * @param attribute The Folder Attributes
	 */
	public FolderCreateRequest(String uid, String token, String parent, String display, String attribute){
		this._userid = uid;
		this._token = token;
		this._parent = parent;
		this._display = display;
		this._attribute = attribute;
	}
	
	private String _token;
	public String getToken(){ return this._token; }
	public void setToken(String value){ this._token = value; }

	private String _scrip= String.valueOf(System.currentTimeMillis());
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }

	private String _userid;
	public String getUserid(){ return this._userid; }
	public void setUserid(String value){ this._userid = value; }

	private String _parent;
	public String getParent(){ return this._parent; }
	public void setParent(String value){ this._parent = value; }

	private boolean _isencrypted;
	public boolean getIsencrypted(){ return this._isencrypted; }
	public void setIsencrypted(boolean value){ this._isencrypted = value; }

	private String _display;
	public String getDisplay(){ return this._display; }
	public void setDisplay(String value){ this._display = value; }

	private String _attribute="";
	public String getAttribute(){ return this._attribute; }
	public void setAttribute(String value){ this._attribute = value; }

	private boolean _issharing;
	public boolean getIssharing(){ return this._issharing; }
	public void setIssharing(boolean value){ this._issharing = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "create");
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
			serializer.startTag("", "isencrypted");
			serializer.text(this._isencrypted?"1":"0");
			serializer.endTag("", "isencrypted");
			serializer.startTag("", "display");
			serializer.text(Base64.encodeToBase64String(this._display));
			serializer.endTag("", "display");
			serializer.startTag("", "attribute");
			serializer.text(this._attribute);
			serializer.endTag("", "attribute");
			serializer.startTag("", "issharing");
			serializer.text(this._issharing?"1":"0");
			serializer.endTag("", "issharing");
			serializer.endTag("", "create");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

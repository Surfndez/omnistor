package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class AcquireTokenRequest {
	
	public AcquireTokenRequest(){}

	public AcquireTokenRequest(String uid, String pwd){
		this._userid = uid;
		this._password = pwd;
	}
	
	public AcquireTokenRequest(String uid, String pwd, String auxpassword){
		this._userid = uid;
		this._password = pwd;
		this.auxpassword = auxpassword;
	}
	
	private String auxpassword;
	public String getAuxpassword()
	{
		return auxpassword;
	}
	public void setAuxpassword(String auxpassword)
	{
		this.auxpassword = auxpassword;
	}

	private String _userid;
	public String getUserid(){ return this._userid; }
	public void setUserid(String value){ this._userid = value; }

	private String _password;
	public String getPassword(){ return this._password; }
	public void setPassword(String value){ this._password = value; }

	private String _token = null;
	public String getToken(){ return this._token; }
	public void setToken(String value){ this._token = value; }

	private String _time=String.valueOf(System.currentTimeMillis());
	public String getTime(){ return this._time; }
	public void setTime(String value){ this._time = value; }
	
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "aaa");
			serializer.startTag("", "userid");
			serializer.text(this._userid);
			serializer.endTag("", "userid");
			serializer.startTag("", "password");
			serializer.text(this._password);
			serializer.endTag("", "password");
			if (this._token!=null){
				serializer.startTag("", "token");
				serializer.text(this._token);
				serializer.endTag("", "token");
			}
			serializer.startTag("", "time");
			serializer.text(this._time);
			serializer.endTag("", "time");
			if(this.auxpassword!=null && this.auxpassword.trim().length()>0){
				serializer.startTag("", "auxpassword");
				serializer.text(this.auxpassword);
				serializer.endTag("", "auxpassword");
			}
			serializer.endTag("", "aaa");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class RequestServiceGatewayRequest {

	public RequestServiceGatewayRequest(){}

	public RequestServiceGatewayRequest(String uid, String pwd){
		this._userid = uid;
		this._password = pwd;
	}
	
	private String _userid;
	public String getUserid(){ return this._userid; }
	public void setUserid(String value){ this._userid = value; }

	private String _password;
	public String getPassword(){ return this._password; }
	public void setPassword(String value){ this._password = value; }

	private String _language="en_US";
	public String getLanguage(){ return this._language; }
	public void setLanguage(String value){ this._language = value; }

	private String _service="1";
	public String getService(){ return this._service; }
	public void setService(String value){ this._service = value; }

	private String _client_c="0";
	public String getClientC(){ return this._client_c; }
	public void setClient(String value){ this._client_c = value; }

	private String _client_ver="2.2.0.0";
	public String getClientVer(){ return this._client_ver; }
	public void setClientVer(String value){ this._client_ver = value; }

	private String _os_name="Android";
	public String getOsName(){ return this._os_name; }
	public void setOsName(String value){ this._os_name = value; }

	private String _os_ver=android.os.Build.VERSION.RELEASE;
	public String getOsVer(){ return this._os_ver; }
	public void setOsVer(String value){ this._os_ver = value; }

	private String _time=String.valueOf(System.currentTimeMillis());
	public String getTime(){ return this._time; }
	public void setTime(String value){ this._time = value; }
	
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "requestservicegateway");
			serializer.startTag("", "userid");
			serializer.text(this._userid);
			serializer.endTag("", "userid");
			serializer.startTag("", "password");
			serializer.text(this._password);
			serializer.endTag("", "password");
			serializer.startTag("", "language");
			serializer.text(this._language);
			serializer.endTag("", "language");
			serializer.startTag("", "service");
			serializer.text(this._service);
			serializer.endTag("", "service");
			
			serializer.startTag("", "client");
				serializer.startTag("", "c");
				serializer.text(this._client_c);
				serializer.endTag("", "c");
				serializer.startTag("", "ver");
				serializer.text(this._client_ver);
				serializer.endTag("", "ver");
			serializer.endTag("", "client");
			
			serializer.startTag("", "os");
				serializer.startTag("", "name");
				serializer.text(this._os_name);
				serializer.endTag("", "name");
				serializer.startTag("", "ver");
				serializer.text(this._os_ver);
				serializer.endTag("", "ver");
			serializer.endTag("", "os");

			serializer.startTag("", "time");
			serializer.text(this._time);
			serializer.endTag("", "time");
			serializer.endTag("", "requestservicegateway");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

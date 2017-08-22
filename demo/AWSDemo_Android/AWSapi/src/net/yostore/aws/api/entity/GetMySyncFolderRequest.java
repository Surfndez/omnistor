package net.yostore.aws.api.entity;

import java.io.StringWriter;
import org.xmlpull.v1.XmlSerializer;
import android.util.Xml;

public class GetMySyncFolderRequest
{
	private String _token;
	private String _userId;
	
	public GetMySyncFolderRequest(){};
	public GetMySyncFolderRequest(String token, String uid)
	{
		this._token  = token;
		this._userId = uid;
	}
	public String getToken()
	{
		return _token;
	}
	public void setToken(String _token)
	{
		this._token = _token;
	}
	public String getUserId()
	{
		return _userId;
	}
	public void setUserId(String _userId)
	{
		this._userId = _userId;
	}
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "getmysyncfolder");
			serializer.startTag("", "token");
			serializer.text(this._token);
			serializer.endTag("", "token");
			serializer.startTag("", "userid");
			serializer.text(this._userId);
			serializer.endTag("", "userid");
			serializer.endTag("", "getmysyncfolder");
			serializer.endDocument();
			return writer.toString();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import net.yostore.utility.Base64;

import android.util.Xml;

/*
 * 
 輸入參數<attribute>內容範例如下：
<creationtime>1209181613</creationtime>
<lastaccesstime>1209181732</lastaccesstime>
<lastwritetime>1209181614</lastwritetime>
<x-machinename>V2ViIHVwbG9hZA==</x-machinename>
其中的數值皆為1970/1/1至今的毫秒數，此欄不影響運算，僅供記錄輔助訊息

 */

public class Attribute {
	private String _creationtime;
	public String getCreationtime(){ return this._creationtime; }
	public void setCreationtime(String value){ this._creationtime = value; }

	private String _lastaccesstime;
	public String getLastaccesstime(){ return this._lastaccesstime; }
	public void setLastaccesstime(String value){ this._lastaccesstime = value; }
	
	private String _lastwritetime;
	public String getLastwritetime(){ return this._lastwritetime; }
	public void setLastwritetime(String value){ this._lastwritetime = value; }
	
	private String xMachinename;
	public String getxMachinename()
	{
		return xMachinename;
	}
	public void setxMachinename(String xMachinename)
	{
		this.xMachinename = xMachinename;
	}
	
	private String clienttype = ApiCookies.sid;
	
	public String getClienttype()
	{
		return clienttype;
	}
	public void setClienttype(String clienttype)
	{
		this.clienttype = clienttype;
	}
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
//			serializer.startDocument("UTF-8", true);
//			serializer.startTag("", "attribute");
			serializer.startTag("", "creationtime");
			serializer.text(this._creationtime);
			serializer.endTag("", "creationtime");
			serializer.startTag("", "lastaccesstime");
			serializer.text(this._lastaccesstime);
			serializer.endTag("", "lastaccesstime");
			serializer.startTag("", "lastwritetime");
			serializer.text(this._lastwritetime);
			serializer.endTag("", "lastwritetime");
			if ( this.xMachinename != null && this.xMachinename.trim().length() > 0 )
			{
				serializer.startTag("", "x-machinename");
				serializer.text(Base64.encodeToBase64String(this.xMachinename));
				serializer.endTag("", "x-machinename");
			}
			if ( this.clienttype != null && this.clienttype.trim().length() > 0 )
			{
				serializer.startTag("", "clienttype");
				serializer.text(this.clienttype);
				serializer.endTag("", "clienttype");
			}
//			serializer.endTag("", "attribute");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

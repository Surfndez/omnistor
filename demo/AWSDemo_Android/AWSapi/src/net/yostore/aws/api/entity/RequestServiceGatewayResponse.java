package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class RequestServiceGatewayResponse extends ApiResponse{


	private String _servicegateway;
	public String getServicegateway(){ return this._servicegateway; }
	public void setServicegateway(String value){ this._servicegateway = value; }

	private String _time;
	public String getTime(){ return this._time; }
	public void setTime(String value){ this._time = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "requestservicegateway");
			serializer.startTag("", "status");
			serializer.text(String.valueOf(this._status));
			serializer.endTag("", "status");
			serializer.startTag("", "servicegateway");
			serializer.text(this._servicegateway);
			serializer.endTag("", "servicegateway");
			serializer.startTag("", "time");
			serializer.text(this._time);
			serializer.endTag("", "time");
			serializer.endTag("", "requestservicegateway");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

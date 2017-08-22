package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class PackageInfo {
	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }

	private String _display;
	public String getDisplay(){ return this._display; }
	public void setDisplay(String value){ this._display = value; }

	private String _capacity;
	public String getCapacity(){ return this._capacity; }
	public void setCapacity(String value){ this._capacity = value; }

	private String _maxfilesize;
	public String getMaxfilesize(){ return this._maxfilesize; }
	public void setMaxfilesize(String value){ this._maxfilesize = value; }

	private String _expire;
	public String getExpire(){ return this._expire; }
	public void setExpire(String value){ this._expire = value; }
	
	private int mearBlockFreeAccFirstGate = 0;
	private int mearBlockFreeAccAfterFGate = 0;
	
	public int getMearBlockFreeAccFirstGate()
	{
		return this.mearBlockFreeAccFirstGate;
	}
	public void setMearBlockFreeAccFirstGate(int mearBlockFreeAccFirstGate)
	{
		this.mearBlockFreeAccFirstGate = mearBlockFreeAccFirstGate;
	}
	public int getMearBlockFreeAccAfterFGate()
	{
		return this.mearBlockFreeAccAfterFGate;
	}
	public void setMearBlockFreeAccAfterFGate(int mearBlockFreeAccAfterFGate)
	{
		this.mearBlockFreeAccAfterFGate = mearBlockFreeAccAfterFGate;
	}
	
	public String toXml(){
		String mearFeaturelist = " <feature name=\"MEar\" enable=\"1\">"+
			"<property name=\"BlockFreeAccFirstGate\" value=\""+this.mearBlockFreeAccFirstGate+"\"/>"+
			"<property name=\"BlockFreeAccAfterFGate\" value=\""+this.mearBlockFreeAccAfterFGate+"\"/>"+
			"</feature>";
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "package");
			serializer.startTag("", "id");
			serializer.text(this._id);
			serializer.endTag("", "id");
			serializer.startTag("", "display");
			serializer.text(this._display);
			serializer.endTag("", "display");
			serializer.startTag("", "capacity");
			serializer.text(this._capacity);
			serializer.endTag("", "capacity");
			serializer.startTag("", "maxfilesize");
			serializer.text(this._maxfilesize);
			serializer.endTag("", "maxfilesize");
			serializer.startTag("", "expire");
			serializer.text(this._expire);
			serializer.endTag("", "expire");
			serializer.startTag("", "featurelist");
			serializer.text(mearFeaturelist);
			serializer.endTag("", "featurelist");
			serializer.endTag("", "package");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

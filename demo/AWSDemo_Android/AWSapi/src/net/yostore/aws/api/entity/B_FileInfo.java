package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class B_FileInfo {
	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }

	private String _display;
	public String getDisplay(){ return this._display; }
	public void setDisplay(String value){ this._display = value; }

	private String _isgroupaware;
	public String getIsgroupaware(){ return this._isgroupaware; }
	public void setIsgroupaware(String value){ this._isgroupaware = value; }


	private long _size;
	public long getSize(){ return this._size; }
	public void setSize(long value){ this._size = value; }


	private boolean _isbackup;
	public boolean getIsbackup(){ return this._isbackup; }
	public void setIsbackup(boolean value){ this._isbackup = value; }

	private boolean _isorigdeleted;
	public boolean getIsorigdeleted(){ return this._isorigdeleted; }
	public void setIsorigdeleted(boolean value){ this._isorigdeleted = value; }

	private boolean _isinfected;
	public boolean getIsinfected(){ return this._isinfected; }
	public void setIsinfected(boolean value){ this._isinfected = value; }

	private boolean _ispublic;
	public boolean getIspublic(){ return this._ispublic; }
	public void setIspublic(boolean value){ this._ispublic = value; }

	private int _headversion;
	public int getHeadversion(){ return this._headversion; }
	public void setHeadversion(int value){ this._headversion = value; }

	private String _createdtime;
	public String getCreatedtime(){ return this._createdtime; }
	public void setCreatedtime(String value){ this._createdtime = value; }

	private String _markid;
	public String getMarkid(){ return this._markid; }
	public void setMarkid(String value){ this._markid = value; }
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "file");
			serializer.startTag("", "id");
			serializer.text(this._id);
			serializer.endTag("", "id");
			
			serializer.startTag("", "isgroupaware");
			serializer.text(this._isgroupaware);
			serializer.endTag("", "isgroupaware");
			
			serializer.startTag("", "rawfilename");
			serializer.text(this._display);
			serializer.endTag("", "rawfilename");
			
			serializer.startTag("", "size");
			serializer.text(String.valueOf(this._size));
			serializer.endTag("", "size");
			
			serializer.startTag("", "isbackup");
			serializer.text(this._isbackup?"1":"0");
			serializer.endTag("", "isbackup");
			
			serializer.startTag("", "isorigdeleted");
			serializer.text(this._isorigdeleted?"1":"0");
			serializer.endTag("", "isorigdeleted");
			
			serializer.endTag("", "isinfected");
			serializer.startTag("", "ispublic");
			serializer.text(this._ispublic?"1":"0");
		
			serializer.startTag("", "ispublic");
			serializer.text(this._ispublic?"1":"0");
			serializer.endTag("", "ispublic");
			
			serializer.startTag("", "headversion");
			serializer.text(String.valueOf(this._headversion));
			serializer.endTag("", "headversion");
			
			serializer.startTag("", "createdtime");
			serializer.text(this._createdtime);
			serializer.endTag("", "createdtime");
			
			serializer.startTag("", "markid");
			serializer.text(this._markid);
			serializer.endTag("", "markid");
			
			serializer.endTag("", "file");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

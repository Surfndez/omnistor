package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class B_FolderInfo {
	private String _id;
	public String getId(){ return this._id; }
	public void setId(String value){ this._id = value; }
	
	private String _treesize;
	public String getTreesize(){ return this._treesize; }
	public void setTreesize(String value){ this._treesize = value; }
	
	private boolean _isgroupaware;
	public boolean getIsgroupaware(){ return this._isgroupaware; }
	public void setIsgroupaware(boolean value){ this._isgroupaware = value; }

	private String _display;
	public String getDisplay(){ return this._display; }
	public void setDisplay(String value){ this._display = value; }

	private boolean _isbackup;
	public boolean getIsbackup(){ return this._isbackup; }
	public void setIsbackup(boolean value){ this._isbackup = value; }

	private boolean _isorigdeleted;
	public boolean getIsorigdeleted(){ return this._isorigdeleted; }
	public void setIsorigdeleted(boolean value){ this._isorigdeleted = value; }

	private boolean _ispublic;
	public boolean getIspublic(){ return this._ispublic; }
	public void setIspublic(boolean value){ this._ispublic = value; }

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
			serializer.startTag("", "folder");
			
			serializer.startTag("", "id");
			serializer.text(this._id);
			serializer.endTag("", "id");
			
			serializer.startTag("", "treesize");
			serializer.text(this._treesize);
			serializer.endTag("", "treesize");
			
			serializer.startTag("", "isgroupaware");
			serializer.text(this._isgroupaware?"1":"0");
			serializer.endTag("", "isgroupaware");
			
			
			serializer.startTag("", "rawfoldername");
			serializer.text(this._display);
			serializer.endTag("", "rawfoldername");
			
			serializer.startTag("", "isbackup");
			serializer.text(this._isbackup?"1":"0");
			serializer.endTag("", "isbackup");
			
			serializer.startTag("", "isorigdeleted");
			serializer.text(this._isorigdeleted?"1":"0");
			serializer.endTag("", "isorigdeleted");
			
			serializer.startTag("", "ispublic");
			serializer.text(this._ispublic?"1":"0");
			serializer.endTag("", "ispublic");
			
			serializer.startTag("", "createdtime");
			serializer.text(this._createdtime);
			serializer.endTag("", "createdtime");
			
			serializer.startTag("", "markid");
			serializer.text(this._markid);
			serializer.endTag("", "markid");
			
			serializer.endTag("", "folder");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

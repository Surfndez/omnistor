package net.yostore.aws.api.entity;

import java.io.StringWriter;
import java.util.HashMap;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class BrowseFolderRequest {
	private String TAG="BrowseFolderRequest";
	private HashMap<String, String> r2v = new HashMap<String, String>();

	public BrowseFolderRequest() {
		initHashMap();
	}

	public BrowseFolderRequest(String uid, String token, String folderid,
			int sort, int sortByDesc) {
		this._userid = uid;
		this._token = token;
		this._folderid = folderid;
		this.sort = sort;
		this.sortByDesc = sortByDesc;
		initHashMap();
	}

	private void initHashMap() {
		r2v.put("-1", "system.my.encrypted.root");
		r2v.put("-3", "system.backup.root");
		r2v.put("-5", "system.sync.root");
		r2v.put("-7", "system.oeo.root");
		// r2v.put("-9", "");
		// r2v.put("-11", "");
		r2v.put("-13", "system.searchcriteria.root");
		r2v.put("-15", "system.familymemo.root");
	}

	private String _token;

	public String getToken() {
		return this._token;
	}

	public void setToken(String value) {
		this._token = value;
	}

	private String _scrip = String.valueOf(System.currentTimeMillis());

	public String getScrip() {
		return this._scrip;
	}

	public void setScrip(String value) {
		this._scrip = value;
	}

	private String _language = "en_US";

	public String getLanguage() {
		return this._language;
	}

	public void setLanguage(String value) {
		this._language = value;
	}

	private String _userid;

	public String getUserid() {
		return this._userid;
	}

	public void setUserid(String value) {
		this._userid = value;
	}

	private String _folderid;

	public String getFolderid() {
		return this._folderid;
	}

	public void setFolderid(String value) {
		this._folderid = value;
	}

	private int _pageno = -1;

	public int getPageno() {
		return this._pageno;
	}

	public void setPageno(int value) {
		this._pageno = value;
	}

	private int _pagesize = 500;

	public int getPagesize() {
		return this._pagesize;
	}

	public void setPagesize(int value) {
		this._pagesize = value;
	}

	private int sort = 1;
	private int sortByDesc = 0;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSortByDesc() {
		return sortByDesc;
	}

	public void setSortByDesc(int sortByDesc) {
		this.sortByDesc = sortByDesc;
	}

	public String toXml() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "browse");
			serializer.startTag("", "token");
			serializer.text(this._token);
			serializer.endTag("", "token");
			serializer.startTag("", "scrip");
			serializer.text(this._scrip);
			serializer.endTag("", "scrip");
			serializer.startTag("", "language");
			serializer.text(this._language);
			serializer.endTag("", "language");
			serializer.startTag("", "userid");
			serializer.text(this._userid);
			serializer.endTag("", "userid");
			serializer.startTag("", "folderid");
			serializer.text(map2virtual());
			serializer.endTag("", "folderid");

			if (this._pageno > 0) {
				serializer.startTag("", "pageno");
				serializer.text(String.valueOf(this._pageno));
				serializer.endTag("", "pageno");
				serializer.startTag("", "pagesize");
				serializer.text(String.valueOf(this._pagesize));
				serializer.endTag("", "pagesize");
			}

			serializer.startTag("", "sortby");
			serializer.text(String.valueOf(this.sort));
			serializer.endTag("", "sortby");
			serializer.startTag("", "sortdirection");
			serializer.text(String.valueOf(this.sortByDesc));
			serializer.endTag("", "sortdirection");

			serializer.endTag("", "browse");
			serializer.endDocument();
			return writer.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String map2virtual() {
		Log.d(TAG, "_folderid"+this._folderid);
		if (this._folderid.startsWith("-"))
			return this.r2v.get(this._folderid);
		else
			return this._folderid;
	}
}// end class 
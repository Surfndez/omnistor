package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class SetAdvancedSharecodeRequest {

	public SetAdvancedSharecodeRequest() {
	}

	private String _userid;

	public String getUserid() {
		return this._userid;
	}

	public void setUserid(String value) {
		this._userid = value;
	}

	private String _token = null;

	public String getToken() {
		return this._token;
	}

	public void setToken(String value) {
		this._token = value;
	}

	// /////
	private String _isfolder = null;

	public String getIsfolder() {
		return this._isfolder;
	}

	public void setIsfolder(String value) {
		this._isfolder = value;
	}

	// entryid
	private String entryid = null;

	public String getEntryid() {
		return this.entryid;
	}

	public void setEntryid(String value) {
		this.entryid = value;
	}

	// clearshare
	private String clearshare = null;

	public String getClearshare() {
		return this.clearshare;
	}

	public void setClearshare(String value) {
		this.clearshare = value;
	}

	// password

	private String password = null;

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	// validityduration

	private String validityduration = null;

	public String getValidityduration() {
		return this.validityduration;
	}

	public void setvaliditydurationalidityduration(String value) {
		this.validityduration = value;
	}

	// isgroupaware

	private String isgroupaware = null;

	public String getIsgroupaware() {
		return this.isgroupaware;
	}

	public void setIsgroupaware(String value) {
		this.isgroupaware = value;
	}

	// folderquota
	private String folderquota = null;

	public String getFolderquota() {
		return this.folderquota;
	}

	public void setFolderquota(String value) {
		this.folderquota = value;
	}

	public SetAdvancedSharecodeRequest(String token, String userid,
			String _isfolder, String entryid, String password,
			String validityduration, String clearshare, String isgroupaware,
			String folderquota) {
		this._token = token;
		this._userid = userid;
		this._isfolder = _isfolder;
		this.entryid = entryid;
		this.password = password;
		this.validityduration = validityduration;
		this.clearshare = clearshare;
		this.isgroupaware = isgroupaware;
		this.folderquota = folderquota;
	}

	public String toXml() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "setadvancedsharecode");

			serializer.startTag("", "token");
			serializer.text(this._token);
			serializer.endTag("", "token");

			serializer.startTag("", "userid");
			serializer.text(this._userid);
			serializer.endTag("", "userid");

			serializer.startTag("", "isfolder");
			serializer.text(this._isfolder);
			serializer.endTag("", "isfolder");

			serializer.startTag("", "entryid");
			serializer.text(this.entryid);
			serializer.endTag("", "entryid");

			if (this.clearshare != null && this.clearshare.trim().length() > 0) {
				serializer.startTag("", "clearshare");
				serializer.text(this.clearshare);
				serializer.endTag("", "clearshare");
			}

			if (this.password != null && this.password.trim().length() > 0) {
				serializer.startTag("", "password");
				serializer.text(this.password);
				serializer.endTag("", "password");
			}
			if (this.validityduration != null
					&& this.validityduration.trim().length() > 0) {
				serializer.startTag("", "validityduration");
				serializer.text(this.validityduration);
				serializer.endTag("", "validityduration");
			}

			if (this.isgroupaware != null
					&& this.isgroupaware.trim().length() > 0) {
				serializer.startTag("", "isgroupaware");
				serializer.text(this.isgroupaware);
				serializer.endTag("", "isgroupaware");
				
				serializer.startTag("", "shareforuserid");
				serializer.text(this._userid);
				serializer.endTag("", "shareforuserid");
				
			}

			if (this.folderquota != null
					&& this.folderquota.trim().length() > 0) {
				serializer.startTag("", "folderquota");
				serializer.text(this.folderquota);
				serializer.endTag("", "folderquota");
			}

			serializer.endTag("", "setadvancedsharecode");
			serializer.endDocument();
			return writer.toString();
			// return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

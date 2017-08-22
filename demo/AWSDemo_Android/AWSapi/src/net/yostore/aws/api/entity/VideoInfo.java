package net.yostore.aws.api.entity;

import java.io.Serializable;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class VideoInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _type;

	public String getType() {
		return this._type;
	}

	public void setType(String value) {
		this._type = value;
	}

	private String _progressstate;

	public String getProgressstate() {
		return this._progressstate;
	}

	public void setProgressstate(String value) {
		this._progressstate = value;
	}

	private String _resolution;

	public String getResolution() {
		return this._resolution;
	}

	public void setResolution(String value) {
		this._resolution = value;
	}

	private String _abstractstate;

	public String getAbstractstate() {
		return this._abstractstate;
	}

	public void setAbstractstate(String value) {
		this._abstractstate = value;
	}

	public String toXml() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "video");

			serializer.startTag("", "type");
			serializer.text(this._type);
			serializer.endTag("", "type");

			serializer.startTag("", "resolution");
			serializer.text(this._progressstate);
			serializer.endTag("", "progressstate");

			serializer.startTag("", "progressstate");
			serializer.text(this._resolution);
			serializer.endTag("", "resolution");

			serializer.startTag("", "abstractstate");
			serializer.text(this._abstractstate);
			serializer.endTag("", "abstractstate");

			serializer.endTag("", "video");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// public VideoInfo(String type, String progressstate, String resolution,
	// String abstractstate) {
	// super();
	// this._type=type;
	// this._progressstate=progressstate;
	// this._resolution=resolution;
	// this._abstractstate=abstractstate;
	// }

}

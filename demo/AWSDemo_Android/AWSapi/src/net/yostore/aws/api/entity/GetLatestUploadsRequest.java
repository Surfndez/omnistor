package net.yostore.aws.api.entity;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;
/*
 * 
 * <getlatestchangefiles>
	<userid>{ User ID, Ex:doris251 }</userid>
	<token></token>
	<top>{ �]�w�^�ǫe�X��files�A�w�]�Ȭ�10 }</top>
	<targetroot>{ ��j�M��Folder ID,�H�r�����j }</targetroot>
	<sortdirection>{ 0 (���ASC) | 1 (���DESC) }</sortdirection>
	<!-- �d�ҡGtop=30,sortdirection=1�A�|��X�Τ�̪���ɪ��B�ѷs�ƨ��ª�30���O��;top=30,sortdirection=0�A�|��X�Τ�̪���ɪ��B���±ƨ�s��30���O��; -->
</getlatestchangefiles>

 * 
 */
public class GetLatestUploadsRequest {
	public GetLatestUploadsRequest(){}
	public GetLatestUploadsRequest(String userId, String token, int top, String targetroot, int sortdirection){
		this._token = token;
		this.userId = userId;
		this.top = top;
		this.targetroot = targetroot;
		this.sortdirection = sortdirection;
	}
		
	private String _token;
	public String getToken(){ return this._token; }
	public void setToken(String value){ this._token = value; }

	public String userId;
	public int top;
	public String targetroot;
	public int sortdirection;
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public int getTop()
	{
		return top;
	}
	public void setTop(int top)
	{
		this.top = top;
	}
	public String getTargetroot()
	{
		return targetroot;
	}
	public void setTargetroot(String targetroot)
	{
		this.targetroot = targetroot;
	}
	public int getSortdirection()
	{
		return sortdirection;
	}
	public void setSortdirection(int sortdirection)
	{
		this.sortdirection = sortdirection;
	}
	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "getlatestuploads");

			serializer.startTag("", "userid");
			serializer.text(this.userId);
			serializer.endTag("", "userid");
			serializer.startTag("", "token");
			serializer.text(this._token);
			serializer.endTag("", "token");
			serializer.startTag("", "top");
			serializer.text(String.valueOf(this.top));
			serializer.endTag("", "top");
			serializer.startTag("", "targetroot");
			serializer.text(String.valueOf(this.targetroot));
			serializer.endTag("", "targetroot");
			serializer.startTag("", "sortdirection");
			serializer.text(String.valueOf(this.sortdirection));
			serializer.endTag("", "sortdirection");
			
			serializer.endTag("", "getlatestuploads");
			serializer.endDocument();
			return writer.toString();
//			return "?xml=" + URLEncoder.encode(writer.toString());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

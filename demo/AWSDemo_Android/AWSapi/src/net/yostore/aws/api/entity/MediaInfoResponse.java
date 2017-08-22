package net.yostore.aws.api.entity;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class MediaInfoResponse extends ApiResponse{

	/******************************************************************************************************************************************************
	�榡�G
	<mi>�G����T�ɤ�root
	<i>�G�ɮ�id�A�����ɮ�id
	<da>�G�ɮ׮ɶ��A����ɮק�s�ɶ����J
	<d>�G�ɮצW��
	<ty>�G�ɮ׮榡�A�ثe0: photo, 1: movie
	<r>�G���ਤ�סA��0, 90, 180, 270�|��
	<no>�Gnote
	<en>�G���v���ɥثe���ɪ��p�A-1: ���ɤ�, -2: ���ɥ���(�Ӥ��ɵL���񦹸�T)
	<us>�G���ɮץثe�W�Ǫ��p�A0:�w�b����, 1:�W�Ǥ�, 2: �W�ǥ���
	<fr>�G���ɮ׶פJ�ӷ��A0:my collection, -3:backup, -5:MySyncFolder, 1:pixwe client�W��
	�ɮ׽d�ҡG
	<mi>
	    <f><i>19238</i><da>1098912</da><d>img001.jpg</d><ty>0</ty><r>180< /r><no>���ѧڬݨ�ڤ�</no><us>0</us></f>
	    <f><i>233910</i><da>819239012</da><d>img002.jpg</d><ty>1</ty><no>���ѧڤS�ݨ�ڤ�</no><en>1</en><us>0</us></f>
	</mi>
	 
	 ******************************************************************************************************************************************************/
	
	public int getStatus(){
		return 0;
	}
	
	private List<MediaInfoEntity> mieList = new LinkedList<MediaInfoEntity>();

	public List<MediaInfoEntity> getMieList()
	{
		return mieList;
	}

	public void setMieList(List<MediaInfoEntity> mieList)
	{
		this.mieList = mieList;
	}

	public String toXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "mi");
			if(this.mieList.size()>0){
				for(int i=0 ; i<this.mieList.size() ; i++){
					serializer.startTag("", "f");
					serializer.text(this.mieList.get(i).toXml());
					serializer.endTag("", "f");
				}
			}
			
			serializer.endTag("", "mi");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}// end class 

package net.yostore.aws.api.entity;

import java.io.File;


public class DirectUploadRequest{

	String fileName;
	File file;
	byte[] data;
	String parentid;
	long fileid = -999l;
	String attribute ;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public long getFileid()
	{
		return fileid;
	}
	public void setFileid(long fileid)
	{
		this.fileid = fileid;
	}
	public String getAttribute()
	{
		return attribute;
	}
	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}
	
	

}// end class 

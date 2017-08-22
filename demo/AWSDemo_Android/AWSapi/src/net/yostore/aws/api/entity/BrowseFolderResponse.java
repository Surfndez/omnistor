package net.yostore.aws.api.entity;

import java.util.LinkedList;
import java.util.List;

public class BrowseFolderResponse  extends ApiResponse{
	private List<B_FileInfo> fileList = new LinkedList<B_FileInfo>();
	public List<B_FileInfo> getFileList() {return fileList;}
	public void setFileList(List<B_FileInfo> fileList) {this.fileList = fileList;}	
	
	private List<B_FolderInfo> folderList=new LinkedList<B_FolderInfo>();
	public List<B_FolderInfo> getFolderList() {return folderList;}
	public void setFolderList(List<B_FolderInfo> folderList) {this.folderList = folderList;}
	
	private String _logmessage;
	public String getLogmessage(){ return this._logmessage; }
	public void setLogmessage(String value){ this._logmessage = value; }

	private String _scrip;
	public String getScrip(){ return this._scrip; }
	public void setScrip(String value){ this._scrip = value; }
	
	private String _rawfoldername;
	public String getRawfoldername(){ return this._rawfoldername; }
	public void setRawfoldername(String value){ this._rawfoldername = value; }
	
	private String _parent;
	public String getParent(){ return this._parent; }
	public void setParent(String value){ this._parent = value; }
	
	private String _rootfolderid;
	public String getRootfolderid(){ return this._rootfolderid; }
	public void setRootfolderid(String value){ this._rootfolderid = value; }

	private int _pageno;
	public int getPageno(){ return this._pageno; }
	public void setPageno(int value){ this._pageno = value; }

	private int _pagesize;
	public int getPagesize(){ return this._pagesize; }
	public void setPagesize(int value){ this._pagesize = value; }

	private int _totalcount;
	public int getTotalcount(){ return this._totalcount; }
	public void setTotalcount(int value){ this._totalcount = value; }

	private boolean _hasnextpage;
	public boolean getHasnextpage(){ return this._hasnextpage; }
	public void setHasnextpage(boolean value){ this._hasnextpage = value; }
	
	
	

}// end class 
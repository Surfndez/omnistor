package entity;

public class FolderBrowseResponse
{
	/* all fields */
	private String _status = null;
	private String _rawfoldername = null;
	private String _parent = null;
	private String _rootfolderid = null;
	private PageRsp _page = null;
	private java.util.LinkedList<PublicFolderBase> _folders = null;
	private java.util.LinkedList<PublicFileBase> _files = null;

	/* default constructor */
	public FolderBrowseResponse()
	{
	}

	/* all fields constructor */
	public FolderBrowseResponse(String status, String rawfoldername, String parent, String rootfolderid, PageRsp page, java.util.LinkedList<PublicFolderBase> folders, java.util.LinkedList<PublicFileBase> files)
	{
		this._status = status;
		this._rawfoldername = rawfoldername;
		this._parent = parent;
		this._rootfolderid = rootfolderid;
		this._page = page;
		this._folders = folders;
		this._files = files;
	}
	
	/* setters */
	public void setStatus(String status)
	{
		this._status = status;
	}
	
	public void setRawFolderName(String _rawfoldername)
	{
		this._rawfoldername = _rawfoldername;
	}
	
	public void setParent(String _parent)
	{
		this._parent = _parent;
	}
	
	public void setRootFolderID(String _rootfolderid)
	{
		this._rootfolderid = _rootfolderid;
	}
	
	public void setPage(PageRsp page)
	{
		this._page = page;
	}
	
	public void setFolders(java.util.LinkedList<PublicFolderBase> folders)
	{
		this._folders = folders;
	}
	
	public void setFiles(java.util.LinkedList<PublicFileBase> files)
	{
		this._files = files;
	}	

	/* getters */
	public String getStatus()
	{
		return _status;
	}
	
	public String getRawFolderName()
	{
		return _rawfoldername;
	}
	
	public String getParent()
	{
		return _parent;
	}
	
	public String getRootFolderID()
	{
		return _rootfolderid;
	}
	
	public PageRsp getPage()
	{
		return _page;
	}
	
	public java.util.LinkedList<PublicFolderBase> getFolders()
	{
		return _folders;
	}
	
	public java.util.LinkedList<PublicFileBase> getFiles()
	{
		return _files;
	}
		
	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("FolderBrowsePublicResponse=>\n");
		sb.append(" status:").append(_status).append("\n");
		sb.append(" rawfoldername:").append(_rawfoldername).append("\n");
		sb.append(" parent:").append(_parent).append("\n");
		sb.append(" rootfolderid:").append(_rootfolderid).append("\n");
		sb.append(" page:").append(_page).append("\n");
		sb.append(" folders:").append(_folders).append("\n");
		sb.append(" files:").append(_files).append("\n");
		return sb.toString();
	}
}

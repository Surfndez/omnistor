package entity;

import sun.misc.BASE64Decoder;

public class PublicFolderBase
{
	/* all fields */
	private String _id = null;	
	private String _rawfoldername = null;
	private String _treesize = null;
	private boolean _isgroupaware = false;
	private boolean _isbackup = false;
	private boolean _isorigdeleted = false;
	private boolean _ispublic = false;
	private String _createdtime = null;
	private String _markid = null;

	/* default constructor */
	public PublicFolderBase()
	{
	}

	/* all fields constructor */
	public PublicFolderBase(String id, String _rawfoldername, String _treesize, boolean _isgroupaware, boolean isbackup, boolean isorigdeleted, boolean ispublic, String createdtime, String markid)
	{
		this._id = id;
		this._rawfoldername = _rawfoldername;
		this._treesize = _treesize;
		this._isgroupaware = _isgroupaware;
		this._isbackup = isbackup;
		this._isorigdeleted = isorigdeleted;
		this._ispublic = ispublic;
		this._createdtime = createdtime;
		this._markid = markid;
	}

	/* setters */
	public void setId(String id)
	{
		this._id = id;
	}

	public void setRawFolderName(String _rawfoldername)
	{
//		BASE64Decoder decoder = new BASE64Decoder(); 
//		try { 
//		byte[] b = decoder.decodeBuffer(_rawfoldername); 
//		this._rawfoldername= new String(b, "UTF-8"); 
//		} catch (Exception e) { 
//			this._rawfoldername= null; 
//		} 
		this._rawfoldername = _rawfoldername;
		//this._rawfoldername = Base64.decodeFastToString(_rawfoldername);
	}

	public void setTreeSize(String _treesize)
	{
		this._treesize = _treesize;
	}

	public void setIsGroupAware(boolean _isgroupaware)
	{
		this._isgroupaware = _isgroupaware;
	}

	public void setIsbackup(boolean isbackup)
	{
		this._isbackup = isbackup;
	}

	public void setIsorigdeleted(boolean isorigdeleted)
	{
		this._isorigdeleted = isorigdeleted;
	}

	public void setIspublic(boolean ispublic)
	{
		this._ispublic = ispublic;
	}

	public void setCreatedtime(String createdtime)
	{
		this._createdtime = createdtime;
	}

	public void setMarkid(String markid)
	{
		this._markid = markid;
	}

	/* getters */
	public String getId()
	{
		return _id;
	}

	public String getRawFolderName()
	{
		return _rawfoldername;
	}

	public String getTreeSize()
	{
		return _treesize;
	}

	public boolean getIsGroupAware()
	{
		return _isgroupaware;
	}

	public boolean getIsbackup()
	{
		return _isbackup;
	}

	public boolean getIsorigdeleted()
	{
		return _isorigdeleted;
	}

	public boolean getIspublic()
	{
		return _ispublic;
	}

	public String getCreatedtime()
	{
		return _createdtime;
	}

	public String getMarkid()
	{
		return _markid;
	}

	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("PublicFolderBase=>\n");
		sb.append(" id:").append(_id).append("\n");
		sb.append(" rawfoldername:").append(_rawfoldername).append("\n");
//		sb.append(" isbackup:").append(_isbackup).append("\n");
//		sb.append(" isorigdeleted:").append(_isorigdeleted).append("\n");
//		sb.append(" ispublic:").append(_ispublic).append("\n");
//		sb.append(" createdtime:").append(_createdtime).append("\n");
//		sb.append(" markid:").append(_markid).append("\n");
		return sb.toString();
	}
}
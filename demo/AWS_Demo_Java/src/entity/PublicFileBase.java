package entity;

import sun.misc.BASE64Decoder;


public class PublicFileBase
{
	/* all fields */
	private String _id = null;
	private String _rawfilename = null;
	private boolean _isgroupaware = false;
	private long _size = 0;
	private boolean _isbackup = false;
	private boolean _isorigdeleted = false;
	private int _isinfected = 0;
	private boolean _ispublic = false;
	private String _headversion = null;
	private String _createdtime = null;
	private String _markid = null;

	/* default constructor */
	public PublicFileBase()
	{
	}

	/* all fields constructor */
	public PublicFileBase(String id, String _rawfilename, boolean _isgroupaware, long size, boolean isbackup, boolean isorigdeleted, int isinfected, boolean ispublic, String headversion, String createdtime, String markid)
	{
		this._id = id;
		this._rawfilename = _rawfilename;
		this._isgroupaware = _isgroupaware;
		this._size = size;
		this._isbackup = isbackup;
		this._isorigdeleted = isorigdeleted;
		this._isinfected = isinfected;
		this._ispublic = ispublic;
		this._headversion = headversion;
		this._createdtime = createdtime;
		this._markid = markid;
	}

	/* setters */
	public void setId(String id)
	{
		this._id = id;
	}

	public void setRawFileName(String _rawfilename)
	{
		this._rawfilename = _rawfilename;
		//this._base64display = Base64.decodeFastToString(base64display);	
//		BASE64Decoder decoder = new BASE64Decoder(); 
//		try { 
//		byte[] b = decoder.decodeBuffer(base64display); 
//		this._base64display= new String(b, "UTF-8"); 
//		} catch (Exception e) { 
//			this._base64display= null; 
//		} 
		
	}

	public void setIsGroupAware(boolean _isgroupaware)
	{
		this._isgroupaware = _isgroupaware;
	}

	public void setSize(long size)
	{
		this._size = size;
	}

	public void setIsbackup(boolean isbackup)
	{
		this._isbackup = isbackup;
	}

	public void setIsorigdeleted(boolean isorigdeleted)
	{
		this._isorigdeleted = isorigdeleted;
	}

	public void setIsinfected(int isinfected)
	{
		this._isinfected = isinfected;
	}

	public void setIspublic(boolean ispublic)
	{
		this._ispublic = ispublic;
	}

	public void setHeadversion(String headversion)
	{
		this._headversion = headversion;
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

	public String getRawFileName()
	{
		return _rawfilename;
	}


	public boolean getIsGroupAware()
	{
		return _isgroupaware;
	}

	public long getSize()
	{
		return _size;
	}

	public boolean getIsbackup()
	{
		return _isbackup;
	}

	public boolean getIsorigdeleted()
	{
		return _isorigdeleted;
	}

	public int getIsinfected()
	{
		return _isinfected;
	}

	public boolean getIspublic()
	{
		return _ispublic;
	}

	public String getHeadversion()
	{
		return _headversion;
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
		StringBuilder sb = new StringBuilder("PublicFileBase=>\n");
		sb.append(" id:").append(_id).append("\n");
		sb.append(" rawfilename:").append(_rawfilename).append("\n");
//		sb.append(" isgroupaware:").append(_isgroupaware).append("\n");
		sb.append(" size:").append(_size).append("\n");
//		sb.append(" isbackup:").append(_isbackup).append("\n");
//		sb.append(" isorigdeleted:").append(_isorigdeleted).append("\n");
//		sb.append(" isinfected:").append(_isinfected).append("\n");
//		sb.append(" ispublic:").append(_ispublic).append("\n");
//		sb.append(" headversion:").append(_headversion).append("\n");
//		sb.append(" createdtime:").append(_createdtime).append("\n");
//		sb.append(" markid:").append(_markid).append("\n");
		return sb.toString();
	}
}
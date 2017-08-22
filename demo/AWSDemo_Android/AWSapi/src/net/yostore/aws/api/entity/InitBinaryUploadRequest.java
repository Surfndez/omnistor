package net.yostore.aws.api.entity;

public class InitBinaryUploadRequest
{
	private String _token;
	private String _name;
	private long   _parent;
	private String _attribute;
	private String _checksum;
	private long   _fileSize;
	private String _transId;
	private Long   _fileId;
	private Long   _syncFolderId;
	private String _sid;
	
	public String getToken()
	{
		return _token;
	}
	public void setToken(String _token)
	{
		this._token = _token;
	}
	public String getName()
	{
		return _name;
	}
	public void setName(String _name)
	{
		this._name = _name;
	}
	public long getParent()
	{
		return _parent;
	}
	public void setParent(long _parent)
	{
		this._parent = _parent;
	}
	public String getAttribute()
	{
		return _attribute;
	}
	public void setAttribute(String _attribute)
	{
		this._attribute = _attribute;
	}
	public String getChecksum()
	{
		return _checksum;
	}
	public void setChecksum(String _checksum)
	{
		this._checksum = _checksum;
	}
	public long getFileSize()
	{
		return _fileSize;
	}
	public void setFileSize(long _fileSize)
	{
		this._fileSize = _fileSize;
	}
	public String getTransactionId()
	{
		return _transId;
	}
	public void setTransactionId(String _transId)
	{
		this._transId = _transId;
	}
	public Long getFileId()
	{
		return _fileId;
	}
	public void setFileId(Long _fileId)
	{
		this._fileId = _fileId;
	}
	public Long getSyncFolderId()
	{
		return _syncFolderId;
	}
	public void setSyncFolderId(Long _syncFolderId)
	{
		this._syncFolderId = _syncFolderId;
	}
	public String getSid()
	{
		return _sid;
	}
	public void setSid(String _sid)
	{
		this._sid = _sid;
	}
	
	public String toString()
	{
		StringBuilder msg = new StringBuilder();
		msg.append("Token:").append(_token)
		   .append(", Parent:").append(_parent)
		   .append(", FileName:").append(_name)
		   .append(", FileSize:").append(_fileSize)
		   .append(", Attribute:").append(_attribute)
		   .append(", Checksum:").append(_checksum)
		   .append(", TransactionId:").append(_transId)
		   .append(", FileId:").append(_fileId)
		   .append(", SyncFolder:").append(_syncFolderId)
		   .append(", SID:").append(_sid)
		   ;
		
		return msg.toString();
	}
}

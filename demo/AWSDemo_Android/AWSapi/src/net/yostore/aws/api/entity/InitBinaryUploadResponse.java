package net.yostore.aws.api.entity;

public class InitBinaryUploadResponse extends ApiResponse
{
	private int    _status;
	private String _transId;
	private long   _offSet;
	private String _latestChecksum;
	private Long   _fileId;
	
	public int getStatus()
	{
		return _status;
	}
	public void setStatus(int _status)
	{
		this._status = _status;
	}
	public String getTransactionId()
	{
		return _transId;
	}
	public void setTransactionId(String _transId)
	{
		this._transId = _transId;
	}
	public long getOffSet()
	{
		return _offSet;
	}
	public void setOffSet(long _offSet)
	{
		this._offSet = _offSet;
	}
	public String getLatestChecksum()
	{
		return _latestChecksum;
	}
	public void setLatestChecksum(String _latestChecksum)
	{
		this._latestChecksum = _latestChecksum;
	}
	public Long getFileId()
	{
		return _fileId;
	}
	public void setFileId(Long _fileId)
	{
		this._fileId = _fileId;
	}	
	
	public String toString()
	{
		StringBuilder msg = new StringBuilder();
		msg.append("Status:").append(_status)
		   .append(", TransactionId:").append(_transId)
		   .append(", OffSet:").append(_offSet)
		   .append(", LatestChecksum:").append(_latestChecksum)
		   .append(", FileId:").append(_fileId)
		   ;
		
		return msg.toString();
	}
}

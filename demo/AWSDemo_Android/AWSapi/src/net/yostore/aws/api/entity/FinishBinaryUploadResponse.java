package net.yostore.aws.api.entity;

public class FinishBinaryUploadResponse extends ApiResponse
{
	private int  _status;
	private Long _fileId;
	
	public int getStatus()
	{
		return _status;
	}
	public void setStatus(int _status)
	{
		this._status = _status;
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
		   .append(", FileId:").append(_fileId);
		
		return msg.toString();
	}
}

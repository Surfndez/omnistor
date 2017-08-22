package net.yostore.aws.api.entity;

public class ResumeBinaryUploadResponse extends ApiResponse
{
	private int _status;

	public int getStatus()
	{
		return _status;
	}

	public void setStatus(int _status)
	{
		this._status = _status;
	}
	
	public String toString()
	{
		return "Status:" + _status;
	}
	
}

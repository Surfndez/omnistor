package net.yostore.aws.api.entity;

public class FinishBinaryUploadRequest
{
	private String _token;
	private String _transId;
	private String _latestChecksum;
	private String _sid;
	
	public String getToken()
	{
		return _token;
	}
	public void setToken(String _token)
	{
		this._token = _token;
	}
	public String getTransactionId()
	{
		return _transId;
	}
	public void setTransactionId(String _transId)
	{
		this._transId = _transId;
	}
	public String getLatestChecksum()
	{
		return _latestChecksum;
	}
	public void setLatestChecksum(String _latestChecksum)
	{
		this._latestChecksum = _latestChecksum;
	}
	public String getSID()
	{
		return _sid;
	}
	public void setSID(String _sid)
	{
		this._sid = _sid;
	}
	
	public String toString()
	{
		StringBuilder msg = new StringBuilder();
		msg.append("Token:").append(_token)
		   .append(", TransactionId:").append(_transId)
		   .append(", LatestChecksum:").append(_latestChecksum)
		   .append(", SID:").append(_sid);
		
		return msg.toString();
	}
}

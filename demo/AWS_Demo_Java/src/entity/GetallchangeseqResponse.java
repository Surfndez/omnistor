package entity;

public class GetallchangeseqResponse
{
	/* all fields */
	private String _status = null;	
	private long _syncinterval = 0;
	private java.util.LinkedList<PublicChangeseqfolderBase> _syncfolder = null;
	
	/* default constructor */
	public GetallchangeseqResponse()
	{		
	}

	/* all fields constructor */
	public GetallchangeseqResponse(String status, long syncinterval, java.util.LinkedList<PublicChangeseqfolderBase> syncfolder)
	{
		this._status = status;
		this._syncinterval = syncinterval;
		this._syncfolder = syncfolder;
	}
	
	/* setters */
	public void setStatus(String _status)
	{
		this._status = _status;
	}
	
	public void setSyncinterval(long _syncinterval)
	{
		this._syncinterval = _syncinterval;
	}
	
	public void setFolders(java.util.LinkedList<PublicChangeseqfolderBase> syncfolder)
	{
		this._syncfolder = syncfolder;
	}
	
	/* getters */
	public String getStatus()
	{
		return _status;
	}
	
	public long getSyncinterval()
	{
		return _syncinterval;
	}
	
	public java.util.LinkedList<PublicChangeseqfolderBase> getFolders()
	{
		return _syncfolder;
	}
	
	/* toString */
	public String toString()
	{
		StringBuilder msg = new StringBuilder("GetallchangeseqResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" syncinterval:").append(_syncinterval).append("\n");
		msg.append(" syncfolder:").append(_syncfolder).append("\n");
		return msg.toString();
	}
	
}
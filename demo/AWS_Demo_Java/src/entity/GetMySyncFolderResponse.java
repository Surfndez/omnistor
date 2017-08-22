package entity;

public class GetMySyncFolderResponse
{
	/* all fields */
	private String _status = null;	
	private String _id = null;	
	
	/* default constructor */
	public GetMySyncFolderResponse()
	{		
	}

	/* all fields constructor */
	public GetMySyncFolderResponse(String status, String id)
	{
		this._status = status;
		this._id = id;
	}
	
	/* setters */
	public void setStatus(String _status)
	{
		this._status = _status;
	}
	
	public void setId(String _id)
	{
		this._id = _id;
	}
	
	/* getters */
	public String getStatus()
	{
		return _status;
	}
	
	public String getId()
	{
		return _id;
	}
	
	
	/* toString */
	public String toString()
	{
		StringBuilder msg = new StringBuilder("GetMySyncFolderResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" id:").append(_id).append("\n");
		return msg.toString();
	}
	
}
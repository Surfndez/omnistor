package entity;

public class FolderRemoveResponse 
{
	/* all fields */
	private String _status = null;

	/* default constructor */
	public FolderRemoveResponse()
	{		
	}

	/* all fields constructor */
	public FolderRemoveResponse(String status)
	{
		this._status = status;		
	}

	/* setters */
	public void setStatus(String status) {
		this._status = status;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	/* toString */
	public String toString() 
	{
		StringBuilder msg = new StringBuilder("FolderRemoveResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		return msg.toString();	
	}
}

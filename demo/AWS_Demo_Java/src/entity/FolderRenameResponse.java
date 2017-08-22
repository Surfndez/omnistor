package entity;

public class FolderRenameResponse 
{
	/* all fields */
	private String _status = null;

	/* default constructor */
	public FolderRenameResponse()
	{		
	}

	/* all fields constructor */
	public FolderRenameResponse(String status)
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
		StringBuilder msg = new StringBuilder("FolderRenameResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		return msg.toString();	
	}
}

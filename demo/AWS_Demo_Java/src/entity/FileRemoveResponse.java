package entity;

public class FileRemoveResponse 
{
	/* all fields */
	private String _status = null;

	/* default constructor */
	public FileRemoveResponse()
	{		
	}

	/* all fields constructor */
	public FileRemoveResponse(String status)
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
		StringBuilder msg = new StringBuilder("FileRemoveResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		return msg.toString();	
	}
}

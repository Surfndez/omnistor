package entity;

public class FileRenameResponse 
{
	/* all fields */
	private String _status = null;

	/* default constructor */
	public FileRenameResponse()
	{
	}

	/* all fields constructor */
	public FileRenameResponse(String status)
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
		StringBuilder msg = new StringBuilder("FileRenameResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		return msg.toString();	
	}
}

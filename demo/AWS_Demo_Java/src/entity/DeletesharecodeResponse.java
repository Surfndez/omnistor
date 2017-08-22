package entity;

public class DeletesharecodeResponse 
{
	/* all fields */
	private String _status = null;	
	private String _scrip = null;

	/* default constructor */
	public DeletesharecodeResponse()
	{		
	}

	/* all fields constructor */
	public DeletesharecodeResponse(String status, String scrip)
	{
		this._status = status;
		this._scrip = scrip;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setScrip(String _scrip) {
		this._scrip = _scrip;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getScrip() {
		return _scrip;
	}

	/* toString */
	public String toString() 
	{		
		StringBuilder msg = new StringBuilder("DeletesharecodeResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" scrip:").append(_scrip).append("\n");
		return msg.toString();		
	}
}

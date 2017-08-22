package entity;

public class ResumeBinaryUploadResponse 
{
	/* all fields */
	private String _status = null;

	/* default constructor */
	public ResumeBinaryUploadResponse()
	{		
	}

	/* all fields constructor */
	public ResumeBinaryUploadResponse(String status)
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
		StringBuilder msg = new StringBuilder("ResumeBinaryUploadResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		return msg.toString();
	}
}
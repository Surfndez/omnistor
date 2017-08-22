package entity;

public class FinishBinaryUploadResponse 
{
	/* all fields */
	private String _status = null;
	private String _fileid = null;
	private String _logmessage = null;

	/* default constructor */
	public FinishBinaryUploadResponse()
	{		
	}

	/* all fields constructor */
	public FinishBinaryUploadResponse(String status, String fileid, String logmessage)
	{
		this._status = status;		
		this._fileid = fileid;		
		this._logmessage = logmessage;		
	}

	/* setters */
	public void setStatus(String status) {
		this._status = status;
	}

	public void setFileid(String _fileid) {
		this._fileid = _fileid;
	}

	public void setLogmessage(String _logmessage) {
		this._logmessage = _logmessage;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getFileid() {
		return _fileid;
	}

	public String getLogmessage() {
		return _logmessage;
	}

	/* toString */
	public String toString()
	{
		StringBuilder msg = new StringBuilder("FinishBinaryUploadResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" fileid:").append(_fileid).append("\n");
		msg.append(" logmessage:").append(_logmessage).append("\n");
		return msg.toString();
	}
}

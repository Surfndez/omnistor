package entity;

public class GetsharecodeResponse 
{
	/* all fields */
	private String _status = null;	
	private String _scrip = null;
	private String _uri = null;
	private String _ispasswordneeded = null;
	private String _sharecode = null;

	/* default constructor */
	public GetsharecodeResponse()
	{		
	}

	/* all fields constructor */
	public GetsharecodeResponse(String status, String scrip, String uri, String ispasswordneeded, String sharecode)
	{
		this._status = status;
		this._scrip = scrip;
		this._uri = uri;
		this._ispasswordneeded = ispasswordneeded;
		this._sharecode = sharecode;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setScrip(String _scrip) {
		this._scrip = _scrip;
	}

	public void setUri(String uri) {
		this._uri = uri;
	}

	public void setIspasswordneeded(String ispasswordneeded) {
		this._ispasswordneeded = ispasswordneeded;
	}
	
	public void setShareCode(String sharecode) {
		this._sharecode = sharecode;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getScrip() {
		return _scrip;
	}

	public String getUri() {
		return _uri;
	}

	public String getIspasswordneeded() {
		return _ispasswordneeded;
	}
	
	public String getShareCode() {
		return _sharecode;
	}

	/* toString */
	public String toString() 
	{			
		StringBuilder msg = new StringBuilder("GetsharecodeResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" scrip:").append(_scrip).append("\n");
		msg.append(" uri:").append(_uri).append("\n");
		msg.append(" ispasswordneeded:").append(_ispasswordneeded).append("\n");
		msg.append(" sharecode:").append(_sharecode).append("\n");
		return msg.toString();	
	}
}

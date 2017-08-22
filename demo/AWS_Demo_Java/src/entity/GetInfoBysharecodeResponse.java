package entity;

public class GetInfoBysharecodeResponse 
{
	/* all fields */
	private String _status = null;	
	private String _entryid = null;
	private String _entrytype = null;
	private String _isgroupaware = null;
	private String _expiretime = null;
	private String _owner = null;

	/* default constructor */
	public GetInfoBysharecodeResponse()
	{		
	}

	/* all fields constructor */
	public GetInfoBysharecodeResponse(String status, String entryid, String entrytype, String isgroupaware, String expiretime, String owner)
	{
		this._status = status;
		this._entryid = entryid;
		this._entrytype = entrytype;
		this._isgroupaware = isgroupaware;
		this._expiretime = expiretime;
		this._owner = owner;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setEntryID(String _entryid) {
		this._entryid = _entryid;
	}

	public void setEntryType(String entrytype) {
		this._entrytype = entrytype;
	}

	public void setIsGroupAware(String isgroupaware) {
		this._isgroupaware = isgroupaware;
	}
	
	public void setExpireTime(String expiretime) {
		this._expiretime = expiretime;
	}
	
	public void setOwner (String owner) {
		this._owner = owner;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getEntryID() {
		return _entryid;
	}

	public String getEntryType() {
		return _entrytype;
	}

	public String getIsGroupAware() {
		return _isgroupaware;
	}
	
	public String getExpireTime() {
		return _expiretime;
	}
	
	public String getOwner() {
		return _owner;
	}

	/* toString */
	public String toString() 
	{			
		StringBuilder msg = new StringBuilder("GetInfoBysharecodeResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" entryid:").append(_entryid).append("\n");
		msg.append(" entrytype:").append(_entrytype).append("\n");
		msg.append(" isgroupaware:").append(_isgroupaware).append("\n");
		msg.append(" expiretime:").append(_expiretime).append("\n");
		msg.append(" owner:").append(_owner).append("\n");
		return msg.toString();	
	}
}

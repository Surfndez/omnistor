package entity;

public class PropfindResponse 
{
	/* all fields */
	private String _status = null;
	private String _isencrypted = null;
	private String _size = null;
	private String _scrip = null;
	private String _type = null;
	private String _id = null;
	private String _attribute = null;	

	/* default constructor */
	public PropfindResponse()
	{		
	}

	/* all fields constructor */
	public PropfindResponse(String status, String isencrypted, String size, String scrip, String type, String id, String attribute)
	{
		this._status = status;
		this._isencrypted = isencrypted;
		this._size = size;
		this._scrip = scrip;
		this._type = type;
		this._id = id;
		this._attribute = attribute;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setIsencrypted(String isencrypted) {
		this._isencrypted = isencrypted;
	}

	public void setSize(String size) {
		this._size = size;
	}

	public void setScrip(String scrip) {
		this._scrip = scrip;
	}

	public void setType(String type) {
		this._type = type;
	}

	public void setId(String id) {
		this._id = id;
	}

	public void setAttribute(String attribute) {
		this._attribute = attribute;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getIsencrypted() {
		return _isencrypted;
	}

	public String getSize() {
		return _size;
	}

	public String getScrip() {
		return _scrip;
	}

	public String getType() {
		return _type;
	}

	public String getId() {
		return _id;
	}

	public String getAttribute() {
		return _attribute;
	}

	/* toString */
	public String toString() 
	{		
		StringBuilder msg = new StringBuilder("PropfindResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" isencrypted:").append(_isencrypted).append("\n");
		msg.append(" size:").append(_size).append("\n");
		msg.append(" scrip:").append(_scrip).append("\n");
		msg.append(" type:").append(_type).append("\n");
		msg.append(" id:").append(_id).append("\n");
		msg.append(" attribute:").append(_attribute).append("\n");
		return msg.toString();
	}
}

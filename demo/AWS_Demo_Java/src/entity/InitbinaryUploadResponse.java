package entity;

public class InitbinaryUploadResponse 
{
	/* all fields */
	private String _status = null;
	private String _transid = null;
	private long _offset ;
	private String _latestchecksum = null;
	private String _fileid = null;

	/* default constructor */
	public InitbinaryUploadResponse()
	{		
	}

	/* all fields constructor */
	public InitbinaryUploadResponse(String status, String transid, long offset, String latestchecksum, String fileid)
	{
		this._status = status;
		this._transid = transid;
		this._offset = offset;
		this._latestchecksum = latestchecksum;
		this._fileid = fileid;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setTransid(String _transid) {
		this._transid = _transid;
	}

	public void setOffset(long _offset) {
		this._offset = _offset;
	}

	public void setLatestchecksum(String _latestchecksum) {
		this._latestchecksum = _latestchecksum;
	}

	public void setFileid(String _fileid) {
		this._fileid = _fileid;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getTransid() {
		return _transid;
	}

	public long getOffset() {
		return _offset;
	}

	public String getLatestchecksum() {
		return _latestchecksum;
	}

	public String getFileid() {
		return _fileid;
	}

	/* toString */
	public String toString() 
	{		
		StringBuilder msg = new StringBuilder("InitbinaryUploadResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" transid").append(_transid).append("\n");
		msg.append(" offset:").append(_offset).append("\n");
		msg.append(" latestchecksum:").append(_latestchecksum).append("\n");
		msg.append(" fileid:").append(_fileid).append("\n");
		return msg.toString();
	}
}

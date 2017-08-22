package net.yostore.aws.api.entity;

public class GetVideoSnapshotRequest
{
	private long 	_fileId;
	private boolean _preview;
	
	public GetVideoSnapshotRequest() {}
	public GetVideoSnapshotRequest(long _fileId, boolean _preview)
	{
		super();
		this._fileId = _fileId;
		this._preview = _preview;
	}
	public long getFileId()
	{
		return _fileId;
	}
	public void setFileId(long _fileId)
	{
		this._fileId = _fileId;
	}
	public boolean isPreview()
	{
		return _preview;
	}
	public void isPreview(boolean _preview)
	{
		this._preview = _preview;
	}
	public String toString()
	{
		StringBuilder msg = new StringBuilder();
		msg.append("FileId:").append(_fileId)
		   .append(", Preview:").append(_preview)
		   ;
		
		return msg.toString();
	}
}

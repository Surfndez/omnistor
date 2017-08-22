package net.yostore.aws.api.entity;

public class GetFullTextCompanionRequest
{
	private long 	_fileId;
	private int  	_kind;
	private boolean _preview;
	
	public GetFullTextCompanionRequest() {}	
	public GetFullTextCompanionRequest(long _fileId, int _kind)
	{
		this(_fileId, _kind, false);
	}
	public GetFullTextCompanionRequest(long _fileId, int _kind, boolean _preview)
	{
		super();
		this._fileId = _fileId;
		this._kind = _kind;
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
	public int getKind()
	{
		return _kind;
	}
	public void setKind(int _kind)
	{
		this._kind = _kind;
	}
	public boolean isPreview()
	{
		return _preview;
	}
	public void isPreview(boolean _preview)
	{
		this._preview = _preview;
	}
}

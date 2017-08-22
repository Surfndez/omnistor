package net.yostore.aws.api.entity;

public class GetResizedPhotoRequest
{
	private long 	_fileId;
	private int 	_sizeType;
	private boolean _preview;
	
	public GetResizedPhotoRequest() {}
	public GetResizedPhotoRequest(long _fileId, int _sizeType, boolean _preview)
	{
		super();
		this._fileId   = _fileId;
		this._sizeType = _sizeType;
		this._preview  = _preview;
	}
	public long getFileId()
	{
		return _fileId;
	}
	public void setFileId(long _fileId)
	{
		this._fileId = _fileId;
	}
	public int getSizeType()
	{
		return _sizeType;
	}
	public void setSizeType(int _sizeType)
	{
		this._sizeType = _sizeType;
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
		msg.append("Photo FileId:").append(_fileId)
		   .append(", Size Type:").append(_sizeType)
		   .append(", Preview:").append(_preview)
		   ;
		
		return msg.toString();
	}
}

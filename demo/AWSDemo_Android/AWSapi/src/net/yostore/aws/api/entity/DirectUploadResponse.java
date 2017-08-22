package net.yostore.aws.api.entity;


public class DirectUploadResponse extends ApiResponse{

	private String _fileId;
	public String getFileId(){ return this._fileId; }
	public void setFileId(String value){ this._fileId = value; }
	
	private String _fileName;
	public String getRawFileName(){ return this._fileName; }
	public void setRawFileName(String value){ this._fileName = value; }


}// end class 

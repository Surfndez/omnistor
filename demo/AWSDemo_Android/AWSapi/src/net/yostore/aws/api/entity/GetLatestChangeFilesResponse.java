package net.yostore.aws.api.entity;

import java.util.LinkedList;
import java.util.List;

public class GetLatestChangeFilesResponse extends ApiResponse{

	private List<BaseEntry> LastestChangeFileList = new LinkedList<BaseEntry>();
	
	public List<BaseEntry> getLatestChangeFileList()
	{
		return LastestChangeFileList;
	}
	public void setLatestChangeFileList(List<BaseEntry> LastestChangeFileList)
	{
		this.LastestChangeFileList = LastestChangeFileList;
	}

	
}// end class 

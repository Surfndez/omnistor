package net.yostore.aws.api.entity;

import java.util.LinkedList;
import java.util.List;

public class GetLatestUploadsResponse extends ApiResponse{

	private List<BaseEntry> LastestUploadsList = new LinkedList<BaseEntry>();
	
	public List<BaseEntry> getLLastestUploadsList()
	{
		return LastestUploadsList;
	}
	public void setLastestUploadsList(List<BaseEntry> LastestUploadsList)
	{
		this.LastestUploadsList = LastestUploadsList;
	}

	
}// end class 

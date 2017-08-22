package net.yostore.aws.api.helper;

import android.graphics.Bitmap;
import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.GetVideoSnapshotRequest;

public class GetVideoSnapshotHelper
{
	private GetVideoSnapshotRequest request;
	
	public GetVideoSnapshotHelper(long fileId)
	{
		this(fileId, false);
	}
	public GetVideoSnapshotHelper(long fileId, boolean preview)
	{
		request = new GetVideoSnapshotRequest(fileId, preview);
	}
	public Bitmap process(ApiConfig apicfg) throws Exception
	{
		WebRelayAPI wb = new WebRelayAPI(apicfg.webRelay);
		return wb.getVideoSnapshot(request, apicfg);
	}
}

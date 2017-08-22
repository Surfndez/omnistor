package net.yostore.aws.api.helper;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.GetResizedPhotoRequest;

import android.graphics.Bitmap;

public class GetResizedPhotoHelper
{
	private GetResizedPhotoRequest request;
	
	public GetResizedPhotoHelper(long fileId, int st)
	{
		this(fileId, st, false);
	}
	public GetResizedPhotoHelper(long fileId, int st, boolean preview)	
	{
		request = new GetResizedPhotoRequest(fileId, st, preview);
	}
	public Bitmap process(ApiConfig apicfg) throws Exception
	{
		WebRelayAPI wb = new WebRelayAPI(apicfg.webRelay);
		return wb.getResizedPhoto(request, apicfg);
	}
}

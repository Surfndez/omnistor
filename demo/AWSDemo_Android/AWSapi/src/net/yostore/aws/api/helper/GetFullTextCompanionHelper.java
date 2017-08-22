package net.yostore.aws.api.helper;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.GetFullTextCompanionRequest;

public class GetFullTextCompanionHelper
{
	private GetFullTextCompanionRequest request;

	public GetFullTextCompanionHelper(long fileId, int kind)
	{
		this(fileId, kind, false);
	}
	public GetFullTextCompanionHelper(long fileId, int kind, boolean preview)
	{
		request = new GetFullTextCompanionRequest(fileId, kind, preview);
	}
	public byte[] process(ApiConfig apicfg) throws Exception
	{
		WebRelayAPI wb = new WebRelayAPI(apicfg.webRelay);
		return wb.getFullTextCompanion(request, apicfg);
	}
}

package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.SetAdvancedSharecodeRequest;
import net.yostore.aws.api.helper.BaseHelper;

import org.xml.sax.SAXException;

import android.util.Log;

public class SetAdvancedSharecodeHelper extends BaseHelper {

	private String entrytype;
	private String entryid;
	private String clearshare = null;
	private String isgroupaware = null;
	private String folderquota = null;
	private String validityduration = null;

	public SetAdvancedSharecodeHelper(String entrytype, String entryid) {
		this.entrytype = entrytype;
		this.entryid = entryid;
	}

	public SetAdvancedSharecodeHelper(String entrytype, String entryid,
			String clearshare) {
		this.entrytype = entrytype;
		this.entryid = entryid;
		this.clearshare = clearshare;
		Log.d("SetAdvancedSharecodeHelper", "entrytype:" + entrytype + " clearshare:" + clearshare);
	}

	public SetAdvancedSharecodeHelper(String entrytype, String entryid,
			String validityduration, String isgroupaware) {
		this.entrytype = entrytype;
		this.entryid = entryid;
		this.validityduration = validityduration;
		this.isgroupaware = isgroupaware;

		Log.d(TAG, "entrytype:" + entrytype + " validityduration:"
				+ validityduration + " isgroupaware:" + isgroupaware);
	}

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException,
			ProtocolException, IOException, SAXException {

		SetAdvancedSharecodeRequest request = new SetAdvancedSharecodeRequest(
				apicfg.token, apicfg.userid, this.entrytype, this.entryid,
				null, validityduration, clearshare, isgroupaware, folderquota);

		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.getSetAdvancedSharecode(request);

	}
}

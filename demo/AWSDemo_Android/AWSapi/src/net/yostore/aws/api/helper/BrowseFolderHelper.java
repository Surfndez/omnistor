package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.InfoRelayApi;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.BrowseFolderRequest;
import net.yostore.aws.api.helper.BaseHelper;

import org.xml.sax.SAXException;

import android.util.Log;

public class BrowseFolderHelper extends BaseHelper {

	private String parent;
	private int sort = 1;
	private int sortByDesc = 0;
	private int pagesize = 500;
	private int page = -1;
	private String TAG = "BrowseFolderHelper";

	public BrowseFolderHelper(String parent, int sort, int sortByDesc,
			int pagesize, int page) {
		this.parent = parent;
		this.sort = sort;
		this.sortByDesc = sortByDesc;
		this.pagesize = pagesize;
		this.page = page;

	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSortByDesc() {
		return sortByDesc;
	}

	public void setSortByDesc(int sortByDesc) {
		this.sortByDesc = sortByDesc;
	}

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException,
			ProtocolException, IOException, SAXException {
		Log.d(TAG, "this.parent" + this.parent);
		BrowseFolderRequest request = new BrowseFolderRequest(apicfg.userid,
				apicfg.token, this.parent,
				this.sort, this.sortByDesc);

		if (page > 0 && pagesize > 0) {
			request.setPageno(page);
			request.setPagesize(pagesize);
		}

		InfoRelayApi ir = new InfoRelayApi(apicfg.infoRelay);
		return ir.getBrowseFolder(request);

	}

}

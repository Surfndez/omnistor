package net.yostore.aws.api;

import java.io.IOException;

import net.yostore.aws.api.entity.BrowseFolderRequest;
import net.yostore.aws.api.entity.BrowseFolderResponse;
import net.yostore.aws.api.entity.FileRemoveRequest;
import net.yostore.aws.api.entity.FileRemoveResponse;
import net.yostore.aws.api.entity.FileRenameRequest;
import net.yostore.aws.api.entity.FileRenameResponse;
import net.yostore.aws.api.entity.FindPropfindRequest;
import net.yostore.aws.api.entity.FindPropfindResponse;
import net.yostore.aws.api.entity.FolderCreateRequest;
import net.yostore.aws.api.entity.FolderCreateResponse;
import net.yostore.aws.api.entity.FolderRemoveRequest;
import net.yostore.aws.api.entity.FolderRemoveResponse;
import net.yostore.aws.api.entity.FolderRenameRequest;
import net.yostore.aws.api.entity.FolderRenameResponse;
import net.yostore.aws.api.entity.GetLatestChangeFilesRequest;
import net.yostore.aws.api.entity.GetLatestChangeFilesResponse;
import net.yostore.aws.api.entity.GetLatestUploadsRequest;
import net.yostore.aws.api.entity.GetLatestUploadsResponse;
import net.yostore.aws.api.entity.GetMySyncFolderRequest;
import net.yostore.aws.api.entity.GetMySyncFolderResponse;
import net.yostore.aws.api.entity.GetShareCodeRequest;
import net.yostore.aws.api.entity.GetShareCodeResponse;
import net.yostore.aws.api.entity.SetAdvancedSharecodeRequest;
import net.yostore.aws.api.entity.SetAdvancedSharecodeResponse;
import net.yostore.aws.api.entity.UpdateFolderAttributeRequest;
import net.yostore.aws.api.entity.UpdateFolderAttributeResponse;
import net.yostore.aws.api.sax.BrowseFolder;
import net.yostore.aws.api.sax.FileRemove;
import net.yostore.aws.api.sax.FileRename;
import net.yostore.aws.api.sax.FindPropfind;
import net.yostore.aws.api.sax.FolderCreate;
import net.yostore.aws.api.sax.FolderRemove;
import net.yostore.aws.api.sax.FolderRename;
import net.yostore.aws.api.sax.GetShareCode;
import net.yostore.aws.api.sax.LatestChangeFiles;
import net.yostore.aws.api.sax.LatestUploads;
import net.yostore.aws.api.sax.SetAdvancedSharecode;
import net.yostore.aws.api.sax.UpdateFolderAttribute;

import org.xml.sax.SAXException;

import ostore.aws.api.sax.GetMySyncFolder;

import android.util.Log;



public class InfoRelayApi extends BaseApi {
	private static final String TAG = "InfoRelayApi";

	/**
	 * 
	 * @param Url
	 * @param proxy
	 */
	public InfoRelayApi(String Url) {
		super(Url);
	}
    

	public FolderCreateResponse folderCreate(FolderCreateRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (FolderCreateResponse)super.getResponse("/folder/create/", params, new FolderCreate());
	}
	
	public UpdateFolderAttributeResponse updateFolderAttribute(UpdateFolderAttributeRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (UpdateFolderAttributeResponse)super.getResponse("/folder/updateattribute/", params, new UpdateFolderAttribute());
	}
	
	public FindPropfindResponse findPropfind(FindPropfindRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (FindPropfindResponse)super.getResponse("/find/propfind/", params, new FindPropfind());
	}
	
	public GetShareCodeResponse getShareCode(GetShareCodeRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (GetShareCodeResponse)super.getResponse("/fsentry/getsharecode/", params, new GetShareCode());
	}
	
	public FolderRemoveResponse folderRemove(FolderRemoveRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (FolderRemoveResponse)super.getResponse("/folder/remove/", params, new FolderRemove());
	}
	
	public FolderRenameResponse folderRename(FolderRenameRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (FolderRenameResponse)super.getResponse("/folder/rename/", params, new FolderRename());
	}

	public FileRemoveResponse fileRemove(FileRemoveRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (FileRemoveResponse)super.getResponse("/file/remove/", params, new FileRemove());
	}
	
	public FileRenameResponse fileRename(FileRenameRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (FileRenameResponse)super.getResponse("/file/rename/", params, new FileRename());
	}
	
	public GetLatestChangeFilesResponse getLatestChangeFiles(GetLatestChangeFilesRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (GetLatestChangeFilesResponse)super.getResponse("/file/getlatestchangefiles/", params, new LatestChangeFiles());
	}
	
	public GetLatestUploadsResponse getLatestUploads(GetLatestUploadsRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (GetLatestUploadsResponse)super.getResponse("/file/getlatestuploads/", params, new LatestUploads());
	}
	
	public GetMySyncFolderResponse getMySyncFolder(GetMySyncFolderRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (GetMySyncFolderResponse)super.getResponse("/folder/getmysyncfolder/", params, new GetMySyncFolder());
	}
	
	public SetAdvancedSharecodeResponse getSetAdvancedSharecode(SetAdvancedSharecodeRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, params);
		return (SetAdvancedSharecodeResponse)super.getResponse("/fsentry/setadvancedsharecode/", params, new SetAdvancedSharecode());
	}
	
	public BrowseFolderResponse getBrowseFolder(BrowseFolderRequest request) throws IOException, SAXException{
		String params = request.toXml();
		Log.d(TAG, "getBrowseFolder"+params);
		return (BrowseFolderResponse)super.getResponse("/inforelay/browsefolder/", params, new BrowseFolder());
	}
}

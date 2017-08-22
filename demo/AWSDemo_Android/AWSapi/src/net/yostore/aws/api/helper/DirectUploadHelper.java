package net.yostore.aws.api.helper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.DirectUploadRequest;

import org.xml.sax.SAXException;

import android.util.Log;


public class DirectUploadHelper extends BaseHelper{

	private String pfid;
	private File file;
	private long fileid = -999l;
	private String fileName;
	private String attribute;
	private byte[] data ;

	public DirectUploadHelper(String pfid, File file){
		this.pfid = pfid;
		this.file = file;
	}
	
	public DirectUploadHelper(String pfid, File file, String attribute, String fileName){
		this.pfid = pfid;
		this.file = file;
		this.fileName = fileName;
		this.attribute = attribute;
	}
	
	public DirectUploadHelper(String pfid, String fileName, long fileid, String attribute, byte[] data){
		this.pfid = pfid;
		this.fileName = fileName;
		this.fileid = fileid;
		this.attribute = attribute;
		this.data = data;
	}
	
	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException,IOException, SAXException {
		
		WebRelayAPI wr = new WebRelayAPI(apicfg.webRelay);
		DirectUploadRequest request = new DirectUploadRequest();
		request.setParentid(String.valueOf(pfid));
		request.setAttribute(attribute);
		if(fileid>0)
			request.setFileid(fileid);
		if(file==null && (fileName!=null || !fileName.equals(""))){
			request.setFileName(fileName);
			request.setData(data);
			Log.d(TAG, "Parent:" + pfid + "; FileName:" + fileName);
			return wr.directDataUpload(request, apicfg); 
		}else if(file!=null && (fileName!=null && !fileName.equals("")) && !file.getName().equals(fileName)){
			request.setFile(file);
			request.setFileName(fileName);
			Log.d(TAG, "Parent:" + pfid + "; FileName:" + file.getName());
			return wr.directFileUpload(request, apicfg); 
		}else/* if(file!=null)*/{
			request.setFile(file);
			Log.d(TAG, "Parent:" + pfid + "; FileName:" + file.getName());
			return wr.directFileUpload(request, apicfg); 
		}
	}
}

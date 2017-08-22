package net.yostore.aws.api.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.WebRelayAPI;
import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.InitBinaryUploadRequest;
import net.yostore.utility.Base64;

import org.xml.sax.SAXException;

import android.util.Log;

public class InitBinaryUploadHelper extends BaseHelper {
	private String _token;
	private String _name;
	private long _parent;
	private String _attribute;
	private String _checksum;
	private long _fileSize;
	private String _transId;
	private Long _fileId;
	private Long _syncFolderId;
	private String TAG = "InitBinaryUploadHelper";

	public InitBinaryUploadHelper(String token, long parent, String name,
			String attribute, long fileSize, String checksum) {

		Log.d(TAG, "non attribute");

		this._token = token;
		this._parent = parent;

		try {
			this._name = URLEncoder.encode(Base64.encodeToBase64String(name),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			this._attribute = URLEncoder.encode(attribute, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this._fileSize = fileSize;
		this._checksum = checksum;

	}

	public InitBinaryUploadHelper(String token, long parent, String attribute,
			String name, long fileSize, String checksum, String transactionId) {
		// Log.d(TAG, "attribute");
		this(token, parent, name, attribute, fileSize, checksum);
		this._transId = transactionId;
	}

	public void setToken(String _token) {
		this._token = _token;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public void setParent(long _parent) {
		this._parent = _parent;
	}

	public void setAttribute(String _attribute) {
		this._attribute = _attribute;
	}

	public void setChecksum(String _checksum) {
		this._checksum = _checksum;
	}

	public void setFileSize(long _fileSize) {
		this._fileSize = _fileSize;
	}

	public void setTransactionId(String _transId) {
		this._transId = _transId;
	}

	public void setFileId(Long _fileId) {
		this._fileId = _fileId;
	}

	public void setSyncFolderId(Long _syncFolderId) {
		this._syncFolderId = _syncFolderId;
	}

	@Override
	protected ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException,
			ProtocolException, IOException, SAXException {

		Log.d(TAG, "doApi");
		InitBinaryUploadRequest request = new InitBinaryUploadRequest();
		request.setToken(_token);
		request.setParent(_parent);
		request.setAttribute(_attribute);
		request.setFileSize(_fileSize);
		request.setChecksum(_checksum);
		request.setTransactionId(_transId);
		request.setName(_name);
		request.setFileId(_fileId);
		request.setSyncFolderId(_syncFolderId);
		request.setSid(ApiCookies.sid);

		WebRelayAPI wr = new WebRelayAPI(apicfg.webRelay);
		// try
		// {
		try {
			return wr.initBinaryUpload(request, apicfg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// }
		// catch ( Exception e )
		// {
		// Log.e(TAG, e.getMessage(), e);
		//
		// throw new IOException(e.getMessage(), e);
		// }

	}

}

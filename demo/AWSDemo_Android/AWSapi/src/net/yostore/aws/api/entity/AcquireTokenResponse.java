package net.yostore.aws.api.entity;

import android.util.Log;

public class AcquireTokenResponse extends ApiResponse{
	private static final String TAG = "AcquireTokenResponse";
	boolean isFutureList = false;
	
	private PackageInfo packageinfo;
	public PackageInfo getPackageinfo() {return packageinfo;}
	public void setPackageinfo(PackageInfo packageinfo) {this.packageinfo = packageinfo;}
	
	private String _credential;
	public String getCredential() { return this._credential; }
	public void setCredential(String value) { this._credential = value; }
	
	private String _credentialState;
	public String getCredentialState() { return this._credentialState; }
	public void setCredentialState(String value) { this._credentialState = value; }

	private String _token;
	public String getToken(){ return this._token; }
	public void setToken(String value){ this._token = value; }

	private String _inforelay;
	public String getInforelay(){ return this._inforelay; }
	public void setInforelay(String value){ 
		this._inforelay = value; 
		Log.d(TAG, "setInforelay=" + value);
	}

	private String _webrelay;
	public String getWebrelay(){ return this._webrelay; }
	public void setWebrelay(String value){ 
		this._webrelay = value; 
		Log.d(TAG, "setWebrelay=" + value);
	}

	private String _time;
	public String getTime(){ return this._time; }
	public void setTime(String value){ this._time = value; }

	private String auxpasswordurl;
	public String getAuxpasswordurl()
	{
		return auxpasswordurl;
	}
	public void setAuxpasswordurl(String auxpasswordurl)
	{
		this.auxpasswordurl = auxpasswordurl;
	}
	
	public String toString()
	{
		StringBuilder msg = new StringBuilder();
		
		msg.append("Status:").append(this._status).append("\n")
		   .append("Token:").append(this._token).append("\n")
		   .append("InfoRelay:").append(this._inforelay).append("\n")
		   .append("WebRelay:").append(this._webrelay).append("\n")
		   .append("PackageDisplay:").append(this.packageinfo != null ? this.packageinfo.getDisplay() : null)
		   ;
		
		return msg.toString();
	}

}// end class 

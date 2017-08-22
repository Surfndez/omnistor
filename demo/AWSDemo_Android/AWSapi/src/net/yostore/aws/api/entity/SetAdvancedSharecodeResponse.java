package net.yostore.aws.api.entity;

public class SetAdvancedSharecodeResponse extends ApiResponse {

	private String _scrip;

	public String getScrip() {
		return this._scrip;
	}

	public void setScrip(String value) {
		this._scrip = value;
	}
	//
	private String sharecode;

	public String getSharecode() {
		return this.sharecode;
	}

	public void setSharecode(String value) {
		this.sharecode = value;
	}
	//
	private String ispasswordneeded;

	public String getIspasswordneeded() {
		return this.ispasswordneeded;
	}

	public void setIspasswordneeded(String value) {
		this.ispasswordneeded = value;
	}
	//
	private String isgroupaware;

	public String getIsgroupaware() {
		return this.isgroupaware;
	}

	public void setIsgroupaware(String value) {
		this.isgroupaware = value;
	}
	//
	private String expiredtime;

	public String getExpiredtime() {
		return this.expiredtime;
	}

	public void setExpiredtime(String value) {
		this.expiredtime = value;
	}
	//
	private String folderquota;

	public String getFolderquota() {
		return this.folderquota;
	}

	public void setFolderquota(String value) {
		this.folderquota = value;
	}
	
}// end class 
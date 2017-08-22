package net.yostore.aws.api.entity;

import java.util.LinkedList;
import java.util.List;

public class GetVideoconvertprogressResponse extends ApiResponse {

	private List<VideoInfo> video = new LinkedList<VideoInfo>();

	public List<VideoInfo> getVideoList() {
		return video;
	}

	public void setVideoLis(List<VideoInfo> fileList) {
		this.video = fileList;
	}

	private String _progressstate;

	public String getProgressstate() {
		return this._progressstate;
	}

	public void setProgressstate(String value) {
		this._progressstate = value;
	}

	private String _abstractstate;

	public String getAbstractstate() {
		return this._abstractstate;
	}

	public void setAbstractstate(String value) {
		this._abstractstate = value;
	}

}// end class

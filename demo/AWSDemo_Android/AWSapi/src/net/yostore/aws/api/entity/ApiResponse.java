package net.yostore.aws.api.entity;

public abstract class ApiResponse {
	protected int _status;
	public int getStatus(){ return this._status; }
	public void setStatus(int value){ this._status = value; }
}

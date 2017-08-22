package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class CIDCountException extends APIException {
	public CIDCountException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_CID_CNT;
	  }
}

package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class AAAException extends APIException {
	public AAAException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_AAA;
	  }
}

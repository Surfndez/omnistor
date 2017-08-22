package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class ExceedRetryCountException extends APIException {
	public ExceedRetryCountException(String message)
	  {
	    super(message);
	    this.status = APIException.EXC_RTY;
	  }
}

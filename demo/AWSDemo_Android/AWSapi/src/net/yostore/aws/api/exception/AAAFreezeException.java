package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class AAAFreezeException extends APIException {
	public AAAFreezeException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_FRZ;
	  }
}

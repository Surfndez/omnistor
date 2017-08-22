package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class NoMoreWSSpaceException extends APIException {
	public NoMoreWSSpaceException(String message)
	  {
	    super(message);
	    this.status = APIException.EXC_XSP;
	  }
}

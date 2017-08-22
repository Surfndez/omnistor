package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class RegisterException extends APIException {
	public RegisterException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_REG;
	  }
}

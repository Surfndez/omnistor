package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class OTPAuthException extends APIException {
	public OTPAuthException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_OTP_AUTH;
	  }
}

package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class OTPLockException extends APIException {
	public OTPLockException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_OTP_LOCK;
	  }
}

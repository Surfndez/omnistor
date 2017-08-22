package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class OTPCidException extends APIException {
	public OTPCidException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_OTP_CID;
	  }
}

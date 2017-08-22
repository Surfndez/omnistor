package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class CaptchaException extends APIException {
	public CaptchaException(String message, String captchaUri)
	  {
	    super(message);
		this.status = APIException.EXC_CAPTCHA;
		this.captchaUri = captchaUri;
	  }
}

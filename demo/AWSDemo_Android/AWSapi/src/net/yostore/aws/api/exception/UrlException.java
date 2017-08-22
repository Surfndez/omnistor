package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class UrlException extends APIException {
	public UrlException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_AAA;
	  }
}

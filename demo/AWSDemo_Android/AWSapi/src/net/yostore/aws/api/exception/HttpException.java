package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class HttpException extends APIException {
	public HttpException(String message)
	  {
	    super(message);
	  }
}

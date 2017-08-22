package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class FileNotExistException extends APIException {
	public FileNotExistException(String message)
	  {
	    super(message);
	    this.status = APIException.EXC_FSX;
	  }
}

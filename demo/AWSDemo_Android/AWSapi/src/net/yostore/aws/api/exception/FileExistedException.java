package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class FileExistedException extends APIException {
	public FileExistedException(String message)
	  {
	    super(message);
		this.status = APIException.EXC_FEX;
	  }
}

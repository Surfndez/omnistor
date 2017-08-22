package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public class BackupPCNotExistException extends APIException {
	public BackupPCNotExistException(String message)
	  {
	    super(message);
	    this.status = APIException.EXC_FNX;
	  }
}

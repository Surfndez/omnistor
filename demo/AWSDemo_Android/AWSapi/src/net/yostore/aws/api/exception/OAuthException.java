package net.yostore.aws.api.exception;

public class OAuthException extends APIException
{

	public OAuthException(String message)
	{
		super(message);
		this.status = APIException.EXC_OAUTH;
	}

}

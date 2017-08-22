package net.yostore.aws.api.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.AcquireTokenResponse;
import net.yostore.aws.api.exception.AAAException;
import net.yostore.aws.api.exception.AAAFreezeException;
import net.yostore.aws.api.exception.APIException;
import net.yostore.aws.api.exception.BackupPCNotExistException;
import net.yostore.aws.api.exception.CIDCountException;
import net.yostore.aws.api.exception.CaptchaException;
import net.yostore.aws.api.exception.ExceedRetryCountException;
import net.yostore.aws.api.exception.FileExistedException;
import net.yostore.aws.api.exception.HttpException;
import net.yostore.aws.api.exception.NoMoreWSSpaceException;
import net.yostore.aws.api.exception.OAuthException;
import net.yostore.aws.api.exception.OTPAuthException;
import net.yostore.aws.api.exception.OTPCidException;
import net.yostore.aws.api.exception.OTPLockException;
import net.yostore.aws.api.exception.RegisterException;
import net.yostore.aws.api.exception.SaxException;
import net.yostore.aws.api.exception.UrlException;

import org.xml.sax.SAXException;

import android.util.Log;

public abstract class BaseHelper {
	private static final int RETRY_CNT = 3;
	protected static final String TAG = "BaseHelper";

	public ApiResponse process(ApiConfig apicfg) throws APIException{
//	AAAException, NoMoreWSSpaceException, BackupPCNotExistException, UrlException, AAAFreezeException, HttpException, SaxException{
		StringBuilder msg = new StringBuilder();
		int i=0;
		for (i=0; i<RETRY_CNT; i++)
		{
			msg.delete(0, msg.length());
			try 
			{
				ApiResponse response = doApi(apicfg);
				
				msg.append(this.getClass().getSimpleName())
				   .append("(").append(response.getStatus()).append(")");
				   
				switch(response.getStatus()){
				case APIException.GENERAL_SUCC:
					return response;
				case APIException.EXC_AAA:
					throw new AAAException(msg.toString());
				case APIException.EXC_OAUTH:
					throw new OAuthException(msg.toString());
				case APIException.EXC_FNX:
					throw new BackupPCNotExistException(msg.toString());
				case APIException.EXC_FSX:
					throw new FileExistedException(msg.toString());
				case APIException.EXC_XSP:
					throw new NoMoreWSSpaceException(msg.toString());
				case APIException.EXC_FRZ:
					throw new AAAFreezeException(msg.toString());
				case APIException.EXC_REG:
					throw new RegisterException(msg.toString());
				case APIException.EXC_OTP_AUTH:
					throw new OTPAuthException(msg.toString());
//					if(response instanceof RequestTokenResponse)
//						throw new CaptchaException(response.getStatus(), "http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2008/08/27/captcha460.jpg");	
//					else
//						throw new CaptchaException(response.getStatus(), null);	
				case APIException.EXC_OTP_LOCK:
					throw new OTPLockException(msg.toString());
				case APIException.EXC_CID_CNT:
					throw new CIDCountException(msg.toString());
				case APIException.EXC_OTP_CID:
					throw new OTPCidException(msg.toString());
				case APIException.EXC_CAPTCHA:
					if(response instanceof AcquireTokenResponse)
						throw new CaptchaException(msg.toString(), ((AcquireTokenResponse)response).getAuxpasswordurl());	
					else
						throw new CaptchaException(msg.toString(), null);	
				default:
					//general exception, retry
					break;
				}
			}
			  catch (MalformedURLException e){
				Log.w(TAG, e.getMessage());
				msg.append(this.getClass().getSimpleName())
				   .append(", Error:").append(e.getMessage());
				throw new UrlException(msg.toString());
			} catch (ProtocolException e){
				Log.w(TAG, e.getMessage());
				msg.append(this.getClass().getSimpleName())
				   .append(", Error:").append(e.getMessage());
				throw new HttpException(msg.toString());
			} catch (IOException e) {
				Log.w(TAG, e.getMessage());
				msg.append(this.getClass().getSimpleName())
				   .append(", Error:").append(e.getMessage());
				throw new HttpException(msg.toString());
			} catch (SAXException e) {
				Log.e(TAG, e.getMessage());
				msg.append(this.getClass().getSimpleName())
				   .append(", Error:").append(e.getMessage());
				throw new SaxException(msg.toString());
			}
		}
		if (i>=RETRY_CNT) 
			throw new ExceedRetryCountException(this.getClass().getSimpleName()+"...many");
		return null;
	}
	
	protected abstract ApiResponse doApi(ApiConfig apicfg) throws MalformedURLException, ProtocolException, IOException, SAXException;
}

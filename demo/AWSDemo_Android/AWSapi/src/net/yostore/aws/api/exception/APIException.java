package net.yostore.aws.api.exception;

@SuppressWarnings("serial")
public abstract class APIException extends Exception 
{
	public static final int GENERAL_SUCC = 0;
	public static final int EXC_AAA 	 = 2;  	//aaa fail
	public static final int EXC_OAUTH 	 = 5;	//OAuth fail	
	public static final int EXC_RTY 	 = 30;  //retry out
	public static final int EXC_FEX 	 = 214; //same name
	public static final int EXC_FNX 	 = 218;	//folder not exist
	public static final int EXC_FSX 	 = 219; //file not exist
	public static final int EXC_XSP 	 = 224; //no space
	public static final int EXC_FRZ		 = 226;	//Frozen State
	public static final int EXC_REG 	 = 234; //duplicate register
	public static final int EXC_OTP_AUTH = 504; //OTP�{�ҥ��ѡC�]�N�O��J��userid/password/OTP�����T�λݨϥ�OTP�{�ҫo����JOTP
	public static final int EXC_OTP_LOCK = 505; //OTP�A�Ȫ�Credential ID�B��LOCKED���A
	public static final int EXC_CID_CNT  = 506; //�Τ�ҥΪ�Credential ID�ƶq�W�L�t�έ���(eCareme Server�u���\�Τ�֦��@��Credential ID)
	public static final int EXC_OTP_CID  = 507; //���X�k��Credential ID
	public static final int EXC_CAPTCHA  = 508; //CAPTCHA�{�ҥ���
	public static final int GENERAL_ERR	 = 999;
	
	public int status = GENERAL_ERR;
	public String captchaUri;
	
	public APIException(String message)
	{
	    super(message);
	}
}

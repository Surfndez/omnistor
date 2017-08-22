package asuswebstorage.user.info;

import sax.EncryptBean;

public class userInfo {
	public static String sid = "12345678";// It has been provided from ASUS WebStorage 
	public static String userid = "test.@mail.com";// The user's Email address		
	public static String pwd = EncryptBean.getMD5("testpass".toLowerCase());// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	public static String progKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";// It has been provided from ASUS WebStorage

}

package net.yostore.aws.api.entity;


public class ApiCookies {
//	private static final String COMM = ";";
	
	public static String c_ClientType = "0";
	public static String v_ClientVersion = "1.0.0";
	public static String EEE_MANU_Maunfactory = "";
	public static String EEE_PROD_ProductModal = "";
	public static String macaddr = "";
	public static String uuid = "";
	private final String OS_VER = android.os.Build.VERSION.RELEASE;
	public static String sid="123456";
	public static String progKey = "";
	public static String language = "zh_TW";
	private String x_skunum ;

	
	public String getC_ClientType()
	{
		return c_ClientType;
	}


	public String getV_ClientVersion()
	{
		return v_ClientVersion;
	}


	public String getEEE_MANU_Maunfactory()
	{
		return EEE_MANU_Maunfactory;
	}


	public String getEEE_PROD_ProductModal()
	{
		return EEE_PROD_ProductModal;
	}


	public String getOS_VER()
	{
		return OS_VER;
	}


	public String getSid()
	{
		return sid;
	}


	public String getX_skunum()
	{
		return x_skunum;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("a=").append("");		//last url, start with 'http://'
		sb.append(";t=").append("");		//last url time (seconds since 1970/1/1 0:0)
		sb.append(";d=").append("");		//last url duration (millisecond)
		sb.append(";l=").append("");		//byte length of request and response
		sb.append(";c=");
		
		return sb.toString();
	}
	
	
	
	
	/*
Cookie=OMNISTORE_VER=1_0;a=
sb1.Append(LastCmdTimeStr.Split(";").GetValue(0))
sb1.Append(";t=")
sb1.Append(LastCmdTimeStr.Split(";").GetValue(1))
sb1.Append(";d=")
sb1.Append(LastCmdTimeStr.Split(";").GetValue(2))
sb1.Append(";l=")
sb1.Append(LastCmdTimeStr.Split(";").GetValue(3))


	 */
}

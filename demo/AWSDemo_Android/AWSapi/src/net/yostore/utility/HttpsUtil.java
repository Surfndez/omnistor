package net.yostore.utility;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil
{
	public static HostnameVerifier getverifyAlHostnameVerifier()
	{
		HostnameVerifier hostnameVerifier = new HostnameVerifier()
		{
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		};
		return hostnameVerifier;
	}

	public static TrustManager[] getTrustAllCertsTrustManager()
	{
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
		{
			public java.security.cert.X509Certificate[] getAcceptedIssuers()

			{
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{}

		}};
		return trustAllCerts;
	}

	public static void inittrustAllCertsSSLSocketFactory()
	{
		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, getTrustAllCertsTrustManager(), new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		}
		catch ( Throwable e )
		{
			e.printStackTrace();
		}
		return;
	}
}

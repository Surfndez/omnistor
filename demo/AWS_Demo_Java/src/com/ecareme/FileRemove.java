package com.ecareme;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import asuswebstorage.user.info.userInfo;
import entity.AcquireTokenResponse;
import entity.FileRemoveResponse;

public class FileRemove 
{	

	static String API = "/file/remove/";// Service api
	
	public static void main(String[] args) throws Exception
	{	
		FileRemove fileRemove = new FileRemove();
		FileRemoveResponse rsp = fileRemove.getResponse();		
		System.out.println(rsp.toString());
	}

	public FileRemoveResponse getResponse() throws Exception
	{
		/* Fetching token from RequestServiceGateway by userid & pwd. Then you will get token, Inforelay and Webrelay domain/IP */
		AcquireToken at = new AcquireToken();
		AcquireTokenResponse acquireTokenResponse = null;
		try {
			acquireTokenResponse = at.getResponse(userInfo.userid,userInfo.pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// The correct AcquireToken response payload's status value must equals "0
		if (acquireTokenResponse == null || !"0".equals(acquireTokenResponse.getStatus())) {
			throw new Exception("Error : You have to check the AcquireToken api response");
		}
		
		String token = acquireTokenResponse.getToken();// Token value
		String server = acquireTokenResponse.getInfoRelay();// Connection server

		String urlstr = server + API;
		URL url = new URL(urlstr);

		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(60 * 1000); 
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(userInfo.sid).append(";");// Cookies have to add the value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must add the cookies in the header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);			
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		} 		
					
		/* Preparing for DocumentBuilderFactory. This class is available at: 
		 * http://download.oracle.com/javase/1.4.2/docs/api/javax/xml/parsers/DocumentBuilderFactory.html
		 * */		
		String root = "remove";// The root element of request payload  	
		
		/* Compose removed files' id (Removed files can have one value) */ 
		String id;
		LinkedList<Long> ids = new LinkedList<Long>();
		ids.add(123498765L);
		ids.add(987651234L);
		id = (ids.toString().replace("[", "").replace("]", "").replace(" ", "")).toString();		
				
		String[] elmName = new String[] { "token", "userid", "id" };// Define each XML tag name
		String[] data = new String[] { token, userInfo.userid, id };// Set each value of tag

		/* To create XML documents. DocumentBuilderFactory can obtain a parser that produces DOM object trees from XML documents */
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement(root);
		document.appendChild(rootElement);
		Element elm;
		int i;
		for (i = 0; i < data.length; i++) {
			elm = document.createElement(elmName[i]);
			elm.appendChild(document.createTextNode(data[i]));
			rootElement.appendChild(elm);
		}
		
		/* Used to process XML from a variety of sources and write the transformation output to server */
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(connection.getOutputStream());
		transformer.transform(source, result);

		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		FileRemoveResponse rsp = new FileRemoveResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if (nodelist.item(j).getNodeName().equals("status")) {
				rsp.setStatus(nodelist.item(j).getTextContent());
			}
		}

		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
		{
			throw new Exception("Error : You have to check FileRemove api response");
		}
		
		System.out.println(" --- FINISH REMOVE FILE --- \n");
		
		return rsp;
	}
}

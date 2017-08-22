package com.ecareme;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import asuswebstorage.user.info.userInfo;

import entity.AcquireTokenResponse;
import entity.GetAccountInfoResponse;
import entity.RequestServiceGatewayResponse;

public class GetAccountInfo
{		
	
	private static String API = "/member/getinfo/";// Service api
		
	public static void main(String[] args) throws Exception{
		GetAccountInfo gt = new GetAccountInfo();
		GetAccountInfoResponse rsp = gt.getResponse(userInfo.userid);		
		System.out.println(rsp.toString());
	}
	
	public GetAccountInfoResponse getResponse(String userid) throws Exception
	{			
		/* Get servicegateway domain/IP from RequestServiceGateway api by userid & pwd */
		RequestServiceGateway requestservicegateway = new RequestServiceGateway();
		RequestServiceGatewayResponse requestservicegatewayresponse = null;
		try {
			requestservicegatewayresponse = requestservicegateway.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		// The correct RequestServiceGateway response payload's status value must equals "0
		if (requestservicegatewayresponse == null || !"0".equals(requestservicegatewayresponse.getStatus())) {
			throw new Exception("Error : You have to check the RequestServiceGateway api response");
		}	
		
		String server = requestservicegatewayresponse.getServicegateway();// Connection server		
		
		/* Fetching token from RequestServiceGateway by userid & pwd. Then you will get token, Inforelay and webrelay domain/IP */
		AcquireToken at = new AcquireToken();
		AcquireTokenResponse acquireTokenResponse = null;
		try {
			acquireTokenResponse = at.getResponse(userid, userInfo.pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// The correct AcquireToken response payload's status value must equals "0
		if (acquireTokenResponse == null || !"0".equals(acquireTokenResponse.getStatus())) {
			throw new Exception("Error : You have to check the AcquireToken api response");
		}
		
		String token = acquireTokenResponse.getToken();// Token value
		
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
		try 
		{
			connection.connect();
		}
		catch (IOException e) 
		{			
			System.err.println("Get Connection Error:" + e.getMessage());
			throw e;
		}

		/* Preparing for DocumentBuilderFactory. This class is available at: 
		 * http://download.oracle.com/javase/1.4.2/docs/api/javax/xml/parsers/DocumentBuilderFactory.html
		 * */		
		String root = "getinfo";// The root element of request payload 
		
		String[] elmName = { "userid", "token" };// Define each XML tag name
		String[] data = { userid, token };// Set each value of tag

		/* To create XML documents. DocumentBuilderFactory can obtain a parser that produces DOM object trees from XML documents */
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try 
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) 
		{			
			System.err.println("ParserConfigurationException Error:" + e.getMessage());
			throw e;
		}
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
		Transformer transformer = null;
		try 
		{
			transformer = transformerFactory.newTransformer();
		} 
		catch (TransformerConfigurationException e) 
		{
			System.err.println("Get TransformerConfiguration Error:" + e.getMessage());
			throw e;
		}
		DOMSource source = new DOMSource(document);
		StreamResult result = null;
		try 
		{
			result = new StreamResult(connection.getOutputStream());
		} 
		catch (IOException e) 
		{			
			System.err.println("Connection getOutputStream Error:" + e.getMessage());
			throw e;
		}		
		try 
		{
			transformer.transform(source, result);
		} 
		catch (TransformerException e) 
		{			
			System.err.println("XML transformer Error:" + e.getMessage());
			throw e;
		}

		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = null;
		try 
		{
			documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		} 
		catch (Exception e) 
		{
			System.err.println("Connection getInputStream Error:" + e.getMessage() + e);
			throw e;
		}
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		GetAccountInfoResponse rsp = new GetAccountInfoResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if (nodelist.item(j).getNodeName().equals("status")) {
				rsp.setStatus(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("account")) {
				rsp.setAccount(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("email")) {
				rsp.setEmail(nodelist.item(j).getTextContent());
			}
		}
		
		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
			System.err.println("GetAccountInfo response status is:" + rsp.getStatus());
		
		return rsp;		
	}
}
package com.ecareme;

import java.io.IOException;
import java.net.URL;
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

import entity.RequestServiceGatewayResponse;

public class RequestServiceGateway {

	static String API = "/member/requestservicegateway/";// Service api

	public static void main(String[] args) throws Exception {
		RequestServiceGateway requestServiceGateway = new RequestServiceGateway();
		RequestServiceGatewayResponse rsp = requestServiceGateway.getResponse();
		System.out.println(rsp.toString());
	}

	public RequestServiceGatewayResponse getResponse() throws Exception {
		String server = "https://asuscloudportal01.asuswebstorage.com";// Connection server
		String urlstr = server + API;
		URL url = new URL(urlstr);

		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setConnectTimeout(60 * 1000);
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(userInfo.sid).append(";");// Cookies have to add the
														// value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must
																	// add the
																	// cookies
																	// in the
																	// header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		}

		/*
		 * Preparing for DocumentBuilderFactory. This class is available at:
		 * http://download.oracle.com/javase/1.4.2/docs/api/javax/xml/parsers/
		 * DocumentBuilderFactory.html
		 */
		String root = "requestservicegateway";// The root element of request
												// payload
		String language = "zh_TW";// The service language
		String service = "1"; // The fixed parameter

		String[] elmName = { "userid", "password", "language", "service" };// Define
																			// each
																			// XML
																			// tag
																			// name
		String[] data = { userInfo.userid, userInfo.pwd, language, service };// Set each value of
															// tag

		/*
		 * To create XML documents. DocumentBuilderFactory can obtain a parser
		 * that produces DOM object trees from XML documents
		 */
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
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

		/*
		 * Used to process XML from a variety of sources and write the
		 * transformation output to server
		 */
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(connection.getOutputStream());
		transformer.transform(source, result);

		/* Get the response from the server and parse it */
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse
				.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection
				.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		RequestServiceGatewayResponse rsp = new RequestServiceGatewayResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if (nodelist.item(j).getNodeName().equals("status")) {
				rsp.setStatus(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("servicegateway")) {
				rsp.setServicegateway(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("time")) {
				rsp.setTime(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("accountsyncstate")) {
				rsp.setAccountsyncstate(nodelist.item(j).getTextContent());
			}
		}

		// The correct response payload's status value must equals "0"
		if (!"0".equals(rsp.getStatus())) {
			throw new Exception(
					"Error : You have to check RequestServiceGateway api response");
		}

		System.out.println("GET THE SERVICEGATEWAY IS : "
				+ rsp.getServicegateway());

		return rsp;
	}
}
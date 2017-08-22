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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import asuswebstorage.user.info.userInfo;
import entity.AcquireTokenResponse;
import entity.FolderBrowseResponse;
import entity.PageRsp;
import entity.PublicFileBase;
import entity.PublicFolderBase;

public class FolderBrowse 
{	
	
	static String API = "/inforelay/browsefolder/";// InfoRelay API
	
	public static void main(String[] args) throws Exception
	{	
		FolderBrowse folderBrowse = new FolderBrowse();
		FolderBrowseResponse rsp = folderBrowse.getResponse();		
		System.out.println(rsp.toString());
	}

	public FolderBrowseResponse getResponse() throws Exception
	{
		/* Fetching token from RequestServiceGateway by userid & pwd. Then you will get token, Inforelay and Webrelay domain/IP */
		AcquireToken at = new AcquireToken();
		AcquireTokenResponse acquireTokenResponse = null;
		try {
			acquireTokenResponse = at.getResponse(userInfo.userid, userInfo.pwd);
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
		String root = "browse";// The root element of request payload  
		
		String language = "zh_TW";// The service language
		String folderid = "82824190";// Browsing the specify folder's ID
		//String page = "";// The tag name is "page"
		String pageno = "1";// Page number
		String pagesize = "10";// Amount of information access granted to a page 
		String sortby = "1"; // The way of sorting file: 1 = Sort the catalog by file(or folder) name | 2 = Sort the catalog by time 
		String sortdirection = "0";// The way of sorting file: 0 = ASC | 1 = DESC 

		String[] elmName = { "token", "language", "userid", "folderid",	"type", "pageno", "pagesize", "sortby", "sortdirection" };// Define each XML tag name			
		String[] data = { token, language, userInfo.userid, folderid, "", pageno, pagesize, sortby, sortdirection };// Set each value of tag
		
		/* To create XML documents. DocumentBuilderFactory can obtain a parser that produces DOM object trees from XML documents */
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

		FolderBrowseResponse rsp = new FolderBrowseResponse();		
		PageRsp pagersp = new PageRsp();				
		LinkedList<PublicFolderBase> pfolder =new LinkedList<PublicFolderBase>(); 
		LinkedList<PublicFileBase> pfile = new LinkedList<PublicFileBase>();
		
		// Compose the payload
		int j, k, m;		
		for (j = 0; j < nodelist.getLength(); j++) {
			Node node = nodelist.item(j);
			
			/* status */
			if(node.getNodeName().equals("status")){
				rsp.setStatus(node.getTextContent());
			}	
			
			/*rawfoldername*/
			if (node.getNodeName().equals("rawfoldername")) {
				rsp.setRawFolderName(node.getTextContent());
			}
			
			/* parent*/			
			if(node.getNodeName().equals("parent")){	
				rsp.setParent(node.getTextContent());
			}
			
			/* root folder id*/
			if (node.getNodeName().equals("rootfolderid")) {
				rsp.setRootFolderID(node.getTextContent());
			}
			
			/* page */	
			if(node.getNodeName().equals("page")){	
				for(k = 0; k < node.getChildNodes().getLength(); k++){
					if(node.getChildNodes().item(k).getNodeName().equals("pageno")){
						pagersp.setPageno(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));			
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("pagesize")){
						pagersp.setPagesize(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("totalcount")){
						pagersp.setTotalcount(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("hasnextpage")){
						pagersp.setHasnextpage(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));	
					}
				}				
			}

			/* folder */
			if(node.getNodeName().equals("folder")){	
				PublicFolderBase publicfolderbase = new PublicFolderBase();
				for(k = 0; k < node.getChildNodes().getLength(); k++){							
					if(node.getChildNodes().item(k).getNodeName().equals("id")){
						publicfolderbase.setId(node.getChildNodes().item(k).getTextContent());			
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("rawfoldername")){
						publicfolderbase.setRawFolderName(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("treesize")){
						publicfolderbase.setTreeSize(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isgroupaware")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsGroupAware(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isbackup")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsbackup(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isorigdeleted")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsorigdeleted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("ispublic")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIspublic(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("createdtime")){
						publicfolderbase.setCreatedtime(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("markid")){
						publicfolderbase.setMarkid(node.getChildNodes().item(k).getTextContent());							
					}
				}				
				pfolder.add(publicfolderbase);	
			}			

			/* file */	
			if(node.getNodeName().equals("file")){
				PublicFileBase publicfilebase = new PublicFileBase();
				for(k = 0; k < node.getChildNodes().getLength(); k++){
					if(node.getChildNodes().item(k).getNodeName().equals("id")){
						publicfilebase.setId(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("rawfilename")){
						publicfilebase.setRawFileName(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("size")){
						publicfilebase.setSize(Long.parseLong(node.getChildNodes().item(k).getTextContent()));
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isgroupaware")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsGroupAware(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isbackup")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsbackup(b);
					}				
					else if(node.getChildNodes().item(k).getNodeName().equals("isorigdeleted")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsorigdeleted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isinfected")){
						publicfilebase.setIsinfected(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("ispublic")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIspublic(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("headversion")){
						publicfilebase.setHeadversion(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("createdtime")){
						publicfilebase.setCreatedtime(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("markid")){
						publicfilebase.setMarkid(node.getChildNodes().item(k).getTextContent());
					}
				}
				pfile.add(publicfilebase);					
			}
		}
		rsp.setPage(pagersp);	
		rsp.setFolders(pfolder);
		rsp.setFiles(pfile);

		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
		{
			System.out.println("status:"+rsp.getStatus());
			throw new Exception("Error : You have to check FolderBrowse api response");
		}
		
		return rsp;
	}
}

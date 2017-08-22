package net.yostore.utility;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.SAXException;

import android.util.Xml;

import net.yostore.aws.api.sax.BaseSaxHandler;

public class HttpFileUpload implements Runnable
{
	private Map<String, String>  param;
	 
    private String defaultFile; 
    private String ip;
    private int port = 80;
    private BaseSaxHandler handler;
	
	public HttpFileUpload(String indexFile, String ip, int port, BaseSaxHandler handler) { 
        
//        docRoot = new File(root); 
        defaultFile = indexFile; 
        this.ip=ip;
        this.port=port;
    } 
	
	// �ھڭn�D���ɦW�P�_�Ǧ^���A 
    public String returnContentType(String fileName) { 
        if(fileName.endsWith(".html") || fileName.endsWith(".htm")) 
            return "text/html";   // HTML���� 
        else if(fileName.endsWith(".txt") || fileName.endsWith(".java")) 
            return "text/plain";  // ��r���A 
        else if(fileName.endsWith(".gif")) 
            return "image/gif";   // GIF���� 
        else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) 
            return "image/jpeg";  // JPEG���� 
        else if(fileName.endsWith(".class")) 
            return "application/octec-stream"; // �G�i������?{���ɮ� 
        else if(fileName.endsWith(".mp3"))
        	return "audio/mpeg";
        else if(fileName.endsWith(".rar"))
        	return "application/rar";
        else if(fileName.endsWith(".exe"))
        	return "application/x-unknown";
        else // �䥦���i�P�O���ɮפ@�ߵ��r�ɫ��A 
            return "text/plain"; 
    }
	
	public void doUpload() throws IOException, SAXException{

        
		Socket sock = new Socket();       
        InetSocketAddress hostAddr = new InetSocketAddress(ip, port);
        sock.connect(hostAddr);               
       
        DataOutputStream dostream = new DataOutputStream(sock.getOutputStream());
       
        String boundaryTail = "\r\n-----------------------------2609120531336--\r\n";
        String c_FormData = "-----------------------------2609120531336\r\nContent-Disposition: form-data; name=\"%s\"\r\n\r\n%s\r\n";
        String c_FileData = "-----------------------------2609120531336\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: %s\r\n\r\n";
       
        //String kv1 = String.format(c_FormData, "key1", "value1");
        //String kv2 = String.format(c_FormData, "key2", "value2");
        String[] kv = new String[param.size()];
        File fi = new File(defaultFile);
        FileInputStream fos = new FileInputStream(fi); //�W�[�����A�Ⱦ�����Ȥ�ШD
        String file = String.format(c_FileData, "uploadfile", fi.getName(), returnContentType(fi.getName()));
       
//        long ContentLength = kv1.getBytes().length + kv2.getBytes().length
//                                    + file.getBytes().length
//                                    + 10 //file content length
//                                    + boundaryTail.getBytes().length;
        
        long ContentLength = boundaryTail.getBytes().length+file.getBytes().length+fi.length();
        Iterator it= param.keySet().iterator();
        int i=0;
        while (it.hasNext())
        {
	         Object key=it.next();
//	         System.out.println("key:"+key);
//	         System.out.println("value:"+param.get(key));
	         kv[i] = String.format(c_FormData, key, param.get(key));
	         ContentLength = ContentLength+kv[i].getBytes().length;
	         i++;
        }

        String ipAddr = InetAddress.getLocalHost().getHostAddress(); 
        //write http head
        dostream.write("POST /jh/HttpUpload.do HTTP/1.1\r\n".getBytes());
        dostream.write("Accept: */*\r\n".getBytes());       
        dostream.write("Referer: http://218.199.29.199:8080/jh/index.html\r\n".getBytes());
        dostream.write("Accept-Language: zh-cn\r\n".getBytes());
        dostream.write("Content-Type: multipart/form-data; boundary=---------------------------2609120531336\r\n".getBytes());
        dostream.write("Accept-Encoding: gzip, deflate\r\n".getBytes());   
        dostream.write("User-Agent: Mozilla/4.0 (Windows XP 5.1) Java/1. 6.0\r\n".getBytes());
        dostream.write("Host: 127.0.0.1:80\r\n".getBytes());
        dostream.write(String.format("Content-Length: %d\r\n", ContentLength).getBytes());
        dostream.write("Connection: Keep-Alive\r\n".getBytes());       
        dostream.write("Cache-Control: no-cache\r\n".getBytes());       
        dostream.write("\r\n".getBytes());
        //write http data
//        dostream.write(kv1.getBytes());
//        dostream.write(kv2.getBytes());
        for(int j=0;j<kv.length;j++){
        	dostream.write(kv[j].getBytes());
        }
        dostream.write(fi.getName().getBytes());
//        dostream.write("1234567890".getBytes());   ////file content
        byte[] buf = new byte[2048];
        int num = fos.read(buf);
        System.out.println("�ǰe���:" + (String) fi.getName()); // public Socket accept() throws
        while (num != ( - 1)) { //�O�_Ū�����
        	dostream.write(buf, 0, num); //�����Ƽg�X�����w�İ�
        	dostream.flush(); //����w�İϧ��Ƽg���Ȥ��
        	num = fos.read(buf); //�~��q���Ū����
        }
        fos.close();
        dostream.write(boundaryTail.getBytes());
       
        InputStream is = sock.getInputStream();
        Xml.parse(is, Xml.Encoding.UTF_8, handler);
        
        dostream.close();
        
	}
	
	

	@Override
	public void run()
	{
		try
		{
			doUpload();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch ( SAXException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		// TODO Auto-generated method stub
//		String request;           // �n�D����k 
//        String contentType;       // �ɮ׫��A 
//        String httpVersion = "";  // HTTP��w���� 
//        File requestedFile;       // �n�D���ɮ� 
//
//        try { 
//            PrintStream printStream = new PrintStream(
//                               clientSkt.getOutputStream()); 
//            BufferedReader clientReader = new BufferedReader( 
//                new InputStreamReader(clientSkt.getInputStream())); 
//
//            String get = clientReader.readLine(); 
//        
//            // ����Request�����e 
//            String[] tokens = get.split("[ \t]"); 
//            request = tokens[0]; 
//
//            if(request.equals("GET")) { 
//                String file = tokens[1]; 
//
//                if(file.endsWith("/")) // GET / 
//                    file += defaultFile; 
//                contentType = returnContentType(file); 
//
//                if(tokens.length >= 3) { 
//                    httpVersion = tokens[2]; 
//                } 
//
//                // �ڭ̤��B�z�Ȥ�ݪ����Y 
//                while((get = clientReader.readLine()) != null) { 
//                    if(get.trim().equals("")) 
//                        break; 
//                } 
//
//                try { 
//                    requestedFile = new File( 
//                         docRoot, file.substring(1, file.length())); 
//                    FileInputStream fileInputStream = 
//                        new FileInputStream(requestedFile); 
//
//                    // Ū�J�ШD���ɮ� 
//                    int fileLength = (int)requestedFile.length(); 
//                    byte[] requestedData = new byte[fileLength]; 
//                    fileInputStream.read(requestedData); 
//                    fileInputStream.close(); 
//
//                    // �g�X���Y�ܫȤ�� 
//                    if(httpVersion.startsWith("HTTP/")) { 
//                        printStream.print("HTTP/1.0 200 OK\r\n"); 
//                        Date now = new Date(); 
//                        printStream.print("Date: " + now + "\r\n"); 
//                        printStream.print("Server: TinyHttpd v0.1\r\n"); 
//                        printStream.print(
//                                "Content-length: " + fileLength + "\r\n"); 
//                        printStream.print(
//                                "Content-type: " + contentType + "\r\n\r\n"); 
//                    } 
//
//                    // �N�ɮ׶ǵ��Ȥ�� 
//                    printStream.write(requestedData); 
//                    printStream.close(); 
//                } 
//                catch(IOException e) {  // �䤣���ɮ� 
//                    if(httpVersion.startsWith("HTTP/")) { 
//                        printStream.print("HTTP/1.0 404 File Not Found\r\n"); 
//                        Date now = new Date(); 
//                        printStream.print("Date: " + now + "\r\n"); 
//                        printStream.print("Server: TinyHttpd v0.1\r\n"); 
//                        printStream.print("Content-type: text/html\r\n\r\n"); 
//                    } 
//                } 
//
//                // ��ܿ�~�T������ 
//                printStream.println(
//                        "<HTML><HEAD><TITLE>File Not Found</TITLE></HEAD>" 
//                           + "<BODY><H1>HTTP Error 404: File Not Found" + 
//                             "</H1></BODY></HTML>"); 
//                printStream.close(); 
//            } 
//            else { // �Ȥ�ݽШD�F�@�ӨS����@����k�A�ҦpPOST�� 
//                if(httpVersion.startsWith("HTTP/")) { 
//                    printStream.print("HTTP/1.0 501 Not Implemented\r\n"); 
//                    Date now = new Date(); 
//                    printStream.print("Date: " + now + "\r\n"); 
//                    printStream.print("Server: TinyHttpd v1.0\r\n"); 
//                    printStream.print("Content-type: text/html\r\n\r\n"); 
//                } 
//            } 
//            printStream.println(
//                    "<HTML><HEAD><TITLE>Not Implemented</TITLE></HEAD>" 
//                           + "<BODY><H1>HTTP Error 501: Not Implemented" + 
//                             "</H1></BODY></HTML>"); 
//            printStream.close(); 
//        } 
//        catch(IOException e) { 
//            e.printStackTrace(); 
//        } 
//
//        try { 
//            clientSkt.close(); 
//        } 
//        catch(IOException e) { 
//            e.printStackTrace(); 
//        } 
		
    } 

}

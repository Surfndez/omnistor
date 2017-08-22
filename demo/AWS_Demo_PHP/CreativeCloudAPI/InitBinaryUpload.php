<?php
/**
 * Creative Cloud API supports upload file stream. One piece of advice when you writing PHP code to perform streaming upload:
 * The first, you can copy upload file to some directory, and then upload the copy file. 
 * If the file being uploaded has a Chinese name then it is not getting uploaded. Instead a corrupted file with junk name is uploaded or system will show some error.
 * In this case, you can using the parameter "na" of /webrelay/initbinaryupload/ API to rename file.
 */
 
class InitBinaryUpload
{
	private $sid;// It has been provided from Creative Cloud Web
	private static $api = '/webrelay/initbinaryupload/';// Service api
	private $userid;// The user's Email address		
	private $pwd;// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	
	function __construct($sid, $userid, $pwd, $token, $webrelay)
	{		
		$this->sid = $sid; 
		$this->userid = $userid; 
		$this->pwd = $pwd; 
		$this->token = $token; 
		$this->webrelay = $webrelay; 
	} 
	
	function payload($parentFolderID, $transid, $file, $fileName)
	{		
		/* Hash the file context using the SHA512 hashing algorithm then get the result - fileChecksum */
		$base64FileName = urlencode(base64_encode($fileName));// Uploading the specify file's name		
		$fileChecksum = hash("sha512", $file);
		
		// The uploading file's attribute, it needs to be encoded
		$timestamp = time();// The number of seconds since the Unix Epoch (January 1 1970 00:00:00 GMT)
		$attribute = urlencode("<creationtime>$timestamp</creationtime><lastaccesstime>$timestamp</lastaccesstime><lastwritetime>$timestamp</lastwritetime>");// Folder's attribute context
						
		// The fixed query string
		$urlQuery = "/?dis=$this->sid&tk=$this->token&na=$base64FileName&pa=$parentFolderID&sg=$fileChecksum&at=$attribute";
						
		if( !is_null($transid) )
			$urlQuery .= "&tx=$transid";// Compose the query string		
		
		$this->urlQuery = $urlQuery;
		$this->file = $file;
	} 
	
	function getResponse()
	{	
		$dataCenter = $this->webrelay;// Connection server		
		$url = 'https://'.$dataCenter.self::$api;
		
		$url .= $this->urlQuery;
		
		$filename = $this->file;
		if (!$handle = fopen($filename, 'r')) 
		{
		   echo "Cannot open file $filename";
		   exit(1);
		}
			
		// Initialise and execute a cURL request
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 30);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);	
		curl_setopt($ch, CURLOPT_VERBOSE, 1);// TRUE to output verbose information. Writes output to STDERR, or the file specified using CURLOPT_STDERR		 
		curl_setopt($ch, CURLOPT_POST, 1);// Doing a regular HTTP POST		
		curl_setopt($ch, CURLOPT_UPLOAD, 1);// TRUE to prepare for an upload  
		curl_setopt($ch, CURLOPT_INFILE, $handle);// The file that the transfer should be read from when uploading.
		curl_setopt($ch, CURLOPT_INFILESIZE, filesize($filename));// The expected size, in bytes, of the file when uploading a file to a remote site. 
				
		// Get the response from the server		
		$response = curl_exec($ch);

		//HTTP error
		if(curl_errno($ch))
		{
			echo '<br>'.curl_error($ch);
			exit('<br>Error : You have to check HttpsURLConnection or Input payload.<br>');
		}
		
		curl_close($ch); //close curl
		
		/* Get the response from the server and parse it */
		$pos = strpos($response, '<?xml version');		
		$output = substr($response, $pos);// Find out output payload
		
		return $this->parse($output);// Parsing XML payload
	}
	
	/* Get the response from the server and parse it */	
	function parse($xml) 
	{	
		$xml = simplexml_load_string($xml);
		
		foreach($xml->children() as $child)
		{	
			switch($child->getName())
			{
				case 'status':   		 $InitBinaryUploadResponse['status'] = $child; break;
				case 'transid':   		 $InitBinaryUploadResponse['transid'] = $child; break;
				case 'offset':   	 	 $InitBinaryUploadResponse['offset'] = $child; break;
				case 'latestchecksum':   $InitBinaryUploadResponse['latestchecksum'] = $child; break;
				case 'fileid':   	 	 $InitBinaryUploadResponse['fileid'] = $child; break;
			}	 
		}
		
		return $InitBinaryUploadResponse;
	}
}
?>
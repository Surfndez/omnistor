<?php
class FolderCreate
{
	private $sid;// It has been provided from Creative Cloud Web	
	private static $api = '/folder/create/';// Service api
	private $userid;// The user's Email address		
	private $pwd;// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	
	function __construct($sid, $userid, $pwd, $token, $inforelay)
	{		
		$this->sid = $sid; 
		$this->userid = $userid; 
		$this->pwd = $pwd; 
		$this->token = $token; 
		$this->inforelay = $inforelay; 
	} 
	
	/* Represents an entire HTML or XML document.
	 * Preparing for DOMDocument. This class is available at: 
	 * http://www.php.net/manual/en/class.domdocument.php
	 * */	
	function payload($parentFolderID, $isencrypted, $foldername)
	{	
		$displayName = base64_encode($foldername);// Creating the specify folder's name, it needs to be encode by Base64		
		
		//Folder's attribute context
		$timestamp = time();// The number of seconds since the Unix Epoch (January 1 1970 00:00:00 GMT)
		$attribute = urlencode("<creationtime>$timestamp</creationtime><lastaccesstime>$timestamp</lastaccesstime><lastwritetime>$timestamp</lastwritetime>");// Folder's attribute context
				
		$dom = new DOMDocument('1.0');
		$dom->encoding = 'UTF-8';
		$root = $dom->createElement('create');// The root element of request payload 
				$dom->appendChild($root);
		$item = $dom->createElement('token', $this->token);
				$root->appendChild($item);
		$item = $dom->createElement('userid', $this->userid);
				$root->appendChild($item);
		$item = $dom->createElement('parent', $parentFolderID);
				$root->appendChild($item);	
		$item = $dom->createElement('isencrypted', $isencrypted);
				$root->appendChild($item);		
		$item = $dom->createElement('display', $displayName);
				$root->appendChild($item);		
		$item = $dom->createElement('attribute', $attribute);
				$root->appendChild($item);	 
		$xmlStr = $dom->saveXML();
	
		$this->xmlStr = $xmlStr; 
	}
	
	function getResponse()
	{			
		$dataCenter = $this->inforelay;// Connection server		
		$url = 'https://'.$dataCenter.self::$api;
		
		$header = array("Cookie:ONE_VER=1_0;sid=$this->sid;path=/");// Cookies have to add the value of SID
		
		// Initialise and execute a cURL request
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 30);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_HEADER, 1);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
		curl_setopt($ch, CURLOPT_POST, 1);// Doing a regular HTTP POST
		curl_setopt($ch, CURLOPT_POSTFIELDS, $this->xmlStr);
		
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
		$pos = strpos($response, "<?xml version");		
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
				case 'status':   		 $FolderCreateResponse['status'] = $child; break;
				case 'id':   			 $FolderCreateResponse['id'] = $child; break;
			}	 
		}
				 
		return $FolderCreateResponse;		
	}
}
?>
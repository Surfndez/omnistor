<?php
class GetAdvancedSharecode
{
	private $sid;// It has been provided from Creative Cloud Web	
	private static $api = '/fsentry/getadvancedsharecode/';// Service api
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
	function payload($isfolder, $entryid)
	{
		$dom = new DOMDocument('1.0');
		$dom->encoding = 'UTF-8';
		$root = $dom->createElement('getadvancedsharecode');// The root element of request payload 
				$dom->appendChild($root);
		$item = $dom->createElement('token', $this->token);
				$root->appendChild($item);
		$item = $dom->createElement('userid', $this->userid);
				$root->appendChild($item);
		$item = $dom->createElement('isfolder', $isfolder);
				$root->appendChild($item);	
		$item = $dom->createElement('entryid', $entryid);
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
		$pos = strpos($response, "<?xml version='1.0' encoding='utf-8'?>");		
		$output = substr($response, $pos);// Find out output payload
				
		return $this->parse($output);// Parsing XML payload
	}
	
	/* Get the response from the server and parse it */	
	function parse($xml) 
	{
		$xml = simplexml_load_string($xml);
		$j=0;
		foreach($xml->children() as $child)
		{	
			switch($child->getName())
			{
				case 'status':   		 	 $GetAdvancedSharecodeResponse['status'] = $child; break;
				case 'sharecode':   		 $GetAdvancedSharecodeResponse['sharecode'] = $child; break;
				case 'ispasswordneeded':   	 $GetAdvancedSharecodeResponse['ispasswordneeded'] = $child; break;
				case 'isgroupaware':   		 $GetAdvancedSharecodeResponse['isgroupaware'] = $child; break;
				case 'shareforuserid': 	
					$j++;
					$shareforuserid[$j-1] = $child; break;
				case 'expiredtime': 		 $GetAdvancedSharecodeResponse['expiredtime'] = $child; break;
				case 'folderquota': 		 $GetAdvancedSharecodeResponse['folderquota'] = $child; break;
			}	 
		}
		if( isset($shareforuserid) )
		{	
			$GetAdvancedSharecodeResponse['shareforuserid'] = $shareforuserid;
		}
		
		return $GetAdvancedSharecodeResponse;
	}
}
?>
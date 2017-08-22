<?php
class GetAccountInfo
{
	private $sid;// It has been provided from Creative Cloud Web	
	private static $api = '/member/getinfo/';// Service api
	private $userid;// The user's Email address		
	private $pwd;// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	
	function __construct($sid, $userid, $pwd, $token, $servicegateway)
	{		
		$this->sid = $sid; 
		$this->userid = $userid; 
		$this->pwd = $pwd; 
		$this->token = $token; 
		$this->servicegateway = $servicegateway; 
	} 
	
	/* Represents an entire HTML or XML document.
	 * Preparing for DOMDocument. This class is available at: 
	 * http://www.php.net/manual/en/class.domdocument.php
	 * */	
	function payload()
	{			
		$dom = new DOMDocument('1.0');
		$dom->encoding = 'UTF-8';
		$root = $dom->createElement('getinfo');// The root element of request payload 
				$dom->appendChild($root);
		$item = $dom->createElement('token', $this->token);
				$root->appendChild($item);
		$item = $dom->createElement('userid', $this->userid);
				$root->appendChild($item);
		$xmlStr = $dom->saveXML();
	
		$this->xmlStr = $xmlStr; 
	}
	
	function getResponse()
	{			
		$dataCenter = $this->servicegateway;// Connection server		
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
			echo '<br>'.curl_error($ch),			
				 "<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='Index.php';\"/>";
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
				
		$GetAccountInfoResponse = array();
		
		foreach($xml->children() as $child)
		{	
			$backuppc = array();
			$package = array();
			$featurelist = array();
		
			switch($child->getName())
			{
				case 'status':   		 $GetAccountInfoResponse['status'] = $child; break;
				case 'account':   		 $GetAccountInfoResponse['account'] = $child; break;
				case 'email':   		 $GetAccountInfoResponse['email'] = $child; break;
				case 'regyear':   		 $GetAccountInfoResponse['regyear'] = $child; break;
				case 'language':   		 $GetAccountInfoResponse['language'] = $child; break;
				case 'activateddate':    $GetAccountInfoResponse['activateddate'] = $child; break;
				case 'credential':  	 $GetAccountInfoResponse['credential'] = $child; break;
				case 'credentialstate':  $GetAccountInfoResponse['credentialstate'] = $child; break;
				case 'usedbackuppc':  	 $GetAccountInfoResponse['usedbackuppc'] = $child; break;
				case 'backuppc':
					$backuppc['backuppc'] = 'backuppc';
					foreach($child->children() as $backuppcchild)
					{
						switch($backuppcchild->getName())
						{
							case 'name':   		 		$backuppc['name'] = base64_decode($backuppcchild); break;
							case 'createdtime':   		$backuppc['createdtime'] = $backuppcchild; break;
						}
					}					
					array_push($GetAccountInfoResponse, $backuppc);
					break;
				case 'package':	
					$package['package'] = 'package';
					foreach($child->children() as $packagechild)
					{
						switch($packagechild->getName())
						{
							case 'id':   		 		$package['id'] = $packagechild; break;
							case 'display':   			$package['display'] = $packagechild; break;
							case 'capacity':   			$package['capacity'] = $packagechild.' MB'; break;
							case 'uploadbandwidth':   	$package['uploadbandwidth'] = $packagechild.' KB'; break;
							case 'downloadbandwidth':   $package['downloadbandwidth'] = $packagechild.' KB'; break;
							case 'upload':   			$package['upload'] = $packagechild.' MB';	break;
							case 'download':   			$package['download'] = $packagechild.' MB'; break;
							case 'concurrentsession':   $package['concurrentsession'] = $packagechild; break;
							case 'maxfilesize':   		$package['maxfilesize'] = $packagechild.' MB'; break;
							case 'hasencryption':   	$package['hasencryption'] = $packagechild; break;
							case 'expire':   			$package['expire'] = $packagechild;	break;
							case 'maxbackuppc':   		$package['maxbackuppc'] = $packagechild; break;
							case 'featurelist':						
								$featurelist['featurelist'] = 'featurelist';
								foreach($packagechild->children() as $featurelistchild)
								{									
									switch($featurelistchild->getName())
									{										
										case 'feature': 
											$featurelist['feature name'] = (string)$xml->package->featurelist->feature->attributes()->name;					
											$featurelist['feature enable'] = (string)$xml->package->featurelist->feature->attributes()->enable;				
											$featurelist['property name'] = (string)$xml->package->featurelist->feature->property->attributes()->name;				
											$featurelist['property value'] = (string)$xml->package->featurelist->feature->property->attributes()->value;
										break;
									}
								}	
								array_push($package, $featurelist);	
								break;
						}
					}					
					array_push($GetAccountInfoResponse, $package);
					break;
				case 'usedcapacity':  	 $GetAccountInfoResponse['usedcapacity'] = $child.' MB'; break;
				case 'freecapacity':  	 $GetAccountInfoResponse['freecapacity'] = $child.' MB'; break;
			}//end switch	 
			
			unset($backuppc);
			unset($package);
			unset($featurelist);
		}
		
		return $GetAccountInfoResponse;
	}
}
?>
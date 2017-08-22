<?php
class AcquireToken
{
	private $sid;// It has been provided from Creative Cloud Web	
	private static $api = '/member/acquiretoken/';// Service api
	private $userid;// The user's Email address		
	private $pwd;// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	private $progkey;// It has been provided from Creative Cloud Web
				
	function __construct($sid, $progkey, $userid, $pwd, $servicegateway)
	{		
		$this->sid = $sid; 
		$this->progkey = $progkey; 
		$this->userid = $userid; 
		$this->pwd = $pwd; 
		$this->servicegateway = $servicegateway; 
	} 
	
	/* Represents an entire HTML or XML document.
	 * Preparing for DOMDocument. This class is available at: 
	 * http://www.php.net/manual/en/class.domdocument.php
	 * */	
	function payload($OTP, $CAPTCHA)
	{	
		$dom = new DOMDocument('1.0');
		$dom->encoding = 'UTF-8';
		$root = $dom->createElement('aaa');// The root element of request payload 
				$dom->appendChild($root);
		$item = $dom->createElement('userid', $this->userid);
				$root->appendChild($item);	
		$item = $dom->createElement('password', $this->pwd);
				$root->appendChild($item);				
		if( !is_null($OTP) )	
		{
			$item = $dom->createElement('auxpassword', $OTP);
			$root->appendChild($item);	
		}				
		else if( !is_null($CAPTCHA) )	
		{		
			$item = $dom->createElement('auxpassword', $CAPTCHA);
			$root->appendChild($item);
		}	
		
		$xmlStr = $dom->saveXML();
		
		$this->xmlStr = $xmlStr;
	}
	
	function getResponse()
	{			
		$dataCenter = $this->servicegateway;// Connection server
		$url = 'https://'.$dataCenter.self::$api;
		
		$headers = $this->composeAuthorizationHeader($this->progkey);// Compose Developer Authorization String
		$header[] = "Cookie:ONE_VER=1_0;sid=$this->sid;path=/";// Cookies have to add the value of SID
		$header[] = $headers['header'];	
		setcookie('Authorization', $headers['signature']);
		
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
		
		curl_close($ch);//close curl
				 		  
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
				case 'status':   		 	$AcquiretokenResponse['status'] = $child; break;
				case 'token':   			$AcquiretokenResponse['token'] = $child; break;
				case 'inforelay':   		$AcquiretokenResponse['inforelay'] = $child; break;
				case 'webrelay':   			$AcquiretokenResponse['webrelay'] = $child;	break;
				case 'searchserver':   		$AcquiretokenResponse['searchserver'] = $child;	break;
				case 'credential':   		$AcquiretokenResponse['credential'] = $child; break;
				case 'credentialstate':   	$AcquiretokenResponse['credentialstate'] = $child; break;
				case 'package':							
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
						}
					}					
					if( !is_null($child->children()) )
						$AcquiretokenResponse['package'] = $package;// Means this parameter is not null 
					break;					
				case 'auxpasswordurl':   			$AcquiretokenResponse['auxpasswordurl'] = $child; break;
				case 'time':   	 					$AcquiretokenResponse['time'] = $child;	break;
			}	 
		}
		
		return $AcquiretokenResponse;
	}
	
	/** We generate an HTTP header called "Authorization" with the relevant OAuth parameters for the request,
	 * it contains signature_method, timestamp, nonce and signature parameters. 
	 * 
	 * 1. signature_method: HMAC-SHA1 hashing algorithm.
	 * 2. timestamp: The number of seconds since the Unix Epoch (January 1 1970 00:00:00 GMT).
	 * 3. nonce: The random number is unique , it can only be sent once during 60 minutes.
	 * 4. signature: It contains a querystring parameters("signature_method", "timestamp" and "nonce"). 
	 * This parameters sort by alphabet and it needs to be URL encoded, then use the ProgKey to create the original signature in HMAC-SHA1 algorithm. 
	 * Finally, original signature value needs Base64 Hash conversion, once again URL encode, the results is the signature. 	   
	 * */	
	function composeAuthorizationHeader($progkey)
	{	
		$signature_method = 'HMAC-SHA1';		
		$nonce = rand(-888, 9999999);// The random number is unique , it can only be sent once during 60 minutes
		$timestamp = time();// The number of seconds since the Unix Epoch (January 1 1970 00:00:00 GMT).
		
		//Step 1, Compose signature string
		$signaturePre = "nonce=$nonce&signature_method=$signature_method&timestamp=$timestamp";	  
		
		//Step 2, Doing urlencode before doing hash
		$signatureURLEn = urlencode($signaturePre);			
		
		//Step 3, Doing hash signature string by HMAC-SHA1
		$signMethod = hash_hmac('sha1', $signatureURLEn, $progkey, true);	
		
		//Step 4, Doing base64 encoding & doing urlencode again 
		$signature = urlencode(base64_encode($signMethod));  
		
		//Final step, Return all parameters to be authorization header string
		return array(
			'header' => "Authorization:signature_method=\"$signature_method\",timestamp=\"$timestamp\",nonce=\"$nonce\",signature=\"$signature\"",
			'signature' => $signature,
			);
	}
}
?>

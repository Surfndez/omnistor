<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<body>
<?php
class DirectDownload
{
	private $sid;// It has been provided from Creative Cloud Web	
	private static $api = '/webrelay/directdownload/';// Service api
	private $userid;// The user's Email address		
	private $pwd;// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	
	function __construct($sid, $userid, $pwd, $token, $webrelay, $fileID, $preview, $of, $cpt)
	{		
		$this->sid = $sid; 
		$this->userid = $userid; 
		$this->pwd = $pwd; 
		$this->token = $token; 
		$this->webrelay = $webrelay; 
		$this->fileID = $fileID; 
		$this->preview = $preview; 
		$this->offset = $of; 
		$this->cpt = $cpt; 
	} 
		
	function payload()
	{			
		$urlQuery = "$this->token/?dis=$this->sid&fi=$this->fileID&pv=$this->preview&of=$this->offset&cpt=$this->cpt";// Compose the query string				
				
		$this->urlQuery = $urlQuery;
	}
	
	function getResponse()
	{	
		$dataCenter = $this->webrelay;// Connection server		
		$url = 'https://'.$dataCenter.self::$api;
		
		$url .= $this->urlQuery;
				
		// Initialise and execute a cURL request
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 30);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_HEADER, 1);
		curl_setopt($ch, CURLOPT_VERBOSE, 1);
		curl_setopt($ch, CURLOPT_POST, 1);// Doing a regular HTTP POST
		curl_setopt($ch, CURLOPT_POSTFIELDS, $this->payload());
				
		// Get the response from the server		
		$response = curl_exec($ch);

		//HTTP error
		if(curl_errno($ch))
		{
			echo '<br>'.curl_error($ch);
			exit('<br>Error : You have to check HttpsURLConnection or Input payload.<br>');
		}
				 
		curl_close($ch); //close curl
		
		return $this->download($response);// Parse HTTP response header ,and then download file
	}
	
	function download($response) 
	{		
		/* Get the response from the server and parse it */	
		list($headers, $response) = explode("\r\n\r\n", $response, 2);		
		$lines = explode("\r\n", $headers);
		if(preg_match('#^HTTP/1.1 100#', $lines[0]))
		{
			list($headers, $response) = explode("\r\n\r\n", $response, 2);
			$lines = explode("\r\n", $headers);// Every header's content
		}
		
		// Get the HTTP response code from the first line
		$first = array_shift($lines);
		$pattern = '#^HTTP/1.1 ([0-9]{3})#';
		preg_match($pattern, $first, $matches);
		$code = $matches[1];		
		
		if( $code == 200 )
		{
			foreach ($lines as $line)
			{
				list($k, $v) = explode(': ', $line, 2);
				
				if( $k == 'Content-Disposition' )
				{
					$pos = strpos($v, 'filename=');		
					$fileName = substr($v, $pos+10);// Got the file's name from header
					$fileName = substr_replace($fileName, '', -1);
				}
			}
			
			// Got the response body
			if( !$body = json_decode($response) )
				$body = $response;	
				
			$dir = "D:\\";// Download directory
			$fp = fopen($dir.mb_convert_encoding($fileName,"BIG-5","UTF-8"), "wb")
					or die("<br>Error: System can't open file.<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='API.php';\"/>");
			fwrite($fp, $body);
			fclose($fp);
		}
		else
		{
			exit('Error: Please check out DirectDownload API or your computer setting.');
		}	
	}
}
?>
</body>
</html>
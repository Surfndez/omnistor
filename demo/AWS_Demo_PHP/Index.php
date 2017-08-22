<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Creative Cloud API</title>
</head>
<?php
include_once("/web/PayloadStatus.inc.php");// For handling resposnse payload error 
include "/web/APIFunction.inc.php";// The API function

class Index
{	
	function __construct()
	{
		$endTime = strtotime("+3 days", time());// For recording cookie's deadline
		$this->endTime = $endTime;
		
		$callAPI = new APIFunction();// For calling API function
		$this->callAPI = $callAPI;		
	}
	
	function login()
	{
		if( !isset($_COOKIE['token']) )// Caching from AcquireToken API response
		{
			if( !empty($_POST['uid']) && !empty($_POST['password']) )
			{	
				$userid = $_POST['uid'];// The user's WebStorage ID	
				$typePwd = $_POST['password'];// The user typed password	
				$pwd = md5(strtolower($typePwd));// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
				
				// Setting user ID and password cookie
				setcookie('uid', $userid, $this->endTime);
				setcookie('password', $pwd, $this->endTime);
			}
			else
			{
				exit("<script type='text/javascript'>alert(\"Error Tip: Please check out your WebStorage ID or Password.\"); window.location.href='Login.php';</script>");
			}
			
			$this->userid = $userid;
			$this->pwd = $pwd;
			$this->requestServiceGateway();// Calling RequestServiceGateway api
		}
	}		
	
	function requestServiceGateway()
	{		
		try 
		{	
			$RequestservicegatewayResponse = $this->callAPI->
				requestServiceGateway($this->userid, $this->pwd);// Calling API		

			// The correct response payload's status value must equals "0"
			if( $RequestservicegatewayResponse['status'] == 0 )
			{
				$servicegateway = $RequestservicegatewayResponse['servicegateway'];
				$this->servicegateway = $servicegateway;
				
				setcookie('servicegateway', (String)$servicegateway, $this->endTime);
				$this->acquireToken();// Calling AcquireToken api
			}
			else
			{		
				echo '<br>RequestservicegatewayResponse=><br>', 
					 'status:'.$RequestservicegatewayResponse['status'].'<br>',
					 '<br>Error : You have to check RequestServiceGateway api response.<br><br>',
					 "<br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='Login.php';\"/>";	
				exit(1);		
			}	
		} 
		catch(Exception $e) 
		{
			echo 'Caught exception: '.$e->getMessage().'<br>';
		}
	}	

	function acquireToken()
	{			
		try 
		{	
			$OTP = $this->getOTP();// OTP : One-Time Password				
			$CAPTCHA = $this->getCAPTCHA();// CAPTCHA can generate and grade tests that humans can pass but current computer programs cannot
										
			$AcquiretokenResponse = $this->callAPI->
				acquireToken($this->userid, $this->pwd, $this->servicegateway, $OTP, $CAPTCHA);// Calling API	
			
			// The correct response payload's status value must equals "0"
			if( $AcquiretokenResponse['status'] == 0 )
			{
				// Setting token, inforelay and webrelay cookie
				setcookie('token', (String)$AcquiretokenResponse['token'], $this->endTime);
				setcookie('inforelay', (String)$AcquiretokenResponse['inforelay'], $this->endTime);
				setcookie('webrelay', (String)$AcquiretokenResponse['webrelay'], $this->endTime);
			}
			else if( $AcquiretokenResponse['status'] == 504 )// Acquiretoken OTP Error 
			{				
				echo '<br><br>Error Tip: Please check out the "WebStorage ID" or "Password" or "OTP".<br><br>',	
					 "<form action=\"Index.php\" method=\"post\">",
					 "WebStorage ID: <input type=\"text\" name=\"uid\" value='' size=\"30\" /><br><br>",	
					 "Password: <input type=\"password\" name=\"password\" value='' /><br><br>",
					 "OTP: <input type='text' name='otp' value=''><br><br>",
					 "<input type=\"submit\" value=\"submit\">",
					 "</form>";
			}
			else if( $AcquiretokenResponse['status'] == 508 )// Acquiretoken CAPTCHA Error 
			{			
				$captchaURL = urldecode($AcquiretokenResponse['auxpasswordurl']);// Getting captcha URL
	
				echo '<br><br>Error Tip: Please check out your "WebStorage ID" or "Password" and type the CAPTCHA words.<br><br>',
					 "<form action=\"Index.php\" method=\"post\">",
					 "WebStorage ID: <input type=\"text\" name=\"uid\" value=\"$userid\" size=\"30\" /><br><br>",	
					 "Password: <input type=\"password\" name=\"password\" value=\"$typePwd\" /><br><br>",		 
					 "<img src=\"$captchaURL\" /><br><br>",
					 "Type the CAPTCHA words: <input type='text' name='captcha' value=''><br><br>",
					 "<input type=\"submit\" value=\"submit\">",
					 "</form>";
			} 
			else
			{
				$this->statusError('Acquiretoken', $AcquiretokenResponse['status']);
			}
		} 
		catch(Exception $e) 
		{
			echo 'Caught exception: '.$e->getMessage().'<br>';
		}
	}
	
	function getOTP()
	{
		if( !empty($_POST['otp']) )		
			$OTP = $_POST['otp'];
		else
			$OTP = null;
		
		return $OTP;
	}
	
	function getCAPTCHA()
	{
		if( !empty($_POST['captcha']) )		
			$CAPTCHA = $_POST['captcha'];
		else
			$CAPTCHA = null;
		
		return $CAPTCHA;
	}
	
	// This function handling error 
	function statusError($apiName, $status)	
	{
		if( $status == 2 || $status == 214 || $status == 218 || $status == 219 )
		{
			echo payloadStatusError($apiName, $status);// Call this function from PayloadStatus.inc.php
		}
		else
		{
			// Reset cookie
			setcookie('uid', '', time()-3600);
			setcookie('password', '', time()-3600);
			setcookie('servicegateway', '', time()-3600);
			setcookie('token', '', time()-3600);
			setcookie('inforelay', '', time()-3600);
			setcookie('webrelay', '', time()-3600);
			
			echo "<br>$apiName Response=><br>status:$status<br>",				
				 "<br>Error : You have to check $apiName api.<br>";
		}	
	}
}

$Index = new Index();
$Index->login();	
?>
<frameset cols="25%, *">
	<frameset rows="60%, *">
		<frame src="web/RecentArticles.php">
		<frame src="web/AccountInfo.php">
	</frameset>	
	<frameset cols="60%">
		<frame id="rightFrame" name="rightFrame" src="web/OperationPage.php">
	</frameset>
</frameset>
</html>
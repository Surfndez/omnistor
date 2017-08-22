<?php
	/**
	*	It handle the error status of server response payload
	*	( The correct response payload's status value must equals "0" )
	*/
	function payloadStatusError($api, $status)
	{
		if( $status == 2 )// Authentication Error 
		{
			// Reset cookie
			setcookie('uid', '', time()-3600);
			setcookie('password', '', time()-3600);
			setcookie('servicegateway', '', time()-3600);
			setcookie('token', '', time()-3600);
			setcookie('inforelay', '', time()-3600);
			setcookie('webrelay', '', time()-3600);
			
			$message = 	 "<br>$api Response=><br>". 
						 'status:'.$status.'<br>'.
						 'Authentication Fail. You can try login without cache again.'.
						 "<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='Index.php';\"/>";
		}
		else if( $status == 214 )
		{
			$message = 	 "<br>$api Response=><br>". 
						 'status:'.$status.'<br>'.					
						 "<br>Error Tip: Duplicate name.<br>".
						 "<br>Duplicate name Error: You have to check $api api.<br>";
		}
		else if( $status == 218 )
		{
			$message = 	 "<br>$api Response=><br>". 
						 'status:'.$status.'<br>'.					
						 "<br>Error Tip: The folder does not exist or has been deleted.<br>".
						 "<br>Error: You have to check $api api.<br>";
		}	
		else if( $status == 219 )
		{
			$message = 	 "<br>$api Response=><br>". 
						 'status:'.$status.'<br>'.						
						 "<br>Error Tip: The file does not exist or has been deleted.<br>".
						 "<br>Error: You have to check $api api.<br>";
		}	
		else
		{
			$message = 	 "<br>$api Response=><br>". 
						 'status:'.$status.'<br>'.				
						 "<br>Error : You have to check $api api.<br>".
						 "<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
		}	
		
		return $message;
	}	
?>
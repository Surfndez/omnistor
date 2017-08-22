<html>
<body>
<?php
include_once("PayloadStatus.inc.php");// For handling resposnse payload error  
include "APIFunction.inc.php";// The API function

class AccountInfo
{
	function getAccountInfo()
	{
		try 
		{				
			$userid = $_COOKIE["uid"];
			$callAPI = new APIFunction();
			$GetAccountInfoResponse = $callAPI->getAccountinfo();// Calling API
						
			if( $GetAccountInfoResponse['status'] == 0 )
			{			
				if( !empty($_GET['detail']) )// System will show user the details info of this WebStorsge ID 
				{					
					$info = null;
					while( list($key , $val) = each($GetAccountInfoResponse) ) 
					{						
						if( !is_array($val) )
						{	
							$info .= "[$key]: $val \\n";	
						}
						else
						{
							while( list($keyArray , $valArray) = each($val) )
							{		
								if( !is_array($valArray) )
								{	
									if( $keyArray == 'backuppc' || $keyArray == 'package' )
										$info .= "[$valArray]\\n";	
									else
										$info .= "  $keyArray : $valArray \\n";	
								}
								else
								{
									while( list($key_featurelist , $val_featurelist) = each($valArray) )
									{
										if( $key_featurelist == 'featurelist' )
											$info .= "  [$key_featurelist]\\n";	
										else
											$info .= "    $key_featurelist : $val_featurelist \\n";	
									}
								}
							}
						}
					}				
										
					echo "<script type='text/javascript'>alert(\"$userid \\n $info\"); window.location.href='../web/AccountInfo.php';</script>";						
				}
				
				echo "<h2>$userid<br>Account Info</h2>"; 						
				while( list($keyArray , $valArray) = each($GetAccountInfoResponse) ) 
				{					
					if( is_array($valArray) )
					{	
						while( list($key , $val) = each($valArray) )
						{					
							if( $key == 'capacity' )
							{
								echo "<p>Total Space: $val</p>";
								break;	
							}	
						}							
					}
					else
					{
						if( $keyArray == 'usedcapacity' && !is_array($valArray) )
							echo "<div>Used Space: $valArray</div>";							
					}
				}
			}
			else
			{	
				echo payloadStatusError('GetAccountInfo', $GetAccountInfoResponse['status']);
				echo "Some error.<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
				exit(1);	
			}			
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}	
}

$AccountInfo = new AccountInfo();
$AccountInfo->getAccountInfo();
?>
<br><button type="button" onclick="window.location.href='../web/AccountInfo.php?detail=1'">Get Account Info</button>
</body>
</html>
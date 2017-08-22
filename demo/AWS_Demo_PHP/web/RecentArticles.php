<html>
<body>
<?php
include_once("PayloadStatus.inc.php");// For handling resposnse payload error  
include "APIFunction.inc.php";// The API function

class RecentArticle
{	
	function doGetlatestchangefiles()
	{		
		try 
		{		
			$top = '10';// The quantity of files. The default is 10
			$targetroot = '-5';// System folder id: (1)My Collection:system.{package}.home.root | (2)My Backup:system.backup.root | (3)Sync System:system.sync.root
			$sortdirection = '1';// The way of sorting file: 0 = ASC | 1 = DESC 			
			
			$callAPI = new APIFunction();
			$GetlatestchangefilesResponse = $callAPI->getlatestChangefiles($top, $targetroot, $sortdirection);// Calling API
				
			$fileNO = 1;
			if( $GetlatestchangefilesResponse['status'] == 0 )
			{
				echo '<h2 title="To obtain a list of the last four weeks of transaction file (does not contain the deleted file)">10 Recent Changes</h2>'; 							
				while( list($key , $val) = each($GetlatestchangefilesResponse) ) 
				{
					if( is_array($val) && array_key_exists('rawfilename', $val) )
					{	
						$rawfilename = $val['rawfilename'];
						echo "<p>$fileNO.  $rawfilename</p>";
						$fileNO++;												
					}
				}
			}
			else
			{
				echo payloadStatusError('Getlatestchangefiles', $GetlatestchangefilesResponse['status']);
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

$RecentArticle = new RecentArticle();
$RecentArticle->doGetlatestchangefiles();
?>

</body>
</html>
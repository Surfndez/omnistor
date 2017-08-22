<?php	
	function alertErrorMessage($alertMessage)// Showing error message in pop-up window
	{
		echo "<script type='text/javascript'>alert(\"$alertMessage\"); window.location.href='../web/OperationPage.php';</script>";
		exit(1); 
	}
	
	function uploadError($errorCode)// For handling PHP upload file error
	{	
		switch( $errorCode )
		{
			case 1 : echo("<script type='text/javascript'>alert(\"Upload Error : Larger than upload_max_filesize.\"); window.location.href='../web/OperationPage.php';</script>");
			case 2 : echo("<script type='text/javascript'>alert(\"Upload Error : Larger than form MAX_FILE_SIZE.\"); window.location.href='../web/OperationPage.php';</script>");
			case 3 : echo("<script type='text/javascript'>alert(\"Upload Error : Partial upload.\"); window.location.href='../web/OperationPage.php';</script>");
			case 4 : echo("<script type='text/javascript'>alert(\"Upload Error : No file.\"); window.location.href='../web/OperationPage.php';</script>");
		}
		exit(1); 
	}
?>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>
<br>
<button type="button" style="width:100%; height:30px; font-weight:bold; color:#4F82C3;" onclick="window.location.href='../web/MainMenu.html'">Main Menu</button><br><br>
<hr>
<?php
include_once("PayloadStatus.inc.php");// For handling resposnse payload error  
include_once("OperationPageError.inc.php");// For handling user operation error and show message
include "OperationPageView.inc.php";// Display the view of OperationPage.php 
include "APIFunction.inc.php";// The API function

class OperationPage
{
	const FOLDER_BROWSE = 'folderBrowse';// For calling /folder/browse/ API 
	const FOLDER_BROWSE_MYSYNC = 'folderBrowseMySync';
	const FOLDER_CREATE = 'folderCreate';// For calling /folder/create/ API 
	const FOLDER_REMOVE = 'folderRemove';// For calling /folder/remove/ API
	const FILE_REMOVE = 'fileRemove';// For calling /file/remove/ API
	const MULTIPART_UPLOAD = 'multipartUpload';// For calling /webrelay/directupload/ API 
	const STREAMING_UPLOAD = 'StreamingUpload';// For calling /webrelay/initbinaryupload/, /webrelay/resumebinaryupload/, /webrelay/finishbinaryupload/ API 
	const DOWNLOAD = 'directDownload';// For calling /webrelay/directdownload/ API 
	const SET_SHARE_CODE = 'setadvancedsharecode';// For calling /fsentry/setadvancedsharecode/ API 
	const DELETE_SHARE_CODE = 'deletesharecode';// For calling /fsentry/deletesharecode/ API 
	const SET_ENTRY_MARK = 'setEntryMark';// For calling /fsentry/setentrymark/ API 
		
	function __construct()
	{
		$callAPI = new APIFunction();// For calling API function
		$this->callAPI = $callAPI;		
	}

	function selectAPI()
	{			
		if( isset($_GET['folderID']) )
			$this->callAPI(OperationPage::FOLDER_BROWSE);
		else if( !isset($_GET['targetFolderID']) )	
			$this->callAPI(OperationPage::FOLDER_BROWSE_MYSYNC);// The firsts time doing FolderBrowse API, the sample code setting Browse MySyncFolder
		
		if( isset($_POST['FolderCreate']) )
			$this->callAPI(OperationPage::FOLDER_CREATE);
			
		if( isset($_POST['Remove_x'], $_POST['Remove_y']) )
		{	
			if( !isset($_POST['folderID']) && !isset($_POST['fileID']) )
				alertErrorMessage("You didn't select file.");
			$this->callAPI(OperationPage::FOLDER_REMOVE);
			$this->callAPI(OperationPage::FILE_REMOVE); 
		}
		
		if( isset($_POST['Send']) )
			$this->callAPI(OperationPage::STREAMING_UPLOAD);
		
		if( isset($_POST['Download_x'], $_POST['Download_y']) )
			$this->callAPI(OperationPage::DOWNLOAD);
		
		if( isset($_GET['ShareState']) )
		{
			$targetFolderID = $_GET['targetFolderID'];
			$this->folderBrowse($targetFolderID);			
			require_once('ShareSetting.php'); 
		}
		
		if( isset($_POST['Share']) )
			$this->callAPI(OperationPage::SET_SHARE_CODE);
			
		if( isset($_POST['DelShare']) )
			$this->callAPI(OperationPage::DELETE_SHARE_CODE);
			
		if( isset($_GET['MarkID']) )
			$this->callAPI(OperationPage::SET_ENTRY_MARK);
	}
	
	function callAPI($apiName)
	{		
		if( $apiName == 'folderBrowseMySync' )
		{
			$MySyncFolderID = $this->getMySyncFolder();
			$this->folderBrowse($MySyncFolderID);
		}	
		
		if( $apiName == 'folderBrowse' )
			$this->folderBrowse($_GET['folderID']);
		
		if( $apiName == 'folderCreate' ) 
			$this->folderCreate();
		
		if( $apiName == 'folderRemove' ) 
			$this->folderRemove();
				
		if( $apiName == 'fileRemove' ) 
			$this->fileRemove();
		
		if( $apiName == 'StreamingUpload' ) 
			$this->streamingUpload();
		
		if( $apiName == 'directDownload' )
			$this->directDownload();
		
		if( $apiName == 'setadvancedsharecode' )
			$this->setadvancedsharecode();
			
		if( $apiName == 'deletesharecode' )
			$this->deletesharecode();
			
		if( $apiName == 'setEntryMark' )			
			$this->setEntryMark();
	}
	
	function getMySyncFolder()
	{
		$GetMySyncFolderResponse = $this->callAPI->getMySyncFolder();// Calling API
		if( $GetMySyncFolderResponse['status'] != '0' )
			echo payloadStatusError('GetMySyncFolder', $GetMySyncFolderResponse['status']);
		return $GetMySyncFolderResponse['id']; 
	}
	
	function folderBrowse($browseFolderID)
	{ 
		try 
		{				
			$this->browseFolderID = $browseFolderID;
			$type = null;// Browsing the specify file(or folder): FOLDER | DOC | IMAGE | VIDEO | MUSIC | OTHERS 
			$pageno = '1';// Page number
			$pagesize = '';// Amount of information access granted to a page 
			$sortby = '1';// The way of sorting file: 1 = Sort the catalog by file(or folder) name | 2 = Sort the catalog by time 
			$sortdirection = '0';// The way of sorting file: 0 = ASC | 1 = DESC 
						
			$FolderBrowseResponse = $this->callAPI->folderBrowse($browseFolderID,  $type, $pageno, $pagesize, $sortby, $sortdirection);// Calling API
						
			if( $FolderBrowseResponse['status'] == 0 )
			{	
				$parentID = $FolderBrowseResponse['parent'];
				showTopTableBtn($browseFolderID, $parentID);// The tool menu on the top.( Function: 1.Back to parent folder, 2.Download file, 3.Remove file/folder )
					
				while( list($keyArray , $valArray) = each($FolderBrowseResponse) ) 
				{					
					if( is_array($valArray) && array_key_exists('folder', $valArray) )
					{
						$id = $valArray['id'];
						$display = $valArray['rawfoldername'];
						
						if( !array_key_exists('markid', $valArray) )
							$valArray['markid'] = '0';
					
						if( $valArray['ispublic'] == '1' && $valArray['markid'] == '1' )// If file/folder was marked, the value of $valArray['markid'] is '1'
						{
							folderCondition($browseFolderID, 'PUBLIC_AND_MARK', $id, $display);
						}
						else if( $valArray['markid'] == '1' )
						{
							folderCondition($browseFolderID, 'MARK', $id, $display);
						}
						else if( $valArray['ispublic'] == '1' )
						{
							folderCondition($browseFolderID, 'PUBLIC', $id, $display);
						}
						else
						{
							folderCondition($browseFolderID, 'PRIVATE_AND_NOMARK', $id, $display);
						}
					}
					
					if( is_array($valArray) && array_key_exists('file', $valArray) )
					{
						$id = $valArray['id'];
						$display = $valArray['rawfilename'];
					
						if( !array_key_exists('markid', $valArray) )
							$valArray['markid'] = '0';
						
						if( $valArray['ispublic'] == '1' && $valArray['markid'] == '1' )// If file/folder was marked, the value of $valArray['markid'] is '1'
						{
							fileCondition($browseFolderID, 'PUBLIC_AND_MARK', $id, $display);
						}
						else if( $valArray['markid'] == '1' )
						{
							fileCondition($browseFolderID, 'MARK', $id, $display);
						}
						else if( $valArray['ispublic'] == '1' )
						{
							fileCondition($browseFolderID, 'PUBLIC', $id, $display);
						}
						else
						{
							fileCondition($browseFolderID, 'PRIVATE_AND_NOMARK', $id, $display);
						}	
					}				
				}// while loop end				
					
				showBelowTableBtn();// ( Function: 1.Create Folder, 2.Upload file )	
			}
			else
			{				
				echo payloadStatusError('FolderBrowse', $FolderBrowseResponse['status']);
				exit(1);
			}			
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}
		
	function folderCreate()
	{
		try 
		{					
			$targetFolderID = $_POST['targetFolderID'];// Parent folder's id
			$isencrypted = '0';
			$foldername = $_POST['foldername'];// Folder's name 
			
			if( empty($_POST['foldername']) )// If user didn't type folder name
			{	
				echo "<script type='text/javascript'>alert(\"You didn't type folder name\"); window.location.href='../web/OperationPage.php';</script>";
				exit(1); 	
			}	
					
			$FolderCreateResponse = $this->callAPI->folderCreate($targetFolderID, $isencrypted, $foldername);// Calling API
			$this->handleResponse('FolderCreate', $FolderCreateResponse['status'], $targetFolderID, null, 0);// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}	
	
	function folderRemove()
	{			
		if( !isset($_POST['folderID']) )// If user didn't select folder
			return;
		
		try 
		{					
			$ischildonly = '0';// 0 = Delete this folder | 1 = Delete the child folder of this folder
			
			/* Compose removed files' id (Removed files can have one value) */ 
			$IDS = $_POST['folderID'];// Removing the specify folder's id
			$ID = null;
			
			if( !is_array($IDS) )
			{
				$ID = $IDS;
			}
			else
			{
				foreach($IDS as $value)
				{
					$ID .= $value.',';
				}
				$ID = substr_replace($ID, '', -1);				
			}			
						
			$FolderRemoveResponse = $this->callAPI->folderRemove($ID, $ischildonly);// Calling API
			$this->handleResponse('FolderRemove', $FolderRemoveResponse['status'], $_POST['targetFolderID'], null, 0);// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}
	
	function fileRemove()
	{	
		if( !isset($_POST['fileID']) )// If user didn't select file
			return;		
		
		try 
		{					
			/* Compose removed files' id (Removed files can have one value) */ 
			$IDS = $_POST['fileID'];// Removing the specify folder's id
			$ID = null;
			
			if( !is_array($IDS) )
			{
				$ID = $IDS;
			}
			else
			{				
				foreach($IDS as $value)
				{
					$ID .= $value.',';
				}
				$ID = substr_replace($ID, '', -1);
			}
			
			$FileRemoveResponse = $this->callAPI->fileRemove($ID);// Calling API
			$this->handleResponse('FileRemove', $FileRemoveResponse['status'], $_POST['targetFolderID'], null, 0);// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}
	
	function StreamingUpload()
	{
		if( $_FILES['userfile']['error'] > 0 )	
		{	
			uploadError($_FILES['userfile']['error']);// Upload file error code handdle	
			return false;	
		}	
				
		try 
		{					
			$parentFolderID = $_POST['targetFolderID'];// Uploading the specify file's id					
			$transid = null;// If the value of "transid" exists. Then you can get the uploaded total size in last time. Using this information can continue uploading since the last time					
			
			$file = $_FILES['userfile']['tmp_name'];// The system temp file
			$fileName = $_FILES['userfile']['name'];							
			
			//STEP1. calling InitBinaryUpload api
			$InitResponse = $this->callAPI->initBinaryUpload($parentFolderID, $transid, $file, $fileName);// Calling API
			if( $InitResponse['status'] == 0 )// Get the latestchecksum and show result message for client
			{	
				$transid = $InitResponse['transid'];// Get the transid
				$latestchecksum = null;
				
				if( array_key_exists('latestchecksum', $InitResponse) && !empty($InitResponse['latestchecksum']) )
				{
					$latestchecksum = $InitResponse['latestchecksum'];					
					echo "<br>The latestchecksum is : $latestchecksum <br>";
				}	
			}
			else
			{
				echo payloadStatusError('InitBinaryUpload', $InitResponse['status']);
				return false;// Terminated the step1, it has no need to call ResumeBinaryUpload Upload API
			}
			
			//STEP2. calling ResumeBinaryUpload api			
			$ResumeResponse = $this->callAPI->resumeBinaryUpload($transid, $file, $fileName);// Calling API			
			if( $ResumeResponse['status'] != 0 )
			{	
				echo payloadStatusError('ResumeBinaryUpload', $ResumeResponse['status']);
				return false;// Terminated the step2, it has no need to call FinishBinaryUpload Upload API
			}	
			
			//STEP3. calling FinishBinaryUpload api			
			$FinishResponse = $this->callAPI->finishBinaryUpload($latestchecksum, $transid, $file, $fileName);// Calling API			
			if( $FinishResponse['status'] == 0 )
			{						
				$targetFolderID = $_POST['targetFolderID'];	
				echo "<script type='text/javascript'>alert('Finish'); window.location.href='../web/OperationPage.php?folderID=$targetFolderID';</script>";
			}
			else
			{				
				echo payloadStatusError('FinishBinaryUpload', $FinishResponse['status']);
				exit(1);
			}	
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}
	
	function directDownload()
	{
		if( empty($_POST['fileID']) )
			alertErrorMessage("You didn't choose the file.");
		
		if( isset($_POST['folderID']) )
			alertErrorMessage("You have to choose the file.");
			
		try 
		{	
			while( list($key, $val) = each($_POST['fileID']))
				$fileID = $val;// Directdownloading the specify file's id
			
			$preview = 0;// Preview file	
			$of = null;// Start download at file offset
			$cpt = null;// The video file resolve information for transcoding
				
			$DirectDownloadResponse = $this->callAPI->directDownload($fileID, $preview, $of, $cpt);// Calling API			
			$this->handleResponse('DirectDownload', $DirectDownloadResponse['status'], $_POST['targetFolderID'], 'Download Finish. Please Check out \"D directory\"');// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}

	function setadvancedsharecode()
	{
		try 
		{	
			if( $_POST['PwdSetting'] == 1 )// If user want to set password. User have to delete sharecode first.
				$this->deletesharecode();
		
			if( isset($_POST['FileType']) )// When client setting password
			{
				if( $_POST['FileType'] == 'folder' )
					$isfolder = '1';
				else	
					$isfolder = '0';	
			}		
			
			if( isset($_POST['ShareID']) )	
				$entryid = $_POST['ShareID'];
			
			$password = '';// The file's password, it would require using lower-case letters and encrypt the password to its MD5 hash	
			if( isset($_POST['Password']) )
				$password = $_POST['Password'];							
		
			$clearshare = '';// If this value is equals "1", it means this folder isn't shared	
			$isgroupaware = $_POST['isgroupaware'];// If this value equals "1", it means this shared folder is collaboration folder	
			$validityduration = $_POST['validityduration'];// Expiration time(hour)			
			$folderquota = $_POST['folderquota'];// Collaboration quota(byte)				
			$shareforuserid = $_POST['shareforuserid'];// Collaborator UserID				
			
			$SetAdvancedSharecodeResponse = $this->callAPI->setAdvancedSharecode($isfolder, $entryid, $validityduration, $folderquota, $password, $clearshare, $isgroupaware, $shareforuserid);// Calling API			
			$this->handleResponse('SetAdvancedSharecode', $SetAdvancedSharecodeResponse['status'], $_POST['targetFolderID'], 'Finish');// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}
	
	function deletesharecode()
	{
		try 
		{		
			if( isset($_POST['ShareID']) )	
				$fileID = $_POST['ShareID'];// Shared file ID	
			
			if( $_POST['FileType'] == 'folder' )
				$entrytype = '0';// The shared file's type: 0 = Folder | 1 = File
			else	
				$entrytype = '1';
				
			$entryid = $fileID;// Sharing the specify file's id(or folder's id)			
			$password = "";// The file's password, it would require using lower-case letters and encrypt the password to its MD5 hash	
			
			$DeletesharecodeResponse = $this->callAPI->deleteSharecode($entrytype, $entryid, $password);// Calling API			
			$this->handleResponse('Deletesharecode', $DeletesharecodeResponse['status'], $_POST['targetFolderID'], 'Finish');// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}
	
	function setEntryMark()
	{	
		$mark = $_GET['Mark'];// Setting the file(or folder) mark. It can be more than two values, and separated by space. If this parameter is an empty string, system will clear the mark
		$fileID = $_GET['MarkID'];// Shared file ID	
				
		try 
		{		
			if( $_GET['Type'] == 'file' )
				$isfolder = '0';// The shared target's type: 0 = File | 1 = Folder
			else	
				$isfolder = '1';
				
			$entryid = $fileID;// Sharing the specify file's id(or folder's id)			
			$markid = $mark;// Setting the file(or folder) mark. It can be more than two values, and separated by space. If this parameter is an empty string, system will clear the mark
			
			$SetEntryMarkResponse = $this->callAPI->setEntryMark($isfolder, $entryid, $markid);// Calling API			
			$this->handleResponse('SetEntryMark', $SetEntryMarkResponse['status'], $_GET['targetFolderID'], null, 0);// Handle response value( The correct response payload's status value must equals "0" )
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage()."<br><br><br><input type=\"button\" value=\"Back\" onClick=\"window.location='OperationPage.php';\"/>";
			exit(1);
		}
	}

	function handleResponse($apiName, $status, $targetFolderID = null, $msg = null, $ifShowMsg = 1 )
	{
		if( $status == 0 )
		{		
			echo "<script type='text/javascript'>";
			
			if( $ifShowMsg )
				echo "alert(\"$msg\");";
			
			echo "window.location.href='../web/OperationPage.php";
			
			if( !is_null($targetFolderID) )
				echo "?folderID=$targetFolderID";
			
			echo " ';",
				 "</script>";
		}
		else
		{				
			echo payloadStatusError($apiName, $status);						
			exit(1);
		}	
	}	
}

$OperationPage = new OperationPage();
$OperationPage->selectAPI();
?>
</body>
</html>
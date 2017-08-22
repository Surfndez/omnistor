<?php
class APIFunction
{
	function __construct()
	{	
		$sid = 'INSERT_SID_HERE';// It has been provided from Creative Cloud Web		
		$progkey = 'INSERT_PROGKEY_HERE';// It has been provided from Creative Cloud Web	
		
		$this->sid = $sid; 
		$this->progkey = $progkey; 
		
		if( isset($_COOKIE["uid"]) )
			$this->userid = $_COOKIE["uid"];			
		if( isset($_COOKIE["password"]) )	
			$this->pwd = $_COOKIE['password'];			
		if( isset($_COOKIE["token"]) )	
			$this->token = $_COOKIE['token'];			
		if( isset($_COOKIE["servicegateway"]) )	
			$this->servicegateway = $_COOKIE['servicegateway'];
		if( isset($_COOKIE["inforelay"]) )
			$this->inforelay = $_COOKIE['inforelay'];
		if( isset($_COOKIE["webrelay"]) )
			$this->webrelay = $_COOKIE['webrelay'];
	}
	
	function requestServiceGateway($userid, $pwd)
	{
		require_once('/CreativeCloudAPI/RequestServiceGateway.php'); 
		$requestservicegateway = new RequestServiceGateway($this->sid, $userid, $pwd);
		$requestservicegateway->payload();
		$RequestservicegatewayResponse = $requestservicegateway->getResponse();		
		return $RequestservicegatewayResponse; 
	}
	
	function acquireToken($userid, $pwd, $servicegateway, $OTP, $CAPTCHA)
	{
		require_once('/CreativeCloudAPI/AcquireToken.php'); 
		$acquiretoken = new AcquireToken($this->sid, $this->progkey, $userid, $pwd, $servicegateway);
		$acquiretoken->payload($OTP, $CAPTCHA);
		$AcquireTokenResponse = $acquiretoken->getResponse();
		return $AcquireTokenResponse; 
	}
	
	function getMySyncFolder()
	{
		require_once('../CreativeCloudAPI/GetMySyncFolder.php'); 
		$getmysyncfolder = new GetMySyncFolder($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$getmysyncfolder->payload();
		$GetMySyncFolderResponse = $getmysyncfolder->getResponse();		
		return $GetMySyncFolderResponse; 
	}
	
	function folderBrowse($browseFolderID, $type, $pageno, $pagesize, $sortby, $sortdirection)
	{ 
		require_once('../CreativeCloudAPI/FolderBrowse.php'); 
		$folderbrowse = new FolderBrowse($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$folderbrowse->payload($browseFolderID, $type, $pageno, $pagesize, $sortby, $sortdirection);
		$FolderBrowseResponse = $folderbrowse->getResponse();
		return $FolderBrowseResponse; 
	}
		
	function folderCreate($targetFolderID, $isencrypted, $foldername)
	{		
		require_once('../CreativeCloudAPI/FolderCreate.php'); 
		$foldercreate = new FolderCreate($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$foldercreate->payload($targetFolderID, $isencrypted, $foldername);
		$FolderCreateResponse = $foldercreate->getResponse();			
		return $FolderCreateResponse;
	}	
	
	function folderRemove($ID, $ischildonly)
	{				
		require_once('../CreativeCloudAPI/FolderRemove.php'); 
		$folderremove = new FolderRemove($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$folderremove->payload($ID, $ischildonly);
		$FolderRemoveResponse = $folderremove->getResponse();		
		return $FolderRemoveResponse;
	}
	
	function fileRemove($ID)
	{					
		require_once('../CreativeCloudAPI/FileRemove.php'); 
		$fileremove = new FileRemove($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$fileremove->payload($ID);
		$FileRemoveResponse = $fileremove->getResponse();
		return $FileRemoveResponse;
	}
	
	function setAdvancedSharecode($isfolder, $entryid, $validityduration, $folderquota, $password, $clearshare, $isgroupaware, $shareforuserid)
	{		
		require_once('../CreativeCloudAPI/SetAdvancedSharecode.php'); 
		$setadvancedsharecode = new SetAdvancedSharecode($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$setadvancedsharecode->payload($isfolder, $entryid, $validityduration, $folderquota, $password, $clearshare, $isgroupaware, $shareforuserid);
		$SetAdvancedSharecodeResponse = $setadvancedsharecode->getResponse();
		return $SetAdvancedSharecodeResponse;
	}
	
	function getAdvancedSharecode($isfolder, $entryid)
	{		
		require_once('../CreativeCloudAPI/GetAdvancedsharecode.php'); 
		$getadvancedsharecode = new GetAdvancedSharecode($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$getadvancedsharecode->payload($isfolder, $entryid);
		$GetAdvancedSharecodeResponse = $getadvancedsharecode->getResponse();
		return $GetAdvancedSharecodeResponse;
	}
	
	function deleteSharecode($entrytype, $entryid, $password)
	{		
		require_once('../CreativeCloudAPI/DeleteSharecode.php'); 
		$deletesharecode = new Deletesharecode($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$deletesharecode->payload($entrytype, $entryid, $password);
		$DeletesharecodeResponse = $deletesharecode->getResponse(); 
		return $DeletesharecodeResponse;
	}
	
	function setEntryMark($isfolder, $entryid, $markid)
	{			
		require_once('../CreativeCloudAPI/SetEntryMark.php'); 
		$setentrymark = new SetEntryMark($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$setentrymark->payload($isfolder, $entryid, $markid);
		$SetEntryMarkResponse = $setentrymark->getResponse();
		return $SetEntryMarkResponse;
	}
	
	function getlatestChangefiles($top, $targetroot, $sortdirection)
	{	
		require_once('../CreativeCloudAPI/Getlatestchangefiles.php'); 
		$getlatestchangefiles = new Getlatestchangefiles($this->sid, $this->userid, $this->pwd, $this->token, $this->inforelay);
		$getlatestchangefiles->payload($top, $targetroot, $sortdirection);
		$GetlatestchangefilesResponse = $getlatestchangefiles->getResponse();
		return $GetlatestchangefilesResponse;
	}	
	
	function getAccountinfo()
	{	
		require_once('../CreativeCloudAPI/GetAccountInfo.php'); 
		$getaccountinfo = new GetAccountInfo($this->sid, $this->userid, $this->pwd, $this->token, $this->servicegateway);
		$getaccountinfo->payload();
		$GetAccountInfoResponse = $getaccountinfo->getResponse();
		return $GetAccountInfoResponse;
	}	
	
	function initBinaryUpload($parentFolderID, $transid, $file, $fileName)
	{
		require_once('../CreativeCloudAPI/InitBinaryUpload.php'); 
		$initbinaryupload = new InitBinaryUpload($this->sid, $this->userid, $this->pwd, $this->token, $this->webrelay);
		$initbinaryupload->payload($parentFolderID, $transid, $file, $fileName);
		$InitBinaryuploadResponse = $initbinaryupload->getResponse(); 		
		return $InitBinaryuploadResponse;
	}
	
	function resumeBinaryUpload($transid, $file, $fileName)
	{
		require_once('../CreativeCloudAPI/ResumeBinaryUpload.php'); 
		$resumebinaryupload = new ResumeBinaryUpload($this->sid, $this->userid, $this->pwd, $this->token, $this->webrelay);
		$resumebinaryupload->payload($transid, $file, $fileName);
		$ResumeBinaryuploadResponse = $resumebinaryupload->getResponse(); 
		return $ResumeBinaryuploadResponse;
	}
	
	function finishBinaryUpload($latestchecksum, $transid, $file, $fileName)
	{
		require_once('../CreativeCloudAPI/FinishBinaryUpload.php'); 
		$finishbinaryupload = new FinishBinaryUpload($this->sid, $this->userid, $this->pwd, $this->token, $this->webrelay);		
		$finishbinaryupload->payload($transid, $latestchecksum, $file, $fileName);			
		$FinishBinaryuploadResponse = $finishbinaryupload->getResponse();			
		return $FinishBinaryuploadResponse;
	}

	function directDownload($fileID, $preview, $of, $cpt)
	{	
		require_once('../CreativeCloudAPI/DirectDownload.php'); 
		$directdownload = new DirectDownload($this->sid, $this->userid, $this->pwd, $this->token, $this->webrelay, $fileID, $preview, $of, $cpt);
		$directdownload->payload();
		$DirectDownloadResponse = $directdownload->getResponse();		
		return $DirectDownloadResponse;
	}
	
	function getResizedPhoto($photoFileID, $type, $preview)
	{	
		require_once('../CreativeCloudAPI/GetResizedPhoto.php'); 
		$getresizedphoto = new GetResizedPhoto($this->sid, $this->userid, $this->pwd, $this->token, $this->webrelay, $photoFileID, $type, $preview);
		$getresizedphoto->payload();
		$GetresizedphotoResponse = $getresizedphoto->getResponse(); 	
		return $GetresizedphotoResponse;
	}
}	
?>
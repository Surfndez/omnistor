<?php

class FolderBrowse
{
	private $sid;// It has been provided from Creative Cloud Web	
	private static $api = '/inforelay/browsefolder/';// Service api
	private $userid;// The user's Email address		
	private $pwd;// The user password would require using lower-case letters and encrypt the password to its MD5 hash value
	
	function __construct($sid, $userid, $pwd, $token, $inforelay)
	{		
		$this->sid = $sid; 
		$this->userid = $userid; 
		$this->pwd = $pwd; 
		$this->token = $token; 
		$this->inforelay = $inforelay; 
	} 
	
	/* Represents an entire HTML or XML document.
	 * Preparing for DOMDocument. This class is available at: 
	 * http://www.php.net/manual/en/class.domdocument.php
	 * */	
	function payload($folderID, $type, $pageno, $pagesize, $sortby, $sortdirection)
	{
		$page = '';// The tag name is "page"
		
		$dom = new DOMDocument('1.0');
		$dom->encoding = 'UTF-8';
		$root = $dom->createElement('browse');// The root element of request payload 
				$dom->appendChild($root);
		$item = $dom->createElement('token', $this->token);
				$root->appendChild($item);
		$item = $dom->createElement('language', 'zh_TW');// The service language
				$root->appendChild($item);
		$item = $dom->createElement('userid', $this->userid);
				$root->appendChild($item);
		$item = $dom->createElement('folderid', $folderID);
				$root->appendChild($item);
		if( !is_null($type))
		{
		$item = $dom->createElement('type', $type);
				$root->appendChild($item);
		}
		$itemPage = $dom->createElement('pageno', $pageno);
				$item->appendChild($itemPage);	
		$itemPage = $dom->createElement('pagesize', $pagesize);
				$item->appendChild($itemPage);		
		$item = $dom->createElement('sortby', $sortby);
				$root->appendChild($item);		
		$item = $dom->createElement('sortdirection', $sortdirection);
				$root->appendChild($item); 
		$xmlStr = $dom->saveXML();
	
		$this->xmlStr = $xmlStr; 
	}
	
	function getResponse()
	{			
		$dataCenter = $this->inforelay;// Connection server		
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
			echo '<br>'.curl_error($ch);
			exit('<br>Error : You have to check HttpsURLConnection or Input payload.<br>');
		}
		
		curl_close($ch); //close curl
		
		/* Get the response from the server and parse it */	
		$pos = strpos($response, "<?xml version='1.0' encoding='utf-8'?>");		
		$output = substr($response, $pos);// Find out output payload
		
		return $this->parse($output);// Parsing XML payload
	}
	
	/* Get the response from the server and parse it */	
	function parse($xml) 
	{
		$xml = simplexml_load_string($xml);
				
		$FolderBrowseResponse = array();
		
		foreach($xml->children() as $child)
		{							
			$parentfolder = array();
			$page = array();
			$folder = array();
			$file = array();			
				
			if( $child->getName() == 'status')
			{
				$status = $child;
				$FolderBrowseResponse['status'] = $status;
			}	
			else if( $child->getName() == 'rawfoldername')
			{
				$rawfoldername = $child;
				$FolderBrowseResponse['rawfoldername'] = $rawfoldername;
			}	
			else if( $child->getName() == 'parent')
			{
				$parent = $child;
				$FolderBrowseResponse['parent'] = $parent;
			}	
			else if( $child->getName() == 'rootfolderid')
			{
				$rootfolderid = $child;
				$FolderBrowseResponse['rootfolderid'] = $rootfolderid;
			}				
			else if( $child->getName() == 'owner')
			{
				$owner = $child;
				$FolderBrowseResponse['owner'] = $owner;
			}
			
			switch($child->getName())
			{
				case 'page':	
					$page['page'] = 'page';
					foreach($child->children() as $pagechild)
					{
						switch($pagechild->getName())
						{
							case 'pageno':   		 	$page['pageno'] = $pagechild; break;
							case 'pagesize':   			$page['pagesize'] = $pagechild; break;	
							case 'totalcount':   		$page['totalcount'] = $pagechild; break;	
							case 'hasnextpage':   		$page['hasnextpage'] = $pagechild; break;	
						}
					}					
					array_push($FolderBrowseResponse, $page);
					break;
				case 'folder':
					$folder['folder'] = 'folder';				
					foreach($child->children() as $folderchild)
					{
						switch($folderchild->getName())
						{
							case 'id':   		 		$folder['id'] = $folderchild; break;
							case 'rawfoldername':   	$folder['rawfoldername'] = $folderchild; break;	
							case 'treesize':   			$folder['treesize'] = $folderchild; break;	
							case 'isgroupaware':   		$folder['isgroupaware'] = $folderchild; break;	
							case 'isbackup':   			$folder['isbackup'] = $folderchild; break;	
							case 'isorigdeleted':   	$folder['isorigdeleted'] = $folderchild; break;	
							case 'ispublic':   			$folder['ispublic'] = $folderchild; break;	
							case 'createdtime':   		$folder['createdtime'] = $folderchild; break;	
							case 'markid':   			$folder['markid'] = $folderchild; break;	
						}
					}
					array_push($FolderBrowseResponse, $folder);
					break;
				case 'file': 	
					$file['file'] = 'file';				
					foreach($child->children() as $filechild)
					{
						switch($filechild->getName())
						{
							case 'id':   		 		$file['id'] = $filechild; break;
							case 'rawfilename':   		$file['rawfilename'] = $filechild; break;	
							case 'isgroupaware':   		$file['isgroupaware'] = $filechild; break;	
							case 'size':   				$file['size'] = $filechild.' Byte'; break;	
							case 'isbackup':   			$file['isbackup'] = $filechild; break;	
							case 'isorigdeleted':   	$file['isorigdeleted'] = $filechild; break;	
							case 'isinfected':   		$file['isinfected'] = $filechild; break;	
							case 'ispublic':   			$file['ispublic'] = $filechild; break;	
							case 'headversion':   		$file['headversion'] = $filechild; break;	
							case 'createdtime':   		$file['createdtime'] = $filechild; break;	
							case 'markid':   			$file['markid'] = $filechild; break;
						}
					}
					array_push($FolderBrowseResponse, $file);
					break;
			}//end switch			
			
			unset($page);
			unset($folder);
			unset($file);
		}		
		
		return $FolderBrowseResponse;			
	}
}
?>
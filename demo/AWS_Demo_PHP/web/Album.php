<script type="text/javascript" src="../dojo-release-1.7.2-src/dojo/dojo.js" djconfig="isDebug:true,parseOnLoad: true,bindEncoding:'UTF-8'"></script>
<style type="text/css">
	@import "../dojo-release-1.7.2-src/dojox/image/resources/image.css";
</style>	
<script type="text/javascript">
  dojo.require("dojox.image.Gallery");
  dojo.require("dojo.data.ItemFileReadStore");
</script>

<?php
include_once("PayloadStatus.inc.php");// For handling resposnse payload error 
include "APIFunction.inc.php";// The API function

class ResizeedPhoto
{
	function __construct()
	{
		$callAPI = new APIFunction();// For calling API function
		$this->callAPI = $callAPI;		
	}
	
	function getMySyncFolder()
	{
		$GetMySyncFolderResponse = $this->callAPI->getMySyncFolder();// Calling API
		if( $GetMySyncFolderResponse['status'] != '0' )
		{
			echo payloadStatusError('GetMySyncFolder', $GetMySyncFolderResponse['status']);
			exit(1);
		}	
		$this->folderBrowse($GetMySyncFolderResponse['id']); 
	}
	
	function folderBrowse($folderID)
	{
		try 
		{					
			$type = 'IMAGE';// Browsing the specify file(or folder): FOLDER | DOC | IMAGE | VIDEO | MUSIC | OTHERS 
			$pageno = '1';// Page number
			$pagesize = '';// Amount of information access granted to a page 
			$sortby = '1';// The way of sorting file: 1 = Sort the catalog by file(or folder) name | 2 = Sort the catalog by time 
			$sortdirection = '0';// The way of sorting file: 0 = ASC | 1 = DESC 
			
			$FolderBrowseResponse = $this->callAPI->folderBrowse($folderID, $type, $pageno, $pagesize, $sortby, $sortdirection);// Calling API
			
			$FileIDArray = array();
			if( $FolderBrowseResponse['status'] == 0 )
			{	
				$msg = '';
				if( file_exists("images.txt") ) 
					unlink("images.txt");	
					
				while( list($keyArray , $valArray) = each($FolderBrowseResponse) ) 
				{
					if( is_array($valArray) && array_key_exists('file', $valArray) )
					{				
						$value = '';
						$fileType = '';	
						
						if( !is_null($valArray['rawfilename']) )
						{							
							while( list($key , $val) = each($valArray) )
							{								
								if( $key == 'id' )
								{
									$value = $val;
									$msg .= $this->getResizedPhoto($value);				
								}
							}
						}
					}
				}					
				
				$this->writeFile($msg);	
			}
			else
			{
				echo payloadStatusError('FolderBrowse', $FolderBrowseResponse['status']);
				exit(1);
			}
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage();
			exit(1);
		}
	}	
	
	function getResizedPhoto($ID)
	{
		try 
		{					
			$photoFileID = $ID;// Resizing the specify photo's id
			$type = '3';// Resized photo SizeType: 0 = 1024X768 | 1 = 100X100 | 2 = 50X50 | 3 = 640X480 | 4 = 150X150			
			$preview = '1';// Preview file
			
			$url = $this->callAPI->getResizedPhoto($photoFileID, $type, $preview);// Calling API				
			$msg = "{thumb: \"$url\", large: \"$url\", title: \"My Album\", link: \"$url\" },";
  
			return $msg;
		} 
		catch(Exception $e) 
		{
			echo "Caught exception: ".$e->getMessage();
			exit(1);
		}
	}

	function writeFile($msg)
	{
		$myFile = "images.txt";
		$fh = fopen($myFile, 'a') or die("can't open file");
		fwrite($fh, "{items: [ ".$msg." ]}");
		fclose($fh);
	}
}	

$ResizeedPhoto = new ResizeedPhoto();
$ResizeedPhoto->getMySyncFolder();
?>

<body> 
<br><button type="button" style="width:100%; height:30px; font-weight:bold; color:#4F82C3;" onclick="window.location.href='MainMenu.html'">Main Menu</button>
<br><br><hr><font style="font-weight:bold; line-height:29pt; color:#4F82C3;"><h2>MySyncFolder Album</h2></font>
<p>Here is a MySyncFolder album sample.(Calling /webrelay/getresizedphoto/, /folder/browse/ API)</p>
<p>It can show *JPG(or PNG) files from MySyncFolder.(*The JPG(or PNG) files of the same parent folder)</p>
<p>This album browsing MySync folder to be a sample, you can get your brilliant ideas to design another application.</p>
<p>Preparing for DOJO ItemFileReadStore. Documentation is available at : <a href="http://dojotoolkit.org/reference-guide/1.7/dojo/data/ItemFileReadStore.html" target="_blank">http://dojotoolkit.org/reference-guide/1.7/dojo/data/ItemFileReadStore.html</a></p>
<br><div dojotype="dojox.image.Gallery" id="picker"></div>

<script type="text/javascript">
function init()
{
	var imageItemStore = new dojo.data.ItemFileReadStore({ url: 'images.txt' });

	// Define the request, saying that 20 records should be fetched at a time,and to start at record 0
    var request = 
	{
      count: 20,
	  start:0
    };
	
	// Tell the widget to request the "thumb" and "large" parameter, as different stores may use different parameter names
    var itemNameMap = 
	{
      imageThumbAttr: "thumb",
      imageLargeAttr: "large"
    };
	
	// Call the setDataStore function, passing it the data store, the request object, and the name map
	dijit.byId('picker').setDataStore(imageItemStore, request, itemNameMap);
}

dojo.addOnLoad(init);

</script>
</div>
</body>
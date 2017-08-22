<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>
<hr><font style="font-size:20px; font-weight:bold; line-height:29pt; color:#4F82C3;">Sharing Setting</font>
<form name="shareForm" action="OperationPage.php" method="post">
<table>
	<tr>
		<td>
			<div id='Shared' style='visibility:hidden'><b>Status:</b> Shared</div>
			<div id='SharedReadme' style='visibility:hidden'>You've shared this file or folder. You can use following ways to share this file or folder with your friend or colleague.</div>
			<div id='NotShared' style='visibility:hidden'><b>Status:</b> Not shared</div>
			<div id='NotSharedReadme' style='visibility:hidden'>Click "Share" button to share documents, photos, or files with your friend or colleague. 
				<i> * denotes required fields.</i>.
			</div>			
			<div id='collaborationParameter' style='visibility:hidden'>
				<br/>
				<b>*Collaboration</b>:
				<select name="isgroupaware">
					<option value="0" selected>NO</option>
					<option value="1">YES</option>
				</select><br/>You and your friends can add, delete, and edit the files in the assigned folder. All the collaborators will see the update in this folder.
				<br/>					
				<ol>					
					<li>Expiration date(hour): <input type="text" name="validityduration" value=''>(optional)<br/></li>
					<li>Collaboration quota(byte):<input type="text" name="folderquota" value=''>(optional)<br/></li>	
					<li>Collaborator: <input type="text" name="shareforuserid[]" value=''>(optional)
						<span id="fieldSpace"></span> 				
						<a href="javascript:" onclick="addField()">Add Field</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:" onclick="delField()">Delete Field</a>	
					</li>
				</ol>	
				<br/>					
			<div>
		</td>	
	</tr>	
	<?php
	class ShareSet
	{		
		function __construct()
		{
			$callAPI = new APIFunction();// For calling API function
			$this->callAPI = $callAPI;		
		}		
			
		function initSet()
		{			
			$ShareID = $_GET['ShareID'];
			$FileType = $_GET['FileType'];								
			$IfShare = $_GET['ShareState'];// If this value is equals "1", it means this file/folder is shared	
			$targetFolderID = $_GET['targetFolderID'];// Parent folder ID
			$display = $_GET['display'];// The file/folder display			
			
			echo "<script type='text/javascript'>window.scrollTo(0,document.body.scrollHeight);</script>";// Scroll to the Share menu
			echo "You are setting $FileType: $display";
						
			if( $IfShare )// If file/folder is shared, the page will show these info
			{						
				if( $FileType == 'folder' )
					$isfolder = '1';
				else	
					$isfolder = '0';	
					
				$ispasswordneeded = $this->getAdvancedsharecode($isfolder, $ShareID);
				
				if( $ispasswordneeded == 0 )
					$this->desplayStateImage('setPassword()', '../image/Icon_209.ico', 'You hava not set up password');
				else 
					$this->desplayStateImage('cancelPassword()', '../image/Icon_210.ico', 'Password protected');
				
			}
			
			echo "<tr><td>",
				 "<input type='hidden' name='targetFolderID' value=\"$targetFolderID\">",
				 "<input type='hidden' name='PwdSetting' id='PwdSetting'>",
				 "<p id='Tip' style='visibility:hidden'><i>Please Click \"Save\" button to saving your setting.</i></p>",
				 "<br><p id='PasswordTxt' style='visibility:hidden'>Password:<input type='Password' name='Password' id='Pwd'></p>",
				 "<input type='hidden' name='ShareID' id='ShareID' value=\"$ShareID\" >",
				 "<input type='hidden' name='FileType' id='FileType' value=\"$FileType\">";
				 "</td></tr>";	
		}
		
		function desplayStateImage($doFunction, $imagePath, $title)
		{
			echo "<tr><td>",	
				 "<br>Password Setting(Optional):<br>",	
				 "<a href='#' onclick=\"$doFunction\" style='text-decoration:none'>",
				 "<img src=\"$imagePath\" height='70' width='70' title=\"$title\"/></a>",
				 "</td></tr>";				
		}
					
		function getAdvancedsharecode($isfolder, $entryid)
		{
			try 
			{					
				$GetAdvancedSharecodeResponse = $this->callAPI->getAdvancedSharecode($isfolder, $entryid);// Calling API	
			
				if( $GetAdvancedSharecodeResponse['status'] == 0 )
				{								
					$ispasswordneeded = $GetAdvancedSharecodeResponse['ispasswordneeded'];// If this value equals "1", it means this shared file/folder which need password	
					$sharecode = $GetAdvancedSharecodeResponse['sharecode'];
					
					if( isset($GetAdvancedSharecodeResponse['shareforuserid']) )
					{	
						$shareforuserid = $GetAdvancedSharecodeResponse['shareforuserid'];
						$length = count($shareforuserid);
						for ($i=0; $i<$length; $i++ ) 
						{
							echo "<tr><td>",
								 "This folder's collaborator: $shareforuserid[$i]</td></tr>";
						}
					}		
					
					echo "<tr><td>",
						 "You can go to <a href='https://oeo.la' target=_blank>https://oeo.la</a> and enter the share code <b>$sharecode</b> to get the shared files.</td></tr>";
					
					return $ispasswordneeded;
				}		  
				else
				{				
					echo payloadStatusError('GetAdvancedSharecodeResponse', $GetAdvancedSharecodeResponse['status']);							
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
	
	$ShareSet = new ShareSet();
	$ShareSet->initSet();
	?>
	<tr>
		<td>
			<input type="submit" name="Share" id="SettingButton" value="Save" style='visibility:hidden' onClick="reloadPage();">
			<input type="submit" name="Share" id="ShareButton" value="Share" style='visibility:hidden' onClick="reloadPage();">
			<input type="submit" name="DelShare" id="DelShareButton" value="Cancel Share" style='visibility:hidden' onClick="reloadPage();">
		</td>	
	</tr>	
	<tr>
		<td>
		<?php		
			$IfShare = $_GET['ShareState'];
			$FileType = $_GET['FileType'];	
			
			if( $IfShare )
			{		
				echo "<script>",
					 "document.getElementById('Shared').style.visibility = 'visible';",
					 "document.getElementById('SharedReadme').style.visibility = 'visible';",
					 "document.getElementById('DelShareButton').style.visibility = 'visible';",
					 "document.getElementById('ShareButton').style.visibility = 'visible';",
					 "</script>";
					 
				if( $FileType == 'folder' )
				{
					echo "<script>",
						 "document.getElementById('collaborationParameter').style.visibility = 'visible';",
						 "</script>";
				}
			}
			else
			{					
				echo "<script>",
					 "document.getElementById('NotShared').style.visibility = 'visible';",
					 "document.getElementById('NotSharedReadme').style.visibility = 'visible';",
					 "document.getElementById('ShareButton').style.visibility = 'visible';",
					 "</script>";					 
					 
				if( $FileType == 'folder' )
				{
					echo "<script>",
						 "document.getElementById('collaborationParameter').style.visibility = 'visible';",
						 "</script>";
				}
			}		
		?>
		</td>	
	</tr>
</table>
</form>

<script type="text/javascript"> 
function setPassword()// The first User have to call /fsentry/deletesharecode/ api. The second call /fsentry/getsharecode/ api with <password> paremeter 
{ 
	var password = prompt("\n\n Typing password:");
	if( !password )
	{		
		return;
	}
	else
	{	
		document.getElementById("PasswordTxt").style.visibility = "visible";
		document.getElementById("Pwd").style.visibility = "visible";	
		document.getElementById("Pwd").value = password;	
		document.getElementById("Tip").style.visibility = "visible";
		document.getElementById("SettingButton").style.visibility = "visible";
		document.getElementById("DelShareButton").style.visibility = "hidden";		
		document.getElementById("PwdSetting").value = '1';	
		
		alert("Please Click \"Save\" button to saving your setting.");	
	}
}

function cancelPassword()// The first User have to call /fsentry/deletesharecode/ api. The second call /fsentry/setadvancedsharecode/ api without <password> paremeter
{ 
	var type = confirm("Are you sure want to cancel password settng ?");
	if( !type )
	{		
		return;
	}
	else
	{	
		document.getElementById("SettingButton").style.visibility = "visible";
		document.getElementById("Tip").style.visibility = "visible";
		document.getElementById("DelShareButton").style.visibility = "hidden";
		document.getElementById("PwdSetting").value = '1';	
		
		alert("Please Click \"Save\" button to saving your setting.");
	}
}
 
function reloadPage()
{
	window.opener.location.href = window.opener.location.href;
	window.close();
}
</script>
<script type="text/javascript"> 
var countMin = 1; 
var commonName = "shareforuserid[]";
var count = countMin; 

function addField() 
{ 
	document.getElementById("fieldSpace").innerHTML += "<div>Collaborator" + (++count) + ': <input type="text" name="' + commonName + '"></div>';	 
}

function delField() 
{
	if (count > countMin) 
	{
		var fs = document.getElementById("fieldSpace"); 
		fs.removeChild(fs.lastChild);
		count--;
	} 
	else 
	{
		alert("This Field can't be deleted.");
	}	
}
</script>

</body>
</html>
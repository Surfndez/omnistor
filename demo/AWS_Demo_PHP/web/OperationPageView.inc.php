<?php
	/**
	*	It is the view of OperationPage
	*/
	function showTopTableBtn($browseFolderID, $parentID)// The top menu button of table
	{
		echo "<form name='sampleForm' action='OperationPage.php' method='post' enctype='multipart/form-data'>",	 	
			 "<table>",
			 "<tr>",
			 "<td>",
			 "<input type='hidden' name='type' value=\"folder\">",
			 "<input type='hidden' name='targetFolderID' value=\"$browseFolderID\">",
			 "<a href=\"OperationPage.php?folderID=$parentID\">",
			 "<img src=\"../image/Icon_177.ico\" title=\"Browse Parent Folder\" height='35' width='35' /></a>",
			 "<input type='image' src=\"../image/Icon_176.ico\" name='Download' alt='Submit button' height='35' width='35' title='Download One File' >",	
			 "<input type='image' src=\"../image/Icon_85.ico\" name='Remove' alt='Submit button' height='35' width='35' title='Remove files or folders' >",	
			 "</td>",
			 "</tr>";	
	}
	
	function showBelowTableBtn()// The menu button below table
	{
		echo "<tr>",
			 "<td>",					
			 "<input type='hidden' name='SetPassword' id='SetPassword'>",					
			 "<input type='hidden' name='Password' id='Password'>",					
			 "<input type='hidden' name='ShareID' id='ShareID'>",					
			 "<input type='hidden' name='FileType' id='FileType'>",
			 "</td>",
			 "</tr>",
			 
			 "<tr>",
			 "<td>",	
			 "Folder Name : <input type='text' name='foldername'>",
			 "<input type='submit' name='FolderCreate' value='Create Folder'>",
			 "</tr>",
			 "</td>",
			 
			 "<tr>",
			 "<td>",	
			 "Streaming Upload : <input type=\"file\" name=\"userfile\">",
			 "<input type='submit' name='Send' value='Upload'>",
			 "</td>",
			 "</tr>",			 
			 "</table>",
			 "</form>";	
	}
	
	function folderCondition($browseFolderID, $state, $id, $display)// The list of folder 
	{
		if( $state == 'PUBLIC_AND_MARK' )// The state of folder is public and marked
		{
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=folder&Mark=\">",
				 "<img src=\"../image/Icon_204.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='folderID[]' value=\"$id\">",
				 "<a href=\"OperationPage.php?folderID=$id\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_70.ico\" height='25' width='25' /> $display</a>",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=1&ShareID=$id&FileType=folder&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}
		else if( $state == 'MARK' )// The state of folder is marked
		{
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=folder&Mark=\">",
				 "<img src=\"../image/Icon_204.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='folderID[]' value=\"$id\">",
				 "<a href=\"OperationPage.php?folderID=$id\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_4.ico\" height='25' width='25' /> $display</a>",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=0&ShareID=$id&FileType=folder&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}
		else if( $state == 'PUBLIC' )// The state of folder is public
		{
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=folder&Mark=1\">",
				 "<img src=\"../image/Icon_181.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='folderID[]' value=\"$id\">",
				 "<a href=\"OperationPage.php?folderID=$id\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_70.ico\" height='25' width='25' /> $display</a>",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=1&ShareID=$id&FileType=folder&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}
		else// The state of folder is private
		{
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=folder&Mark=1\">",
				 "<img src=\"../image/Icon_181.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='folderID[]' value=\"$id\">",
				 "<a href=\"OperationPage.php?folderID=$id\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_4.ico\" height='25' width='25' /> $display</a>",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=0&ShareID=$id&FileType=folder&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}
	}
	
	function fileCondition($browseFolderID, $state, $id, $display)// The list of file 
	{
		if( $state == 'PUBLIC_AND_MARK' )// The state of file is public and marked
		{
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=file&Mark=\">",
				 "<img src=\"../image/Icon_204.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='fileID[]' value=\"$id\">",
				 "<img src=\"../image/Icon_115.ico\" height='25' width='25' /> $display",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=1&ShareID=$id&FileType=file&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}
		else if( $state == 'MARK' )// The state of file is marked
		{
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=file&Mark=\">",
				 "<img src=\"../image/Icon_204.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='fileID[]' value=\"$id\">",
				 "<img src=\"../image/Icon_15.ico\" height='25' width='25' /> $display",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=0&ShareID=$id&FileType=file&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}
		else if( $state == 'PUBLIC' )// The state of file is public
		{	
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=file&Mark=1\">",
				 "<img src=\"../image/Icon_181.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='fileID[]' value=\"$id\">",
				 "<img src=\"../image/Icon_115.ico\" height='25' width='25' /> $display",
				 "</td>",
				 
				 "<td align='50%'>",				
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=1&ShareID=$id&FileType=file&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",	
				 "</td>",
				 "</tr>";
		}	
		else// The state of file is private
		{	
			echo "<tr>",
				 "<td>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&MarkID=$id&Type=file&Mark=1\">",
				 "<img src=\"../image/Icon_181.ico\" height='25' width='25' /></a>",
				 "<input type='checkbox' name='fileID[]' value=\"$id\">",
				 "<img src=\"../image/Icon_15.ico\" height='25' width='25' /> $display",
				 "</td>",
				 
				 "<td align='50%'>",
				 "<a href=\"OperationPage.php?targetFolderID=$browseFolderID&ShareState=0&ShareID=$id&FileType=file&display=$display\" style='text-decoration:none'>",
				 "<img src=\"../image/Icon_110.ico\" height='25' width='25' title='Sharing Setting'/></a>",
				 "</td>",
				 "</tr>";
		}		
	}
?>

package net.yostore.aws.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ActivityMenu
{
	public static void openOptionsDialog(Activity act)
	{
		new AlertDialog.Builder(act)
					   .setTitle("About")
					   .setMessage("This is ASUS WebStorage OpenAPI Demo")
					   .setPositiveButton("OK", new DialogInterface.OnClickListener()
												{ public void onClick(DialogInterface dialog, int which){}}
							   			 )
					   .show();
					   
	}
}

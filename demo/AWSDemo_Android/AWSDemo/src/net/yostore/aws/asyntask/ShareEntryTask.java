package net.yostore.aws.asyntask;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.entity.GetShareCodeResponse;
import net.yostore.aws.api.exception.APIException;
import net.yostore.aws.api.helper.GetShareCodeHelper;
import net.yostore.aws.dialog.MessageDialog;
import net.yostore.aws.entity.FsInfo;
import net.yostore.aws.view.common.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ShareEntryTask extends AsyncTask<FsInfo, Integer, Integer>
{
	private static final String TAG = "ShareEntryTask";
	AsyncTask<FsInfo, Integer, Integer> task = this;
	private Context ctx;
	private ProgressDialog _mdialog;
	private ApiConfig apiCfg;

	String error = null;
	String shareUri;

	public ShareEntryTask(Context ctx, ApiConfig apiCfg)
	{
		this.ctx 	   = ctx;
		this.apiCfg    = apiCfg;
	}

	@Override
	protected Integer doInBackground(FsInfo... fInfo)
	{
		int rtn = 0;
		GetShareCodeHelper helper = new GetShareCodeHelper("1".equals(fInfo[0].entryId)?"0":"1", fInfo[0].entryId);
		try 
		{
			GetShareCodeResponse response = (GetShareCodeResponse)helper.process(apiCfg);
			if ( response == null )
			{
				error = "Can not connect to server!\rresponse is null";
				Log.e(TAG, error);
			}
			else if (response.getStatus() == 0 )
			{
				shareUri = response.getUri();
				rtn = 1;
			}
			else
			{
				error = "Can not connect to server!\rstatus:" + response.getStatus();
				Log.e(TAG, error);
			}
		} 
		catch (APIException e) 
		{
			error = "Can not connect to server!\r" + e.getMessage();
			Log.e(TAG, error, e);
		} 
		finally 
		{
			this.publishProgress(100);
		}
		return rtn;
	}

	@Override
	protected void onCancelled()
	{
		super.onCancelled();
		if ( _mdialog != null )
		{
			if ( _mdialog != null ) try { _mdialog.dismiss(); } catch ( Exception e ){}
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... values)
	{
		super.onProgressUpdate(values);
		if ( values[0] == 0 )
		{
			try
			{
				_mdialog = ProgressDialog.show(ctx, ctx.getString(R.string.app_name), "Connecting to server...", true, true, new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						task.cancel(true);
					}
				});
			}
			catch(Exception e){}
		}
		else
		{
			if ( _mdialog != null ) try { _mdialog.dismiss(); } catch ( Exception e ){}
		}
	}
	
	protected void onPostExecute(Integer result)
	{
		this.publishProgress(100);
		if ( result == 1 )
		{
			MessageDialog.show(ctx, ctx.getString(R.string.app_name), "ShareLink is \"" + shareUri + "\"");
		}
		else
		{
			Toast.makeText(ctx, error, Toast.LENGTH_LONG).show();
		}
	}
}

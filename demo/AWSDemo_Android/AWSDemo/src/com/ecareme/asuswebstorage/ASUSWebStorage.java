package com.ecareme.asuswebstorage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import net.yostore.aws.api.ApiConfig;
import net.yostore.aws.api.entity.ApiCookies;
import net.yostore.aws.api.entity.VideoInfo;
import net.yostore.aws.entity.FsInfo;
import net.yostore.aws.view.common.R;
import android.app.Application;
import android.os.Build;
import android.os.Environment;

public class ASUSWebStorage extends Application {
	public static ApiConfig apiCfg;
	public static List<FsInfo> processList = new LinkedList<FsInfo>();

	public static File cacheRoot;
	public static String downloadPath;
	public static String uploadBasePath;

	public static final boolean isDebug = false;
	
	public static List<VideoInfo> video;

	// @SuppressWarnings("static-access")
	@SuppressWarnings("static-access")
	@Override
	public void onCreate() {
		super.onCreate();

		apiCfg = new ApiConfig();
		video=new LinkedList<VideoInfo>();
		cacheRoot = new File(Environment.getExternalStorageDirectory()
				.getPath(), java.io.File.separator + "Android"
				+ java.io.File.separator + "data" + java.io.File.separator
				+ this.getApplicationInfo().packageName
				+ java.io.File.separator + "cache" + java.io.File.separator);
		try {
			if (!cacheRoot.exists())
				cacheRoot.mkdirs();
		} catch (Exception e) {
		}

		// Setting download target directory
		StringBuilder download = new StringBuilder();
		download.append(Environment.getExternalStorageDirectory().getPath())
				.append(java.io.File.separator)
				.append(getString(R.string.app_name))
				.append(java.io.File.separator).append("download")
				.append(java.io.File.separator);

		downloadPath = download.toString();

		// Setting upload file based searching directory
		uploadBasePath = Environment.getExternalStorageDirectory().getPath()
				+ java.io.File.separator;

		// Initial a loading message
		processList.add(new FsInfo(FsInfo.EntryType.Process, "Loading..."));

		// only for debug
		if (isDebug)
			apiCfg.SERVICEPORTAL = "192.168.1.225:8443";

		// Have to replace them to the values that have been assigned by ASUS
		// Creative Cloud
		ApiCookies.sid = "12345678";
		ApiCookies.progKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

		ApiCookies.v_ClientVersion = "1.0.1";
		ApiCookies.EEE_MANU_Maunfactory = Build.BRAND;
		ApiCookies.EEE_PROD_ProductModal = Build.PRODUCT;
	}

}

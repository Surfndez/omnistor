
package net.yostore.utility;

import java.io.*;
import java.util.*;

public final class FileLister
{
	private String dir = "./";
	private String extendedName = "";

	/* anonym inner class acts as the assistant to deal with extended name */
	private FileFilter filter = new FileFilter()
	{
		public boolean accept(File f)
		{
			if ( f.isDirectory() )
				return true;
			return f.getName().lastIndexOf("." + extendedName) != -1;
		}
	};

	public FileLister(String dir, String ext)
	{
		this.dir = dir;
		extendedName = ext;
	}

	private void walkThrough(File f, List dirLs, List fileLs)
	{
		File[] fs = f.listFiles(filter);

		for ( int i = 0; i < fs.length; i++ )
		{
			if ( fs[i].isDirectory() )
			{
				walkThrough(fs[i], dirLs, fileLs);
				dirLs.add(fs[i].getPath());
			}
			else
			{
				fileLs.add(fs[i].getPath());
			}
		}
	}

	public String[] listFile(boolean isDirectoryFirst, boolean ascend)
	{

		List dirLs = new ArrayList();
		List fileLs = new ArrayList();

		walkThrough(new File(dir), dirLs, fileLs);
		Object[] dirs = dirLs.toArray();
		Object[] fs = fileLs.toArray();
		Arrays.sort(dirs);
		Arrays.sort(fs);

		String[] s = new String[dirs.length + fs.length];

		if ( isDirectoryFirst && ascend )
		{// dir fiest and ascend
			for ( int j = 0; j < dirs.length; j++ )
			{
				s[j] = (String)dirs[j];
			}

			for ( int k = 0; k < fs.length; k++ )
			{
				s[dirs.length + k] = (String)fs[k];
			}
		}
		else if ( isDirectoryFirst && !ascend )
		{// dir first and not ascend
			for ( int j = 0; j < dirs.length; j++ )
			{
				s[j] = (String)dirs[dirs.length - j - 1];
			}

			for ( int k = 0; k < fs.length; k++ )
			{
				s[dirs.length + k] = (String)fs[fs.length - k - 1];
			}
		}
		else if ( !isDirectoryFirst && ascend )
		{// files first and ascend

			for ( int j = 0; j < fs.length; j++ )
			{
				s[j] = (String)fs[j];
			}
			for ( int k = 0; k < dirs.length; k++ )
			{
				s[fs.length + k] = (String)dirs[k];
			}
		}
		else
		{// files first and not ascend
			for ( int j = 0; j < fs.length; j++ )
			{
				s[j] = (String)fs[fs.length - j - 1];
			}

			for ( int k = 0; k < dirs.length; k++ )
			{
				s[fs.length + k] = (String)dirs[dirs.length - k - 1];
			}
		}
		return s;
	}

	public static void main(String[] args)
	{
		final String errTip = "Usage: java FileLister dir extendedName";

		// validate parameters
		if ( args.length != 2 || args[0] == null || args[0].length() < 1 || args[1] == null || args[1].length() < 1 )
		{
			System.err.println(errTip);
			System.exit(0);
		}

		String[] s = new FileLister(args[0].trim(), args[1].trim()).listFile(true, true);
		for ( int x = 0; x < s.length; x++ )
			System.out.println(s[x]);
	}
}

using System;
using System.Collections.Generic;
using System.Text;
using System.IO;


namespace Ecareme.Utility
{
    public class log
    {
        public static void writeLog(string message)
        {
            StreamWriter profileFile2;
            profileFile2 = new StreamWriter(System.Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + "\\WebStorageContentProfiler_debuglog.txt", true);
            profileFile2.WriteLine(DateTime.Now.ToString("HH:mm:ss:fff") + "," + message);
            profileFile2.Close();
            return;
        }
    }
}

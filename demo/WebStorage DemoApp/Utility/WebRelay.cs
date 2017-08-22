using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Net;
using System.IO;
using System.Text;
using System.Xml;
using System.Threading;
using System.Timers;
using Microsoft.Win32;
using Ecareme.Utility;
using System.Collections.Specialized;

namespace DemoApp
{
    class webUpload
    {

        public string fileName = "";
        public string parentFolderID = "";
        public string tokenString;
        public int progressValue = 0;
        public string webRelayURL;
        public string uploadAFile()
        {
            CookieContainer cookies = new CookieContainer();
            //add or use cookies
            NameValueCollection querystring = new NameValueCollection();
            querystring["pa"] = parentFolderID;
            querystring["d"] = Path.GetFileName(fileName);
            querystring["u"] = "";
            querystring["pr"] = "8902384908394";
            string uploadfile;// set to file to upload
            uploadfile = fileName;

            //everything except upload file and url can be left blank if needed
            string outdata = UploadFileEx(uploadfile,
                 webRelayURL + "/webrelay/directupload/" + tokenString + "/", "", "",
                 querystring, cookies);
            XmlDocument ydom = new XmlDocument();
            XmlNodeList xNodes;

            outdata = outdata.Replace("\r", "");
            outdata = outdata.Replace("\n", "");

            ydom.LoadXml(outdata);

            xNodes = ydom.SelectNodes("directupload");

            string fileID = xNodes.Item(0).SelectNodes("fileid").Item(0).InnerText;

            return fileID;
        }

        public string UploadFileEx(string uploadfile, string url,
string fileFormName, string contenttype, NameValueCollection querystring,
CookieContainer cookies)
        {
            try
            {
                if ((fileFormName == null) ||
                    (fileFormName.Length == 0))
                {
                    fileFormName = "file";
                }

                if ((contenttype == null) ||
                    (contenttype.Length == 0))
                {
                    contenttype = "application/octet-stream";
                }


                string postdata;
                postdata = "?";
                if (querystring != null)
                {
                    foreach (string key in querystring.Keys)
                    {
                        postdata += key + "=" + querystring.Get(key) + "&";
                    }
                }
                Uri uri = new Uri(url);


                string boundary = "----------" + DateTime.Now.Ticks.ToString("x");
                HttpWebRequest webrequest = (HttpWebRequest)WebRequest.Create(uri);
                webrequest.CookieContainer = cookies;
                webrequest.ContentType = "multipart/form-data; boundary=" + boundary;
                webrequest.Method = "POST";


                // Build up the post message header
                StringBuilder sb = new StringBuilder();

                if (querystring != null)
                {
                    foreach (string key in querystring.Keys)
                    {
                        sb.Append("--");
                        sb.Append(boundary);
                        sb.Append("\r\n");
                        sb.Append("Content-Disposition: form-data; name=\"");
                        sb.Append(key);
                        sb.Append("\"");
                        sb.Append("\r\n\r\n");
                        sb.Append(querystring.Get(key));
                        sb.Append("\r\n");
                        //postdata += key + "=" + querystring.Get(key) + "&";
                    }
                }

                sb.Append("--");
                sb.Append(boundary);
                sb.Append("\r\n");
                sb.Append("Content-Disposition: form-data; name=\"");
                sb.Append(fileFormName);
                sb.Append("\"; filename=\"");
                sb.Append(Path.GetFileName(uploadfile));
                sb.Append("\"");
                sb.Append("\r\n");
                sb.Append("Content-Type: ");
                sb.Append(contenttype);
                sb.Append("\r\n");
                sb.Append("\r\n");

                string postHeader = sb.ToString();
                byte[] postHeaderBytes = Encoding.UTF8.GetBytes(postHeader);

                // Build the trailing boundary string as a byte array
                // ensuring the boundary appears on a line by itself
                byte[] boundaryBytes =
                       Encoding.ASCII.GetBytes("\r\n--" + boundary + "\r\n");

                FileStream fileStream = new FileStream(uploadfile,
                                            FileMode.Open, FileAccess.Read);
                long length = postHeaderBytes.Length + fileStream.Length +
                                                       boundaryBytes.Length;
                webrequest.ContentLength = length;

                Stream requestStream = webrequest.GetRequestStream();

                // Write out our post header
                requestStream.Write(postHeaderBytes, 0, postHeaderBytes.Length);

                // Write out the file contents
                byte[] buffer = new Byte[checked((uint)Math.Min(4096,
                                         (int)fileStream.Length))];
                int bytesRead = 0;
                int progressValue = 0;
                int totalRead = 0;
                while ((bytesRead = fileStream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    totalRead = totalRead + bytesRead;
                    requestStream.Write(buffer, 0, bytesRead);
                    progressValue = totalRead * 100 / (int)fileStream.Length;

                }

                // Write out the trailing boundary
                requestStream.Write(boundaryBytes, 0, boundaryBytes.Length);
                WebResponse responce = webrequest.GetResponse();
                Stream s = responce.GetResponseStream();
                StreamReader sr = new StreamReader(s);
                progressValue = 100;

                return sr.ReadToEnd();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                progressValue = 100;
                return null;
            }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Web;
using System.Text.RegularExpressions;

namespace Ecareme.Utility
{
    class Crypto
    {
        public static string doHMacSH1(string input, string key)
        {
            string retVal = "";
            byte[] keyBytes = Encoding.UTF8.GetBytes(key);
            using (HMACSHA1 hmac = new HMACSHA1(keyBytes))
            {
                byte[] hashBytes = hmac.ComputeHash(Encoding.UTF8.GetBytes(input));
                string hashValue = Convert.ToBase64String(hashBytes);
                retVal = UrlEncodeUpperCase(hashValue);
            }
            return retVal;
        }
        public static string UrlEncodeUpperCase(string value) 
        { 
            value = HttpUtility.UrlEncode(value);
            return Regex.Replace(value, "(%[0-9a-f][0-9a-f])",
                                 delegate(Match match)
                                 {
                                     string v = match.ToString();
                                     return v.ToUpper();
                                 }
                                ); 
        } 
    }
}

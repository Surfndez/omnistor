using System;
using System.Collections.Generic;
using System.Text;

namespace Ecareme.Utility
{
    class Times
    {
        public static double GetTimestamp(DateTime now)
        {
            //計算本地時差
            double tz = TimeZone.CurrentTimeZone.GetUtcOffset(DateTime.Now).TotalHours;

            DateTime gtm = new DateTime(1970, 1, 1);        //宣告一個GTM時間出來
            //DateTime utc = DateTime.UtcNow.AddHours(tz);    //宣告一個目前的時間
            double timestamp = ((TimeSpan)now.Subtract(gtm)).TotalMilliseconds;
            timestamp = Convert.ToInt64(timestamp);

            return timestamp;
        }
    }
}

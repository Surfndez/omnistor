using System;
using System.Text;
namespace Ecareme.Utility
{
	/// <summary>
	/// Summary description for SimpleBase64.
	/// </summary>
	public class SimpleBase64
	{
		public static string Encode(string WEStr)
		{
			string functionReturnValue = null;



			byte[] weba1;
			try
			{
				weba1 = Encoding.UTF8.GetBytes(WEStr);
				functionReturnValue = Convert.ToBase64String(weba1, 0, weba1.Length);
				if ((weba1 != null) & weba1.Length > 0)
				{
					Array.Clear(weba1, 0, weba1.Length - 1);
				}

				weba1 = null;

			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.Message);
				weba1 = null;
				functionReturnValue = "";
			}
			return functionReturnValue;


		}

		public static string Decode(string WEStr)
		{
			string functionReturnValue = null;



			byte[] weba1;
			try
			{
				weba1 = Convert.FromBase64String(WEStr);

				functionReturnValue = Encoding.UTF8.GetString(weba1, 0, weba1.Length);
				if ((weba1 != null) & weba1.Length > 0)
				{
					Array.Clear(weba1, 0, weba1.Length - 1);
				}
				weba1 = null;


			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.Message);
				weba1 = null;
				functionReturnValue = "";
			}
			return functionReturnValue;

		}
	
	}
}

package net.yostore.aws.api.entity;

public class MediaInfoEntity
{
//	private long id = -1l;
	private long i = -999l;
	private long da = System.currentTimeMillis();
	private String d = null;
	private int ty = 0;
	private int r = 0;
	private String no = "";
	private int en = 0;
	private int us = 0;
	private int fr = 1;
//	private String userName;
	
	/******************************************************************************************************************************************************
	格式：
	<i>：檔案id，實體檔案id
	<da>：檔案時間，抓取檔案更新時間後填入
	<d>：檔案名稱
	<ty>：檔案格式，目前0: photo, 1: movie
	<r>：旋轉角度，有0, 90, 180, 270四種
	<no>：note
	<en>：此影片檔目前轉檔狀況，1: 轉檔中, 2: 轉檔失敗(照片檔無須填此資訊)
	<us>：此檔案目前上傳狀況，0:已在雲端, 1:上傳中, 2: 上傳失敗
	<fr>：此檔案匯入來源，0:my collection, -3:backup, -5:MySyncFolder, 1:pixwe client上傳
	檔案範例：
	<i>19238</i><da>1098912</da><d>img001.jpg</d><ty>0</ty><r>180< /r><no>今天我看到歐文走進旅館</no><us>0</us>
	 
	 ******************************************************************************************************************************************************/
	public MediaInfoEntity(){
		
	}
	
//	public long getId()
//	{
//		return id;
//	}
//
//	public void setId(long id)
//	{
//		this.id = id;
//	}
//
//	public String getUserName()
//	{
//		return userName;
//	}
//
//	public void setUserName(String userName)
//	{
//		this.userName = userName;
//	}

	public long getI()
	{
		return i;
	}

	public void setI(long i)
	{
		this.i = i;
	}

	public long getDa()
	{
		return da;
	}

	public void setDa(long da)
	{
		this.da = da;
	}

	public String getD()
	{
		return d;
	}

	public void setD(String d)
	{
		this.d = d;
	}

	public int getTy()
	{
		return ty;
	}

	public void setTy(int ty)
	{
		this.ty = ty;
	}

	public int getR()
	{
		return r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

	public String getNo()
	{
		return no;
	}

	public void setNo(String no)
	{
		this.no = no;
	}

	public int getEn()
	{
		return en;
	}

	public void setEn(int en)
	{
		this.en = en;
	}

	public int getUs()
	{
		return us;
	}

	public void setUs(int us)
	{
		this.us = us;
	}
	
	public int getFr() {
		return fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public String toXml(){
		String rtn="";
		rtn+="<i>"+i+"</i><da>"+da+"</da><d>"+d+"</d><ty>"+ty+"</ty><r>"+r+"</r><no>"+no+"</no>"+(en>0?("<en>"+en+"</en>"):"")+"<us>"+us+"</us>"+"<fr>"+fr+"</fr>";
		return rtn;
	}
	
}

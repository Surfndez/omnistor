package net.yostore.aws.api.entity;

/**
 * 
 *		<id>{ File ID 或 Folder ID}</id>
		<parent>{ Parent folder ID }</parent>
		<rawfilename>{ 檔案名稱}</rawfilename>
		<isbackup>{ 0 | 1 }</isbackup>
		<isorigdeleted>{ 0未刪除:1已刪除 }</isorigdeleted>
		<marks>{附屬於此file的Markid，若有多個MarkId則以空白分隔。entry沒有markid時則此element不出現}
		</marks>
		<createdtime>{ 格式為yyyy-MM-dd HH:mm:ss ，來自UserFileTable的Time欄位 }</createdtime>
		<lastchangetime>{ 資料型別為長整數 ，來自UserFileTable.LastChangeTime欄位 }</lastchangetime>
		<attribute>{同Info Relay/folder/browse/的attribute格式}</attribute>
		<isinfected>{檔案是否感染病毒0：表示未感染| 1：表示已感染。}</isinfected>
		<size>{檔案大小。}</size>
		<ispublic>{是否為public。0：私有 | 1：公開}</ispublic>

 * @author Scott
 *
 */
public class BaseEntry {
	
	private String id;
	private String parent;
	private String rawfilename;
	private boolean isBackup;
	private boolean isorigdeleted;
	private String marks;
	private String createdtime;
	private long lastchangetime;
	private Attribute attribute;
	private boolean isinfected;
	private long size;
	private boolean ispublic;
	
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getParent()
	{
		return parent;
	}
	public void setParent(String parent)
	{
		this.parent = parent;
	}
	public String getRawfilename()
	{
		return rawfilename;
	}
	public void setRawfilename(String rawfilename)
	{
		this.rawfilename = rawfilename;
	}
	public boolean getIsBackup()
	{
		return isBackup;
	}
	public void setIsBackup(boolean isBackup)
	{
		this.isBackup = isBackup;
	}
	public String getCreatedtime()
	{
		return createdtime;
	}
	public void setCreatedtime(String createdtime)
	{
		this.createdtime = createdtime;
	}
	public long getLastchangetime()
	{
		return lastchangetime;
	}
	public void setLastchangetime(long lastchangetime)
	{
		this.lastchangetime = lastchangetime;
	}
	public boolean getIsOrigdeleted()
	{
		return isorigdeleted;
	}
	public void setIsorigdeleted(boolean isorigdeleted)
	{
		this.isorigdeleted = isorigdeleted;
	}
	public String getMarks()
	{
		return marks;
	}
	public void setMarks(String marks)
	{
		this.marks = marks;
	}
	public Attribute getAttribute()
	{
		return attribute;
	}
	public void setAttribute(Attribute attribute)
	{
		this.attribute = attribute;
	}
	public boolean getIsInfected()
	{
		return isinfected;
	}
	public void setIsinfected(boolean isinfected)
	{
		this.isinfected = isinfected;
	}
	public long getSize()
	{
		return size;
	}
	public void setSize(long size)
	{
		this.size = size;
	}
	public boolean getIsPublic()
	{
		return ispublic;
	}
	public void setIspublic(boolean ispublic)
	{
		this.ispublic = ispublic;
	}
}

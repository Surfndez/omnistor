package entity;

public class PublicChangeseqfolderBase
{
	/* all fields */
	private String _folderid = null;	
	private long _changeseq = 0;

	/* default constructor */
	public PublicChangeseqfolderBase()
	{
	}

	/* all fields constructor */
	public PublicChangeseqfolderBase(String folderid, long changeseq)
	{
		this._folderid = folderid;
		this._changeseq = changeseq;
	}

	/* setters */
	public void setFolderid(String folderid)
	{
		this._folderid = folderid;
	}

	public void setChangeseq(long changeseq)
	{
		this._changeseq = changeseq;
	}
	
	/* getters */
	public String getFolderid()
	{
		return _folderid;
	}

	public long getChangeseq()
	{
		return _changeseq;
	}
	
	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("PublicChangeseqfolderBase=>\n");
		sb.append(" folderid:").append(_folderid).append("\n");
		sb.append(" changeseq:").append(_changeseq).append("\n");
		return sb.toString();
	}
}
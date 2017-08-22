package net.yostore.aws.api.sax;

import net.yostore.aws.api.entity.ApiResponse;
import net.yostore.aws.api.entity.Attribute;
import net.yostore.aws.api.entity.B_FileInfo;
import net.yostore.aws.api.entity.B_FolderInfo;
import net.yostore.aws.api.entity.BrowseFolderResponse;
import net.yostore.aws.api.entity.ParentFolder;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class BrowseFolder extends BaseSaxHandler {

	private BrowseFolderResponse response = new BrowseFolderResponse();
	boolean isFolder = false;
	boolean isFile = false;
	B_FileInfo fi;
	B_FolderInfo fo;
	Attribute at;
	ParentFolder pf;
	//private String TAG = "BrowseFolder";

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);

		if (this.isFolder) {
			if (localName.equalsIgnoreCase("folder")) {
				response.getFolderList().add(fo);

				this.isFolder = false;
			} else if (localName.equalsIgnoreCase("id")) {
				fo.setId(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("treesize")) {
				fo.setTreesize(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("isgroupaware")) {
				fo.setIsgroupaware("1".equalsIgnoreCase(builder.toString()
						.trim()) ? true : false);
			} else if (localName.equalsIgnoreCase("rawfoldername")) {
				fo.setDisplay(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("isbackup")) {
				fo.setIsbackup("1".equalsIgnoreCase(builder.toString().trim()) ? true
						: false);
			} else if (localName.equalsIgnoreCase("isorigdeleted")) {
				fo.setIsorigdeleted("1".equalsIgnoreCase(builder.toString()
						.trim()) ? true : false);
			} else if (localName.equalsIgnoreCase("ispublic")) {
				fo.setIspublic("1".equalsIgnoreCase(builder.toString().trim()) ? true
						: false);
			} else if (localName.equalsIgnoreCase("createdtime")) {
				fo.setCreatedtime(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("markid")) {
				fo.setMarkid(builder.toString().trim());
			}
		} else if (this.isFile) {
			if (localName.equalsIgnoreCase("file")) {
				response.getFileList().add(fi);
				this.isFile = false;
			} else if (localName.equalsIgnoreCase("id")) {
				fi.setId(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("isgroupaware")) {
				fi.setIsgroupaware(builder.toString());
			} else if (localName.equalsIgnoreCase("rawfilename")) {
				fi.setDisplay(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("size")) {
				fi.setSize(Long.parseLong(builder.toString().trim()));
			} else if (localName.equalsIgnoreCase("isbackup")) {
				fi.setIsbackup("1".equalsIgnoreCase(builder.toString().trim()) ? true
						: false);
			} else if (localName.equalsIgnoreCase("isorigdeleted")) {
				fi.setIsorigdeleted("1".equalsIgnoreCase(builder.toString()
						.trim()) ? true : false);
			} else if (localName.equalsIgnoreCase("isinfected")) {
				fi.setIsinfected("1"
						.equalsIgnoreCase(builder.toString().trim()) ? true
						: false);
			} else if (localName.equalsIgnoreCase("ispublic")) {
				fi.setIspublic("1".equalsIgnoreCase(builder.toString().trim()) ? true
						: false);
			} else if (localName.equalsIgnoreCase("headversion")) {
				fi.setHeadversion(Integer.parseInt(builder.toString().trim()));
			} else if (localName.equalsIgnoreCase("createdtime")) {
				fi.setCreatedtime(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("markid")) {
				fi.setMarkid(builder.toString().trim());
			}

		} else {
			if (localName.equalsIgnoreCase("status")) {
				response.setStatus(Integer.parseInt(builder.toString().trim()));
			} else if (localName.equalsIgnoreCase("logmessage")) {
				response.setLogmessage(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("scrip")) {
				response.setScrip(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("rawfoldername")) {
				response.setRawfoldername(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("parent")) {
				response.setParent(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("rootfolderid")) {
				response.setRootfolderid(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("pageno")) {
				response.setPageno(Integer.parseInt(builder.toString().trim()));
			} else if (localName.equalsIgnoreCase("pagesize")) {
				response.setPagesize(Integer
						.parseInt(builder.toString().trim()));
			} else if (localName.equalsIgnoreCase("totalcount")) {
				response.setTotalcount(Integer.parseInt(builder.toString()
						.trim()));
			} else if (localName.equalsIgnoreCase("hasnextpage")) {
				response.setHasnextpage("1".equalsIgnoreCase(builder.toString()
						.trim()) ? true : false);
			}
		}

		builder.setLength(0);

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("folder")) {
			this.fo = new B_FolderInfo();
			this.isFolder = true;
		} else if (localName.equalsIgnoreCase("file")) {
			this.fi = new B_FileInfo();
			this.isFile = true;
		}

	}

	@Override
	public ApiResponse getResponse() {
		return this.response;
	}
}
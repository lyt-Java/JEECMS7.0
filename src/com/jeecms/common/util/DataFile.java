package com.jeecms.common.util;

/**
 * @author Tom
 */
public class DataFile {
	private String fileName;// 备份文件的名称
	private String fileDate;// 备份文件的日期
	private String filePath;// 备份文件的地址
	private String fileSize;// 备份文件的大小

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

package com.jeecms.core.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeecms.common.upload.UploadUtils;
import com.jeecms.common.util.MyFileUtils;
import com.jeecms.core.entity.base.BaseFtp;

public class Ftp extends BaseFtp {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(Ftp.class);

	public String storeByExt(String path, String ext, InputStream in)
			throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		store(filename, in);
		return filename;
	}

	public String storeByFilename(String filename, InputStream in)
			throws IOException {
		store(filename, in);
		return filename;
	}

	public File retrieve(String name,String fileName) throws IOException {
		String path = System.getProperty("java.io.tmpdir");
		File file = new File(path, fileName);
		file = UploadUtils.getUniqueFile(file);
		FTPClient ftp = getClient();
		OutputStream output = new FileOutputStream(file);
		ftp.retrieveFile(getPath() + name, output);
		output.close();
		ftp.logout();
		ftp.disconnect();
		return file;
	}

	public boolean restore(String name, File file) throws IOException {
		store(name, FileUtils.openInputStream(file));
		file.deleteOnExit();
		return true;
	}

	public int storeByFloder(String folder,String rootPath){
		String fileAbsolutePath;
		String fileRelativePath;
		try {
			FTPClient ftp = getClient();
			if (ftp != null) {
				List<File>files=MyFileUtils.getFiles(new File(folder));
				for(File file:files){
					String filename = getPath() + file.getName();
					String name = FilenameUtils.getName(filename);
					String path = FilenameUtils.getFullPath(filename);
					fileAbsolutePath=file.getAbsolutePath();
					fileRelativePath=fileAbsolutePath.substring(rootPath.length()+1,fileAbsolutePath.indexOf(name));
					path+=fileRelativePath.replace("\\", "/");
					if (!ftp.changeWorkingDirectory(path)) {
						String[] ps = StringUtils.split(path, '/');
						String p = "/";
						ftp.changeWorkingDirectory(p);
						for (String s : ps) {
							p += s + "/";
							if (!ftp.changeWorkingDirectory(p)) {
								ftp.makeDirectory(s);
								ftp.changeWorkingDirectory(p);
							}
						}
					}
					//解决中文乱码问题
					name=new String(name.getBytes(getEncoding()),"iso-8859-1");
					if(!file.isFile()){
						ftp.makeDirectory(name);
					}else{
						InputStream in=new FileInputStream(file.getAbsolutePath());
						ftp.storeFile(name, in);
						in.close();
					}
				}
				ftp.logout();
				ftp.disconnect();
			}
			return 0;
		} catch (SocketException e) {
			log.error("ftp store error", e);
			return 3;
		} catch (IOException e) {
			log.error("ftp store error", e);
			return 4;
		}
	}
	
	private int store(String remote, InputStream in) {
		try {
			FTPClient ftp = getClient();
			if (ftp != null) {
				String filename = getPath() + remote;
				String name = FilenameUtils.getName(filename);
				String path = FilenameUtils.getFullPath(filename);
				if (!ftp.changeWorkingDirectory(path)) {
					String[] ps = StringUtils.split(path, '/');
					String p = "/";
					ftp.changeWorkingDirectory(p);
					for (String s : ps) {
						p += s + "/";
						if (!ftp.changeWorkingDirectory(p)) {
							ftp.makeDirectory(s);
							ftp.changeWorkingDirectory(p);
						}
					}
				}
				ftp.storeFile(name, in);
				ftp.logout();
				ftp.disconnect();
			}
			in.close();
			return 0;
		} catch (SocketException e) {
			log.error("ftp store error", e);
			return 3;
		} catch (IOException e) {
			log.error("ftp store error", e);
			return 4;
		}
	}

	private FTPClient getClient() throws SocketException, IOException {
		FTPClient ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
		ftp.setDefaultPort(getPort());
		ftp.connect(getIp());
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			log.warn("FTP server refused connection: {}", getIp());
			ftp.disconnect();
			return null;
		}
		if (!ftp.login(getUsername(), getPassword())) {
			log.warn("FTP server refused login: {}, user: {}", getIp(),
					getUsername());
			ftp.logout();
			ftp.disconnect();
			return null;
		}
		ftp.setControlEncoding(getEncoding());
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		return ftp;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Ftp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Ftp (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Ftp (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String ip,
		java.lang.Integer port,
		java.lang.String encoding,
		java.lang.String url) {

		super (
			id,
			name,
			ip,
			port,
			encoding,
			url);
	}

	/* [CONSTRUCTOR MARKER END] */

}
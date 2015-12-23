package com.jeecms.cms.manager.assist.impl;

import static com.jeecms.common.web.Constants.SPT;
import static com.jeecms.common.web.Constants.UTF8;
import static com.jeecms.core.web.util.FrontUtils.RES_EXP;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeecms.cms.entity.assist.CmsFile;
import com.jeecms.cms.manager.assist.CmsFileMng;
import com.jeecms.cms.manager.assist.CmsResourceMng;
import com.jeecms.common.file.FileWrap;
import com.jeecms.common.file.FileWrap.FileComparator;
import com.jeecms.common.util.Zipper.FileEntry;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.web.util.FrontUtils;

@Service
public class CmsResourceMngImpl implements CmsResourceMng {
	private static final Logger log = LoggerFactory
			.getLogger(CmsResourceMngImpl.class);

	public List<FileWrap> listFile(String path, boolean dirAndEditable) {
		File parent = new File(realPathResolver.get(path));
		if (parent.exists()) {
			File[] files;
			if (dirAndEditable) {
				files = parent.listFiles(filter);
			} else {
				files = parent.listFiles();
			}
			Arrays.sort(files, new FileComparator());
			List<FileWrap> list = new ArrayList<FileWrap>(files.length);
			for (File f : files) {
				list.add(new FileWrap(f, realPathResolver.get("")));
			}
			return list;
		} else {
			return new ArrayList<FileWrap>(0);
		}
	}
	
	public List<FileWrap> listFileValid(String path, boolean dirAndEditable) {
		File parent = new File(realPathResolver.get(path));
		if (parent.exists()) {
			File[] files;
			if (dirAndEditable) {
				files = parent.listFiles(filter);
			} else {
				files = parent.listFiles();
			}
			Arrays.sort(files, new FileComparator());
			List<FileWrap> list = new ArrayList<FileWrap>(files.length);
			CmsFile file;
			for (File f : files) {
				file=fileMng.findByPath(f.getName());
				if(file!=null){
					list.add(new FileWrap(f, realPathResolver.get(""),null,file.getFileIsvalid()));
				}else{
					list.add(new FileWrap(f, realPathResolver.get(""),null,false));
				}
			}
			return list;
		} else {
			return new ArrayList<FileWrap>(0);
		}
	}
	
	public List<FileWrap> queryFiles(String path, Boolean valid){
		File parent = new File(realPathResolver.get(path));
		if (parent.exists()) {
			File[] files;
			files = parent.listFiles();
			Arrays.sort(files, new FileComparator());
			List<FileWrap> list = new ArrayList<FileWrap>(files.length);
			CmsFile file;
			for (File f : files) {
				file=fileMng.findByPath(f.getName());
				if(valid!=null){
					if(file!=null){
						if(file.getFileIsvalid().equals(valid)){
							list.add(new FileWrap(f, realPathResolver.get(""),null,valid));
						}
					}else{
						if(valid.equals(false)){
							list.add(new FileWrap(f, realPathResolver.get(""),null,false));
						}
					}
				}else{
					if(file!=null){
						list.add(new FileWrap(f, realPathResolver.get(""),null,file.getFileIsvalid()));
					}else{
						list.add(new FileWrap(f, realPathResolver.get(""),null,false));
					}
				}
			}
			return list;
		} else {
			return new ArrayList<FileWrap>(0);
		}
	}

	public boolean createDir(String path, String dirName) {
		File parent = new File(realPathResolver.get(path));
		parent.mkdirs();
		File dir = new File(parent, dirName);
		return dir.mkdir();
	}

	public void saveFile(String path, MultipartFile file)
			throws IllegalStateException, IOException {
		File dest = new File(realPathResolver.get(path), file
				.getOriginalFilename());
		file.transferTo(dest);
	}

	public void createFile(String path, String filename, String data)
			throws IOException {
		File parent = new File(realPathResolver.get(path));
		parent.mkdirs();
		File file = new File(parent, filename);
		FileUtils.writeStringToFile(file, data, UTF8);
	}

	public String readFile(String name) throws IOException {
		File file = new File(realPathResolver.get(name));
		return FileUtils.readFileToString(file, UTF8);
	}

	public void updateFile(String name, String data) throws IOException {
		File file = new File(realPathResolver.get(name));
		FileUtils.writeStringToFile(file, data, UTF8);
	}

	public int delete(String[] names) {
		int count = 0;
		File f;
		for (String name : names) {
			f = new File(realPathResolver.get(name));
			if (FileUtils.deleteQuietly(f)) {
				count++;
			}
		}
		return count;
	}

	public void rename(String origName, String destName) {
		File orig = new File(realPathResolver.get(origName));
		File dest = new File(realPathResolver.get(destName));
		orig.renameTo(dest);
	}

	public void copyTplAndRes(CmsSite from, CmsSite to) throws IOException {
		String fromSol = from.getTplSolution();
		String toSol = to.getTplSolution();
		File tplFrom = new File(realPathResolver.get(from.getTplPath()),
				fromSol);
		File tplTo = new File(realPathResolver.get(to.getTplPath()), toSol);
		FileUtils.copyDirectory(tplFrom, tplTo);
		File resFrom = new File(realPathResolver.get(from.getResPath()),
				fromSol);
		if (resFrom.exists()) {
			File resTo = new File(realPathResolver.get(to.getResPath()), toSol);
			FileUtils.copyDirectory(resFrom, resTo);
		}
	}

	public void delTplAndRes(CmsSite site) throws IOException {
		File tpl = new File(realPathResolver.get(site.getTplPath()));
		File res = new File(realPathResolver.get(site.getResPath()));
		FileUtils.deleteDirectory(tpl);
		FileUtils.deleteDirectory(res);
	}

	public String[] getSolutions(String path) {
		File tpl = new File(realPathResolver.get(path));
		return tpl.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}
		});
	}

	public List<FileEntry> export(CmsSite site, String solution) {
		List<FileEntry> fileEntrys = new ArrayList<FileEntry>();
		File tpl = new File(realPathResolver.get(site.getTplPath()), solution);
		fileEntrys.add(new FileEntry("", "", tpl));
		File res = new File(realPathResolver.get(site.getResPath()), solution);
		if (res.exists()) {
			for (File r : res.listFiles()) {
				fileEntrys.add(new FileEntry(FrontUtils.RES_EXP, r));
			}
		}
		return fileEntrys;
	}

	@SuppressWarnings("unchecked")
	public void imoport(File file, CmsSite site) throws IOException {
		String resRoot = site.getResPath();
		String tplRoot = site.getTplPath();
		// 用默认编码或UTF-8编码解压会乱码！windows7的原因吗？
		ZipFile zip = new ZipFile(file, "GBK");
		ZipEntry entry;
		String name;
		String filename;
		File outFile;
		File pfile;
		byte[] buf = new byte[1024];
		int len;
		InputStream is = null;
		OutputStream os = null;
		String solution = null;

		Enumeration<ZipEntry> en = zip.getEntries();
		while (en.hasMoreElements()) {
			name = en.nextElement().getName();
			if (!name.startsWith(RES_EXP)) {
				solution = name.substring(0, name.indexOf("/"));
				break;
			}
		}
		if (solution == null) {
			return;
		}
		en = zip.getEntries();
		while (en.hasMoreElements()) {
			entry = en.nextElement();
			if (!entry.isDirectory()) {
				name = entry.getName();
				log.debug("unzip file：{}", name);
				// 模板还是资源
				if (name.startsWith(RES_EXP)) {
					filename = resRoot + "/" + solution
							+ name.substring(RES_EXP.length());
				} else {
					filename = tplRoot + SPT + name;
				}
				log.debug("解压地址：{}", filename);
				outFile = new File(realPathResolver.get(filename));
				pfile = outFile.getParentFile();
				if (!pfile.exists()) {
					pfile.mkdirs();
				}
				try {
					is = zip.getInputStream(entry);
					os = new FileOutputStream(outFile);
					while ((len = is.read(buf)) != -1) {
						os.write(buf, 0, len);
					}
				} finally {
					if (is != null) {
						is.close();
						is = null;
					}
					if (os != null) {
						os.close();
						os = null;
					}
				}
			}
		}
	}


	public void unZipFile(File file) throws IOException {
		// 用默认编码或UTF-8编码解压会乱码！windows7的原因吗？
		//解压之前要坚持是否冲突
		ZipFile zip = new ZipFile(file, "GBK");
		ZipEntry entry;
		String name;
		String filename;
		File outFile;
		File pfile;
		byte[] buf = new byte[1024];
		int len;
		InputStream is = null;
		OutputStream os = null;
		Enumeration<ZipEntry> en = zip.getEntries();
		while (en.hasMoreElements()) {
			entry = en.nextElement();
			name = entry.getName();
			if (!entry.isDirectory()) {
				name = entry.getName();
				filename =  name;
				outFile = new File(realPathResolver.get(filename));
				if(outFile.exists()){
					break;
				}
				pfile = outFile.getParentFile();
				if (!pfile.exists()) {
					//	pfile.mkdirs();
					createFolder(outFile);
				}
				try {
					is = zip.getInputStream(entry);
					os = new FileOutputStream(outFile);
					while ((len = is.read(buf)) != -1) {
						os.write(buf, 0, len);
					}
				} finally {
					if (is != null) {
						is.close();
						is = null;
					}
					if (os != null) {
						os.close();
						os = null;
					}
				}
			}
		}
		zip.close();
	}
	
	public void deleteZipFile(File file) throws IOException {
		//根据压缩包删除解压后的文件
		// 用默认编码或UTF-8编码解压会乱码！windows7的原因吗
		ZipFile zip = new ZipFile(file, "GBK");
		ZipEntry entry;
		String name;
		String filename;
		File directory;
		//删除文件
		Enumeration<ZipEntry> en = zip.getEntries();
		while (en.hasMoreElements()) {
			entry = en.nextElement();
			if (!entry.isDirectory()) {
				name = entry.getName();
				filename =  name;
				directory = new File(realPathResolver.get(filename));
				if(directory.exists()){
					directory.delete();
				}
			}
		}
		//删除空文件夹
		en= zip.getEntries();
		while (en.hasMoreElements()) {
			entry = en.nextElement();
			if (entry.isDirectory()) {
				name = entry.getName();
				filename =  name;
				directory = new File(realPathResolver.get(filename));
				if(!directoryHasFile(directory)){
					directory.delete();
				}
			}
		}
		zip.close();
	}
	
	private  void createFolder(File f){
		File parent=f.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
			createFolder(parent);
		}
	}
	
	//文件夹判断是否有文件
	private  boolean directoryHasFile(File directory){
		File[] files = directory.listFiles();
		if(files!=null&&files.length>0){
			for(File f:files){
				if(directoryHasFile(f)){
					return true;
				}else{
					continue;
				}
			}
			return false;
		}else{
			return false;
		}
	}

	// 文件夹和可编辑文件则显示
	private FileFilter filter = new FileFilter() {
		public boolean accept(File file) {
			return file.isDirectory()
					|| FileWrap.editableExt(FilenameUtils.getExtension(file
							.getName()));
		}
	};

	private RealPathResolver realPathResolver;
	private CmsFileMng fileMng;

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}

	@Autowired
	public void setFileMng(CmsFileMng fileMng) {
		this.fileMng = fileMng;
	}

	
}

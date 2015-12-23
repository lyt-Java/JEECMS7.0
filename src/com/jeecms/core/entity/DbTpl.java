package com.jeecms.core.entity;

import static com.jeecms.common.web.Constants.SPT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.jeecms.common.web.Constants;
import com.jeecms.core.entity.base.BaseDbTpl;
import com.jeecms.core.tpl.Tpl;

public class DbTpl extends BaseDbTpl implements Tpl {
	private static final long serialVersionUID = 1L;

	/**
	 * 获得文件夹或文件的所有父文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static String[] getParentDir(String path) {
		Assert.notNull(path);
		if (!path.startsWith(SPT)) {
			throw new IllegalArgumentException("path must start with /");
		}
		List<String> list = new ArrayList<String>();
		int index = path.indexOf(SPT, 1);
		while (index >= 0) {
			list.add(path.substring(0, index));
			index = path.indexOf(SPT, index + 1);
		}
		String[] arr = new String[list.size()];
		return list.toArray(arr);
	}

	public String getName() {
		return getId();
	}

	public String getPath() {
		String name = getId();
		return getId().substring(0, name.lastIndexOf("/"));
	}

	public String getFilename() {
		String name = getId();
		if (!StringUtils.isBlank(name)) {
			int index = name.lastIndexOf(Constants.SPT);
			if (index != -1) {
				return name.substring(index + 1, name.length());
			}
		}
		return name;
	}

	public long getLength() {
		if (isDirectory() || getSource() == null) {
			return 128;
		} else {
			// 一个英文字符占1个字节，而一个中文则占3-4字节，这里取折中一个字符1.5个字节
			return (long) (getSource().length() * 1.5);
		}
	}

	public int getSize() {
		return (int) (getLength() / 1024) + 1;
	}

	public Date getLastModifiedDate() {
		return new Timestamp(getLastModified());
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public DbTpl () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DbTpl (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DbTpl (
		java.lang.String id,
		long lastModified,
		boolean directory) {

		super (
			id,
			lastModified,
			directory);
	}

	/* [CONSTRUCTOR MARKER END] */
}
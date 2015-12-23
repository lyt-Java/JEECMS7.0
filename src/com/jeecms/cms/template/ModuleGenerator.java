package com.jeecms.cms.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模块生成器
 * 
 * <p>
 * 用于生成JEE模块。
 * <p>
 * 包括JAVA类：action,dao,dao.impl,manager,manager.impl；
 * 配置文件：action配置,spring配置；ftl页面：list.html,add.html,edit.html。
 * 数据验证文件：Act-Com_save
 * -validation.xml,Act-Com_edit-validation.xml,Act-Com_update-validation.xml
 * <p>
 * 可设置的参数有：模块实体类名、java类包地址、配置文件地址、ftl页面地址。
 */
public class ModuleGenerator {
	private static final Logger log = LoggerFactory
			.getLogger(ModuleGenerator.class);
	public static final String SPT = File.separator;

	public static final String ENCODING = "UTF-8";

	private Properties prop = new Properties();
	
	private String packName;
	private String fileName;
	private File daoImplFile;
	private File daoFile;
	private File managerFile;
	private File managerImplFile;
	private File actionFile;
	private File pageListFile;
	private File pageEditFile;
	private File pageAddFile;

	private File daoImplTpl;
	private File daoTpl;
	private File managerTpl;
	private File managerImplTpl;
	private File actionTpl;
	private File pageListTpl;
	private File pageEditTpl;
	private File pageAddTpl;

	public ModuleGenerator(String packName, String fileName) {
		this.packName = packName;
		this.fileName = fileName;
	}

	@SuppressWarnings("unchecked")
	private void loadProperties() {
		try {
			log.debug("packName=" + packName);
			log.debug("fileName=" + fileName);
			FileInputStream fileInput = new FileInputStream(getFilePath(
					packName, fileName));
			prop.load(fileInput);
			String entityUp = prop.getProperty("Entity");
			log.debug("entityUp:" + entityUp);
			if (entityUp == null || entityUp.trim().equals("")) {
				log.warn("Entity not specified, exit!");
				return;
			}
			String entityLow = entityUp.substring(0, 1).toLowerCase()
					+ entityUp.substring(1);
			log.debug("entityLow:" + entityLow);
			prop.put("entity", entityLow);
			if (log.isDebugEnabled()) {
				Set ps = prop.keySet();
				for (Object o : ps) {
					log.debug(o + "=" + prop.get(o));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareFile() {
		String daoImplFilePath = getFilePath(prop.getProperty("dao_impl_p"),
				prop.getProperty("Entity") + "DaoImpl.java");
		daoImplFile = new File(daoImplFilePath);
		log.debug("daoImplFile:" + daoImplFile.getAbsolutePath());

		String daoFilePath = getFilePath(prop.getProperty("dao_p"), prop
				.getProperty("Entity")
				+ "Dao.java");
		daoFile = new File(daoFilePath);
		log.debug("daoFile:" + daoFile.getAbsolutePath());

		String managerFilePath = getFilePath(prop.getProperty("manager_p"),
				prop.getProperty("Entity") + "Mng.java");
		managerFile = new File(managerFilePath);
		log.debug("managerFile:" + managerFile.getAbsolutePath());

		String managerImplFilePath = getFilePath(prop
				.getProperty("manager_impl_p"), prop.getProperty("Entity")
				+ "MngImpl.java");
		managerImplFile = new File(managerImplFilePath);
		log.debug("managerImplFile:" + managerImplFile.getAbsolutePath());
		String actionFilePath = getFilePath(prop.getProperty("action_p"), prop
				.getProperty("Entity")
				+ "Act.java");
		actionFile = new File(actionFilePath);
		log.debug("actionFile:" + actionFile.getAbsolutePath());

		String pagePath = "WebContent/WEB-INF/"
				+ prop.getProperty("config_sys") + "/"
				+ prop.getProperty("config_entity") + "/";
		pageListFile = new File(pagePath + "list.html");
		log.debug("pageListFile:" + pageListFile.getAbsolutePath());
		pageEditFile = new File(pagePath + "edit.html");
		log.debug("pageEditFile:" + pageEditFile.getAbsolutePath());
		pageAddFile = new File(pagePath + "add.html");
		log.debug("pageAddFile:" + pageAddFile.getAbsolutePath());
	}

	private void prepareTemplate() {
		String tplPack = prop.getProperty("template_dir");
		log.debug("tplPack:" + tplPack);
		daoImplTpl = new File(getFilePath(tplPack, "dao_impl.txt"));
		daoTpl = new File(getFilePath(tplPack, "dao.txt"));
		managerImplTpl = new File(getFilePath(tplPack, "manager_impl.txt"));
		managerTpl = new File(getFilePath(tplPack, "manager.txt"));
		actionTpl = new File(getFilePath(tplPack, "action.txt"));
		pageListTpl = new File(getFilePath(tplPack, "page_list.txt"));
		pageAddTpl = new File(getFilePath(tplPack, "page_add.txt"));
		pageEditTpl = new File(getFilePath(tplPack, "page_edit.txt"));
	}

	private static void stringToFile(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, ENCODING);
	}

	private void writeFile() {
		try {
			if ("true".equals(prop.getProperty("is_dao"))) {
				stringToFile(daoImplFile, readTpl(daoImplTpl));
				stringToFile(daoFile, readTpl(daoTpl));
			}
			if ("true".equals(prop.getProperty("is_manager"))) {
				stringToFile(managerImplFile, readTpl(managerImplTpl));
				stringToFile(managerFile, readTpl(managerTpl));
			}
			if ("true".equals(prop.getProperty("is_action"))) {
				stringToFile(actionFile, readTpl(actionTpl));
			}
			if ("true".equals(prop.getProperty("is_page"))) {
				stringToFile(pageListFile, readTpl(pageListTpl));
				stringToFile(pageAddFile, readTpl(pageAddTpl));
				stringToFile(pageEditFile, readTpl(pageEditTpl));
			}
		} catch (IOException e) {
			log.warn("write file faild! " + e.getMessage());
		}
	}

	private String readTpl(File tpl) {
		String content = null;
		try {
			content = FileUtils.readFileToString(tpl, ENCODING);
			Set<Object> ps = prop.keySet();
			for (Object o : ps) {
				String key = (String) o;
				String value = prop.getProperty(key);
				content = content.replaceAll("\\#\\{" + key + "\\}", value);
			}
		} catch (IOException e) {
			log.warn("read file faild. " + e.getMessage());
		}
		return content;

	}

	private String getFilePath(String packageName, String name) {
		log.debug("replace:" + packageName);
		String path = packageName.replaceAll("\\.", "/");
		log.debug("after relpace:" + path);
		return "src/" + path + "/" + name;
	}

	public void generate() {
		loadProperties();
		prepareFile();
		prepareTemplate();
		writeFile();
	}

	public static void main(String[] args) {
		String packName = "com.jeecms.common.developer.template";
		String fileName = "template.properties";
		new ModuleGenerator(packName, fileName).generate();
	}
}

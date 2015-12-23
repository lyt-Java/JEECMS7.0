package com.jeecms.cms.template;


public class CmsModuleGenerator {
	private static String packName = "com.jeecms.cms.template";
	private static String fileName = "jeecms.properties";

	public static void main(String[] args) {
		new ModuleGenerator(packName, fileName).generate();
	}
}

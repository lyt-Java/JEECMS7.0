package com.jeecms.common.office;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tom
 */
public class FileUtils {

	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}

	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}
	
	public static String getFileName(String path) {
		int lastIndex = path.lastIndexOf(".");
		int firstIndex = path.lastIndexOf("/");
		return path.substring(firstIndex+1, lastIndex);
	}
	
	public static String getFilePath(String fileName) {
		int splitIndex = fileName.lastIndexOf("/");
		return fileName.substring(0,splitIndex+1);
	}
	
	public static Set<String> listFiles(File directory,String prefixFileName,String suffix) {
		Set<String>filenames=new HashSet<String>();
		if(directory!=null&&directory.isDirectory()){
			File[]files = directory.listFiles();
			for(File f:files){
				String fname=f.getName();
				if(fname.endsWith(suffix)&&fname.startsWith(prefixFileName)){
					filenames.add(fname);
				}
			}
		}
		return filenames;
	}

	public static void copyFile(String inputFile, String outputFile)
			throws FileNotFoundException {
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;
		try {
			while ((temp = fis.read()) != -1) {
				fos.write(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String toHtmlString(File file, String filepath) {
		// 获取HTML文件流
		StringBuffer htmlSb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "gb2312"));
			while (br.ready()) {
				htmlSb.append(br.readLine());
			}
			br.close();
			// 删除临时文件
			file.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// HTML文件字符串
		String htmlStr = htmlSb.toString();
		// 返回经过清洁的html文本
		return htmlStr;
	//	return clearFormat(htmlStr, filepath);
	}
	
	
	public static String subString(String html,String prefix,String subfix) {
		return html.substring(html.indexOf(prefix)+prefix.length(), html.indexOf(subfix));
	}

	/**
	 * 清除一些不需要的html标记
	 * 
	 * @param htmlStr
	 *            带有复杂html标记的html语句
	 * @return 去除了不需要html标记的语句
	 */
	public static String clearFormat(String htmlStr, String docImgPath) {
		// 获取body内容的正则
		String bodyReg = "<BODY .*</BODY>";
		Pattern bodyPattern = Pattern.compile(bodyReg);
		Matcher bodyMatcher = bodyPattern.matcher(htmlStr);
		if (bodyMatcher.find()) {
			// 获取BODY内容，并转化BODY标签为DIV
			htmlStr = bodyMatcher.group().replaceFirst("<BODY", "<DIV")
					.replaceAll("</BODY>", "</DIV>");
		}
		// 调整图片地址
		htmlStr = htmlStr.replaceAll("<IMG SRC=\"", "<IMG SRC=\"" + docImgPath
				+ "/");
		// 把<P></P>转换成</div></div>保留样式
		// content = content.replaceAll("(<P)([^>]*>.*?)(<\\/P>)",
		// "<div$2</div>");
		// 把<P></P>转换成</div></div>并删除样式
		htmlStr = htmlStr.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");
		// 删除不需要的标签
		htmlStr = htmlStr
				.replaceAll(
						"<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>",
						"");
		// 删除不需要的属性
		htmlStr = htmlStr
				.replaceAll(
						"<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>",
						"<$1$2>");
		return htmlStr;
	}
}
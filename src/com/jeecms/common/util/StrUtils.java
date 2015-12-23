package com.jeecms.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 字符串的帮助类，提供静态方法，不可以实例化。
 * 
 */
public class StrUtils {
	/**
	 * 禁止实例化
	 */
	private StrUtils() {
	}

	/**
	 * 处理url
	 * 
	 * url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
	 * 
	 * @param url
	 * @return
	 */
	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if (url.equals("") || url.startsWith("http://")
				|| url.startsWith("https://")) {
			return url;
		} else {
			return "http://" + url.trim();
		}
	}

	/**
	 * 分割并且去除空格
	 * 
	 * @param str
	 *            待分割字符串
	 * @param sep
	 *            分割符
	 * @param sep2
	 *            第二个分隔符
	 * @return 如果str为空，则返回null。
	 */
	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (!StringUtils.isBlank(sep2)) {
			str = StringUtils.replace(str, sep2, sep);
		}
		String[] arr = StringUtils.split(str, sep);
		// trim
		for (int i = 0, len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	/**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isBlank(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}

	public static String htmlCut(String s, int len, String append) {
		String text = html2Text(s, len * 2);
		return textCut(text, len, append);
	}

	public static String html2Text(String html, int len) {
		try {
			Lexer lexer = new Lexer(html);
			Node node;
			StringBuilder sb = new StringBuilder(html.length());
			while ((node = lexer.nextNode()) != null) {
				if (node instanceof TextNode) {
					sb.append(node.toHtml());
				}
				if (sb.length() > len) {
					break;
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param keyword 源词汇
	 * @param smart 是否智能分词
	 * @return 分词词组(,拼接)
	 */
	public static String getKeywords(String keyword, boolean smart) {
		StringReader reader = new StringReader(keyword);
		IKSegmenter iks = new IKSegmenter(reader, smart);
		StringBuilder buffer = new StringBuilder();
		try {
			Lexeme lexeme;
			while ((lexeme = iks.next()) != null) {
				buffer.append(lexeme.getLexemeText()).append(',');
			}
		} catch (IOException e) {
		}
		//去除最后一个,
		if (buffer.length() > 0) {
			buffer.setLength(buffer.length() - 1);
		}
		return buffer.toString();
	}
	
	/**
	 * p换行
	 * @param inputString
	 * @return
	 */
	public static String removeHtmlTagP(String inputString) {  
	    if (inputString == null)  
	        return null;  
	    String htmlStr = inputString; // 含html标签的字符串  
	    String textStr = "";  
	    java.util.regex.Pattern p_script;  
	    java.util.regex.Matcher m_script;  
	    java.util.regex.Pattern p_style;  
	    java.util.regex.Matcher m_style;  
	    java.util.regex.Pattern p_html;  
	    java.util.regex.Matcher m_html;  
	    try {  
	        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>  
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";   
	        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>  
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";   
	        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
	        m_style = p_style.matcher(htmlStr);  
	        htmlStr = m_style.replaceAll(""); // 过滤style标签  
	        htmlStr.replace("</p>", "\n");
	        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	        m_html = p_html.matcher(htmlStr);  
	        htmlStr = m_html.replaceAll(""); // 过滤html标签  
	        textStr = htmlStr;  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return textStr;// 返回文本字符串  
	}  
	
	public static String removeHtmlTag(String inputString) {  
	    if (inputString == null)  
	        return null;  
	    String htmlStr = inputString; // 含html标签的字符串  
	    String textStr = "";  
	    java.util.regex.Pattern p_script;  
	    java.util.regex.Matcher m_script;  
	    java.util.regex.Pattern p_style;  
	    java.util.regex.Matcher m_style;  
	    java.util.regex.Pattern p_html;  
	    java.util.regex.Matcher m_html;  
	    try {  
	        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>  
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";   
	        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>  
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";   
	        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
	        m_style = p_style.matcher(htmlStr);  
	        htmlStr = m_style.replaceAll(""); // 过滤style标签  
	        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	        m_html = p_html.matcher(htmlStr);  
	        htmlStr = m_html.replaceAll(""); // 过滤html标签  
	        textStr = htmlStr;  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return textStr;// 返回文本字符串  
	}  

	/**
	 * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
	 * 
	 * @param str
	 * @param search
	 * @return
	 */
	public static boolean contains(String str, String search) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
			return false;
		}
		String reg = StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	public static boolean containsKeyString(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		if (str.contains("'") || str.contains("\"") || str.contains("\r")
				|| str.contains("\n") || str.contains("\t")
				|| str.contains("\b") || str.contains("\f")) {
			return true;
		}
		return false;
	}
	
	
	public static String addCharForString(String str, int strLength,char c,int position) {
		  int strLen = str.length();
		  if (strLen < strLength) {
			  while (strLen < strLength) {
			  StringBuffer sb = new StringBuffer();
			  if(position==1){
				  //右補充字符c
				  sb.append(c).append(str);
			  }else{
				//左補充字符c
				  sb.append(str).append(c);
			  }
			  str = sb.toString();
			  strLen = str.length();
			  }
			}
		  return str;
	 }

	// 将""和'转义
	public static String replaceKeyString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
					"\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
					"\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}
	
	//单引号转化成双引号
	public static String replaceString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\"").replace("\"", "\\\"").replace("\r",
					"\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
					"\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}
	
	public static String getSuffix(String str) {
		int splitIndex = str.lastIndexOf(".");
		return str.substring(splitIndex + 1);
	}

	public static void main(String args[]) {
		System.out.println(replaceKeyString("&nbsp;\r" + "</p>"));
	}

}

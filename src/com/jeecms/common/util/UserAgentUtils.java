package com.jeecms.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tom
 */
public class UserAgentUtils {

	/**
	 * 获取客户端浏览器信息
	 * @param request
	 * @return string
	 */
	public static String getBrowserInfo(HttpServletRequest req) {
		String browserInfo = "other";
		String ua = req.getHeader("User-Agent").toLowerCase();
		String s;
		String version;
		String msieP = "msie ([\\d.]+)";
		String ieheighP = "rv:([\\d.]+)";
		String firefoxP = "firefox\\/([\\d.]+)";
		String chromeP = "chrome\\/([\\d.]+)";
		String operaP = "opr.([\\d.]+)";
		String safariP = "version\\/([\\d.]+).*safari";

		Pattern pattern = Pattern.compile(msieP);
		Matcher mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			version=s.split(" ")[1];
			browserInfo = "ie " + version.substring(0, version.indexOf("."));
			return browserInfo;
		}
		
		pattern = Pattern.compile(firefoxP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			version=s.split("/")[1];
			browserInfo = "firefox " + version.substring(0, version.indexOf("."));
			return browserInfo;
		}
		
		pattern = Pattern.compile(ieheighP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			version=s.split(":")[1];
			browserInfo = "ie " + version.substring(0, version.indexOf("."));
			return browserInfo;
		}
		
		pattern = Pattern.compile(operaP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			version=s.split("/")[1];
			browserInfo = "opera " + version.substring(0, version.indexOf("."));
			return browserInfo;
		}
		
		pattern = Pattern.compile(chromeP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			version=s.split("/")[1];
			browserInfo = "chrome " + version.substring(0, version.indexOf("."));
			return browserInfo;
		}
		
		pattern = Pattern.compile(safariP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			version=s.split("/")[1].split(" ")[0];
			browserInfo = "safari " +version.substring(0, version.indexOf("."));
			return browserInfo;
		}
		return browserInfo;
	}

	/**
	 * 获取客户端操作系统信息
	 * @param request
	 * @return string
	 */
	public static String getClientOS(HttpServletRequest req) {
		String userAgent = req.getHeader("User-Agent");
		String cos = "unknow os";
		Pattern p = Pattern.compile(".*(Windows NT 6\\.2).*");
		Matcher m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win 8";
			return cos;
		}
		
		p = Pattern.compile(".*(Windows NT 6\\.1).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win 7";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.1|Windows XP).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "WinXP";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.2).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win2003";
			return cos;
		}

		p = Pattern.compile(".*(Win2000|Windows 2000|Windows NT 5\\.0).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win2000";
			return cos;
		}

		p = Pattern.compile(".*(Mac|apple|MacOS8).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "MAC";
			return cos;
		}

		p = Pattern.compile(".*(WinNT|Windows NT).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "WinNT";
			return cos;
		}

		p = Pattern.compile(".*Linux.*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Linux";
			return cos;
		}

		p = Pattern.compile(".*(68k|68000).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Mac68k";
			return cos;
		}

		p = Pattern
				.compile(".*(9x 4.90|Win9(5|8)|Windows 9(5|8)|95/NT|Win32|32bit).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win9x";
			return cos;
		}

		return cos;
	}
}

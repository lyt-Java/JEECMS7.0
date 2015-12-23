package com.jeecms.cms.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jeecms.cms.Constants;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;

/**
 * 网站防火墙拦截器
 * 
 */
public class FireWallInterceptor extends HandlerInterceptorAdapter implements InitializingBean ,DisposableBean {
	private  static  String property_firewall_open = "firewall.open";
	private  static  String property_firewall_domain = "firewall.domain";
	private  static  String property_firewall_hour = "firewall.hour";
	private  static  String property_firewall_week = "firewall.week";
	private  static  String property_firewall_ips = "firewall.ips";
	
	public static final String FIREWALL_CONFIG_LASTMODIFIED = "firewall_config_lastmodified";
	
	private InputStream in;
	private Properties p=new Properties();
	private File fireWallConfigFile;
	public void afterPropertiesSet() throws Exception {
		fireWallConfigFile=new File(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
		in = new FileInputStream(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
		p.load(in);
	}
	public void destroy() throws Exception {
		in.close();
	}
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Boolean configFileModified=false;
		Long configLastModifiedTime=getFireWallConfigFileLastModifiedTime(request);
		if(configLastModifiedTime==null||fireWallConfigFile.lastModified()>configLastModifiedTime){
			configFileModified=true;
			changeConfigModifiedTime(request);
		}
		String open;
		String domain;
		String ips;
		String week;
		String hour;
		if(configFileModified){
			in = new FileInputStream(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
			p.load(in);
			in.close();
		}
		open = p.getProperty(property_firewall_open);
		domain=p.getProperty(property_firewall_domain);
		ips=p.getProperty(property_firewall_ips);
		week=p.getProperty(property_firewall_week);
		hour=p.getProperty(property_firewall_hour);
		String[] ipArrays=StringUtils.split(ips,",");
		String[] weekArrays=StringUtils.split(week,",");
		String[] hourArrays=StringUtils.split(hour,",");
		
		String requestIp = RequestUtils.getIpAddr(request);
		if(open.equals("1")){
			if(!isAuthDomain(domain,request.getServerName())){
				return false;
			}else{
				if(!isAuthIp(ipArrays, requestIp)){
					return false;
				}else{
					if(!isAuthWeek(weekArrays)){
						return false;
					}else{
						if(!isAuthHour(hourArrays)){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	private Boolean isAuthDomain(String domain,String requestDomain) {
		if(StringUtils.isNotBlank(domain)){
			if(domain.equals(requestDomain)){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	private Boolean isAuthIp(String[]ips,String requestIp){
		if(ips!=null&&ips.length>0){
			for(String ip:ips){
				if(ip.equals(requestIp)){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	private Boolean isAuthWeek(String[]weeks){
		Calendar c=Calendar.getInstance();
		int day_of_week=c.get(Calendar.DAY_OF_WEEK);
		if(weeks!=null&&weeks.length>0){
			for(String week:weeks){
				if(week.equals(day_of_week+"")){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	private Boolean isAuthHour(String[]hours){
		Calendar c=Calendar.getInstance();
		int hour_of_day=c.get(Calendar.HOUR_OF_DAY);
		if(hours!=null&&hours.length>0){
			for(String hour:hours){
				if(hour.equals(hour_of_day+"")){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	private Long getFireWallConfigFileLastModifiedTime(HttpServletRequest request){
		return (Long) request.getSession().getServletContext().getAttribute(FIREWALL_CONFIG_LASTMODIFIED);
	}
	private void changeConfigModifiedTime(HttpServletRequest request){
		request.getSession().getServletContext().setAttribute(FIREWALL_CONFIG_LASTMODIFIED, Calendar.getInstance().getTime().getTime());
	}
	@Autowired
	private RealPathResolver realPathResolver;
}
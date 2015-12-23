package com.jeecms.cms.action.admin.assist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.common.web.session.SessionProvider;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.web.util.CmsUtils;

import com.jeecms.cms.Constants;

/**
 * @author Tom
 */
@Controller
public class CmsFireWallConfigAct {

	public static final String FIREWALL_LOGIN = "firewall_login";
	public static final String FIREWALL_CONFIG_LASTMODIFIED = "firewall_config_lastmodified";
	String property_firewall_password = "firewall.password";
	String property_firewall_open = "firewall.open";
	String property_firewall_domain = "firewall.domain";
	String property_firewall_hour = "firewall.hour";
	String property_firewall_week = "firewall.week";
	String property_firewall_ips = "firewall.ips";
	
	@RequiresPermissions("config:v_login")
	@RequestMapping("/config/v_login.do")
	public String v_login(HttpServletRequest request, Model model) {
		return "config/firewall_login";
	}
	
	@RequiresPermissions("config:o_login")
	@RequestMapping("/config/o_login.do")
	public String o_login(String password,HttpServletRequest request,HttpServletResponse response,Model model)
			throws IOException {
		InputStream in = new FileInputStream(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
		Properties p = new Properties();
		p.load(in);
		String pass = p.getProperty(property_firewall_password);
		if(pass.equals(password)){
			session.setAttribute(request, response, FIREWALL_LOGIN, true);
			return edit(request,model);
		}else{
			return "config/firewall_login_error";
		}
	}
	
	@RequiresPermissions("config:v_firewall")
	@RequestMapping("/config/v_firewall.do")
	public String edit(HttpServletRequest request, Model model)
			throws IOException {
		Boolean is_login=(Boolean) session.getAttribute(request, FIREWALL_LOGIN);
		if(is_login!=null&&is_login){
			InputStream in = new FileInputStream(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
			Properties p = new Properties();
			p.load(in);
			String password = p.getProperty(property_firewall_password);
			String open = p.getProperty(property_firewall_open);
			String domain = p.getProperty(property_firewall_domain);
			String hour = p.getProperty(property_firewall_hour);
			String week = p.getProperty(property_firewall_week);
			String ips = p.getProperty(property_firewall_ips);
			String[] hours=StringUtils.split(hour, ",");
			Set<Integer>hourIds=new HashSet<Integer>();
			for(String h:hours){
				hourIds.add(Integer.decode(h));
			}
			String[] weeks=StringUtils.split(week, ",");
			Set<Integer>weekIds=new HashSet<Integer>();
			for(String w:weeks){
				weekIds.add(Integer.decode(w));
			}
			model.addAttribute("password", password);
			model.addAttribute("open", open);
			model.addAttribute("domain", domain);
			model.addAttribute("hour", hour);
			model.addAttribute("week", week);
			model.addAttribute("ips", ips);
			model.addAttribute("hours", hourIds);
			model.addAttribute("weeks", weekIds);
			return "config/firewall_edit";
		}else{
			return "config/firewall_login";
		}
	}

	@RequiresPermissions("config:o_firewall")
	@RequestMapping("/config/o_firewall.do")
	public String update(HttpServletRequest request, Model model, String open,String oldPassword,
			String password, String domain, String[] week,String[] hour, String ips)
			throws IOException {
		InputStream in = new FileInputStream(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
		Properties p = new Properties();
		p.load(in);
		if(StringUtils.isNotBlank(password)){
			p.setProperty(property_firewall_password, password);
		}
		p.setProperty(property_firewall_open, open);
		p.setProperty(property_firewall_domain, domain);
		CmsSite site=CmsUtils.getSite(request);
		configSiteDomainAlias(site, domain);
		if(week!=null&&week.length>0){
			p.setProperty(property_firewall_week,convertArrayToString(week));
		}else{
			p.setProperty(property_firewall_week, "");
		}
		if(hour!=null&&hour.length>0){
			p.setProperty(property_firewall_hour,convertArrayToString(hour));
		}else{
			p.setProperty(property_firewall_hour,"");
		}
		p.setProperty(property_firewall_ips, ips);
		OutputStream out = new FileOutputStream(realPathResolver.get(Constants.FIREWALL_CONFIGPATH));
		p.store(out, "update firewall config");
		model.addAttribute("message", "global.success");
		return edit(request, model);
	}
	
	private String convertArrayToString(String[]arrays){
		String str="";
		if(arrays!=null&&arrays.length>0){
			for(String s:arrays){
				str+=s+",";
			}
		}
		return str;
	}
	private void configSiteDomainAlias(CmsSite site,String domain){
		if(StringUtils.isNotBlank(site.getDomainAlias())){
			if(!site.getDomainAlias().contains(domain)){
				site.setDomainAlias(site.getDomainAlias()+","+domain);
			}
		}else{
			site.setDomainAlias(domain);
		}
		if(site.getUploadFtp()!=null){
			siteManager.update(site, site.getUploadFtp().getId());
		}else{
			siteManager.update(site, null);
		}
	}
	@Autowired
	private RealPathResolver realPathResolver;
	@Autowired
	private SessionProvider session;
	@Autowired
	private CmsSiteMng siteManager;

}

package com.jeecms.cms.action.admin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class FrameAct {
	@RequiresPermissions("frame:config_main")
	@RequestMapping("/frame/config_main.do")
	public String configMain(ModelMap model) {
		return "frame/config_main";
	}

	@RequiresPermissions("frame:config_left")
	@RequestMapping("/frame/config_left.do")
	public String configLeft(ModelMap model) {
		return "frame/config_left";
	}

	@RequiresPermissions("frame:config_right")
	@RequestMapping("/frame/config_right.do")
	public String configRight(ModelMap model) {
		return "frame/config_right";
	}

	@RequiresPermissions("frame:user_main")
	@RequestMapping("/frame/user_main.do")
	public String userMain(ModelMap model) {
		return "frame/user_main";
	}

	@RequiresPermissions("frame:user_left")
	@RequestMapping("/frame/user_left.do")
	public String userLeft(ModelMap model) {
		return "frame/user_left";
	}

	@RequiresPermissions("frame:user_right")
	@RequestMapping("/frame/user_right.do")
	public String userRight(ModelMap model) {
		return "frame/user_right";
	}

	@RequiresPermissions("frame:maintain_main")
	@RequestMapping("/frame/maintain_main.do")
	public String maintainMain(ModelMap model) {
		return "frame/maintain_main";
	}

	@RequiresPermissions("frame:maintain_left")
	@RequestMapping("/frame/maintain_left.do")
	public String maintainLeft(ModelMap model) {
		model.addAttribute("db", db);
		return "frame/maintain_left";
	}

	@RequiresPermissions("frame:maintain_right")
	@RequestMapping("/frame/maintain_right.do")
	public String maintainRight(ModelMap model) {
		return "frame/maintain_right";
	}
	

	@RequiresPermissions("frame:content_main")
	@RequestMapping("/frame/content_main.do")
	public String contentMain(String source,ModelMap model) {
		model.addAttribute("source", source);
		return "frame/content_main";
	}
	
	@RequiresPermissions("frame:statistic_main")
	@RequestMapping("/frame/statistic_main.do")
	public String statisticMain(ModelMap model) {
		return "frame/statistic_main";
	}
	
	@RequiresPermissions("frame:statistic_left")
	@RequestMapping("/frame/statistic_left.do")
	public String statisticLeft(){
		return "frame/statistic_left";
	}
	
	@RequiresPermissions("frame:statistic_right")
	@RequestMapping("/frame/statistic_right.do")
	public String statisticRight(){
		return "frame/statistic_right";
	}
	
	
	@RequiresPermissions("frame:expand_main")
	@RequestMapping("/frame/expand_main.do")
	public String expandMain(ModelMap model) {
		return "frame/expand_main";
	}
	
	@RequiresPermissions("frame:expand_left")
	@RequestMapping("/frame/expand_left.do")
	public String expandLeft(ModelMap model){
		Map<String,String>menus=getMenus();
		Map<String,String>menuNames=new HashMap<String,String>();
		Map<String,String>menuUrls=new HashMap<String,String>();
		Map<String,String>menuPerms=new HashMap<String,String>();
		Set<String>menuPrioritys= new TreeSet<String>(new Comparator<String>(){  
		    public int compare(String o1, String o2) {  
		        if(Integer.parseInt(o1)>Integer.parseInt(o2)){  
		            return 1;  
		        }else if (Integer.parseInt(o1)==Integer.parseInt(o2)) {  
		            return 0;  
		        }  
		        return -1;  
		    }  
		});  
		menuPrioritys.addAll(menus.keySet());
		Iterator<String>it=menuPrioritys.iterator();
		while(it.hasNext()){
			String priority=it.next();
			String str=menus.get(priority);
			String[]array=str.split(";");
			menuNames.put(priority, array[0]);
			menuUrls.put(priority, array[1]);
			menuPerms.put(priority, array[2]);
		}
		model.addAttribute("menuPrioritys", menuPrioritys);
		model.addAttribute("menuNames", menuNames);
		model.addAttribute("menuUrls", menuUrls);
		model.addAttribute("menuPerms", menuPerms);
		return "frame/expand_left";
	}
	
	@RequiresPermissions("frame:expand_right")
	@RequestMapping("/frame/expand_right.do")
	public String expandRight(){
		return "frame/expand_right";
	}
	private Map<String,String> menus;
	//数据库种类(mysql、oracle、sqlserver、db2)
	private String db;

	public Map<String, String> getMenus() {
		return menus;
	}

	public void setMenus(Map<String, String> menus) {
		this.menus = menus;
	}
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
	
	
}

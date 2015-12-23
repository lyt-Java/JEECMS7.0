package com.jeecms.cms.action.front;


import static com.jeecms.cms.Constants.TPLDIR_MEMBER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubject.Builder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.entity.main.CmsThirdAccount;
import com.jeecms.cms.manager.main.CmsThirdAccountMng;
import com.jeecms.common.security.encoder.PwdEncoder;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.session.SessionProvider;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.entity.UnifiedUser;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.manager.UnifiedUserMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;


/**
 * 第三方登录Action
 * 腾讯qq、新浪微博登陆
 */
@Controller
public class ThirdLoginAct {
	public static final String TPL_BIND = "tpl.member.bind";
	public static final String TPL_AUTH = "tpl.member.auth";
	
	
	@RequestMapping(value = "/public_auth.jspx")
	public String auth(String openId,HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, TPL_AUTH);
	}
	
	@RequestMapping(value = "/public_auth_login.jspx")
	public void authLogin(String key,String source,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		if(StringUtils.isNotBlank(source)){
			if(source.equals(CmsThirdAccount.QQ_PLAT)){
				session.setAttribute(request,response,CmsThirdAccount.QQ_KEY, key);
			}else if(source.equals(CmsThirdAccount.QQ_WEBO_PLAT)){
				session.setAttribute(request,response,CmsThirdAccount.QQ_WEBO_KEY, key);
			}else if(source.equals(CmsThirdAccount.SINA_PLAT)){
				session.setAttribute(request,response,CmsThirdAccount.SINA_KEY, key);
			}
		}
		JSONObject json=new JSONObject();
		//库中存放的是加密后的key
		if(StringUtils.isNotBlank(key)){
			key=pwdEncoder.encodePassword(key);
		}
		CmsThirdAccount account=accountMng.findByKey(key);
		if(account!=null){
			json.put("succ", true);
			//已绑定直接登陆
			loginByKey(key, request, response, model);
		}else{
			json.put("succ", false);
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequestMapping(value = "/public_bind.jspx",method = RequestMethod.GET)
	public String bind_get(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, TPL_BIND);
	}
	
	@RequestMapping(value = "/public_bind.jspx",method = RequestMethod.POST)
	public String bind_post(String username,String password,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		boolean usernameExist=unifiedUserMng.usernameExist(username);
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors=WebErrors.create(request);
		String source="";
		if(!usernameExist){
			//用户名不存在
			errors.addErrorCode("error.usernameNotExist");
		}else{
			UnifiedUser u=unifiedUserMng.getByUsername(username);
			boolean passwordValid=unifiedUserMng.isPasswordValid(u.getId(), password);
			if(!passwordValid){
				errors.addErrorCode("error.passwordInvalid");
			}else{
				//获取用户来源
				String openId=(String) session.getAttribute(request, CmsThirdAccount.QQ_KEY);
				String uid=(String) session.getAttribute(request, CmsThirdAccount.SINA_KEY);
				String weboOpenId=(String) session.getAttribute(request, CmsThirdAccount.QQ_WEBO_KEY);
				if(StringUtils.isNotBlank(openId)){
					source=CmsThirdAccount.QQ_PLAT;
				}else if(StringUtils.isNotBlank(uid)){
					source=CmsThirdAccount.SINA_PLAT;
				}else if(StringUtils.isNotBlank(weboOpenId)){
					source=CmsThirdAccount.QQ_WEBO_PLAT;
				}
				//提交登录并绑定账号
				loginByUsername(username, request, response, model);
			}
		}
		if(errors.hasErrors()){
			errors.toModel(model);
			model.addAttribute("success",false);
		}else{
			model.addAttribute("success",true);
		}
		model.addAttribute("source", source);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),TPLDIR_MEMBER, TPL_BIND);
	}
	
	@RequestMapping(value = "/public_bind_username.jspx")
	public String bind_username_post(String username,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors=WebErrors.create(request);
		String source="";
		if(StringUtils.isBlank(username)){
			//用户名为空
			errors.addErrorCode("error.usernameRequired");
		}else{
			boolean usernameExist=unifiedUserMng.usernameExist(username);
			if(usernameExist){
				//用户名存在
				errors.addErrorCode("error.usernameExist");
			}else{
				//获取用户来源
				String openId=(String) session.getAttribute(request, CmsThirdAccount.QQ_KEY);
				String uid=(String) session.getAttribute(request, CmsThirdAccount.SINA_KEY);
				String weboOpenId=(String) session.getAttribute(request, CmsThirdAccount.QQ_WEBO_KEY);
				//(获取到登录授权key后可以注册用户)
				if(StringUtils.isNotBlank(openId)||StringUtils.isNotBlank(uid)||StringUtils.isNotBlank(weboOpenId)){
					//初始设置密码同用户名
					cmsUserMng.registerMember(username, null, username, RequestUtils.getIpAddr(request), null, null, false, new CmsUserExt(), null);
				}
				if(StringUtils.isNotBlank(openId)){
					source=CmsThirdAccount.QQ_PLAT;
				}else if(StringUtils.isNotBlank(uid)){
					source=CmsThirdAccount.SINA_PLAT;
				}else if(StringUtils.isNotBlank(weboOpenId)){
					source=CmsThirdAccount.QQ_WEBO_PLAT;
				}
				//提交登录并绑定账号
				loginByUsername(username, request, response, model);
			}
		}
		if(errors.hasErrors()){
			errors.toModel(model);
			model.addAttribute("success",false);
		}else{
			model.addAttribute("success",true);
		}
		model.addAttribute("source", source);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),TPLDIR_MEMBER, TPL_BIND);
	}
	
	/**
	 * 用户名登陆,绑定用户名和第三方账户key
	 * @param username
	 * @param request
	 * @param response
	 * @param model
	 */
	private void loginByUsername(String username, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String openId=(String) session.getAttribute(request, CmsThirdAccount.QQ_KEY);
		String uid=(String) session.getAttribute(request, CmsThirdAccount.SINA_KEY);
		String weboOpenId=(String) session.getAttribute(request, CmsThirdAccount.QQ_WEBO_KEY);
		if(StringUtils.isNotBlank(openId)){
			loginShiro(request, response, username);
			//绑定账户
			bind(username, openId,  CmsThirdAccount.QQ_PLAT);
		}
		if(StringUtils.isNotBlank(uid)){
			loginShiro(request, response, username);
			//绑定账户
			bind(username, uid,  CmsThirdAccount.SINA_PLAT);
		}
		if(StringUtils.isNotBlank(weboOpenId)){
			loginShiro(request, response, username);
			//绑定账户
			bind(username, weboOpenId,  CmsThirdAccount.QQ_WEBO_PLAT);
		}
	}
	
	/**
	 * 已绑定用户key登录
	 * @param key
	 * @param request
	 * @param response
	 * @param model
	 */
	private void loginByKey(String key,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsThirdAccount account=accountMng.findByKey(key);
		if(StringUtils.isNotBlank(key)&&account!=null){
			String username=account.getUsername();
			loginShiro(request, response, username);
		}
	}
	
	
	private void loginShiro(HttpServletRequest request,HttpServletResponse response,String username){
		PrincipalCollection principals = new SimplePrincipalCollection(username, username);  
		Builder builder = new WebSubject.Builder( request,response);  
		builder.principals(principals);  
		builder.authenticated(true);  
		WebSubject subject = builder.buildWebSubject();  
		ThreadContext.bind(subject); 
	}
	
	private void bind(String username,String openId,String source){
		CmsThirdAccount account=accountMng.findByKey(openId);
		if(account==null){
			account=new CmsThirdAccount();
			account.setUsername(username);
			//第三方账号唯一码加密存储 防冒名登录
			openId=pwdEncoder.encodePassword(openId);
			account.setAccountKey(openId);
			account.setSource(source);
			accountMng.save(account);
		}
	}
	
	@Autowired
	private UnifiedUserMng unifiedUserMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsThirdAccountMng accountMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private PwdEncoder pwdEncoder;
}

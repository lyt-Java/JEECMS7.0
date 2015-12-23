package com.jeecms.cms.action.member;

import static com.jeecms.cms.Constants.TPLDIR_COMMENT;
import static com.jeecms.common.page.SimplePage.cpn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsComment;
import com.jeecms.cms.manager.assist.CmsCommentMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.MemberConfig;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;

/**
 * 会员中心获取评论Action
 * 
 * @author 江西金磊科技发展有限公司
 * 
 */
@Controller
public class CommentMemberAct {
	private static final Logger log = LoggerFactory
			.getLogger(CommentMemberAct.class);

	public static final String COMMENT_LIST = "tpl.commentLists";
	public static final String COMMENT_MNG = "tpl.commentMng";
	public static final String COMMENT_REPLY = "tpl.commentReply";

	/**
	 * 我的评论
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/mycomments.jspx")
	public String mycomments(Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination pagination = commentMng.getPageForMember(site.getId(), null,
				user.getId(), null, null, null, null, true, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_COMMENT, COMMENT_LIST);
	}
	/**
	 * 查看评论回复
	 */
	@RequestMapping(value = "/member/comment_replay.jspx")
	public String guestbook_replay(Integer id, String nextUrl,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		CmsComment comment=commentMng.findById(id);
		if(!comment.getCommentUser().equals(user)){
			WebErrors errors=WebErrors.create(request);
			errors.addErrorCode("error.noPermissionsView");
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("comment", comment);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_COMMENT, COMMENT_REPLY);
	}

	/**
	 * 我的信息所有的评论
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/news_comments.jspx")
	public String news_comments(Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination pagination = commentMng.getPageForMember(site.getId(), null,
				null, user.getId(), null, null, null, true, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_COMMENT, COMMENT_MNG);
	}

	/**
	 * 删除评论（id，评论人id，来访ip）
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/comment_delete.jspx")
	public String delete(Integer commentId, Integer userId, String ip,
			Integer pageNo, String nextUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		// 删除单条评论
		CmsComment bean;
		if (commentId != null) {
			CmsComment cmsComment=commentMng.findById(commentId);
			if(cmsComment==null){
				return FrontUtils.showMessage(request, model, "comment.notFound");
			}
			if(!canDeleteComment(cmsComment, user)){
				return FrontUtils.showMessage(request, model, "comment.deleteError");
			}
			bean = commentMng.deleteById(commentId);
			log.info("delete CmsComment id={}", bean.getId());
		} else {
			// 依据评论人或者评论ip删除评论
			List<CmsComment> comments = commentMng.getListForDel(site.getId(),
					user.getId(), userId, ip);
			for (int i = 0; i < comments.size(); i++) {
				bean = comments.get(i);
				if(!canDeleteComment(bean, user)){
					return FrontUtils.showMessage(request, model, "comment.deleteError");
				}
				commentMng.deleteById(comments.get(i).getId());
				log.info("delete CmsComment id={}", bean.getId());
			}
		}
		/*
		 * Pagination pagination = commentMng.getPageForMember(site.getId(),
		 * null, null, user.getId(), null, null, null, true, cpn(pageNo),
		 * CookieUtils.getPageSize(request)); model.addAttribute("pagination",
		 * pagination);
		 */
		// 返回评论列表
		return FrontUtils.showSuccess(request, model, nextUrl);
	}
	
	private  boolean canDeleteComment(CmsComment comment,CmsUser user){
		//匿名用户评论文章的所有者可以删除
		if(comment.getCommentUser()==null&&!comment.getContent().getUser().equals(user)){
			return false;
		}else if(comment.getCommentUser()==null&&comment.getContent().getUser().equals(user)){
			return true;
		}else{
			//非匿名用户评论 文章的所有者可以删除，评论者也可以删除
			if(comment.getCommentUser().equals(user)||comment.getContent().getUser().equals(user)){
				return true;
			}else{
				return false;
			}
		}
	}

	@Autowired
	private CmsCommentMng commentMng;
}

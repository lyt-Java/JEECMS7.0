package com.jeecms.cms.service;

import java.util.Set;

import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentExt;
import com.jeecms.cms.entity.main.ContentTxt;


/**
 * @author Tom
 */
public interface WeiXinSvc {
	/**
	 * 获取公众号的token
	 * @return  access_token
	 * access_token需要有缓存 微信限制2000次/天
	 */
	public String  getToken();
	/**
	 * 获取公众号的订阅用户
	 * @return
	 */
	public Set<String>  getUsers(String access_token);
	/**
	 * 发送纯文本消息
	 * @param title
	 * @param content
	 */
	public void  sendText(String access_token,String content);
	/**
	 * 上传多媒体视频
	 * @param access_token
	 * @param filePath
	 */
	public String  uploadFile(String access_token,String filePath,String type);
	/**
	 * 发送视频消息
	 * @param media_id
	 * @param title
	 * @param description
	 */
	public void  sendVedio(String access_token,String title,String description,String media_id);
	/**
	 * 发送图文消息
	 * @param url
	 * @param picurl
	 * @param title
	 * @param description
	 */
	public void  sendContent(String access_token,String title,String description,String url,String picurl);
	/**
	 * 
	 * @param access_token
	 * @param sendType 发送微信信息类型(1纯文本 2视频 3图文 0不发送)
	 * @param selectImg 图文图片选择(0自定义上传 1类型图 2标题图 3内容图)
	 * @param weixinImg 图文消息图片的自定义上传图片路径
	 * @param bean 内容bean
	 * @param ext 内容ext bean
	 * @param txt 内容txt bean
	 */
	public void sendMessage(Integer sendType,Integer selectImg,String weixinImg,Content bean, ContentExt ext, ContentTxt txt);
}

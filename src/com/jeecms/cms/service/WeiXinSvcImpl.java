package com.jeecms.cms.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecms.cms.Constants;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentExt;
import com.jeecms.cms.entity.main.ContentTxt;
import com.jeecms.common.upload.FileUpload;
import com.jeecms.common.util.PropertyUtils;
import com.jeecms.common.util.StrUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsConfig;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsConfigMng;

/**
 * @author Tom
 */
@Service
public class WeiXinSvcImpl implements WeiXinSvc {
	private static final Logger log = LoggerFactory.getLogger(WeiXinSvcImpl.class);
	//微信token地址key
	public static final String TOKEN_KEY="weixin.address.token";
	//微信公众号关注用户地址key
	public static final String USERS_KEY="weixin.address.users";
	//微信发送消息地址key
	public static final String SEND_KEY="weixin.address.send";
	//微信上传地址key
	public static final String UPLOAD_KEY="weixin.address.upload";
	//每次抽取关注号数量
	public static final Integer USERS_QUERY_MAX=10000;
	
	public String getToken() {
		String tokenGetUrl=PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)),TOKEN_KEY);
		CmsConfig config=configMng.get();
		String appid=config.getWeixinID();
		String secret=config.getWeixinKey();
		JSONObject tokenJson=new JSONObject();
		if(StringUtils.isNotBlank(appid)&&StringUtils.isNotBlank(secret)){
			tokenGetUrl+="&appid="+appid+"&secret="+secret;
			tokenJson=getUrlResponse(tokenGetUrl);
			try {
				return (String) tokenJson.get("access_token");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}

	public Set<String> getUsers(String access_token) {
		String usersGetUrl=PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)),USERS_KEY);
		usersGetUrl+="?access_token="+access_token;
		JSONObject data=getUrlResponse(usersGetUrl);
		Set<String>openIds=new HashSet<String>();
		Integer total=0,count=0;
		try {
			total=(Integer) data.get("total");
			count=(Integer) data.get("count");
			//总关注用户数超过默认一万
			if(count<total){
				openIds.addAll(getUsers(openIds,usersGetUrl, access_token, (String)data.get("next_openid")));
			}else{
				//有关注者 json才有data参数
				if(count>0){
					JSONObject openIdData=(JSONObject) data.get("data");
					JSONArray openIdArray= (JSONArray) openIdData.get("openid");
					for(int i=0;i<openIdArray.length();i++){
						openIds.add((String) openIdArray.get(i));
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return openIds;
	}

	
	public String  uploadFile(String access_token,String filePath,String type){
		String sendGetUrl=PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)),UPLOAD_KEY);
        String url = sendGetUrl+"?access_token=" + access_token;
		String result = null;
		String mediaId="";
		FileUpload fileUpload = new FileUpload();
		try {
			result = fileUpload.uploadFile(url,filePath, type);
			JSONObject json=new JSONObject(result);
			mediaId=json.getString("media_id");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediaId;
	}

	public void sendText(String access_token,String content) {
		String sendGetUrl=PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)),SEND_KEY);
        String url = sendGetUrl+"?access_token=" + access_token;
		Set<String> openIds=getUsers(access_token);
	    //发送给所有关注者消息
		for(String openId:openIds){
		    String strJson = "{\"touser\" :\""+openId+"\",";
	        strJson += "\"msgtype\":\"text\",";
	        strJson += "\"text\":{";
	        strJson += "\"content\":\""+content+"\"";
	        strJson += "}}";
		    post(url, strJson);
		}
	}
	
	public void sendContent(String access_token,String title, String description, String url,
			String picurl) {
		String sendUrl=PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)),SEND_KEY);
        sendUrl = sendUrl+"?access_token=" + access_token;
		Set<String> openIds=getUsers(access_token);
		if(description==null){
			description="";
		}
	    //发送给所有关注者消息
		for(String openId:openIds){
		    String strJson = "{\"touser\" :\""+openId+"\",";
	        strJson += "\"msgtype\":\"news\",";
	        strJson += "\"news\":{";
	        strJson += "\"articles\": [{";
	        strJson +="\"title\":\""+title+"\",";    
	        strJson +="\"description\":\""+description+"\",";  
	        strJson +="\"url\":\""+url+"\",";  
	        strJson +="\"picurl\":\""+picurl+"\"";  
	        strJson += "}]}}";
		    post(sendUrl, strJson);
		}
	}

	public void sendVedio(String access_token,String title, String description, String media_id) {
		String sendGetUrl=PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)),SEND_KEY);
        String url = sendGetUrl+"?access_token=" + access_token;
		Set<String> openIds=getUsers(access_token);
		if(description==null){
			description="";
		}
	    //发送给所有关注者消息
		for(String openId:openIds){
		    String strJson = "{\"touser\" :\""+openId+"\",";
	        strJson += "\"msgtype\":\"video\",";
	        strJson += "\"video\":{";
	        strJson += "\"media_id\":\""+media_id+"\",";
	        strJson += "\"title\":\""+title+"\",";
	        strJson += "\"description\":\""+description+"\"";
	        strJson += "}}";
		    post(url, strJson);
		}
	}
	
	public void sendMessage(Integer sendType,Integer selectImg,String weixinImg,Content bean, ContentExt ext, ContentTxt txt){
		CmsSite site=bean.getSite();
		//发送微信消息
		if(sendType!=null&&sendType!=0){
			String token=weixinTokenCache.getToken();
			if(sendType==1){
				//纯文本消息
				sendText(token, StrUtils.removeHtmlTagP(txt.getTxt()));
			}else if(sendType==2){
				//视频消息
				if(StringUtils.isNotBlank(ext.getMediaPath())){
					String vedioPath=ext.getMediaPath();
					if(StringUtils.isNotBlank(site.getContextPath())){
						vedioPath=vedioPath.substring(site.getContextPath().length());
					}
					//上传视频
					String media_id=uploadFile(token, realPathResolver.get(vedioPath),"video");
					sendVedio(token, ext.getTitle(), ext.getDescription(), media_id);
				}
			}else if(sendType==3){
				if(selectImg!=null){
					String weixinPicUrl="";
					if(selectImg==0){
						//自定义上传
						if (!StringUtils.isBlank(weixinImg)) {
							weixinImg = site.getProtocol()+site.getDomain()+":"+site.getPort()+weixinImg;
						}
						weixinPicUrl=weixinImg;
					}else if(selectImg==1){
						//类型图
						weixinPicUrl=bean.getTypeImgWhole();
					}else if(selectImg==2){
						//标题图
						weixinPicUrl=bean.getTitleImgWhole();
					}else if(selectImg==3){
						//内容图
						weixinPicUrl=bean.getContentImgWhole();
					}
					sendContent(token,ext.getTitle(), ext.getDescription(), bean.getUrl(), weixinPicUrl);
				}
			}
		}
	}
	
	private  Set<String> getUsers(Set<String>openIds,String url,String access_token,String next_openid) {
		JSONObject data=getUrlResponse(url);
		try {
			Integer count=(Integer) data.get("count");
			String nextOpenId=(String) data.get("next_openid");
			if(count>0){
				JSONObject openIdData=(JSONObject) data.get("data");
				JSONArray openIdArray= (JSONArray) openIdData.get("openid");
				for(int i=0;i<openIdArray.length();i++){
					openIds.add((String) openIdArray.get(i));
				}
			}
			if(StringUtils.isNotBlank(nextOpenId)){
				return getUsers(openIds,url, access_token, nextOpenId);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return openIds;
	}
	
	
	
	private  JSONObject getUrlResponse(String url){
		CharsetHandler handler = new CharsetHandler("UTF-8");
		try {
			HttpGet httpget = new HttpGet(new URI(url));
		    DefaultHttpClient client = new DefaultHttpClient();
	        client = (DefaultHttpClient) wrapClient(client);
			return new JSONObject(client.execute(httpget, handler));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	   
	private  void post(String url, String json)
	   {
	       DefaultHttpClient client = new DefaultHttpClient();
	       client = (DefaultHttpClient) wrapClient(client);
	       HttpPost post = new HttpPost(url);
	       try
	       {
	           StringEntity s = new StringEntity(json,"utf-8");
	           s.setContentType("application/json");
	           post.setEntity(s);
	           HttpResponse res = client.execute(post);
               HttpEntity entity = res.getEntity();
	           log.info(EntityUtils.toString(entity, "utf-8"));
	       }
	       catch (Exception e)
	       {
	    	   e.printStackTrace();
	       }
	   }
	 
	private  HttpClient wrapClient(HttpClient base) {
	        try {
	            SSLContext ctx = SSLContext.getInstance("TLS");
	            X509TrustManager tm = new X509TrustManager() {
	                public void checkClientTrusted(X509Certificate[] xcs,
	                        String string) throws CertificateException {
	                }
	 
	                public void checkServerTrusted(X509Certificate[] xcs,
	                        String string) throws CertificateException {
	                }
	 
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	            };
	            ctx.init(null, new TrustManager[] { tm }, null);
	            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
	            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	            ClientConnectionManager ccm = base.getConnectionManager();
	            SchemeRegistry sr = ccm.getSchemeRegistry();
	            sr.register(new Scheme("https", ssf, 443));
	            return new DefaultHttpClient(ccm, base.getParams());
	        } catch (Exception ex) {
	            return null;
	        }
	    }
	
	private class CharsetHandler implements ResponseHandler<String> {
		private String charset;

		public CharsetHandler(String charset) {
			this.charset = charset;
		}

		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (!StringUtils.isBlank(charset)) {
					return EntityUtils.toString(entity, charset);
				} else {
					return EntityUtils.toString(entity);
				}
			} else {
				return null;
			}
		}
	}
	
	@Autowired
	private RealPathResolver realPathResolver;
	@Autowired
	private CmsConfigMng configMng;
	@Autowired
	private WeixinTokenCache weixinTokenCache;

}

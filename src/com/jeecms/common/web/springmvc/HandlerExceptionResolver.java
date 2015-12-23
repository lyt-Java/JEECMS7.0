package com.jeecms.common.web.springmvc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.servlet.ModelAndView;

import com.jeecms.cms.Constants;
import com.jeecms.core.web.WebErrors;

/**
 * @author Tom
 */
public class HandlerExceptionResolver  implements org.springframework.web.servlet.HandlerExceptionResolver,InitializingBean ,DisposableBean  {
	
	@SuppressWarnings("unchecked")
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object o, Exception e) {
		 Map<String, Object> model = new HashMap<String, Object>();
	     String classSimpleName=o.getClass().getSuperclass().getSimpleName();
	     Class exceptionClass=e.getClass();
	     model.put("exception", e);
	   //  e.printStackTrace();
	     //数据被引用异常，脚本异常
	     if(exceptionClass.equals(DataIntegrityViolationException.class)){
	    	 String errorCode=(String) p.get(classSimpleName);
		     String errorMsg=WebErrors.create(request).getMessage(errorCode);
		     model.put("error",errorMsg); 
		     return new ModelAndView("/error/integrityViolation", model);
	     }else if(exceptionClass.equals(BadSqlGrammarException.class)){
		     String errorMsg=WebErrors.create(request).getMessage("BadSqlGrammar");
		     model.put("error",errorMsg);
		     return new ModelAndView("/error/badSqlGrammar", model);
	     }else{
		     return new ModelAndView("/error/uncaughtException", model);
	     }
	}

	private InputStream in;
	private Properties p=new Properties();
	public void afterPropertiesSet() throws Exception {
		in = new FileInputStream(realPathResolver.get(Constants.CLASS_ERROR_CODE));
		p.load(in);
	}
	public void destroy() throws Exception {
		in.close();
	}
	@Autowired
	private RealPathResolver realPathResolver;
}

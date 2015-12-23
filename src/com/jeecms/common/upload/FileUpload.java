package com.jeecms.common.upload;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Tom
 */
public class FileUpload {
	/**
	* 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
	* 
	* @param url 请求地址 form表单url地址
	* @param filePath 文件在服务器保存路径
	* @return String 正确上传返回media_id
	* @throws IOException
	*/
	
	/** 微信上传文件接口  */
	public  String uploadFile(String url,String filePath,String type) throws Exception {
		 File file = new File(filePath);
		 String result = null;
	        if (!file.exists() || !file.isFile()) {
	            return "文件路径错误";
	        }
	        /**
	         * 第一部分
	         */
	        url = url+"&type="+type;
	        URL urlObj = new URL(url);
	        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

	        /**
	         * 设置关键值
	         */
	        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
	        con.setDoInput(true);
	        con.setDoOutput(true);
	        con.setUseCaches(false); // post方式不能使用缓存
	        // 设置请求头信息
	        con.setRequestProperty("Connection", "Keep-Alive");
	        con.setRequestProperty("Charset", "UTF-8");
	
	        // 设置边界
	        String BOUNDARY = "----------" + System.currentTimeMillis();
	        con.setRequestProperty("content-type", "multipart/form-data; boundary=" + BOUNDARY);
	        //con.setRequestProperty("Content-Type", "multipart/mixed; boundary=" + BOUNDARY);
	        //con.setRequestProperty("content-type", "text/html");
	        // 请求正文信息
	
	        // 第一部分：
	        StringBuilder sb = new StringBuilder();
	        sb.append("--"); // ////////必须多两道线
	        sb.append(BOUNDARY);
	        sb.append("\r\n");
	        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
	                + file.getName() + "\"\r\n");
	        sb.append("Content-Type:application/octet-stream\r\n\r\n");
	        byte[] head = sb.toString().getBytes("utf-8");
	        // 获得输出流
	        OutputStream out = new DataOutputStream(con.getOutputStream());
	        out.write(head);
	        
	        // 文件正文部分
	        DataInputStream in = new DataInputStream(new FileInputStream(file));
	        int bytes = 0;
	        byte[] bufferOut = new byte[1024];
	        while ((bytes = in.read(bufferOut)) != -1) {
	            out.write(bufferOut, 0, bytes);
	        }
	        in.close();
	        // 结尾部分
	        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
	        out.write(foot);
	        out.flush();
	        out.close();
	        /**
	         * 读取服务器响应，必须读取,否则提交不成功
	         */
	       // con.getResponseCode();

	        /**
	         * 下面的方式读取也是可以的
	         */

	         try {

	         // 定义BufferedReader输入流来读取URL的响应
	        	 StringBuffer buffer = new StringBuffer();
		         BufferedReader reader = new BufferedReader(new InputStreamReader(
		         con.getInputStream(),"UTF-8"));
		         String line = null;
		         while ((line = reader.readLine()) != null) {
		            //System.out.println(line);
		            buffer.append(line);
		         }
		         if(result==null){
					result = buffer.toString();
				}
		         System.out.println(buffer.toString());
		         return buffer.toString();
	         } catch (Exception e) {
	        	 System.out.println("发送POST请求出现异常！" + e);
	        	 e.printStackTrace();
	         }
	         return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		String filePath = "d:/mv1.jpg";
		String token="Jdr_B5dQzbWlmmTAlMxbpOZiUfe100laWKeNjRgqfYAJ2GkgCdbQCQO4gAA6e0qd7uYM8fhhzx9ehQBCHlQvKQ";
		String result = null;
		FileUpload fileUpload = new FileUpload();
		result = fileUpload.uploadFile(token, filePath, "image");
		System.out.println(result);
	}
}

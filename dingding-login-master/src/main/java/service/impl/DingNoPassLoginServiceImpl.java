package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import service.DingNoPassLoginService;

import com.alibaba.fastjson.JSONObject;

import dingding.DingTalkConstant;
import dingding.demo.HttpHelper;
import dingding.demo.OApiException;
@Service
public class DingNoPassLoginServiceImpl implements DingNoPassLoginService{

	public String getDingNoPassLogin(HttpServletRequest request) {
		/*
		*以http://localhost/test.do?a=b&c=d为例
		*request.getRequestURL的结果是http://localhost/test.do
		*request.getQueryString的返回值是a=b&c=d
		*/
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();
			
		String url=null;
		if(queryString!=null){
			url=urlString+queryString;
		}
		else{
			url=urlString;
		}
			
		String corpId=DingTalkConstant.XY_TINY_DING_CORP_ID;        //一些比较重要的不变得参数本人存储在properties文件中
		String corpSecret=DingTalkConstant.XY_TINY_DING_SSO_SECRET;
		String nonceStr="1245458474";
		String agentId =DingTalkConstant.XY_AGENT_ID;     //agentid参数
		
		long timeStamp = System.currentTimeMillis() / 1000;     //时间戳参数
		String signedUrl = url;                                 //请求链接的参数，这个链接主要用来生成signatrue，并不需要传到前端
		String accessToken = null;                              //token参数
		String ticket = null;                                   //ticket参数
		String signature = null;                                //签名参数
				
		try {
				
			accessToken=getAccess_Token(corpId,corpSecret);
			ticket=getTicket(accessToken);
			signature=getSign(ticket,nonceStr,timeStamp,signedUrl);
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
		+ timeStamp + "',corpId:'" + corpId + "',agentId:'" + agentId+ "'}");	
		return "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
		+ timeStamp + "',corpId:'" + corpId + "',agentId:'" + agentId+ "'}";

		
		
		
		
		
		//return null;
	}
	
	public static String getAccess_Token(String corpid,String corpsecret) throws OApiException{	
		String url="https://oapi.dingtalk.com/gettoken?"+"corpid="+corpid+"&corpsecret="+corpsecret;
			
		JSONObject res=HttpHelper.httpGet(url);                      //将httpGet方法封装在HttpHelper类中
		String access_token="";
		if(res!=null){
			access_token=res.getString("access_token");
		}
		else{
			new Exception("Cannot resolve field access_token from oapi resonpse");
		}
		return access_token;
	}
	
	
	/*
	* 向网页请求ticket值，用Get方式请求网页
	* param:
	* 	access_token:上面得到的access_token值
	* 
	* return:
	* 	返回值是ticket
	*/
	public static String getTicket(String access_token) throws OApiException{
		String url="https://oapi.dingtalk.com/get_jsapi_ticket?"+
				"access_token="+access_token;
			
		JSONObject res=HttpHelper.httpGet(url);                          
		String ticket="";
		if(res!=null){
			ticket=res.getString("ticket");
		}
		else{
			new Exception("Cannot resolve field ticket from oapi resonpse");
		}
		return ticket;
	}
	
	
	
	/*
	* 生成签名的函数
	* params:
	* 	ticket:签名数据
	* 	nonceStr:签名用的随机字符串，从properties文件中读取
	* 	timeStamp:生成签名用的时间戳
	* 	url:当前请求的URL地址
	*/
	public static String getSign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "×tamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");    //安全hash算法
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));                       //根据参数产生hash值
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}
	}
	 
	//将bytes类型的数据转化为16进制类型
	private static String bytesToHex(byte[] hash) {                    //将字符串转化为16进制的数据
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

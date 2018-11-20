package dingding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.JsapiTicket;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageBody.TextBody;
import com.dingtalk.open.client.api.service.corp.CorpConnectionService;
import com.dingtalk.open.client.api.service.corp.JsapiService;
import com.dingtalk.open.client.api.service.corp.MessageService;

/***
 * 钉钉的一些认证工具，这个类在 jsp 页面有引用不要修改
 * 
 * @author bill.xu
 *
 */
public class DingTalkUtil {

	private  static final Logger LOGGER = LoggerFactory.getLogger(DingTalkUtil.class);
	
	
	/***
	 * 得到 token , TODO  后期可以根据  业务需求 保存这些 token在缓存中
	 * @return
	 * @throws Exception
	 */
	public static String getToken() throws Exception{
		
        CorpConnectionService corpConnectionService = ServiceFactory.getInstance().getOpenService(CorpConnectionService.class);
       
        return corpConnectionService.getCorpToken(DingTalkConstant.DING_TALK_CORP_ID, DingTalkConstant.DING_TALK_CORP_SECRET);
	}
	
	
	/***
	 * 得到 js 的 ticket  TODO  后期可以根据  业务需求 保存这些 ticket 在缓存中
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static String getJsapiTicket(String accessToken) throws Exception{
		
		JsapiService jsapiService = ServiceFactory.getInstance().getOpenService(JsapiService.class);

		JsapiTicket jsapiTicket = jsapiService.getJsapiTicket(accessToken, "jsapi");
	    
		return jsapiTicket.getTicket();
	}
	
	/****
	 *     签名
	 *     
	 * @param ticket    jsticket
	 * @param nonceStr   随机数
	 * @param timeStamp   时间戳
	 * @param url     当前的请求 url 
	 * @return
	 * @throws Exception
	 */
	public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("#### 钉钉进行js 调用生成 ticket 时异常", e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("#### 钉钉进行js 调用生成 ticket 时异常", e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	private static String bytesToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static String getConfig(HttpServletRequest request) {
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();

		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}
		String nonceStr = DingTalkConstant.SIGN_NONCESTR;
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		String agentid = DingTalkConstant.AGENT_ID;

		try {
			accessToken = getToken();
			ticket = getJsapiTicket(accessToken);
			signature = sign(ticket, nonceStr, timeStamp, signedUrl);
			
		} catch (Exception  e) {
			e.printStackTrace();
		}
		//TODO   demo 中没有 token ，为了页面操作方便加入 token，后期 该token不可传入页面
		String configValue = "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
				+ timeStamp + "',corpId:'" + DingTalkConstant.DING_TALK_CORP_ID + "',agentid:'" + agentid+  "',accessToken:'"+accessToken+"'}";
		
		LOGGER.info("#######  钉钉进行签名的 configValue is: {}",configValue);
		//System.out.println("####### \t"+configValue);
		return configValue;
	}
	
	
	public Object sendMessage(){
		
		try {
			
		ServiceFactory factory = ServiceFactory.getInstance();
		// 获取token 
		CorpConnectionService  corpConnectionService = factory.getOpenService(CorpConnectionService.class);
		 
		String token = corpConnectionService.getCorpToken(DingTalkConstant.DING_TALK_CORP_ID, DingTalkConstant.DING_TALK_CORP_SECRET);
		
		MessageService messageService = factory.getOpenService(MessageService.class);
		
		TextBody n = new MessageBody.TextBody();
		
		n.setContent("这是一个textbody");
		
		messageService.sendToCorpConversation(token, "touser", "topart", "agentid", "msgtype", n );
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
}

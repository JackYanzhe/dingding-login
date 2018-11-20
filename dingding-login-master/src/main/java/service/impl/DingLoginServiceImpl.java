package service.impl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service.DingLoginService;
import com.alibaba.fastjson.JSONObject;
import dingding.DingTalkConstant;
import dingding.demo.HttpHelper;
import dingding.demo.OApiException;

@Service
public class DingLoginServiceImpl implements DingLoginService{

	private static final Logger logger = LoggerFactory.getLogger(DingLoginServiceImpl.class);
	/**
	 * 通过扫描二维码返回的code值，得到用户相关信息
	 */
	public JSONObject getDingLogin(String code) {
		JSONObject userData = null;
		try{
            //获取accesstoken
            String accessToken = getAccesstoken();
            //获取用户授权的持久授权码
            JSONObject json = getPersistentCode(accessToken, code);
            String openId = "";
            String persistentCode = "";
            if(null!=json){
                openId = json.getString("openid");
                persistentCode = json.getString("persistent_code");
            }
            //获取用户授权的SNS_TOKEN
            String snsToken = getSnsToken(accessToken, openId, persistentCode); 
            //获取用户unionid
            String unionId = getUnionId(snsToken); 
            //根据unionid获取用户userId
            String appAccessToken = getAppAccesstoken();
            logger.info("用户id:"+unionId+",用户token:"+appAccessToken);
            String userId = getUserId(appAccessToken, unionId);
            if (StringUtils.isEmpty(unionId)) {
				return null;
			}
            //获取用户详细数据
            userData = getUserData(appAccessToken, userId);
           
        }catch (Exception e){
        	logger.error("扫码登录时出现异常，异常信息如下："+e.getMessage());
           throw new RuntimeException(e);
        }
		 return userData;
	}
	
	/**
	 * 获取accesstoken
	 * @return
	 * @throws OApiException
	 */
	public String getAccesstoken() throws OApiException {
       String url = "https://oapi.dingtalk.com/sns/gettoken?appid="+DingTalkConstant.APP_ID+"&appsecret="+DingTalkConstant.APP_SECRET;
		JSONObject json = HttpHelper.httpGet(url);
        if(null!=json){
            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
                String accessToken = json.getString("access_token");
                return accessToken;
            }
        }
        return "";
    }
 
	/**
	 * 获取appAccesstoken信息
	 * @return
	 * @throws OApiException
	 */
    public String getAppAccesstoken() throws OApiException {
        String url = "https://oapi.dingtalk.com/gettoken?corpid="+DingTalkConstant.DING_TALK_CORP_ID+"&corpsecret="+DingTalkConstant.DING_TALK_CORP_SECRET;
        JSONObject json =  HttpHelper.httpGet(url);
        if(null!=json){
            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
                String appAccessToken = json.getString("access_token");
                return appAccessToken;
            }
        }
        return "";
    }
 
    /**
     * 获取用户授权的持久授权码
     * @param accessToken
     * @param code
     * @return
     * @throws OApiException
     */
    public JSONObject getPersistentCode(String accessToken,String code) throws OApiException {
        String url = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token=" + accessToken;
        JSONObject jsonData = new JSONObject();
        jsonData.put("tmp_auth_code", code);
        JSONObject json =  ossHttpPostUtil(url, jsonData);
        if(null!=json){
            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
                return json;
            }
        }
        return null;
    }
 
    /**
     * 获取用户授权的SNS_TOKEN
     * @param accesstoken
     * @param openid
     * @param persistent_code
     * @return
     * @throws OApiException
     */
    public String getSnsToken(String accesstoken, String openid, String persistent_code) throws OApiException {
        String url = "https://oapi.dingtalk.com/sns/get_sns_token?access_token="+accesstoken;
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonData = new JSONObject();
        jsonData.put("openid", openid);
        jsonData.put("persistent_code", persistent_code);
        JSONObject json = ossHttpPostUtil(url, jsonData);
        
        if(null!=json){
            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
                String snsToken = json.getString("sns_token");
                return snsToken;
            }
        }
        return null;
    }
 
    /**
     * 获取用户unionid
     * @param snsToken
     * @return
     * @throws OApiException
     */
    public String getUnionId(String snsToken) throws OApiException {
        String url = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token="+snsToken;
        JSONObject json = HttpHelper.httpGet(url);
        if(null!=json){
            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
                JSONObject jsonUser = json.getJSONObject("user_info");
                String unionid = jsonUser.getString("unionid");
                return unionid;
            }
        }
        return "";
    }
 
    public String getUserId(String accessToken, String unionId) throws OApiException {
    	String url = "https://oapi.dingtalk.com/user/getUseridByUnionid?unionid="+unionId+"&access_token="+accessToken;
    		 JSONObject json = HttpHelper.httpGet(url);
    		 logger.info(json+":***");
    	        if(null!=json){
    	            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
    	                String userId = json.getString("userid");
    	                return userId;
    	            }
    	        }
        return "";
    }
 
    public JSONObject getUserData(String accessToken, String userId) throws OApiException {
        String url = "https://oapi.dingtalk.com/user/get?access_token="+accessToken+"&userid="+userId;
        JSONObject json = HttpHelper.httpGet(url);
        if(null!=json){
            if (Integer.parseInt(json.get("errcode").toString()) == 0) {
                return json;
            }
        }
        return null;
    }
 
    /*private JSONObject ossHttpGetUtil(String url){
        HttpGet httpGet = new HttpGet(url);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpClient = httpClientBuilder.build();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (Exception e) {
        }
        BufferedReader bufferedReader = null;
        StringBuilder entityStringBuilder = new StringBuilder();
        //得到httpResponse的状态响应码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        JSONObject jsonObject = null;
        String access_token = "";
        if (statusCode == HttpStatus.SC_OK) {
            //得到httpResponse的实体数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                try {
                    return jsonObject = JSONObject.parseObject(EntityUtils.toString(httpEntity));
                } catch (Exception e) {
 
                }
            }
        }
        return null;
    }*/
 
    private JSONObject ossHttpPostUtil(String url, JSONObject json){
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity = null;
        httpEntity = new StringEntity(json.toString(), "UTF-8");
        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = null;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpClient = httpClientBuilder.build();
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (Exception e) {
 
        }
        StringBuilder entityStringBuilder = new StringBuilder();
        //得到httpResponse的状态响应码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            //得到httpResponse的实体数据
            HttpEntity httpEntity2 = httpResponse.getEntity();
            JSONObject jsonObject = null;
            if (httpEntity2 != null) {
                try {
                    return jsonObject = jsonObject.parseObject(EntityUtils.toString(httpEntity2));
                } catch (Exception e) {
 
                }
            }
        }
        return null;
    }


}

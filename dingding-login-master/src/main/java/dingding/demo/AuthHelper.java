package dingding.demo;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.JsapiTicket;
import com.dingtalk.open.client.api.service.corp.CorpConnectionService;
import com.dingtalk.open.client.api.service.corp.JsapiService;

import dingding.DingTalkConstant;
import dingding.Env;

/**
 * AccessToken和jsticket的获取封装
 */
public class AuthHelper {

    // 调整到1小时50分钟
    public static final long cacheTime = 1000 * 60 * 55 * 2;

    /*
     * 在此方法中，为了避免频繁获取access_token，
     * 在距离上一次获取access_token时间在两个小时之内的情况，
     * 将直接从持久化存储中读取access_token
     *
     * 因为access_token和jsapi_ticket的过期时间都是7200秒
     * 所以在获取access_token的同时也去获取了jsapi_ticket
     * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
     * 具体信息请查看开发者文档--权限验证配置
     */
    public static String getAccessToken() throws OApiException {
        long curTime = System.currentTimeMillis();
        JSONObject accessTokenValue = (JSONObject) FileUtils.getValue("accesstoken", DingTalkConstant.DING_TALK_CORP_ID);
        String accToken = "";
        JSONObject jsontemp = new JSONObject();
        if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CorpConnectionService corpConnectionService = serviceFactory.getOpenService(CorpConnectionService.class);
                accToken = corpConnectionService.getCorpToken(DingTalkConstant.DING_TALK_CORP_ID, DingTalkConstant.DING_TALK_CORP_SECRET);
                // save accessToken
                JSONObject jsonAccess = new JSONObject();
                jsontemp.clear();
                jsontemp.put("access_token", accToken);
                jsontemp.put("begin_time", curTime);
                jsonAccess.put(DingTalkConstant.DING_TALK_CORP_ID, jsontemp);
                //真实项目中最好保存到数据库中
                FileUtils.write2File(jsonAccess, "accesstoken");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return accessTokenValue.getString("access_token");
        }

        return accToken;
    }

    /**
     * 获取JSTicket, 用于js的签名计算
     * 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
     */
    public static String getJsapiTicket(String accessToken) throws OApiException {
        JSONObject jsTicketValue = (JSONObject) FileUtils.getValue("jsticket", Env.CORP_ID);
        long curTime = System.currentTimeMillis();
        String jsTicket = "";

        if (jsTicketValue == null || curTime -
                jsTicketValue.getLong("begin_time") >= cacheTime) {
            ServiceFactory serviceFactory;
            try {
                serviceFactory = ServiceFactory.getInstance();
                JsapiService jsapiService = serviceFactory.getOpenService(JsapiService.class);

                JsapiTicket JsapiTicket = jsapiService.getJsapiTicket(accessToken, "jsapi");
                jsTicket = JsapiTicket.getTicket();

                JSONObject jsonTicket = new JSONObject();
                JSONObject jsontemp = new JSONObject();
                jsontemp.clear();
                jsontemp.put("ticket", jsTicket);
                jsontemp.put("begin_time", curTime);
                jsonTicket.put(Env.CORP_ID, jsontemp);
                FileUtils.write2File(jsonTicket, "jsticket");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsTicket;
        } else {
            return jsTicketValue.getString("ticket");
        }
    }


    public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
        try {
            return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
        } catch (Exception ex) {
            throw new OApiException(0, ex.getMessage());
        }
    }


    /**
     * 计算当前请求的jsapi的签名数据<br/>
     * <p>
     * 如果签名数据是通过ajax异步请求的话，签名计算中的url必须是给用户展示页面的url
     *
     * @param request
     * @return
     */
    public static String getConfig(HttpServletRequest request) {
        String urlString = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        System.out.println(urlString+","+queryString);
        
        String queryStringEncode = null;
        String url;
        if (queryString != null) {
            queryStringEncode = URLDecoder.decode(queryString);
            url = urlString + "?" + queryStringEncode;
        } else {
            url = urlString;
        }

        String nonceStr = "abcdefg";
        long timeStamp = System.currentTimeMillis() / 1000;
        String signedUrl = url;
        String accessToken = null;
        String ticket = null;
        String signature = null;
        String agentid = null;

        try {
        	System.out.println("-------------------");
            accessToken = AuthHelper.getAccessToken();
            System.out.println(accessToken+"----");
            ticket = AuthHelper.getJsapiTicket(ticket);
            System.out.println(accessToken+"----");
            signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
            System.out.println(signature+"----");
            agentid = "184204455"; //后期改

        } catch (OApiException e) {
            e.printStackTrace();
        }
        String configValue = "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
                + timeStamp + "',corpId:'" + Env.CORP_ID + "',agentid:'" + agentid + "'}";
        System.out.println(configValue);
        return configValue;
    }


    public static String getSsoToken() throws OApiException {
        String url = "https://oapi.dingtalk.com/sso/gettoken?corpid=" + Env.CORP_ID + "&corpsecret=" + Env.SSO_Secret;
        JSONObject response = HttpHelper.httpGet(url);
        String ssoToken;
        if (response.containsKey("access_token")) {
            ssoToken = response.getString("access_token");
        } else {
            throw new OApiException("Sso_token");
        }
        return ssoToken;

    }

}

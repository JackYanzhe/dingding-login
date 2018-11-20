package dingding;

/***
 * 这里是钉钉的一些账号配置信息，都可以在 开发平台上进行查找
 * 
 *   http://open-dev.dingtalk.com/#/corpAuthInfo?_k=hgpm9q
 *   
 *   
 * 
 * @author bill.xu
 *
 */
public class DingTalkConstant {

	//dadadaboluo（个人账户）							
//	public  static final String DING_TALK_CORP_ID = "ding512c651b0ba2942f35c2f4657eb6378f";
//	
//	public  static final String DING_TALK_CORP_SECRET = "Sx6H5v-zwOIrkFLg0zOnz_Ja4xJFpo5SUunbV0cRnwEA0DQLumCqPxxjVHSljzEp";
//	public  static final String AGENT_ID = "112598673";
//	public  static final String APP_ID = "dingoa34ktugcilijrtndy";
//	public  static final String APP_SECRET = "ParRhlOPI80HoLVIAA7jqj_u8p_C_xH5immhuYwaDNpkOBlHso6RA2SN3jj0CMTX";
	
	
	public  static final String DING_TALK_CORP_ID = "ding507bc0781a6eeea135c2f4657eb6378f";
	public  static final String DING_TALK_CORP_SECRET = "EuTx0CbMxdHUiedvd1gK6l4TZVHupeTKEjiZRWBhf1ffU8M63qocrK8qHygLCt8x";
	public  static final String AGENT_ID = "184204455";
	public  static final String APP_ID = "dingoawgtaauo7rvbyzaah";
	public  static final String APP_SECRET = "m2JsFeeoCV_rp3aFzLjYVNZKH_eJEyketHz2fPvMtCSxWOscmZFswa4pSTiN28uJ";
	
	//公司四衡开发（自主注册）
//	public  static final String DING_TALK_CORP_ID = "ding6fb686dd4c25be0c35c2f4657eb6378f";
//	public  static final String DING_TALK_CORP_SECRET = "ll_AQxsJEfd6GYMusDRlMMEAK88nJCXVhPifFEURE2ohHd1xTzZBtwm-NSwPSiht";
//	public  static final String AGENT_ID = "112636030";
//	public  static final String APP_ID = "dingoaxth7oxokc96vshys";
//	public  static final String APP_SECRET = "soqHGZyaklZVOxdIQefpguKyfYBc9qoJpwEmS3pEqZQfogVNsQQnnyhCljn6fX7R";
	
	//公司四衡开发(公司账号对应)---   线上应该要用这个 ，为了测试方便，加了上面两个的 
//	public  static final String DING_TALK_CORP_SECRET = "MBg-cyrp5ShudJY5ip0_uZ-20a_ozRSS25qTYWDEasxac-hQ-NhJ04tJUqOdL6cc";
//	public  static final String DING_TALK_CORP_ID = "ding97bd04a9ae353721";
//	public  static final String AGENT_ID = "";
//	public  static final String APP_ID = "";
//	public  static final String APP_SECRET = "";
	
	
	
	
	
	/**进行 回调接口注册 的加密参数  企业账户可以自己设置*/
	public  static final String TOKEN="0123456789";
	//public  static final String TOKEN="123456";
	
	/**进行 回调接口注册 的加密参数  企业账户可以自己设置  为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成*/
	public  static final String ENCODING_AES_KEY = "XjVQoohHEmNsapiKpzBleMrBUaHQjHMqOwNK4ShiotL";
	//public  static final String ENCODING_AES_KEY = "4g5j64qlyl3zvetqxz5jiocdr586fn2zvjpa8zls3ij";
	
	
	/**进行 回调接口注册 的加密参数 钉钉注册接口的回调地址 */
	//public  static final String CALL_BACK_URL = "http://112.64.178.162:8080/siheng_core/a/ding/dingcallback";
	//public  static final String CALL_BACK_URL = "http://192.168.0.232:8081/siheng_core/a/ding/dingcallback";
	//public  static final String CALL_BACK_URL = "http://112.64.178.163:8099/financial-department/a?login";
	public  static final String CALL_BACK_URL = "http://192.168.0.100:8080/dingding-login-master/dingUser/callback";
	
	
	
	//星云科技（测试使用）
    public  static final String XYDING_TALK_CORP_ID = "ding507bc0781a6eeea135c2f4657eb6378f";
	
	public  static final String XYDING_TALK_CORP_SECRET = "EuTx0CbMxdHUiedvd1gK6l4TZVHupeTKEjiZRWBhf1ffU8M63qocrK8qHygLCt8x";
	
	//星云科技微应用相关秘钥（测试使用）
	 public  static final String XY_TINY_DING_CORP_ID = "ding507bc0781a6eeea135c2f4657eb6378f";
		
	 public  static final String XY_TINY_DING_SSO_SECRET = "NXpTmGTOs3qFB50tUaMV-XYBsB_hYmwt1ZjAPOwX0ork-8fWoBlHMoGmI6_x6Iwx";
	 public  static final String XY_AGENT_ID = "184338101";
	
	
	
	
	
	
	
	
	
	
	//47.88.18.109（代理地址）    192.168.0.232（局域网地址）   101.81.182.188（外网映射）  ----112.64.178.162
	
	
	public  static final String PC_TYPE = "2";
	
	public  static final String APP_TYPE = "1";
	
	
	
	/** 进行签名认证的随机数 */
	public  static final String SIGN_NONCESTR = "123456";
	
	
	
	/** pc 企业用户扫码登录回调的地址*/
   //	public  static final String RETURN_URL	 = "http://112.64.178.162:8080/siheng_core/a/ding/callback";
		public  static final String RETURN_URL	 = "http://192.168.0.232:8081/siheng_core/a/ding/callback";

	/**pc 企业用户 生成 扫码登录二维码的地址 后面 还要加上回调的地址*/
	public  static final String DING_LOGIN_URL	 = "https://oapi.dingtalk.com/connect/qrconnect?appid="+APP_ID+"&response_type=code&state=STATE&scope=snsapi_login&redirect_uri="+RETURN_URL;
	
	
    /** 是用钉钉时，构造一个默认的 password 用于 shiro 的校验 */	
	public  static final String DEFAULT_PASSWORD	 = "123456789000000000000000000000000000000";
	
	
	/**request 请求过来的 url key ，存在 redis 中 */
	public  static final String REQUEST_LAST_URL = "REQUEST_LAST_URL";
	
	
	
	
	
}

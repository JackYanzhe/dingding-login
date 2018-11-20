package dingding.main;

import com.alibaba.fastjson.JSONObject;

import service.impl.DingLoginServiceImpl;

public class DingUserLogin {

	public static void main(String[] args) {
		DingLoginServiceImpl dingLoginServiceImpl = new DingLoginServiceImpl();
		JSONObject userData = dingLoginServiceImpl.getDingLogin("9e285aae1b3430c09d9999544af44ca7");
		System.out.println(userData.toJSONString());
		
		
		
		
		
	}
}

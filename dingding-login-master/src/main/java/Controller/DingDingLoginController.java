package Controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.DingLoginService;
import service.DingNoPassLoginService;

import com.alibaba.fastjson.JSONObject;

import dingding.DingTalkConstant;
import entity.User;

@RequestMapping("/dingUser")
@Controller
public class DingDingLoginController {
	
	private Logger logger = LoggerFactory.getLogger(DingDingLoginController.class);
	@Autowired
	private DingLoginService dingLoginService;
	
	@Autowired
	private DingNoPassLoginService dingNoPassLoginService;
	
	
	/**
	 * 扫码登录
	 * @param request
	 * @param response
	 * @param model
	 */
	/*@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("成功进入请求钉钉页1，生成二维码登录页面");
		String time = System.currentTimeMillis() + "";
        System.out.println(time);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("https://oapi.dingtalk.com/connect/qrconnect?appid=dingoawgtaauo7rvbyzaah&")
				.append("response_type=code&scope=snsapi_login&state=")
				.append(time)
				.append("&redirect_uri=" + DingTalkConstant.CALL_BACK_URL);
		try {
			response.sendRedirect(stringBuilder.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}*/
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("成功进入请求钉钉页1，生成二维码登录页面");
		String time = System.currentTimeMillis() + "";
        System.out.println(time);
		StringBuilder stringBuilder = new StringBuilder();
		String result="";
		stringBuilder
				.append("https://oapi.dingtalk.com/connect/qrconnect?appid=dingoawgtaauo7rvbyzaah&")
				.append("response_type=code&scope=snsapi_login&state=")
				.append(time)
				.append("&redirect_uri=" + DingTalkConstant.CALL_BACK_URL);
		try {
			result = stringBuilder.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;

	}
	
	/**
	 * 第三方授权免密登录
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/login3")
	public void login3(HttpServletRequest request, HttpServletResponse response,Model model) {
		logger.info("成功进入请求钉钉页1，生成二维码登录页面");
		String time = System.currentTimeMillis() + "";
		
		
        System.out.println(time);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=dingoawgtaauo7rvbyzaah&")
				.append("response_type=code&scope=snsapi_login&state=")
				.append(time)
				.append("&redirect_uri=" + DingTalkConstant.CALL_BACK_URL);
		try {
			response.sendRedirect(stringBuilder.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	
	
	
	@RequestMapping(value="/callback", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response, Model model, String code, String state) {
		System.out.println("成功进入请求钉钉用户信息页面,code=" + code+",登录时间戳："+state);
		
		User user = new User();
		JSONObject userJson = new JSONObject();
		String result = "";
		try {
			userJson = dingLoginService.getDingLogin(code);
			if (null == userJson) {
				result = "登录失败，请重试，或您不是本公司员工不能进行登录操作";
				System.out.println(result);
				return result;
			}
			System.out.println(userJson.toJSONString());
			user = JSONObject.toJavaObject(userJson, User.class);
			System.out.println(user.name + "," + user.mobile);
			result = "欢迎你：" + user.name + ",您的电话为：" + user.mobile + ",user-->json："+ userJson.toJSONString();
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;

	}
	
	
	
/*	@RequestMapping(value="/callback", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response, Model model, String code, String state) {
		System.out.println("成功进入请求钉钉用户信息页面,code=" + code+",登录时间戳："+state);
		 User user = new User();
		 JSONObject userJson = new JSONObject();
		 String result = "";
		if (!StringUtils.isEmpty(state)) {
			try {
					String time = stateData.get(state);
					//匹配校验，加同步
					if (null!=time&&time.equals(state)) {
						System.out.println("校验成功！！！！！");
						stateData.remove(state);
						
							userJson = dingLoginService.getDingLogin(code);
							if (null == userJson) {
								result = "登录失败，请重试，或您不是本公司员工不能进行登录操作";
								System.out.println(result);
								return result;
							}
							System.out.println(userJson.toJSONString());
							user = JSONObject.toJavaObject(userJson, User.class);
							System.out.println(user.name + "," + user.mobile);
							result = "欢迎你：" + user.name + ",您的电话为：" + user.mobile + ",user-->json："+ userJson.toJSONString();
							System.out.println(result);
							
						
						//return出去
						
					}else {
						result = "登录校验失败，请重新登录";
						System.out.println(result);
					}
				} catch (Exception e) {
					result = "出现异常信息，登录失败";
					System.out.println(e.getMessage());
					logger.error(e.getMessage());
				}
					
				}else {
					result = "登录校验失败,验证状态为空，请重新登录";
					System.out.println(result);
					
				}
		
		
		return result;

	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@RequestMapping("/login2")
	public void login2(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		System.out.println("成功进入请求钉钉页，生成二维码登录页面");
		String time = System.currentTimeMillis() + "";

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("https://oapi.dingtalk.com/connect/qrconnect?appid=dingoa34ktugcilijrtndy&")
				.append("response_type=code&scope=snsapi_login&state=")
				.append(time)
				.append("&redirect_uri=" + "http://192.168.0.28:8081/financial-department/a/login");
		try {
			response.sendRedirect(stringBuilder.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	

   public String loginGitTest(HttpServletRequest request, HttpServletResponse response,Model model) {
		logger.info("成功进入请求钉钉页1，生成二维码登录页面");
		//此时为第5次修改
		String time = System.currentTimeMillis() + "";
        System.out.println(time);
		//新加修改数据信息
		StringBuilder stringBuilder = new StringBuilder();
		String result="";
		
		try {
			result = stringBuilder.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;

	}
	
}

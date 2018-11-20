package service;

import javax.servlet.http.HttpServletRequest;

public interface DingNoPassLoginService {

	/**
	 * 免密登录
	 * @return
	 */
	public String getDingNoPassLogin(HttpServletRequest request);
}

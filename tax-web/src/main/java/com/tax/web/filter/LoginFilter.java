package com.tax.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tax.core.constant.Constant;
import com.tax.pojo.nsfw.User;

/**
 * 登陆过滤器
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月29日 下午4:40:58
 * @version  v1.0
 */

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	/**
	 * 登陆过滤
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		//1.用户是否登陆，未登陆跳转到登陆页面
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.USER);
		String uri = request.getRequestURI();
		// 登陆模块的不拦截，否则登陆不了
		if(uri.length() > 0 && !uri.contains("sys/login")) {
			if (null != user) {
				chain.doFilter(request, response);
			} else {
				response.sendRedirect(request.getContextPath()+"/sys/login_toLoginUI.action");
			}
		} else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
	}

}

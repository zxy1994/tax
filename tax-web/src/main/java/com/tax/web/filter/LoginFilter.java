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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tax.core.constant.Constant;
import com.tax.pojo.nsfw.User;
import com.tax.web.permission.PermissionCheck;

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
		uri = uri.replaceAll(request.getContextPath(), "");
		// 登陆模块的不拦截，否则登陆不了
		if(uri.length() > 0 && !uri.contains("sys/login")) {
			if (null != user) {
				// 判断权限
				String moudle = this.subUri(uri);
				if("sys".equals(moudle)){
					chain.doFilter(request, response);
					return;
				}
				// 通过Spring工具类获取到spring容器
				WebApplicationContext applicationContext = WebApplicationContextUtils
						.getWebApplicationContext(session.getServletContext());
				PermissionCheck permissionCheck = (PermissionCheck) applicationContext.getBean("permissionCheck");
				// 有权限就放行，没权限就跳到没权限页面
				if(permissionCheck.isAccesssible(user, moudle)) {
					chain.doFilter(request, response);
				} else {
					response.sendRedirect(request.getContextPath()+"/sys/login_toNoPermissionUI.action");
				}
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
	
	
	/**
	 * 对uri截取的方法
	 * @param uri
	 * @return
	 */
	private String subUri(String uri) {
		int first = uri.indexOf("/");
		int second = uri.indexOf("/",first + 1);
		// first为-1时返回空串
		return (first == -1) ? "" : uri.substring(first + 1, (second == -1) ? uri.length() : second);
	}
}

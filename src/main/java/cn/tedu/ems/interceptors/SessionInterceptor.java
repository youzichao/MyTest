package cn.tedu.ems.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession();
		Object obj=session.getAttribute("user");
		if(obj==null){
			//若从session对象上找不到对应的数据
			//说明没有登录，重定向到登录页面
			response.sendRedirect("toLogin.do");
			return false;
		}
		//如果已经登录过，则继续向后调用
		return true;
	}

	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}

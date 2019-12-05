package com.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CrossInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("拦截到一个敏感请求");

		String method = request.getMethod().toLowerCase();
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8888");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Expose-Headers", "TK");
		if (method.equals("options")){
			return false;
		}

		HttpSession session=request.getSession();
		response.setHeader("TK",session.getId());

//		Object obj=session.getAttribute("loginAdmin");
//		if(obj==null){
//			String path=request.getContextPath();
//			request.setAttribute("error", "未登陆，请先登陆");
//			return false;
//		}
		System.out.println(request.getRequestURL());
		return true;
	}
}

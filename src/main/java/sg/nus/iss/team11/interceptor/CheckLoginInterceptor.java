package sg.nus.iss.team11.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CheckLoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws IOException {
		System.out.println("Intercepting: " + request.getRequestURI());
		HttpSession session = request.getSession();
		// Check if the user already has set attribute userLogin
		if (session.getAttribute("user") != null)
			return true;
		String[] splitURI = request.getRequestURI().split("/");
		
		
		List<String> publicEndpoints = Arrays.asList(
				"style.css", 
				"login", 
				"authenticate");
		
		if (publicEndpoints.contains(splitURI[splitURI.length - 1]))
			return true;
		
		
		// If the user has not logged in, redirect her/him to login
		response.sendRedirect("/login");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
	HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
	HttpServletResponse response, Object handler, Exception ex) {
	}
}

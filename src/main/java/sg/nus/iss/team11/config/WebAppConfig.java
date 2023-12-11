package sg.nus.iss.team11.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sg.nus.iss.team11.interceptor.CheckLoginInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer {
	@Autowired
	CheckLoginInterceptor checklogininterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(checklogininterceptor);
	}
}

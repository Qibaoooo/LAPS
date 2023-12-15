package sg.nus.iss.team11.security;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage());
    String url = request.getRequestURI();
    // handling for REST API
    if (url.contains("/api")) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized" + authException.getMessage());
        return;
    }
    
    // handling for MVC 
    if (request.getSession().getAttribute("user") != null) {
    	// user is NOT null means this is a no access error.
    	response.sendRedirect("/v1/no-access");
    } else {    	
    	// user is null means not logged in, redirect to login page.
    	response.sendRedirect("/v1/login");
    }
    
  }
}

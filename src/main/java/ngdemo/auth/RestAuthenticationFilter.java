package ngdemo.auth;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class RestAuthenticationFilter implements javax.servlet.Filter {
	static Logger log = Logger.getLogger(RestAuthenticationFilter.class);
	public static final String AUTHENTICATION_HEADER = "Authorization";

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authCredentials = httpServletRequest
					.getHeader(AUTHENTICATION_HEADER);
			log.info("Authentication. Start checking: "+authCredentials);

			// better injected
			//AuthenticationService authenticationService = new AuthenticationService();
			AuthenticationServiceCustom authenticationService = new AuthenticationServiceCustom();
			boolean authenticationStatus = false;
			try{
				authenticationStatus = authenticationService
					.authenticate(authCredentials);
			}
			catch(Exception ex) {
				log.error("Authentication. Fail to check: "+authCredentials);
			}

			if (authenticationStatus) {
				filter.doFilter(request, response);
				log.info("Authentication. Success for: "+authCredentials);
			} else {
				log.info("Authentication. Failed for: "+authCredentials);
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse
							.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
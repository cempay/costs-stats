package ngdemo.auth;
import java.io.IOException;
//import java.util.Base64;
import java.util.StringTokenizer;

import sun.misc.BASE64Decoder;
import net.viralpatel.common.QueryResponse;

import org.apache.log4j.Logger;

public class AuthenticationServiceCustom {
	static Logger log = Logger.getLogger(AuthenticationServiceCustom.class);
	
	public boolean authenticate(String authCredentials) {

		if (null == authCredentials)
			return false;
		String[] authInfo = LoginUtils.decodeAuth(authCredentials);
		final String username = authInfo[0];
		final String password = authInfo[1];

		QueryResponse resp = new QueryResponse();
		boolean authenticationStatus = (username == null || password == null) ? false :
				LoginUtils.checkUserValid(username, password, resp);
		if (!authenticationStatus)
			log.info("Authentication. Failed check for login: "+username+". Reason: "+resp.getMessage());
		return authenticationStatus;
	}
}
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
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"
				+ " ", "");
		String usernameAndPassword = null;
		try {
//			byte[] decodedBytes = Base64.getDecoder().decode(
//					encodedUserPassword);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			log.error("Authentication. Decoding from string '"+ encodedUserPassword +"' failed", e);
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		QueryResponse resp = new QueryResponse();
		boolean authenticationStatus = LoginUtils.checkUserValid(username, password, resp);
		if (!authenticationStatus)
			log.info("Authentication. Failed check for login: "+username+". Reason: "+resp.getMessage());
		return authenticationStatus;
	}
}
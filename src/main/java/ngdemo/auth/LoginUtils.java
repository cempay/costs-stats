package ngdemo.auth;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.hibernate.HibernateUtil;
import net.viralpatel.hibernate.User;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import sun.misc.BASE64Decoder;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

public class LoginUtils {
	static Logger log = Logger.getLogger(LoginUtils.class); 
	public static final Pattern loginPattern = Pattern.compile("[a-zA-Z\\d]+");
	public static final Pattern pwdPattern = Pattern.compile("[a-zA-Z\\d]+");

	public static String getLogin(HttpHeaders headers){
		MultivaluedMap<String, String> headerParams = headers.getRequestHeaders();
		String[] authInfo = LoginUtils.decodeAuth(headerParams.get("authorization").get(0));
		return authInfo[0];
	}

	public static String[] decodeAuth(String authCredentials) {
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"
				+ " ", "");
		String usernameAndPassword = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			log.error("Authentication. Decoding from string '"+ encodedUserPassword +"' failed", e);
			return new String[] {null, null};
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		return new String[] {username, password};
	}
	
	public static Boolean checkUserValid(String login, String pwd, QueryResponse resp) {
		Boolean valid = false;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {			
			Matcher loginMatcher = loginPattern.matcher(login);
			if (!loginMatcher.matches()) {
				resp.setCode(ResponseCode.OK);
				return valid;
			}			
			Matcher pwdMatcher = pwdPattern.matcher(pwd);
			if (!pwdMatcher.matches()) {
				resp.setCode(ResponseCode.OK);
				return valid;
			}
			List<User> users = session.createCriteria(User.class)
				.add(Restrictions.eq("login", login))
				.list();
			if (!users.isEmpty() && users.size() == 1 && users.get(0).getPassword().equals(pwd))
				valid = true;
			resp.setCode(ResponseCode.OK);
		}
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
			log.error("Database. Authentication. Failed user check", ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "Ошибка проверки логина/пароля.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return valid;
	}
}

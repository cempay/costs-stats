package ngdemo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginRestService  {
	protected final static Map<String,String> locres = new HashMap<String,String>();
	
	//static block
	{
		locres.put("Title", "Вход в систему");
    	locres.put("Name", "Введите логин");
    	locres.put("Password", "Введите пароль");
    	locres.put("LoginButton", "Войти");		
	}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getLoginInJSON(){
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	List<String> errors = new ArrayList<String>();
    	result.put("errors", errors);
    	result.put("locres", locres);
    	
    	return result;
    }
}

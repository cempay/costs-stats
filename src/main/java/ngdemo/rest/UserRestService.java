package ngdemo.rest;

import java.util.ArrayList;
import java.util.List;

import ngdemo.domain.CategoryRest;
import ngdemo.domain.User;
import ngdemo.service.UserService;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


//@Path("/users")
//public class UserRestService {
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public User getDefaultUserInJSON() {
//        UserService userService = new UserService();
//        return userService.getDefaultUser();
//    }
//}

@Path("/users")
public class UserRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getDefaultUserInJSON(/*@Context HttpServletRequest req*/) {
    	//HttpSession session = req.getSession(true);
        UserService userService = new UserService();
        List<User> users = new ArrayList<User>();
        users.add(userService.createUser("Ivan", "Petrov"));
        users.add(userService.createUser("Nadezhda", "Gugo"));
//        List<CategoryREST> categs = userService.getCategories();
//        for(String s: sts){
//        	users.add(userService.createUser(s, s));
//        }
        return users;
    }
}




package ngdemo.service;

import ngdemo.domain.User;

public class UserService {

    public User getDefaultUser() {
        User user = new User();
        user.setFirstName("JonFromREST");
        user.setLastName("DoeFromREST");
        return user;
    }
    
    public User createUser(String firstname, String lastname){
        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        return user;
    }
    
//    public static void main(String[] args){
//    	System.out.println("Hello: "+ Arrays.toString(new UserService().getCategoriesNames()));
//    }
}

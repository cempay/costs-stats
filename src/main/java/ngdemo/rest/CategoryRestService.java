package ngdemo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.hibernate.Category;
import ngdemo.auth.LoginUtils;
import ngdemo.domain.CategoryRest;

@Path("/user/categories")
public class CategoryRestService {	
	protected final static Map<String,String> locres = new HashMap<String,String>();
	
	{
		locres.put("Head", "Список категорий:");
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";CHARSET=UTF-8")
    public Map<String, Object> getCategoriesInJSON(@Context HttpHeaders hh){
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	List<String> errors = new ArrayList<String>();
    	result.put("errors", errors);
    	
    	List<CategoryRest> categRestList = new ArrayList<CategoryRest>();
    	List<Category> categs = CategoryService.getCategories(LoginUtils.getLogin(hh));
    	for(Category categ: categs){
    		categRestList.add(new CategoryRest(categ.getId(), categ.getName()));
    	}
    	result.put("categories", categRestList);
    	result.put("locres", locres);
    	
    	return result;
    }
}

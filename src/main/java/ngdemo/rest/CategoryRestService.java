package ngdemo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.hibernate.Category;
import ngdemo.domain.CategoryRest;

@Path("/user/categories")
public class CategoryRestService {	
	protected final static Map<String,String> locres = new HashMap<String,String>();
	
	{
		locres.put("Head", "Список категорий:");
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getCategoriesInJSON(){
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	List<String> errors = new ArrayList<String>();
    	result.put("errors", errors);
    	
    	List<CategoryRest> categRestList = new ArrayList<CategoryRest>();
    	List<Category> categs = CategoryService.getCategories();
    	for(Category categ: categs){
    		categRestList.add(new CategoryRest(categ.getId(), categ.getName()));
    	}
    	result.put("categories", categRestList);
    	result.put("locres", locres);
    	
    	return result;
    }
}

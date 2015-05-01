package ngdemo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.hibernate.Category;

@Path("/user/newcategory")
public class CategoryNewRestService {	
	protected final static Map<String,String> locres = new HashMap<String,String>();
	
	//static block
	{
		locres.put("Head", "Создание новой категории");
    	locres.put("CategoryName", "Название категории");
    	locres.put("CategoryNameEmptyAlert", "Название категории не должно быть пустым");
    	locres.put("SendButton", "Сохранить");		
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getNewCategoryFormInJSON(){
    	Map<String, Object> result = new HashMap<String, Object>();    	
    	List<String> errors = new ArrayList<String>();
    	
    	result.put("locres", locres);
    	result.put("errors", errors);
    	return result;
    }	
      @GET
	  @Path("{categoryName}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Map<String, Object> sendNewCategoryInJSON(@PathParam("categoryName") String categoryName){
	  	Map<String, Object> result = new HashMap<String, Object>();	  	
	  	List<String> errors = new ArrayList<String>();    	
	  	Map<String, String> info = new HashMap<String, String>();
    	    	
    	QueryResponse resp = new QueryResponse();
    	Category categ = CategoryService.createCategory(categoryName, resp);
    	if (resp.getCode() != ResponseCode.OK) {
    		errors.add(resp.getMessage());
    	} else {			    	
	    	info.put("success", "Категория '" + categoryName + "' успешно сохранена");
    	}

    	result.put("info", info);
    	result.put("locres", locres);
    	result.put("errors", errors);
	  	return result;
	  }
}

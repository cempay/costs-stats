package ngdemo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.User;
import ngdemo.auth.LoginUtils;

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
	public Map<String, Object> sendNewCategoryInJSON(@PathParam("categoryName") String categoryName, @Context HttpHeaders hh) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		result.put("locres", locres);
		result.put("errors", errors);

		QueryResponse resp = new QueryResponse();
		User user = LoginUtils.getUserEntity(hh);
		if (user == null) {
			errors.add("Ошибка аутентификации, необходимо перезайти в систему");
		} else {
			Category categ = CategoryService.createCategory(user, categoryName, resp);
			if (resp.getCode() != ResponseCode.OK) {
				errors.add(resp.getMessage());
			} else {
				info.put("success", "Категория '" + categoryName + "' успешно сохранена");
			}
		}
		return result;
	}
}

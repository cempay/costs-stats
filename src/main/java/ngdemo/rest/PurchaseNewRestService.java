package ngdemo.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import net.viralpatel.common.DateUtils;
import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.dataAccess.PurchaseService;
import net.viralpatel.hibernate.Category;
import ngdemo.auth.LoginUtils;
import ngdemo.domain.CategoryRest;

@Path("/user/newpurchase")
public class PurchaseNewRestService {	
	protected final static Map<String,String> locres = new HashMap<String,String>();
	
	//static block
	{
		locres.put("Title", "Создание новой покупки");
    	locres.put("PurchaseName", "Название");
    	locres.put("Price", "Цена");
    	locres.put("PayDate", "Дата");
    	locres.put("CategoryType", "Категория");
    	locres.put("PurchaseNameEmptyAlert", "Название покупки не должно быть пустым");
    	locres.put("InvalidFieldValue", "Поле '%s' заполнено неверно");
    	locres.put("SendButton", "Сохранить");		
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getNewPurchaseFormInJSON(@Context HttpHeaders hh){
    	Map<String, Object> result = new HashMap<String, Object>();    	
    	List<String> errors = new ArrayList<String>();
    	result.put("categories", getCategoryRestList(LoginUtils.getLogin(hh)));
    	result.put("locres", locres);
    	result.put("errors", errors);
    	return result;
    }	
      @GET
	  @Path("{categoryType}/{payDate}/{purchaseName}/{price}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Map<String, Object> sendNewPurchaseInJSON(
			  @PathParam("categoryType") String categoryType, 
			  @PathParam("payDate") String payDate,
			  @PathParam("purchaseName") String purchaseName, 
			  @PathParam("price") String price,
			  @Context HttpHeaders hh){
	  	Map<String, Object> result = new HashMap<String, Object>();	  	
	  	List<String> errors = new ArrayList<String>();    	
	  	Map<String, String> info = new HashMap<String, String>();
		String login = LoginUtils.getLogin(hh);
    	result.put("categories", getCategoryRestList(login));
    	result.put("info", info);
    	result.put("locres", locres);
    	result.put("errors", errors);
	    
	  	purchaseName = purchaseName.trim();
	  	Date date = null;	  	
		if ("".equals(purchaseName.trim())){
			errors.add("Необходимо ввести название покупки.");
			return result;
		}
		Long categoryId = null;
		try {
			categoryId = Long.valueOf(categoryType.trim());					
		}
		catch (Exception ex) {
			errors.add("Необходимо выбрать категорию.");
			return result;
		}
		try {
			date = new SimpleDateFormat(DateUtils.DD_MM_YYYY).parse(payDate.trim());					
		}
		catch (Exception ex) {
			errors.add("Формат введенной даты не соответсвует 'dd.MM.yyyy'.");
			return result;
		}
		BigDecimal aPrice = null;
		try {
			aPrice = new BigDecimal(price.trim())
				.setScale(2, RoundingMode.HALF_DOWN);
		}
		catch (Exception ex){
			errors.add("Введено некорректное значение в поле 'Цена'.");
			return result;
		}	  	
	  	
	  	QueryResponse resp = PurchaseService.createPurchaseByCategoryId(
	  			login, purchaseName, categoryId, date, aPrice);
    	if (resp.getCode() != ResponseCode.OK) {
    		errors.add(resp.getMessage());
    	} else {			    	
	    	info.put("success", "Покупка '" + purchaseName + "' успешно сохранена");
    	}

	  	return result;
	  }
      
      private List<CategoryRest> getCategoryRestList(String login){
      	List<CategoryRest> categRestList = new ArrayList<CategoryRest>();
      	List<Category> categs = CategoryService.getCategories(login);
      	for(Category categ: categs){
      		categRestList.add(new CategoryRest(categ.getId(), categ.getName()));
      	}  
      	return categRestList;
      }
}


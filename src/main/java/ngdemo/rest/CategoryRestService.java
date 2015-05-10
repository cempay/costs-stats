package ngdemo.rest;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import net.viralpatel.common.DateUtils;
import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.common.SumUtils;
import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.dataAccess.PurchaseService;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.Purchase;
import ngdemo.auth.LoginUtils;
import ngdemo.domain.CategoryRest;
import ngdemo.domain.PurchaseRest;

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
		String login = LoginUtils.getLogin(hh);
		Date dateFrom = DateUtils.beginOfMonth(null);
		Date dateTo = DateUtils.endOfMonth(null);
    	List<Category> categs = CategoryService.getCategories(login);
    	for(Category categ: categs){
    		categRestList.add(new CategoryRest(categ.getId(), categ.getName()));
    	}
		if (categRestList.size() > 0){
			QueryResponse resp= new QueryResponse();
			List<Purchase> purchases = PurchaseService.getPurchasesByCategoryIdByPeriod(
					login, categRestList.get(0).getId(), dateFrom, dateTo, resp);
			if (resp.getCode() != ResponseCode.OK) {
				errors.add(resp.getMessage());
			} else {
				List<PurchaseRest> purchaseRests = new ArrayList<PurchaseRest>();
				for (Purchase p : purchases) {
					purchaseRests.add(new PurchaseRest(p.getId(), p.getName(),
							new SimpleDateFormat(DateUtils.DD_MM_YYYY).format(p.getPayDate()),
							SumUtils.decimalFormatter.format(p.getPrice())
					));
				}
				categRestList.get(0).setPurchases(purchaseRests);
			}
		}
    	result.put("categories", categRestList);
    	result.put("locres", locres);
    	
    	return result;
    }
}

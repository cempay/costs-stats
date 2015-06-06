package ngdemo.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import net.viralpatel.common.SumUtils;
import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.dataAccess.PurchaseService;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.Purchase;
import ngdemo.auth.LoginUtils;
import ngdemo.domain.CategoryRest;
import ngdemo.domain.PurchaseRest;

@Path("/user/purchases/{categoryId}")
public class PurchaseRestService {	
    @GET
//    @Path("{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getPurchasesInJSON(@PathParam("categoryId") String categoryId, @Context HttpHeaders hh){
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	List<String> errors = new ArrayList<String>();    	
    	
    	List<PurchaseRest> purchRestList = new ArrayList<PurchaseRest>();
    	Date dateFrom = DateUtils.beginOfMonth(null);
    	Date dateTo = DateUtils.endOfMonth(null);
		String login = LoginUtils.getLogin(hh);
    	
    	QueryResponse resp = new QueryResponse();
    	List<Purchase> purchases = PurchaseService.getPurchasesByCategoryIdByPeriod(
    			login, Long.valueOf(categoryId), dateFrom, dateTo, resp);
    	if (resp.getCode() != ResponseCode.OK) {
    		errors.add(resp.getMessage());
    	} else {    	
	    	for(Purchase purch: purchases){
	    		purchRestList.add(new PurchaseRest(
	    				purch.getId(), purch.getName(), 
	    				new SimpleDateFormat(DateUtils.DD_MM_YYYY).format(purch.getPayDate()), 
	    				SumUtils.decimalFormatter.format(purch.getPrice())
	    				));
	    	}
	    	result.put("purchases", purchRestList);
			int[] outPeriod = PurchaseService.checkOutCurrentPeriodPurchasesByCategoryIdByPeriod(
					login, Long.valueOf(categoryId), dateFrom, dateTo, resp);
	    	result.put("prev", outPeriod[0]);
	    	result.put("next", outPeriod[1]);

    	}
    	
    	result.put("errors", errors);
    	return result;
    }
}

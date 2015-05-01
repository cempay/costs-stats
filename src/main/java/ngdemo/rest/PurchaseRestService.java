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
import javax.ws.rs.core.MediaType;

import net.viralpatel.common.DateUtils;
import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.common.SumUtils;
import net.viralpatel.dataAccess.CategoryService;
import net.viralpatel.dataAccess.PurchaseService;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.Purchase;
import ngdemo.domain.CategoryRest;
import ngdemo.domain.PurchaseRest;

@Path("/user/purchases/{categoryId}")
public class PurchaseRestService {	
    @GET
//    @Path("{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getPurchasesInJSON(@PathParam("categoryId") String categoryId){
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	List<String> errors = new ArrayList<String>();    	
    	
    	List<PurchaseRest> purchRestList = new ArrayList<PurchaseRest>();
/*    	Calendar calendar = new GregorianCalendar();
    	calendar.set(2015, Calendar.FEBRUARY, 1);
    	Date dateFrom = calendar.getTime();
    	calendar.set(2015, Calendar.MARCH, 31);
    	Date dateTo = calendar.getTime();*/
/*    	Calendar calendar = new GregorianCalendar();
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.add(Calendar.SECOND, -1);*/
    	Date dateFrom = DateUtils.beginOfMonth(null);
    	Date dateTo = DateUtils.endOfMonth(null);
    	
    	QueryResponse resp = new QueryResponse();
    	List<Purchase> purchases = PurchaseService.getPurchasesByCategoryIdByPeriod(
    			Long.valueOf(categoryId), dateFrom, dateTo, resp);
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
    	}
    	
    	result.put("errors", errors);
    	return result;
    }
}

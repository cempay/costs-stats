package ngdemo.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.dataAccess.CategoryInfo;
import net.viralpatel.dataAccess.DatabaseService;
import ngdemo.auth.LoginUtils;

@Path("/user/statistic")
public class StatisticRestService {
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getCategoriesInJSON(@Context HttpHeaders hh){
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	List<String> errors = new ArrayList<String>();    	

		String login = LoginUtils.getLogin(hh);
    	List<CategoryInfo> categInfoList = DatabaseService.collectCategoryInfos(login, null, null);
    	result.put("categoryInfoList", categInfoList);
    	
    	QueryResponse resp = new QueryResponse();
    	List<Object[]> categMonthGroup = DatabaseService.getCategoryMonthGroups(login, resp);
    	if (resp.getCode() != ResponseCode.OK) {
    		errors.add(resp.getMessage());
    	} else {
    		TreeSet<Integer> seriesSet = new TreeSet<Integer>();    		
    		for(Object[] group: categMonthGroup){
    			seriesSet.add(Integer.valueOf(group[1].toString()));
    		}
    		int months = seriesSet.size();
    		Integer[] series = seriesSet.toArray(new Integer[0]);    		
    		List<String> monthSeries = monthLabelList(series);
    		result.put("series", monthSeries);
    		
    		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();    		
    		String curCateg = null;
    		HashMap<String, Object> data = null;
			int cutNum = 6;
    		for(Object[] group: categMonthGroup){
    			if (!group[0].toString().equals(curCateg)) {
    				//new category name:
        			curCateg = group[0].toString();    	
    				data = new HashMap<String, Object>();
    				data.put("tooltip", curCateg);
    				String curCategCut = curCateg;
					if (curCateg.length() > cutNum + 3)
						curCategCut = curCateg.substring(0, cutNum)+".."+curCateg.substring(curCateg.length()-4, curCateg.length()-1);
    				data.put("x", curCategCut);
    				//init sums array:
    				BigDecimal[] sums = new BigDecimal[months];
    				for(int i=0; i<sums.length; i++)    					
    					sums[i] = BigDecimal.ZERO;
    				int index = Arrays.binarySearch(series, Integer.valueOf(group[1].toString()));
    				if (index >= 0) 
    					sums[index] = (BigDecimal)group[2];
    				data.put("y", sums);    				
    				datas.add(data);
    			} else {
    				//add sum element in existed array
    				BigDecimal[] sums = (BigDecimal[]) data.get("y");
    				int index = Arrays.binarySearch(series, Integer.valueOf(group[1].toString()));
    				if (index >= 0) 
    					sums[index] = (BigDecimal)group[2];
    			}
    		}
    		result.put("data", datas);  		
    		
    	}
    	
    	result.put("errors", errors);
    	return result;
    }
    
    private List<String> monthLabelList (Integer[] series){
		List<String> monthSeries = new ArrayList<String>();
		for(Integer ss:series){
			int s = ss.intValue();
			switch (s){
				case 1: monthSeries.add("¤нварь"); break;
				case 2: monthSeries.add("февраль"); break;
				case 3: monthSeries.add("март"); break;
				case 4: monthSeries.add("апрель"); break;
				
				case 5: monthSeries.add("май"); break;
				case 6: monthSeries.add("июнь"); break;
				case 7: monthSeries.add("июль"); break;
				case 8: monthSeries.add("август"); break;
				
				case 9: monthSeries.add("сент¤брь"); break;
				case 10: monthSeries.add("окт¤брь"); break;
				case 11: monthSeries.add("но¤брь"); break;
				case 12: monthSeries.add("декабрь"); break;
				default: monthSeries.add("не известно"); break;
			}
			
		}
		return monthSeries;
    }
}

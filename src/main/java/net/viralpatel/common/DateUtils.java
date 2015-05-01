package net.viralpatel.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils 
{
	public final static String DD_MM_YYYY = "dd.MM.yyyy";
	
	public static Date beginOfMonth(Date date){
    	Calendar calendar = new GregorianCalendar();
    	if (date != null)
    		calendar.setTime(date);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	calendar.add(Calendar.SECOND, -1);
    	return calendar.getTime();
	}
	
	public static Date endOfMonth(Date date){
    	Calendar calendar = new GregorianCalendar();
    	if (date != null)
    		calendar.setTime(date);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.add(Calendar.MONTH, 1);
    	calendar.add(Calendar.DAY_OF_MONTH, -1);
    	calendar.set(Calendar.HOUR, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	calendar.add(Calendar.SECOND, -1);
    	return calendar.getTime();
	}
	
	public static Date truncate(Date date){
		Calendar calendar = new GregorianCalendar();
		if (date != null)
			calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
    
    public static void main(String[] args){
    	Date dateFrom = DateUtils.beginOfMonth(null);
    	Date dateTo = DateUtils.endOfMonth(null);
    	Date dateFrom2 = dateFrom;
    }
}

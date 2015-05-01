package net.viralpatel.dataAccess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.HibernateUtil;
import net.viralpatel.hibernate.Purchase;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class PurchaseService {
	/**
	 * @deprecated
	 */
	public static Purchase createPurchase(String name, Category category, Date date, BigDecimal price){
		Purchase purchase = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
    	try {
	    	session.beginTransaction();	    	
	    	purchase = new Purchase(name, category, date, price);
	    	session.save(purchase);	
	    	session.getTransaction().commit();
		}	
		catch(HibernateException ex){
			purchase = null;
			System.out.println("#Database error: "+ ex);
		}    	
		finally {
			session.close();
		}
    	return purchase;
	}
	
	public static QueryResponse createPurchase(String name, String categoryName, Date date, BigDecimal price){
		QueryResponse resp = new QueryResponse();
		Category categ = CategoryService.isCategoryExist(categoryName, resp);
		
		if(resp.getCode() != ResponseCode.OK) {
			return resp;
		} else if (categ == null) {
			return new QueryResponse(ResponseCode.ERROR, "Категория с указанным именем '" + categoryName + "' не существует");
		} else {
	    	SessionFactory sf = HibernateUtil.getSessionFactory();
	    	Session session = sf.openSession();
	    	try {
		    	session.beginTransaction();	    	
		    	Purchase purchase = new Purchase(name, categ, date, price);
		    	session.save(purchase);	
		    	session.getTransaction().commit();
		    	resp = new QueryResponse(ResponseCode.OK);
			}	
			catch(HibernateException ex){
				resp = new QueryResponse(ResponseCode.ERROR, "Ошибка при создании покупки '" + name + "'", ex.getMessage());
				System.out.println("#Database error: "+ ex);
			}    	
			finally {
				session.close();
			}
		}
		return resp;
	}
	
	/** best */
	public static QueryResponse createPurchaseByCategoryId(String name, Long categoryId, Date date, BigDecimal price){
		QueryResponse resp = new QueryResponse();		
		Category categ = CategoryService.isCategoryExistById(categoryId, resp);
		
		if(resp.getCode() != ResponseCode.OK) {
			return resp;
		} else if (categ == null) {
			return new QueryResponse(ResponseCode.ERROR, "Категория с указанным id '" + categoryId.toString() + "' не существует");
		} else {
	    	SessionFactory sf = HibernateUtil.getSessionFactory();
	    	Session session = sf.openSession();
	    	try {
		    	session.beginTransaction();	    	
		    	Purchase purchase = new Purchase(name, categ, date, price);
		    	session.save(purchase);	
		    	session.getTransaction().commit();
		    	resp = new QueryResponse(ResponseCode.OK);
			}	
			catch(HibernateException ex){
				resp = new QueryResponse(ResponseCode.ERROR, "Ошибка при создании покупки '" + name + "'", ex.getMessage());
				System.out.println("#Database error: "+ ex);
			}    	
			finally {
				session.close();
			}
		}
		return resp;
	}
	
	public static List<Purchase> getPurchasesByCategory(Category category){
		List res = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
		try {
			res = session.createCriteria(Purchase.class)
					.add(Restrictions.eq("category", category))
					.list();
		}	
    	catch(HibernateException ex){
    		System.out.println("#Database error: "+ ex);
    	}    	
		finally {
			session.close();
		}
		return res;
	}
	
	public static List<Purchase> getPurchasesByCategoryByPeriod(String categoryName, Date dateFrom, Date dateTo, QueryResponse resp){
		List res = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
		try {
/*			String hql = "from Purchase"
					+ " where category.name = :categoryName"
					+ " and payDate >= :dateFrom"
					+ " and payDate < :dateTo"
					+ " order by payDate desc"
					;
			Query query = session.createQuery(hql);
			query.setParameter("categoryName", categoryName);
			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			res = query.list();
			resp.setCode(ResponseCode.OK);*/
			
			Criteria cr1 = session.createCriteria(Category.class);
			cr1.add(Restrictions.eq("name", categoryName));
			List<Category> categories = (List<Category>)cr1.list();
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateFrom);
			calendar.add(Calendar.SECOND, -1);
			dateFrom = calendar.getTime();
			
			Criteria cr = session.createCriteria(Purchase.class);
			cr.add(Restrictions.eq("category", categories.get(0)))
			.add(Restrictions.between("payDate", dateFrom, dateTo))
			;
			res = cr.list();
			
			resp.setCode(ResponseCode.OK);
			
		}	
    	catch(HibernateException ex){
    		System.out.println("#Database error: "+ ex);
    		resp.fillQueryResponse(ResponseCode.ERROR, "Ошибка получения списка покупок по периодам.", ex.getMessage());
    	}    	
		finally {
			session.close();
		}
		return res;
	}
	
	public static List<Purchase> getPurchasesByCategoryIdByPeriod(Long categoryId, Date dateFrom, Date dateTo, QueryResponse resp){
		List res = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
		try {
/*			String hql = "from Purchase"
					+ " where category.id = :categoryId"
					+ " and payDate >= :dateFrom"
					+ " and payDate < :dateTo"
					+ " order by payDate desc"
					;
			Query query = session.createQuery(hql);
			query.setParameter("categoryId", categoryId);
			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			res = query.list();
			resp.setCode(ResponseCode.OK);*/
			
			
			Criteria cr1 = session.createCriteria(Category.class);
			cr1.add(Restrictions.eq("id", categoryId));
			List<Category> categories = (List<Category>)cr1.list();
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateFrom);
			calendar.add(Calendar.SECOND, -1);
			dateFrom = calendar.getTime();
			
			Criteria cr = session.createCriteria(Purchase.class);
			cr.add(Restrictions.eq("category", categories.get(0)))
			.add(Restrictions.between("payDate", dateFrom, dateTo))
			;
			res = cr.list();
			
			resp.setCode(ResponseCode.OK);
			
		}	
    	catch(HibernateException ex){
    		System.out.println("#Database error: "+ ex);
    		resp.fillQueryResponse(ResponseCode.ERROR, "Ошибка получения списка покупок по периодам.", ex.getMessage());
    	}    	
		finally {
			session.close();
		}
		return res;
	}
	
	public static List<Object[]> getMonthPeriods(String category, QueryResponse resp){
		List<Object[]> result = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
			String hql = "select distinct month(payDate) as m, year(payDate) as y"
					+ " from Purchase as p, Category as c"
					+ " where c.id=p.category"
					+ " and c.name=:categ"
					+ " order by y,m";			
			Query query = session.createQuery(hql);
			query.setParameter("categ", category);
			result = query.list();
			resp.setCode(ResponseCode.OK);
		}	
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "Ошибка получения списка периодов.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return result;
	}

}

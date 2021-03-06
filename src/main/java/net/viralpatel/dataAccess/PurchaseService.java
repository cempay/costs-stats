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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class PurchaseService {

	@Deprecated
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

	@Deprecated
	public static QueryResponse createPurchase(String name, String categoryName, Date date, BigDecimal price){
		QueryResponse resp = new QueryResponse();
		Category categ = CategoryService.isCategoryExist(null, categoryName, resp);
		
		if(resp.getCode() != ResponseCode.OK) {
			return resp;
		} else if (categ == null) {
			return new QueryResponse(ResponseCode.ERROR, "��������� � ��������� ������ '" + categoryName + "' �� ����������");
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
				resp = new QueryResponse(ResponseCode.ERROR, "������ ��� �������� ������� '" + name + "'", ex.getMessage());
				System.out.println("#Database error: "+ ex);
			}    	
			finally {
				session.close();
			}
		}
		return resp;
	}
	
	/** best */
	public static QueryResponse createPurchaseByCategoryId(String login, String name, Long categoryId, Date date, BigDecimal price){
		QueryResponse resp = new QueryResponse();		
		Category categ = CategoryService.isCategoryExistById(login, categoryId, resp);
		
		if(resp.getCode() != ResponseCode.OK) {
			return resp;
		} else if (categ == null) {
			return new QueryResponse(ResponseCode.ERROR, "��������� � ��������� id '" + categoryId.toString() + "' �� ����������");
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
				resp = new QueryResponse(ResponseCode.ERROR, "������ ��� �������� ������� '" + name + "'", ex.getMessage());
				System.out.println("#Database error: "+ ex);
			}    	
			finally {
				session.close();
			}
		}
		return resp;
	}

	/** best */
	public static QueryResponse updatePurchaseByPurchaseId(Long purchaseId, String login, String name, Long categoryId, Date date, BigDecimal price){
		QueryResponse resp = new QueryResponse();
		Category categ = CategoryService.isCategoryExistById(login, categoryId, resp);

		if(resp.getCode() != ResponseCode.OK) {
			return resp;
		} else if (categ == null) {
			return new QueryResponse(ResponseCode.ERROR, "��������� � ��������� id '" + categoryId.toString() + "' �� ����������");
		} else {
	    	SessionFactory sf = HibernateUtil.getSessionFactory();
	    	Session session = sf.openSession();
	    	try {
		    	session.beginTransaction();
//		    	Purchase purchase = new Purchase(name, categ, date, price);
//		    	session.save(purchase);
				Purchase purchase = getPurchaseById(purchaseId);
				purchase.setCategory(categ);
				purchase.setName(name);
				purchase.setPayDate(date);
				purchase.setPrice(price);
				session.update(purchase);
		    	session.getTransaction().commit();
		    	resp = new QueryResponse(ResponseCode.OK);
			}
			catch(HibernateException ex){
				resp = new QueryResponse(ResponseCode.ERROR, "������ ��� �������� ������� '" + name + "'", ex.getMessage());
				System.out.println("#Database error: "+ ex);
			}
			finally {
				session.close();
			}
		}
		return resp;
	}
	
	public static List<Purchase> getPurchasesByCategory(String login, Category category){
		List res = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
		try {
//			res = session.createCriteria(Purchase.class)
//					.add(Restrictions.eq("category", category))
//					.createCriteria("user").add(Restrictions.eq("login", login))
//					.addOrder(Order.desc("payDate"))
//					.list();

			String hql = "from Purchase where category=:category and category.user.login = :login order by payDate desc";
			Query query = session.createQuery(hql);
			query.setParameter("category", category);
			query.setParameter("login", login);
			res = query.list();
		}	
    	catch(HibernateException ex){
    		System.out.println("#Database error: "+ ex);
    	}    	
		finally {
			session.close();
		}
		return res;
	}

	public static Purchase getPurchaseById(/*todo String login,*/ Long id){
		Purchase purch = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
		try {
			List res = null;
			String hql = "from Purchase where id=:id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			res = query.list();
			if (res.size() > 0)
				purch = (Purchase)res.get(0);
		}
    	catch(Exception ex){
    		System.out.println("#Database error: "+ ex);
    	}
		finally {
			session.close();
		}
		return purch;
	}

	@Deprecated
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
    		resp.fillQueryResponse(ResponseCode.ERROR, "������ ��������� ������ ������� �� ��������.", ex.getMessage());
    	}    	
		finally {
			session.close();
		}
		return res;
	}

	public static List<Purchase> getPurchasesByCategoryIdByPeriod(String login, Long categoryId, Date dateFrom, Date dateTo, QueryResponse resp){
		List res = null;
    	SessionFactory sf = HibernateUtil.getSessionFactory();
    	Session session = sf.openSession();
		try {
			Criteria cr1 = session.createCriteria(Category.class);
			cr1.add(Restrictions.eq("id", categoryId))
					.createCriteria("user").add(Restrictions.eq("login", login));
			List<Category> categories = (List<Category>)cr1.list();
			
			/*Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateFrom);
			calendar.add(Calendar.SECOND, -1);
			dateFrom = calendar.getTime();*/
			
			Criteria cr = session.createCriteria(Purchase.class);
			cr.add(Restrictions.eq("category", categories.get(0)))
					.add(Restrictions.between("payDate", dateFrom, dateTo))
					.addOrder(Order.desc("payDate"))
			;
			res = cr.list();
			resp.setCode(ResponseCode.OK);
		}	
    	catch(HibernateException ex){
    		System.out.println("#Database error: "+ ex);
    		resp.fillQueryResponse(ResponseCode.ERROR, "������ ��������� ������ ������� �� ��������.", ex.getMessage());
    	}    	
		finally {
			session.close();
		}
		return res;
	}

	/**
	 * @return int[2], 2 флага наличия предшествующих/последующих периодов для отображения prev|next
	 */
	public static int[] checkOutCurrentPeriodPurchasesByCategoryIdByPeriod(String login, Long categoryId, Date dateFrom, Date dateTo, QueryResponse resp){
		int[] res = {0 , 0};
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
			Criteria cr1 = session.createCriteria(Category.class);
			cr1.add(Restrictions.eq("id", categoryId))
					.createCriteria("user").add(Restrictions.eq("login", login));
			List<Category> categories = (List<Category>)cr1.list();

			String hql = "select 1 from Purchase p where p.category=:categ and p.payDate < :dateFrom";
			Query query = session.createQuery(hql);
			query.setParameter("categ", categories.get(0));
			query.setParameter("dateFrom", dateFrom);
			List prev = query.list();
			if (prev!=null && prev.size()>0)
				res[0] = 1;

			hql = "select 1 from Purchase p where p.category=:categ and p.payDate > :dateTo";
			query = session.createQuery(hql);
			query.setParameter("categ", categories.get(0));
			query.setParameter("dateTo", dateTo);
			List next = query.list();
			if (next!=null && next.size()>0)
				res[1] = 1;
			resp.setCode(ResponseCode.OK);
		}
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "������ ��������� ������ ������� �� ��������.", ex.getMessage());
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
			resp.fillQueryResponse(ResponseCode.ERROR, "������ ��������� ������ ��������.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return result;
	}

}

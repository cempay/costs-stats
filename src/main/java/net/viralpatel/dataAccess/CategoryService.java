package net.viralpatel.dataAccess;

import java.util.List;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.HibernateUtil;

import net.viralpatel.hibernate.User;
import ngdemo.auth.LoginUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class CategoryService {

	@Deprecated
	public static Category createCategory(String name){
		Category category = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
	    	session.beginTransaction();	    	
	    	category = new Category(name);
	    	session.save(category);
	    	session.getTransaction().commit();
		}	
		catch(HibernateException ex){
			category = null;
			System.out.println("#Database error: "+ ex);
		}    	
		finally {
			session.close();
		}
		return category;
	}

	public static Category createCategory(User user, String name, QueryResponse resp){
		Category category = isCategoryExist(user.getLogin(), name, resp);
		if (resp.getCode() != ResponseCode.OK){
			return null;
		}
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
	    	session.beginTransaction();	    	
	    	category = new Category(name);
			category.setUser(user);
	    	session.save(category);
	    	session.getTransaction().commit();
	    	resp.setCode(ResponseCode.OK);
		}	
		catch(HibernateException ex){
			category = null;
			System.out.println("#Database error: "+ ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "������ ��� �������� ���������.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return category;
	}

	public static List<Category> getCategories(String login){
		List res = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
			res = session.createCriteria(Category.class)
					.createCriteria("user").add(Restrictions.eq("login", login))
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

	@Deprecated
	public static Boolean isCategoryExist(String name) {
		Boolean exist = false;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
			List categories = session.createCriteria(Category.class)
				.add(Restrictions.eq("name", name))
				.list();
			exist = !categories.isEmpty();
		}	
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
		}    	
		finally {
			session.close();
		}
		return exist;
	}

	public static Category isCategoryExist(String login, String name, QueryResponse resp) {
		Category categ = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
			List categories = session.createCriteria(Category.class)
				.add(Restrictions.eq("name", name))
					.createCriteria("user").add(Restrictions.eq("login", login))
					.list();
			if (!categories.isEmpty())
				categ = (Category) categories.get(0);
			resp.setCode(ResponseCode.OK);
		}	
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "������ ��� �������� ������������� ���������.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return categ;
	}	

	public static Category isCategoryExistById(String login, Long id, QueryResponse resp) {
		Category categ = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
			List categories = session.createCriteria(Category.class)
				.add(Restrictions.eq("id", id))
					.createCriteria("user").add(Restrictions.eq("login", login))
					.list();
			if (!categories.isEmpty())
				categ = (Category) categories.get(0);
			resp.setCode(ResponseCode.OK);
		}	
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "������ ��� �������� ������������� ���������.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return categ;
	}
	
/*	public static String[] getCategoriesNames(){
		String[] res = null;
		List<Category> categs = CategoryService.getCategories();
		if (categs != null && categs.size() != 0) {
			res = new String[categs.size()];
			for(int i=0; i<categs.size(); i++){
				res[i] = categs.get(i).getName();
			}
		}
		if (res == null) res = new String[0];
		return res;
	}*/

}

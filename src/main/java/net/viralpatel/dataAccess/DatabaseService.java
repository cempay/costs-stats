package net.viralpatel.dataAccess;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.viralpatel.common.QueryResponse;
import net.viralpatel.common.ResponseCode;
import net.viralpatel.hibernate.Category;
import net.viralpatel.hibernate.HibernateUtil;
import net.viralpatel.hibernate.Purchase;

public class DatabaseService {
	public final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.##");
	public final static BigDecimal HUNDRED = BigDecimal.valueOf(100L);

	@Deprecated
	public static void consoleStatisticByScope(Date dateFrom, Date dateTo){
		StringBuffer stats = new StringBuffer();
		List<CategoryInfo> infos = collectCategoryInfos(null, dateFrom, dateTo);
		stats.append("\n------------- ���������� �� ����������: -------------\n");		
		if(infos.isEmpty()) {
			stats.append("������ ��������� ����.");
		} else {			
			for(CategoryInfo info: infos){
				if (info.getTotalSum().equals(BigDecimal.ZERO)) {
					System.out.println("Warning: total category sum is zero");
					stats.append(String.format("��������� '%s'\n", info.getName()
							));
				} else {
					BigDecimal res = info.getCategorySum().multiply(HUNDRED).divide(info.getTotalSum(), BigDecimal.ROUND_HALF_DOWN);
					stats.append(String.format("��������� '%s': %s %% \n", info.getName(), 
							//info.getCategorySum().divide(info.getTotalSum().set)
							DECIMAL_FORMAT.format(res.doubleValue())
							));
				}
			}
		}
		stats.append("\n-----------------------------------------------------\n");
		System.out.println(stats.toString());
	}
	
	public static List<CategoryInfo> collectCategoryInfos(String login, Date dateFrom, Date dateTo) {
		List<Category> categories = CategoryService.getCategories(login);
		BigDecimal totalSum = BigDecimal.ZERO;
		List<CategoryInfo> categInfos = new ArrayList<CategoryInfo>();
		for(Category categ: categories){				
			BigDecimal categSum = BigDecimal.ZERO;
			List<Purchase> categPurchases = PurchaseService.getPurchasesByCategory(categ);
			for(Purchase purch: categPurchases)
				categSum = categSum.add(purch.getPrice());
			CategoryInfo info = new CategoryInfo(categ.getName(), categSum, null, null);
			categInfos.add(info);
			totalSum = totalSum.add(info.getCategorySum());
		}
		for(CategoryInfo info: categInfos) {
			info.setPercent(info.getCategorySum().multiply(new BigDecimal(100)).divide(totalSum, 2, RoundingMode.HALF_UP));
			info.setTotalSum(totalSum);
		}
		return categInfos;
	}
	
	public static List<Object[]> getCategoryMonthGroups(String login, QueryResponse resp){
		List<Object[]> result = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		try {
/*			String hql = "select distinct month(payDate) as m, year(payDate) as y"
					+ " from Purchase as p, Category as c"
					+ " where c.id=p.category"
					+ " and c.name=:categ"
					+ " order by y,m";			
			Query query = session.createQuery(hql);
			query.setParameter("categ", category);*/
			
			String hql = "select c.name, month(p.payDate), sum(p.price) "
					+ " from Purchase as p, Category as c"
					+ " where c.user.login=:login and c.id=p.category "
					+ " group by c.name, month(p.payDate) "
					+ " order by c.name, month(p.payDate)";			
			Query query = session.createQuery(hql);
			query.setParameter("login", login);
			result = query.list();
			resp.setCode(ResponseCode.OK);
		}	
		catch(HibernateException ex){
			System.out.println("#Database error: "+ ex);
			resp.fillQueryResponse(ResponseCode.ERROR, "������ ��������� ����� ��������� � �������.", ex.getMessage());
		}    	
		finally {
			session.close();
		}
		return result;
	}	
	

}

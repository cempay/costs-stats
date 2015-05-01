package net.viralpatel.dataAccess;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryInfo {
	String name;
	BigDecimal categorySum;
	BigDecimal percent;
	BigDecimal totalSum;
	Date dateFrom;
	Date dateTo;	
	
	public CategoryInfo(String name, BigDecimal categorySum, Date dateFrom,
			Date dateTo) {
		super();
		this.name = name;
		this.categorySum = categorySum;
		this.totalSum = BigDecimal.ZERO;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {

		this.totalSum = totalSum;
	}	

	public BigDecimal getCategorySum() {
		return categorySum;
	}

	public void setCategorySum(BigDecimal categorySum) {
		this.categorySum = categorySum;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	
		
}

package net.viralpatel.hibernate;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PURCHASE")
public class Purchase {
	@Id
	@Column
	@SequenceGenerator(name="purSeq", sequenceName="PURCHASE_SEQ")
	@GeneratedValue(generator="purSeq", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="PAYDATE")
	private Date payDate;
	
	@Column
	private BigDecimal price;
	
	public Purchase(String name, Category category, Date payDate, BigDecimal price){
		this.name = name;
		this.category = category;
		this.payDate = payDate;
		this.price = price;
	}
	
	public Purchase(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	
}

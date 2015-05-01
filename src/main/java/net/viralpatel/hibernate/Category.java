package net.viralpatel.hibernate;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category {
	@Id
	@Column
	@SequenceGenerator(name="catSeq", sequenceName="CATEGORY_SEQ")
	@GeneratedValue(generator="catSeq", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy="category")
	private Set<Purchase> purchases;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public Category(){
		
	}
	
	public Category(String name){
		this.name = name;
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

	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
	
	
}

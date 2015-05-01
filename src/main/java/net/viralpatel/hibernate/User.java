package net.viralpatel.hibernate;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@Column
	@SequenceGenerator(name="userSeq", sequenceName="USERS_SEQ")
	@GeneratedValue(generator="userSeq", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(unique=true)
	private String login;
	
	@Column
	private String password;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy="user")
	private Set<Category> categories;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}	
	
}

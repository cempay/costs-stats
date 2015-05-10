package ngdemo.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CategoryRest {
	public Long id;
	public String name;
	public List<PurchaseRest> purchases;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PurchaseRest> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<PurchaseRest> purchases) {
		this.purchases = purchases;
	}

	public CategoryRest(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}

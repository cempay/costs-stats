package ngdemo.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PurchaseRest {
	public Long id;
	public String name;
	public String payDate;
	public String price;
	public PurchaseRest(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}	
	public PurchaseRest(Long id, String name, String payDate, String price) {
		super();
		this.id = id;
		this.name = name;
		this.payDate = payDate;
		this.price = price;
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
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	
}

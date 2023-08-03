package com.ftg.orderservice.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String orderId;
	
	private Long resturantId;
	
	private String userId;
	
	private String status;
	
	private double totalAmount;
	
	private List<Long> menuItems;

//	@OneToMany(mappedBy = "order",fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
//	// @JsonManagedReference
//	private List<OrderItem> items = new ArrayList<>();

	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
	private Payment payment;
	
//	 public void addItem(OrderItem item) {
//	        items.add(item);
//	        item.setOrder(this);
//	    }
//
//	    public void removeItem(OrderItem item) {
//	        items.remove(item);
//	        item.setOrder(null);
//	    }

	    public void setPayment(Payment payment) {
	        this.payment = payment;
	        payment.setOrder(this);
	    }
}

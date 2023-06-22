package com.ftg.restaurantservice.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long restaurantId;

	private String name;

	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuItem> menuItem;

	@OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;

	@OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private ContactDetails contactDetails;

	private String openingHour;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItem) {
		for (MenuItem item : menuItem) {
			item.setRestaurant(this);
		}
		this.menuItem = menuItem;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		address.setRestaurant(this);
		this.address = address;
	}

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		contactDetails.setRestaurant(this);
		this.contactDetails = contactDetails;
	}

	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}


	public void updateAddress(Address address) {
		this.address.setAddress(address.getAddress());
		this.address.setNearBy(address.getNearBy());
		this.address.setZip(address.getZip());
	}
	
	public void updateContactDetails(ContactDetails contactDetails) {
		this.contactDetails.setEmail(contactDetails.getEmail());
		this.contactDetails.setContactNumber(contactDetails.getContactNumber());
	}
	

}

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuItem> menuItem;

	@OneToOne(mappedBy = "address",fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;

	@OneToOne(mappedBy = "contactDetails",fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
	private ContactDetails contactDetails;

	private String openingHour;

}

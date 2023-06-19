package com.ftg.restaurantservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "menu_items")
public class MenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private double price;

	@OneToOne
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnore
	private String Category;

	@OneToOne
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnore
	private String Description;

	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnore
	private Restaurant restaurant;
}

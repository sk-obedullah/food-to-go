package com.ftg.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.restaurantservice.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

}

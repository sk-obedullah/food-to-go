package com.ftg.restaurantservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.restaurantservice.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	 Optional<MenuItem> findByRestaurantRestaurantIdAndId(Long restaurantId, Long menuItemId);
}

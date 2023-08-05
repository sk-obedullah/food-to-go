package com.ftg.restaurantservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ftg.restaurantservice.dto.MenuItemDTO;
import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.exception.ResourceNotFoundException;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.model.Restaurant;
import com.ftg.restaurantservice.repository.MenuItemRepository;
import com.ftg.restaurantservice.repository.RestaurantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestauranSerive {

	private final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	private RestaurantRepository restaurantRepository;
	private MenuItemRepository menuItemRepository;
	private ModelMapper modelMapper;

	@Override
	public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
		try {
			Restaurant restaurant = new Restaurant();
			restaurant.setName(restaurantDTO.getName());
			restaurant.setOpeningHour(restaurantDTO.getOpeningHour());
			Address address = new Address();
			address.setAddress(restaurantDTO.getRestaurantLocation());
			restaurant.setAddress(address);
			restaurant.setImageUrl(restaurantDTO.getRestaurantImage());
			Restaurant save = restaurantRepository.save(restaurant);
			RestaurantDTO dto = new RestaurantDTO(save.getRestaurantId(), save.getName(), save.getMenuItem(),
					save.getAddress(),save.getAddress().getAddress(), save.getContactDetails(),"", save.getOpeningHour());
			return dto;
		} catch (Exception e) {
			logger.error("Failed to create restaurant: {}", e.getMessage());
			throw new IllegalStateException("Failed to create restaurant");
		}
	}

	@Override
	public RestaurantDTO getRestaurantById(Long restaurantId) {
		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId)
					.orElseThrow(() -> new ResourceNotFoundException("Restaurant", "Id", restaurantId));
			RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
			return restaurantDTO;
		} catch (Exception e) {
			logger.error("Failed to retrieve restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new IllegalStateException("Failed to retrieve restaurant by id");
		}
	}

	@Override
	public List<RestaurantDTO> getAllRestaurats() {
		try {
			List<Restaurant> findAll = restaurantRepository.findAll();
			List<RestaurantDTO> dtoList = new ArrayList<>();
			for (Restaurant restaurant : findAll) {
				RestaurantDTO map = modelMapper.map(restaurant, RestaurantDTO.class);
				dtoList.add(map);
			}
			return dtoList;
		} catch (Exception e) {
			logger.error("Failed to retrieve all restaurants: {}", e.getMessage());
			throw new IllegalStateException("Failed to retrieve all restaurants");
		}
	}

	@Override
	public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
		try {
			Restaurant restaurant = restaurantRepository.findById(id).get();
			restaurant.setName(restaurantDTO.getName());
			// restaurant.updateAddress(restaurantDTO.getAddress());
			// restaurant.updateContactDetails(restaurantDTO.getContactDetails());
			restaurant.setMenuItem(restaurantDTO.getMenuItem());
			restaurant.setOpeningHour(restaurantDTO.getOpeningHour());

			Restaurant save = restaurantRepository.save(restaurant);
			RestaurantDTO map2 = modelMapper.map(save, RestaurantDTO.class);
			return map2;
		} catch (Exception e) {
			logger.error("Failed to update restaurant with id {}: {}", id, e.getMessage());
			throw new IllegalStateException("Failed to update restaurant");
		}
	}

	@Override
	public void deleteRestaurant(Long restaurantID) {
		try {
			Optional<Restaurant> findById = restaurantRepository.findById(restaurantID);
			if (findById.isPresent()) {
				restaurantRepository.delete(findById.get());
			}
		} catch (Exception e) {
			logger.error("Failed to delete restaurant with id {}: {}", restaurantID, e.getMessage());
			throw new IllegalStateException("Failed to delete restaurant");
		}
	}

	public ContactDetails getContactDetails(Long restaurantId) {

		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId)
					.orElseThrow(() -> new ResourceNotFoundException("Restaurant", "ID", restaurantId));
			return restaurant.getContactDetails();
		} catch (Exception e) {
			logger.error("Failed to retrieve contact details for restaurant with id {}: {}", restaurantId,
					e.getMessage());
			throw new IllegalStateException("Failed to retrieve contact details");
		}
	}

	public ContactDetails updateContactDetails(Long restaurantId, ContactDetails contactDetails) {
		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
			restaurant.setContactDetails(contactDetails);
			return restaurantRepository.save(restaurant).getContactDetails();
		} catch (Exception e) {
			logger.error("Failed to update contact details for restaurant with id {}: {}", restaurantId,
					e.getMessage());
			throw new IllegalStateException("Failed to update contact details");
		}
	}

	public Address getAddress(Long restaurantId) {
		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId)
					.orElseThrow(() -> new ResourceNotFoundException("Restaurant", "ID", restaurantId));
			return restaurant.getAddress();

		} catch (ResourceNotFoundException e) {
			logger.error("Failed to retrieve address for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResourceNotFoundException("Restaurant", "ID", restaurantId);
		} catch (Exception e) {
			logger.error("Failed to retrieve address for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new IllegalStateException("Failed to retrieve address");
		}
	}

	public Address updateAddress(Long restaurantId, Address address) {
		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
			restaurant.setAddress(address);
			return restaurantRepository.save(restaurant).getAddress();
		} catch (Exception e) {
			logger.error("Failed to update address for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new IllegalStateException("Failed to update address");
		}
	}

	public List<MenuItem> getMenuItems(Long restaurantId) {
		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
			return restaurant.getMenuItem();
		} catch (Exception e) {
			logger.error("Failed to retrieve menu items for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new IllegalStateException("Failed to retrieve menu items");
		}
	}

	public MenuItem createMenuItem(Long restaurantId, MenuItemDTO menuItemDto) {
		try {
			MenuItem menuItem=new MenuItem();
			menuItem.setName(menuItemDto.getItemName());
			menuItem.setImageUrl(menuItemDto.getItemImage());
			menuItem.setCategory(menuItemDto.getItemType());
			menuItem.setDescription(menuItemDto.getItemDescription());
			menuItem.setPrice(Double.parseDouble(menuItemDto.getItemPrice()));
			
			Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
			menuItem.setRestaurant(restaurant);
			restaurant.getMenuItem().add(menuItem);
			Restaurant save = restaurantRepository.save(restaurant);
			return save.getMenuItem().stream().filter(item -> item.getName().equals(menuItem.getName())).findFirst()
					.orElseThrow(() -> new IllegalStateException("Failed to create menu item"));

		} catch (Exception e) {
			logger.error("Failed to create menu item for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new IllegalStateException("Failed to create menu item");
		}
	}

	public void deleteMenuItem(Long restaurantId, Long menuItemId) {
		try {
			Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
			restaurant.getMenuItem().removeIf(menuItem -> menuItem.getId().equals(menuItemId));
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			logger.error("Failed to delete menu item with id {} for restaurant with id {}: {}", menuItemId,
					restaurantId, e.getMessage());
			throw new IllegalStateException("Failed to delete menu item");
		}
	}

	public MenuItem getMenuItemByMenuId(Long restaurantId, Long itemId) {
		Optional<Restaurant> optionalRestaurant;
		Optional<MenuItem> optionalMenuItem;
		try {
			optionalRestaurant = restaurantRepository.findById(restaurantId);
		} catch (Exception e) {
			logger.error("Failed to Retrieve menu item with id {} for restaurant with id {}: {}", itemId, restaurantId,
					e.getMessage());
			throw new IllegalStateException("Failed to delete menu item");
		}

		if (optionalRestaurant.isEmpty()) {
			throw new ResourceNotFoundException("Restaurant", "RestaurantId", restaurantId);
		}

		try {
			optionalMenuItem = menuItemRepository.findByRestaurantRestaurantIdAndId(restaurantId, itemId);
		} catch (Exception e) {
			logger.error("Failed to retrieve menu item with id {} for restaurant with id {}: {}", itemId, restaurantId,
					e.getMessage());
			throw new IllegalStateException("Failed to delete menu item");
		}

		if (optionalMenuItem.isEmpty()) {
			throw new ResourceNotFoundException("MenuItem", "MenuId", restaurantId);
		}

		return optionalMenuItem.get();

	}

}

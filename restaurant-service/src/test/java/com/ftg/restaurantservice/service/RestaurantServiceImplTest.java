package com.ftg.restaurantservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.ftg.restaurantservice.dto.MenuItemDTO;
import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.model.Restaurant;
import com.ftg.restaurantservice.repository.MenuItemRepository;
import com.ftg.restaurantservice.repository.RestaurantRepository;

public class RestaurantServiceImplTest {

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private MenuItemRepository menuItemRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private RestaurantServiceImpl restaurantService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddRestaurant() {
		RestaurantDTO inputDTO = new RestaurantDTO();
		inputDTO.setName("Test Restaurant");

		Restaurant savedRestaurant = new Restaurant();
		savedRestaurant.setRestaurantId(1L);
		savedRestaurant.setName("Test Restaurant");
		savedRestaurant.setAddress(new Address());

		RestaurantDTO savedRestaurantDTO = new RestaurantDTO();
		savedRestaurantDTO.setRestaurantId(1L);
		savedRestaurantDTO.setName("Test Restaurant");
		savedRestaurantDTO.setAddress(new Address());

		when(restaurantRepository.save(any(Restaurant.class))).thenReturn(savedRestaurant);
		when(modelMapper.map(ArgumentMatchers.any(Restaurant.class), any())).thenReturn(savedRestaurantDTO);
		RestaurantDTO resultDTO = restaurantService.addRestaurant(inputDTO);

		assertNotNull(resultDTO);
		assertEquals(1L, resultDTO.getRestaurantId());
		assertEquals("Test Restaurant", resultDTO.getName());

		verify(restaurantRepository, times(1)).save(any(Restaurant.class));
	}

	@Test
	public void testGetRestaurantById() {
		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(1L);
		restaurant.setName("Test Restaurant");
		restaurant.setImageUrl("urlIMG");
		restaurant.setContactDetails(new ContactDetails(1l, "email", "123", restaurant));
		restaurant.setOpeningHour("");
		restaurant.setMenuItem(Arrays.asList(new MenuItem()));

		RestaurantDTO savedRestaurantDTO = new RestaurantDTO();
		savedRestaurantDTO.setRestaurantId(1L);
		savedRestaurantDTO.setName("Test Restaurant");
		savedRestaurantDTO.setAddress(new Address());

		when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
		when(modelMapper.map(ArgumentMatchers.any(Restaurant.class), any())).thenReturn(savedRestaurantDTO);
		RestaurantDTO resultDTO = restaurantService.getRestaurantById(1L);

		assertNotNull(resultDTO);
		assertEquals(1L, resultDTO.getRestaurantId());
		assertEquals("Test Restaurant", resultDTO.getName());

		verify(restaurantRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetAllRestaurants() {
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setRestaurantId(1L);
		restaurant1.setName("Restaurant 1");

		Restaurant restaurant2 = new Restaurant();
		restaurant2.setRestaurantId(2L);
		restaurant2.setName("Restaurant 2");

		List<Restaurant> restaurantList = new ArrayList<>();
		restaurantList.add(restaurant1);
		restaurantList.add(restaurant2);

		when(restaurantRepository.findAll()).thenReturn(restaurantList);

		List<RestaurantDTO> resultDTOList = restaurantService.getAllRestaurats();

		assertNotNull(resultDTOList);
		assertEquals(2, resultDTOList.size());

		verify(restaurantRepository, times(1)).findAll();
	}

	@Test
	public void testUpdateRestaurant() {
		Long restaurantId = 1L;

		RestaurantDTO inputDTO = new RestaurantDTO();
		inputDTO.setName("Updated Restaurant");
		inputDTO.setRestaurantId(restaurantId);
		inputDTO.setMenuItem(Arrays.asList(new MenuItem(1l, "", 1, "", "", "", null)));

		Restaurant existingRestaurant = new Restaurant();
		existingRestaurant.setRestaurantId(restaurantId);
		existingRestaurant.setName("Original Restaurant");

		Restaurant updatedRestaurant = new Restaurant();
		updatedRestaurant.setRestaurantId(restaurantId);
		updatedRestaurant.setName("Updated Restaurant");
		updatedRestaurant.setContactDetails(new ContactDetails(1l, "", "", updatedRestaurant));
		updatedRestaurant.setMenuItem(Arrays.asList(new MenuItem(1l, "", 1, "", "", "", updatedRestaurant)));
		updatedRestaurant.setOpeningHour("");

		when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
		when(restaurantRepository.save(any(Restaurant.class))).thenReturn(updatedRestaurant);
		when(modelMapper.map(ArgumentMatchers.any(Restaurant.class), any())).thenReturn(inputDTO);

		RestaurantDTO resultDTO = restaurantService.updateRestaurant(restaurantId, inputDTO);

		assertNotNull(resultDTO);
		assertEquals(restaurantId, resultDTO.getRestaurantId());
		assertEquals("Updated Restaurant", resultDTO.getName());

		verify(restaurantRepository, times(1)).findById(restaurantId);
		verify(restaurantRepository, times(1)).save(any(Restaurant.class));
	}

	@Test
	public void testDeleteRestaurant() {
		Long restaurantId = 1L;

		Restaurant existingRestaurant = new Restaurant();
		existingRestaurant.setRestaurantId(restaurantId);

		when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));

		restaurantService.deleteRestaurant(restaurantId);

		verify(restaurantRepository, times(1)).findById(restaurantId);
		verify(restaurantRepository, times(1)).delete(existingRestaurant);
	}

	@Test
	public void testGetMenuItems() {
		Long restaurantId = 1L;

		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(restaurantId);
		restaurant.setMenuItem(new ArrayList<MenuItem>());

		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1L);
		menuItem1.setName("Item 1");

		MenuItem menuItem2 = new MenuItem();
		menuItem2.setId(2L);
		menuItem2.setName("Item 2");

		restaurant.getMenuItem().add(menuItem1);
		restaurant.getMenuItem().add(menuItem2);

		when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

		List<MenuItem> resultItems = restaurantService.getMenuItems(restaurantId);

		assertNotNull(resultItems);
		assertEquals(2, resultItems.size());

		verify(restaurantRepository, times(1)).findById(restaurantId);
	}

	@Test
	public void testCreateMenuItem() {
		Long restaurantId = 1L;

		MenuItemDTO menuItemDTO = new MenuItemDTO(1L, 1, "itemName", "112", "imageUrl", "15min", "veg", "itemDescp");
		menuItemDTO.setItemName("New Item");

		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(restaurantId);

		when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

		MenuItem createdMenuItem = new MenuItem();
		createdMenuItem.setId(1L);
		createdMenuItem.setName("New Item");
		List<MenuItem> items = new ArrayList<>();
		items.add(createdMenuItem);
		restaurant.setMenuItem(items);

		when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

		MenuItem resultItem = restaurantService.createMenuItem(restaurantId, menuItemDTO);

		assertNotNull(resultItem);
		assertEquals("New Item", resultItem.getName());

		verify(restaurantRepository, times(1)).findById(restaurantId);
		verify(restaurantRepository, times(1)).save(any(Restaurant.class));
	}

	@Test
	public void testDeleteMenuItem() {
		Long restaurantId = 1L;
		Long menuItemId = 1L;

		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(restaurantId);
		restaurant.setMenuItem(new ArrayList<MenuItem>());

		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(menuItemId);
		menuItem1.setName("Item 1");

		restaurant.getMenuItem().add(menuItem1);

		when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

		restaurantService.deleteMenuItem(restaurantId, menuItemId);

		verify(restaurantRepository, times(1)).findById(restaurantId);
		verify(restaurantRepository, times(1)).save(any(Restaurant.class));
	}

}

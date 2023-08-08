package com.ftg.restaurantservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.service.RestaurantServiceImpl;

public class RestauranControllerUserTest {

	private MockMvc mockMvc;

	@Mock
	private RestaurantServiceImpl restaurantService;

	@InjectMocks
	private RestauranControllerUser restaurantController;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
	}

	@Test
	public void testGetRestaurantById() throws Exception {
		RestaurantDTO restaurantDTO = new RestaurantDTO();
		restaurantDTO.setRestaurantId(1L);
		restaurantDTO.setName("Test Restaurant");

		when(restaurantService.getRestaurantById(1L)).thenReturn(restaurantDTO);

		mockMvc.perform(get("/api/user/restaurant-service/{id}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.restaurantId").value(1)).andExpect(jsonPath("$.name").value("Test Restaurant"));

		verify(restaurantService, times(1)).getRestaurantById(1L);
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testGetAllRestaurant() throws Exception {
		List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
		RestaurantDTO restaurantDTO1 = new RestaurantDTO();
		restaurantDTO1.setRestaurantId(1L);
		restaurantDTO1.setName("Restaurant 1");
		restaurantDTOList.add(restaurantDTO1);

		RestaurantDTO restaurantDTO2 = new RestaurantDTO();
		restaurantDTO2.setRestaurantId(2L);
		restaurantDTO2.setName("Restaurant 2");
		restaurantDTOList.add(restaurantDTO2);

		when(restaurantService.getAllRestaurats()).thenReturn(restaurantDTOList);

		mockMvc.perform(get("/api/user/restaurant-service")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].restaurantId").value(1))
				.andExpect(jsonPath("$[0].name").value("Restaurant 1"))
				.andExpect(jsonPath("$[1].restaurantId").value(2))
				.andExpect(jsonPath("$[1].name").value("Restaurant 2"));

		verify(restaurantService, times(1)).getAllRestaurats();
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testGetMenuItems() throws Exception {
		List<MenuItem> menuItems = new ArrayList<>();
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setId(1L);
		menuItem1.setName("Item 1");
		menuItems.add(menuItem1);

		MenuItem menuItem2 = new MenuItem();
		menuItem2.setId(2L);
		menuItem2.setName("Item 2");
		menuItems.add(menuItem2);

		when(restaurantService.getMenuItems(1L)).thenReturn(menuItems);

		mockMvc.perform(get("/api/user/restaurant-service/{restaurantId}/menu-items", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("Item 1"))
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].name").value("Item 2"));

		verify(restaurantService, times(1)).getMenuItems(1L);
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testGetMenuItemByMenuId() throws Exception {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Item 1");

		when(restaurantService.getMenuItemByMenuId(1L, 1L)).thenReturn(menuItem);

		mockMvc.perform(get("/api/user/restaurant-service/{restaurantId}/menu-items/{itemId}", 1, 1))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Item 1"));

		verify(restaurantService, times(1)).getMenuItemByMenuId(1L, 1L);
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testGetAddress() throws Exception {
		Address address = new Address();
		address.setId(1L);
		address.setAddress("123 Main St");

		when(restaurantService.getAddress(1L)).thenReturn(address);

		mockMvc.perform(get("/api/user/restaurant-service/{restaurantId}/address", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.address").value("123 Main St"));

		verify(restaurantService, times(1)).getAddress(1L);
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testGetContactDetails() throws Exception {
		ContactDetails contactDetails = new ContactDetails();
		contactDetails.setId(1L);
		contactDetails.setContactNumber("123-456-7890");
		// Set other properties for contactDetails...

		when(restaurantService.getContactDetails(1L)).thenReturn(contactDetails);

		mockMvc.perform(get("/api/user/restaurant-service/{restaurantId}/contact-details", 1))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.contactNumber").value("123-456-7890"));

		verify(restaurantService, times(1)).getContactDetails(1L);
		verifyNoMoreInteractions(restaurantService);
	}

}

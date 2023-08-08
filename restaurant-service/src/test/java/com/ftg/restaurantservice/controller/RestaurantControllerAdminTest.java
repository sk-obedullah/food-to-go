package com.ftg.restaurantservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftg.restaurantservice.dto.MenuItemDTO;
import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.service.RestaurantServiceImpl;

public class RestaurantControllerAdminTest {

	private MockMvc mockMvc;

	@Mock
	private RestaurantServiceImpl restaurantService;

	@InjectMocks
	private RestaurantControllerAdmin restaurantController;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
	}

	@Test
	public void testAddRestaurant() throws Exception {
		RestaurantDTO restaurantDTO = new RestaurantDTO();
		restaurantDTO.setRestaurantId(1L);
		restaurantDTO.setName("Test Restaurant");

		when(restaurantService.addRestaurant(any())).thenReturn(restaurantDTO);

		mockMvc.perform(post("/api/admin/restaurant-service").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(restaurantDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.restaurantId").value(1)).andExpect(jsonPath("$.name").value("Test Restaurant"));

		verify(restaurantService, times(1)).addRestaurant(any());
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testUpdateRestaurant() throws Exception {
		RestaurantDTO restaurantDTO = new RestaurantDTO();
		restaurantDTO.setRestaurantId(1L);
		restaurantDTO.setName("Updated Restaurant");

		when(restaurantService.updateRestaurant(eq(1L), any())).thenReturn(restaurantDTO);

		mockMvc.perform(put("/api/admin/restaurant-service/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(restaurantDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.restaurantId").value(1))
				.andExpect(jsonPath("$.name").value("Updated Restaurant"));

		verify(restaurantService, times(1)).updateRestaurant(eq(1L), any());
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testDeleteRestaurant() throws Exception {
		doNothing().when(restaurantService).deleteRestaurant(1L);
		mockMvc.perform(delete("/api/admin/restaurant-service/{restaurantId}", 1)).andExpect(status().isOk());

		verify(restaurantService, times(1)).deleteRestaurant(1L);
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testCreateMenuItem() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setRestaurantId(1);
		menuItemDTO.setItemName("Test Item");

		MenuItem menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Test Item");

		when(restaurantService.createMenuItem(eq(1L), any())).thenReturn(menuItem);

		mockMvc.perform(post("/api/admin/restaurant-service/{restaurantId}/menu-items", 1)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(menuItemDTO)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Test Item"));

		verify(restaurantService, times(1)).createMenuItem(eq(1L), any());
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testDeleteMenuItem() throws Exception {
		doNothing().when(restaurantService).deleteMenuItem(1L, 1L);

		mockMvc.perform(delete("/api/admin/restaurant-service/{restaurantId}/menu-items/{menuItemId}", 1, 1))
				.andExpect(status().isOk());

		verify(restaurantService, times(1)).deleteMenuItem(1L, 1L);
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testUpdateAddress() throws Exception {
		Address address = new Address();
		address.setId(1L);
		address.setAddress("123 Updated St");

		when(restaurantService.updateAddress(eq(1L), any())).thenReturn(address);

		mockMvc.perform(put("/api/admin/restaurant-service/{restaurantId}/address", 1)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.address").value("123 Updated St"));

		verify(restaurantService, times(1)).updateAddress(eq(1L), any());
		verifyNoMoreInteractions(restaurantService);
	}

	@Test
	public void testUpdateContactDetails() throws Exception {
		ContactDetails contactDetails = new ContactDetails();
		contactDetails.setId(1L);
		contactDetails.setContactNumber("123-456-7890");
		when(restaurantService.updateContactDetails(eq(1L), any())).thenReturn(contactDetails);

		mockMvc.perform(put("/api/admin/restaurant-service/{restaurantId}/contact-details", 1)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(contactDetails)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.contactNumber").value("123-456-7890"));

		verify(restaurantService, times(1)).updateContactDetails(eq(1L), any());
		verifyNoMoreInteractions(restaurantService);
	}

}

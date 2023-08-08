package com.ftg.accountservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftg.accountservice.dto.AuthRequest;
import com.ftg.accountservice.model.User;
import com.ftg.accountservice.model.UserDTO;
import com.ftg.accountservice.service.AuthService;
import com.ftg.accountservice.service.UserService;

public class UserControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Mock
	private AuthService authService;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserController userController;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testRegisterUser() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName("TestUser");
		userDTO.setUserEmail("test@example.com");
		userDTO.setUserCity("TestCity");
		userDTO.setUserMobile("1234567890");
		userDTO.setUserPassword("password");

		User user = new User();
		user.setName(userDTO.getUserName());
		user.setEmail(userDTO.getUserEmail());
		user.setUserCity(userDTO.getUserCity());
		user.setUserMobile(userDTO.getUserMobile());
		user.setPassword(userDTO.getUserPassword());
		user.setRole("ROLE_USER");

		when(userService.addUser(any(User.class))).thenReturn(userDTO);

		mockMvc.perform(post("/api/user/user-service/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.userName").value("TestUser"))
				.andExpect(jsonPath("$.userEmail").value("test@example.com"))
				.andExpect(jsonPath("$.userCity").value("TestCity"))
				.andExpect(jsonPath("$.userMobile").value("1234567890"));

		verify(userService, times(1)).addUser(any(User.class));
		verifyNoMoreInteractions(userService);
	}
//
//	@Test
//	public void testGetToken() throws Exception {
//		AuthRequest authRequest = new AuthRequest("testuser", "password");
//
//		User user = new User();
//		user.setName("TestUser");
//		user.setRole("ROLE_USER");
//		  Authentication authenticate=new Authentication() {
//			
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public boolean isAuthenticated() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public Object getPrincipal() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public Object getDetails() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public Object getCredentials() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public Collection<? extends GrantedAuthority> getAuthorities() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//		 when(authenticationManager.authenticate(any())).thenReturn(authenticate);
//		
//		when(authenticate.isAuthenticated()).thenReturn(true);
//		when(userService.getUserByuserName("testuser")).thenReturn(user);
//		when(authService.generateToken("testuser", "ROLE_USER")).thenReturn("test-token");
//
//		mockMvc.perform(post("/api/user/user-service/auth/token").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(authRequest))).andExpect(status().isOk());
//
//		verify(authenticationManager, times(1)).authenticate(any());
//		verify(userService, times(1)).getUserByuserName("testuser");
//		verify(authService, times(1)).generateToken("testuser", "ROLE_USER");
//		verifyNoMoreInteractions(authenticationManager, userService, authService);
//	}

	@Test
	public void testValidateToken() throws Exception {
		String token = "test-token";

		mockMvc.perform(get("/api/user/user-service/auth/validate").param("token", token)).andExpect(status().isOk());

		verify(authService, times(1)).validateToken(token);
		verifyNoMoreInteractions(authService);
	}
	
	
}

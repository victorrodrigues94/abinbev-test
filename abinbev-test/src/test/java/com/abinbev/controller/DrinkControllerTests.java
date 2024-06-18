package com.abinbev.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.abinbev.dto.DrinkRequestDto;
import com.abinbev.dto.DrinkResponseDto;
import com.abinbev.service.DrinkService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DrinkController.class)
@ActiveProfiles("test")
public class DrinkControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DrinkService drinkService;

	private DrinkRequestDto drinkRequestDto;
	private DrinkResponseDto drinkResponseDto;

	@BeforeEach
	void setUp() {
		drinkRequestDto = new DrinkRequestDto();
		drinkRequestDto.setNome("Stella");
		drinkRequestDto.setTipo("Cerveja");
		drinkRequestDto.setPreco(5.00);
		drinkRequestDto.setQuantidade(100);

		drinkResponseDto = new DrinkResponseDto();
		drinkResponseDto.setId(UUID.randomUUID().toString());
		drinkResponseDto.setNome("Stella");
		drinkResponseDto.setTipo("Cerveja");
		drinkResponseDto.setPreco(5.00);
		drinkResponseDto.setQuantidade(100);
	}

	@Test
	void testCreateDrink() throws Exception {
		when(drinkService.createDrink(any(DrinkRequestDto.class))).thenReturn(drinkResponseDto);

		mockMvc.perform(post("/bebidas").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(drinkRequestDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Stella"));

		verify(drinkService, times(1)).createDrink(any(DrinkRequestDto.class));
	}

}

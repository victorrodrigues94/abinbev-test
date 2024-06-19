package com.abinbev.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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

@ActiveProfiles("test")
@WebMvcTest(DrinkController.class)
public class DrinkControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DrinkService drinkService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Method: CreateDrink - Post Drink successfully")
	public void createDrinkSuccess() throws Exception {
		DrinkRequestDto requestDto = new DrinkRequestDto();
		requestDto.setNome("Stella");
		requestDto.setTipo("Cerveja");
		requestDto.setPreco(2.6);
		requestDto.setQuantidade(500);

		DrinkResponseDto responseDto = new DrinkResponseDto();
		responseDto.setId("1");
		responseDto.setNome("Stella");
		responseDto.setTipo("Cerveja");
		responseDto.setPreco(2.6);
		responseDto.setQuantidade(500);

		when(drinkService.createDrink(requestDto)).thenReturn(responseDto);

		mockMvc.perform(post("/bebidas").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.nome").value("Stella"))
				.andExpect(jsonPath("$.tipo").value("Cerveja")).andExpect(jsonPath("$.preco").value(2.6))
				.andExpect(jsonPath("$.quantidade").value(500));
	}

	@Test
	@DisplayName("Method: updateDrink - Patch Drink successfully")
	public void updateDrinkSuccess() throws Exception {
		DrinkRequestDto requestDto = new DrinkRequestDto();
		requestDto.setNome("H2oh!");
		requestDto.setTipo("Refrigerante");
		requestDto.setPreco(3.5);
		requestDto.setQuantidade(100);

		DrinkResponseDto responseDto = new DrinkResponseDto();
		responseDto.setId("1");
		responseDto.setNome("H2oh!");
		responseDto.setTipo("Refrigerante");
		responseDto.setPreco(3.5);
		responseDto.setQuantidade(100);

		when(drinkService.updateDrinkById("1", requestDto)).thenReturn(responseDto);

		mockMvc.perform(patch("/bebidas/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.nome").value("H2oh!"))
				.andExpect(jsonPath("$.tipo").value("Refrigerante")).andExpect(jsonPath("$.preco").value(3.5))
				.andExpect(jsonPath("$.quantidade").value(100));
	}

	@Test
	@DisplayName("Method: findDrinkByParams - Get Drinks successfully")
	public void findDrinkByParamsSuccess() throws Exception {
		DrinkResponseDto responseDto1 = new DrinkResponseDto();
		responseDto1.setId("1");
		responseDto1.setNome("H2oh!");
		responseDto1.setTipo("Refrigerante");
		responseDto1.setPreco(3.5);
		responseDto1.setQuantidade(100);

		List<DrinkResponseDto> responseDtoList = Arrays.asList(responseDto1);

		when(drinkService.findDrinkByParams("H2oh!", "Refrigerante")).thenReturn(responseDtoList);

		mockMvc.perform(get("/bebidas").param("nome", "H2oh!").param("tipo", "Refrigerante")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value("1")).andExpect(jsonPath("$[0].nome").value("H2oh!"))
				.andExpect(jsonPath("$[0].tipo").value("Refrigerante")).andExpect(jsonPath("$[0].preco").value(3.5))
				.andExpect(jsonPath("$[0].quantidade").value(100));

	}

}

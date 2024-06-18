package com.abinbev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.abinbev.dto.DrinkRequestDto;
import com.abinbev.dto.DrinkResponseDto;
import com.abinbev.messaging.MessagePublisher;
import com.abinbev.model.Drink;
import com.abinbev.repository.DrinkRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DrinkServiceTests {
	
	@InjectMocks
	private DrinkService drinkService;

	@Mock
	private DrinkRepository drinkRepository;
	
	@Mock
	private MessagePublisher messagePublisher;
	
	private Drink drink;
	private DrinkRequestDto drinkRequestDto;
	
	@BeforeEach
	void setUp() {
		drink = new Drink();
		drink.setId(UUID.randomUUID().toString());
		drink.setNome("Stella");
		drink.setTipo("Cerveja");
		drink.setPreco(5.00);
		drink.setQuantidade(100);

		drinkRequestDto = new DrinkRequestDto();
		drinkRequestDto.setNome("Stella");
		drinkRequestDto.setTipo("Cerveja");
		drinkRequestDto.setPreco(5.00);
		drinkRequestDto.setQuantidade(100);
	}
	
	@Test
	void testCreateDrink() {
		when(drinkRepository.save(any(Drink.class))).thenReturn(drink);
		
		DrinkResponseDto drinkResposeDTO = drinkService.createDrink(drinkRequestDto);
		
		assertNotNull(drinkResposeDTO);
		assertEquals(drink.getNome(), drinkResposeDTO.getNome());
		
		verify(drinkRepository, times(1)).save(any(Drink.class));
        verify(messagePublisher, times(1)).publishMessage(anyString());
	}
}

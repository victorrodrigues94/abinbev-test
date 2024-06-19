package com.abinbev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

	@Mock
	private DrinkRepository drinkRepository;

	@Mock
	private MessagePublisher messagePublisher;

	@InjectMocks
	private DrinkService drinkService;

	private DrinkRequestDto drinkRequestDto;
	private Drink drink;
	private DrinkResponseDto drinkResponseDto;

	@BeforeEach
	void setup() {
		drinkRequestDto = new DrinkRequestDto();
		drinkRequestDto.setNome("Stella");
		drinkRequestDto.setTipo("Cerveja");
		drinkRequestDto.setPreco(2.6);
		drinkRequestDto.setQuantidade(500);

		drink = new Drink();
		drink.setId("1");
		drink.setNome("Stella");
		drink.setTipo("Cerveja");
		drink.setPreco(2.6);
		drink.setQuantidade(500);

		drinkResponseDto = new DrinkResponseDto(drink);
	}

	@Test
	@DisplayName("Method: createDrink - create Drink service successfully")
	void createDrinkSuccess() {
		doAnswer(invocation -> {
			Drink savedDrink = invocation.getArgument(0);
			savedDrink.setId("1");
			return savedDrink;
		}).when(drinkRepository).save(any(Drink.class));

		DrinkResponseDto result = drinkService.createDrink(drinkRequestDto);

		assertEquals(drinkResponseDto, result);
		verify(drinkRepository).save(any(Drink.class));
		verify(messagePublisher).publishMessage(any(String.class));
	}
	
	@Test
	@DisplayName("Method: updateDrinkById - update Drink service successfully")
    void updateDrinkByIdSuccess() {
        when(drinkRepository.findById("1")).thenReturn(Optional.of(drink));
        when(drinkRepository.save(any(Drink.class))).thenReturn(drink);

        DrinkResponseDto result = drinkService.updateDrinkById("1", drinkRequestDto);

        assertEquals(drinkResponseDto, result);
        verify(drinkRepository).findById("1");
        verify(drinkRepository).save(any(Drink.class));
    }
	
	@Test
	@DisplayName("Method: findDrinkByParams - find Drink service successfully")
    void findDrinkByParamsSuccess() {
        when(drinkRepository.findAll()).thenReturn(Arrays.asList(drink));

        List<DrinkResponseDto> result = drinkService.findDrinkByParams(null, null);

        assertEquals(1, result.size());
        assertEquals(drinkResponseDto, result.get(0));
        verify(drinkRepository).findAll();
    }
}

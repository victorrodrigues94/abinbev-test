package com.abinbev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abinbev.dto.DrinkRequestDto;
import com.abinbev.dto.DrinkResponseDto;
import com.abinbev.exception.ResourceNotFoundException;
import com.abinbev.messaging.MessagePublisher;
import com.abinbev.model.Drink;
import com.abinbev.repository.DrinkRepository;

@Service
public class DrinkService {

	private final DrinkRepository drinkRepository;
	private final MessagePublisher messagePublisher;

	public DrinkService(DrinkRepository drinkRepository, MessagePublisher messagePublisher) {
		this.drinkRepository = drinkRepository;
		this.messagePublisher = messagePublisher;
	}

	public DrinkResponseDto createDrink(DrinkRequestDto drinkRequestDto) {
		Drink drink = new Drink();
		drink.setNome(drinkRequestDto.getNome());
		drink.setTipo(drinkRequestDto.getTipo());
		drink.setPreco(drinkRequestDto.getPreco());
		drink.setQuantidade(drinkRequestDto.getQuantidade());

		drinkRepository.save(drink);

		String message = String.format(
				"\n  ** Nova bebida cadastrada ** - \n ID: %s; \n Nome: %s; \n Tipo: %s; \n Preço: %s; \n Quantidade: %s \n",
				drink.getId(), drink.getNome(), drink.getTipo(), drink.getPreco(), drink.getQuantidade());
		messagePublisher.publishMessage(message);

		return new DrinkResponseDto(drink);
	}

	public DrinkResponseDto updateDrinkById(String id, DrinkRequestDto drinkRequestDto) {
		Drink drink = drinkRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		if (drinkRequestDto.getNome() != null) {
			drink.setNome(drinkRequestDto.getNome());
		}
		if (drinkRequestDto.getTipo() != null) {
			drink.setTipo(drinkRequestDto.getTipo());
		}
		if (drinkRequestDto.getPreco() != null) {
			drink.setPreco(drinkRequestDto.getPreco());
		}

		drink = drinkRepository.save(drink);
		return new DrinkResponseDto(drink);
	}

	public List<DrinkResponseDto> findDrinkByParams(String nome, String tipo) {
		List<Drink> drink;
		if (nome != null && tipo != null) {
			drink = drinkRepository.findByNomeAndTipoRegex(nome, tipo);
		} else if (nome != null) {
			drink = drinkRepository.findByNomeRegex(nome);
		} else if (tipo != null) {
			drink = drinkRepository.findByTipoRegex(tipo);
		} else {
			drink = drinkRepository.findAll();
		}

		return drink.stream().map(DrinkResponseDto::new).collect(Collectors.toList());

	}
}

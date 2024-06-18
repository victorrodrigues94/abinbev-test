package com.abinbev.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abinbev.dto.DrinkRequestDto;
import com.abinbev.dto.DrinkResponseDto;
import com.abinbev.service.DrinkService;

@Controller
@RequestMapping("/bebidas")
public class DrinkController {

	private final DrinkService drinkService;

	public DrinkController(DrinkService drinkService) {
		this.drinkService = drinkService;
	}

	@PostMapping
	public ResponseEntity<DrinkResponseDto> createDrink(@RequestBody DrinkRequestDto drinkRequestDto) {
		DrinkResponseDto drinkResponse = drinkService.createDrink(drinkRequestDto);
		return ResponseEntity.ok(drinkResponse);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<DrinkResponseDto> updateDrink(@PathVariable String id,
			@RequestBody DrinkRequestDto drinkRequestDto) {
		DrinkResponseDto drink = drinkService.updateDrinkById(id, drinkRequestDto);
		return ResponseEntity.ok(drink);
	}

	@GetMapping
	public ResponseEntity<List<DrinkResponseDto>> findDrinkByParams(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String tipo) {
		List<DrinkResponseDto> drinks = drinkService.findDrinkByParams(nome, tipo);
		return ResponseEntity.ok(drinks);
	}

}

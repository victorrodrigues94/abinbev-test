package com.abinbev.dto;

import com.abinbev.model.Drink;

import lombok.Data;

@Data
public class DrinkResponseDto {
	
	private String id;
	private String nome;
	private String tipo;
	private Double preco;
	private int quantidade;
	
	public DrinkResponseDto(Drink drink) {
		super();
		this.id = drink.getId();
		this.nome = drink.getNome();
		this.tipo = drink.getTipo();
		this.preco = drink.getPreco();
		this.quantidade = drink.getQuantidade();
	}
}

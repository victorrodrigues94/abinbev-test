package com.abinbev.dto;

import lombok.Data;

@Data
public class DrinkRequestDto {
	private String nome;
	private String tipo;
	private Double preco;
	private int quantidade;

}

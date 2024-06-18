package com.abinbev.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "drink")
public class Drink {

	@Id
	private String id;
	private String nome;
    private String tipo;
    private Double preco;
    private Integer quantidade;
	
}

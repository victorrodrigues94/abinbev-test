package com.abinbev.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.abinbev.model.Drink;

@Repository
public interface DrinkRepository extends MongoRepository<Drink,String> {
	
	@Query("{ 'nome': { $regex: ?0, $options: 'i' } }")
    List<Drink> findByNomeRegex(String nome);
    
    @Query("{ 'tipo': { $regex: ?0, $options: 'i' } }")
    List<Drink> findByTipoRegex(String tipo);
    
    @Query("{ 'nome': { $regex: ?0, $options: 'i' }, 'tipo': { $regex: ?1, $options: 'i' } }")
    List<Drink> findByNomeAndTipoRegex(String nome, String tipo);

}

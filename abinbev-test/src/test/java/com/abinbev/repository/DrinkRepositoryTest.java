package com.abinbev.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.abinbev.config.EmbeddedMongoConfig;
import com.abinbev.model.Drink;

@DataMongoTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(EmbeddedMongoConfig.class)
public class DrinkRepositoryTest {

	@Autowired
	private DrinkRepository drinkRepository;

	@Test
	@DisplayName("Method: findByNome - Get Drink by nome successfully from DB")
	void findByNomeSuccess() {
		Drink drink = createDrink();
		drinkRepository.save(drink);

		List<Drink> drinks = this.drinkRepository.findByNome(drink.getNome());

		assertThat(drinks.isEmpty()).isFalse();
		assertThat(drinks.get(0).getNome()).isEqualTo(drink.getNome());
	}

	@Test
	@DisplayName("Method: findByNome - Get Drink by nome ERROR from DB")
	void findByNomeError() {
		drinkRepository.save(createDrink());

		List<Drink> drinks = this.drinkRepository.findByNome("Brahma");

		assertThat(drinks.isEmpty()).isTrue();
	}

	@Test
	@DisplayName("Method: findByTipo - Get Drink by tipo successfully from DB")
	void findByTipoSuccess() {
		Drink drink = createDrink();
		drinkRepository.save(drink);

		List<Drink> drinks = this.drinkRepository.findByTipo(drink.getTipo());

		assertThat(drinks.isEmpty()).isFalse();
		assertThat(drinks.get(0).getTipo()).isEqualTo(drink.getTipo());
	}

	@Test
	@DisplayName("Method: findByTipo - Get Drink by tipo ERROR from DB")
	void findByTypoError() {
		drinkRepository.save(createDrink());

		List<Drink> drinks = this.drinkRepository.findByTipo("Refrigerante");

		assertThat(drinks.isEmpty()).isTrue();
	}

	@Test
	@DisplayName("Method: findByNomeAndTipo - Get Drink by nome and tipo successfully from DB")
	void findByNomeAndTipoSuccess() {
		Drink drink = createDrink();
		drinkRepository.save(drink);

		List<Drink> drinks = this.drinkRepository.findByNomeAndTipo(drink.getNome(), drink.getTipo());

		assertThat(drinks.isEmpty()).isFalse();
		assertThat(drinks.get(0).getNome()).isEqualTo(drink.getNome());
		assertThat(drinks.get(0).getTipo()).isEqualTo(drink.getTipo());
	}

	@Test
	@DisplayName("Method: findByNomeAndTipo - Get Drink by nome and tipo ERROR from DB")
	void findByNomeAndTipoError() {
		drinkRepository.save(createDrink());

		List<Drink> drinks = this.drinkRepository.findByNome("Brahma");

		assertThat(drinks.isEmpty()).isTrue();
	}

	private Drink createDrink() {
		Drink drink = new Drink();
		drink.setNome("Stella");
		drink.setTipo("Cerveja");
		drink.setPreco(2.65);
		drink.setQuantidade(100);
		return drink;

	}
}

package com.abinbev.config;

import java.io.IOException;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;

@TestConfiguration
@EnableMongoRepositories(basePackages = "com.abinbev.repository")
public class EmbeddedMongoConfig {

	private MongodExecutable mongodExecutable;

	@Bean
	MongoTemplate mongoTemplate() throws IOException {
		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(MongodConfig.builder().version(Version.Main.DEVELOPMENT)
				.net(new Net("localhost", 27017, false)).build());
		mongodExecutable.start();

		return new MongoTemplate(
				new SimpleMongoClientDatabaseFactory(MongoClients.create("mongodb://localhost:27017"), "testdb"));
	}

	public void stopMongoExecutable() {
		if (mongodExecutable != null) {
			mongodExecutable.stop();
		}
	}
}

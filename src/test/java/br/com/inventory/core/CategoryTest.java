package br.com.inventory.core;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

class CategoryTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	private static Category category;

	@BeforeAll
	public static void setup() {
		CategoryTest.category = new Category();
		category.setId(152L);
		category.setName("Cadernos e Agendas");
		category.setDescription("Cadernos universitários, cartografia, fichários, etc.");
	}

	@Test
	public void serializesToJSON() throws Exception {
		final String expected = MAPPER
				.writeValueAsString(MAPPER.readValue(fixture("fixtures/category.json"), Category.class));

		assertThat(MAPPER.writeValueAsString(category)).isEqualTo(expected);
	}

	@Test
	public void deserializesFromJSON() throws Exception {
		assertThat(MAPPER.readValue(fixture("fixtures/category.json"), Category.class)).isEqualTo(category);
	}

}

package br.com.inventory.core;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

class ProductTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private static Product product;
	
	@BeforeAll
	public static void setup() {
		ProductTest.product = new Product();
		product.setBarcode("1234567891011121314151617");
		product.setName("Caderno 96 fls");
		product.setDescription("Caderno com espiral de 96 fls");
		product.setQuantity(50);
		final Category category = new Category();
		category.setId(152L);
		category.setName("Cadernos e Agendas");
		category.setDescription("Cadernos universitários, cartografia, fichários, etc.");
		product.setCategory(category);
	}

	@Test
	public void serializesToJSON() throws Exception {
		final String expected = MAPPER
				.writeValueAsString(MAPPER.readValue(fixture("fixtures/product.json"), Product.class));

		assertThat(MAPPER.writeValueAsString(product)).isEqualTo(expected);
	}
	
	@Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/product.json"), Product.class))
                .isEqualTo(product);
    }

}

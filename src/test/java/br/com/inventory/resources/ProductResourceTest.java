package br.com.inventory.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import br.com.inventory.core.Category;
import br.com.inventory.core.Product;
import br.com.inventory.db.ProductDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ProductResourceTest {
	private static final String PRODUCT_ENDPOINT = "/product";

	private static final ProductDao PRODUCT_DAO = mock(ProductDao.class);
	public static final ResourceExtension RESOURCES = ResourceExtension.builder()
			.addResource(new ProductResource(PRODUCT_DAO)).build();
	private ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
	private Product product;

	@BeforeEach
	public void setUp() {
		final Category category = new Category(50L, "category 1", "desc 1");
		product = new Product("123123123123123123123123", "name 1", "desc 1", 10, category);
	}

	@AfterEach
	public void tearDown() {
		reset(PRODUCT_DAO);
	}

	@Test
	public void createProduct() {
		when(PRODUCT_DAO.create(any(Product.class))).thenReturn(product);
		final Response response = RESOURCES.target(PRODUCT_ENDPOINT).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(product, MediaType.APPLICATION_JSON_TYPE));

		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
		verify(PRODUCT_DAO).create(productCaptor.capture());
		assertThat(productCaptor.getValue()).isEqualTo(product);
	}

	@Test
	public void createProductWithoutCategory() {
		product.setCategory(null);

		final Response response = RESOURCES.target(PRODUCT_ENDPOINT).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(product, MediaType.APPLICATION_JSON_TYPE));

		assertThat(response.getStatusInfo()).isNotEqualTo(Response.Status.OK);
		String readEntity = response.readEntity(String.class);
		assertThat(readEntity).contains("must not be null");
	}

	@Test
	public void findAllProducts() throws Exception {
		final List<Product> products = Collections.singletonList(product);
		when(PRODUCT_DAO.findAll()).thenReturn(products);

		final List<Product> response = RESOURCES.target(PRODUCT_ENDPOINT).request()
				.get(new GenericType<List<Product>>() {
				});

		verify(PRODUCT_DAO).findAll();
		assertThat(response).containsAll(products);
	}

}

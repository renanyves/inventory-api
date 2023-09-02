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
import br.com.inventory.db.CategoryDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
class CategoryResourceTest {

	public static final String CATEGORY_ENDPOINT = "/category";

	private static final CategoryDao CATEGORY_DAO = mock(CategoryDao.class);
	public static final ResourceExtension RESOURCES = ResourceExtension.builder()
			.addResource(new CategoryResource(CATEGORY_DAO)).build();
	private ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
	private Category category;

	@BeforeEach
	public void setUp() {
		category = new Category();
		category.setName("category 1");
		category.setDescription("desc 1");
	}

	@AfterEach
	public void tearDown() {
		reset(CATEGORY_DAO);
	}

	@Test
	public void createCategory() {
		when(CATEGORY_DAO.create(any(Category.class))).thenReturn(category);
		final Response response = RESOURCES.target(CATEGORY_ENDPOINT).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(category, MediaType.APPLICATION_JSON_TYPE));

		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
		verify(CATEGORY_DAO).create(categoryCaptor.capture());
		assertThat(categoryCaptor.getValue()).isEqualTo(category);
	}

	@Test
	public void listCategory() throws Exception {
		final List<Category> expected = Collections.singletonList(category);
		when(CATEGORY_DAO.findAll()).thenReturn(expected);

		List<Category> response = RESOURCES.target(CATEGORY_ENDPOINT).request()
				.get(new GenericType<List<Category>>() {
				});

		verify(CATEGORY_DAO).findAll();

		assertThat(response).containsAll(expected);
	}

}

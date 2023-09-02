package br.com.inventory.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.inventory.core.Category;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CategoryDaoTest {

	public static final String CATEGORY_NAME = "Outros papéis";
	public static final String CATEGORY_DESCRIPTION = "papéis diversos";

	private static final DAOTestExtension daoTestExtension = DAOTestExtension.newBuilder()
			.addEntityClass(Category.class).build();

	private static final CategoryDao categoryDao = new CategoryDao(daoTestExtension.getSessionFactory());

	@BeforeAll
	public static void setup() {
		daoTestExtension.inTransaction(() -> {
			Category category = new Category();
			category.setName(CATEGORY_NAME);
			category.setDescription(CATEGORY_DESCRIPTION);
			return categoryDao.create(category);
		});
	}
	
	@Test
	public void create() {
		final String name = "category name";
		final String desc = "category desc";
		Category wizard = daoTestExtension.inTransaction(() -> {
			Category category = new Category();
			category.setName(name);
			category.setDescription(desc);
			return categoryDao.create(category);
		});
		assertThat(wizard.getId()).isGreaterThan(0);
		assertThat(wizard.getName()).isEqualTo(name);
		assertThat(wizard.getDescription()).isEqualTo(desc);
	}

	@Test
	public void findAll() {
		List<Category> findAll = categoryDao.findAll();
		assertThat(findAll.stream().anyMatch(c -> CATEGORY_NAME.equals(c.getName())));
	}

	@Test
	public void findByName() {
		List<Category> categoryList = categoryDao.findByName(CATEGORY_NAME);
		assertThat(categoryList.stream().anyMatch(c -> CATEGORY_NAME.equals(c.getName())));
	}

	@Test
	public void delete() {
		Category category = daoTestExtension.inTransaction(() -> {
			Category c = new Category();
			c.setName("category name 123");
			c.setDescription("category desc 123");
			return categoryDao.create(c);
		});

		categoryDao.delete(category.getId());
	}

	@Test
	public void update() {
		final List<Category> categoryList = categoryDao.findByName(CATEGORY_NAME);
		final Category category = categoryList.get(0);
		final String newDesc = "New desc";
		category.setDescription(newDesc);

		daoTestExtension.inTransaction(() -> categoryDao.update(category));

		final Category newCategory = categoryDao.findById(category.getId());
		assertThat(newCategory.getId()).isEqualTo(category.getId());
		assertThat(newCategory.getName()).isEqualTo(CATEGORY_NAME);
		assertThat(newCategory.getDescription()).isEqualTo(newDesc);
	}

}

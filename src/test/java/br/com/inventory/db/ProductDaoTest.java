package br.com.inventory.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.inventory.core.Category;
import br.com.inventory.core.Product;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ProductDaoTest {

	public static final String PRODUCT_BARCODE = "123123123123123123123123";
	public static final String PRODUCT_NAME = "Product Name";
	public static final String PRODUCT_DESCRIPTION = "Product description";
	public static final Integer PRODUCT_QUANTITY = 10;
	public static final Long PRODUCT_CATEGORY_ID = 5L;

	@Rule
	private static final DAOTestExtension daoTestExtension = DAOTestExtension.newBuilder().addEntityClass(Product.class)
			.addEntityClass(Category.class).build();

	private static final ProductDao productDao = new ProductDao(daoTestExtension.getSessionFactory());
	private static final CategoryDao categoryDao = new CategoryDao(daoTestExtension.getSessionFactory());

	@BeforeAll
	public static void setUp() throws Exception {

		Category category = daoTestExtension.inTransaction(() -> {
			Category c = new Category();
			c.setName(CategoryDaoTest.CATEGORY_NAME);
			c.setDescription(CategoryDaoTest.CATEGORY_DESCRIPTION);
			return categoryDao.create(c);
		});

		daoTestExtension.inTransaction(() -> {
			Product product = new Product(PRODUCT_BARCODE, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_QUANTITY,
					category);
			return productDao.create(product);
		});
	}

	@Test
	public void create() {
		final String categoryName = "name1";
		final String categoryDesc = "desc1";
		Category category = daoTestExtension.inTransaction(() -> {
			Category c = new Category(categoryName, categoryDesc);
			return categoryDao.create(c);
		});

		final String productBarcode = "0123456789";
		final String productName = "name1";
		final String productDesc = "desc1";
		final Integer productQt = 10;

		Product persistedProduct = daoTestExtension.inTransaction(() -> {
			Product product = new Product(productBarcode, productName, productDesc, productQt, category);
			return productDao.create(product);
		});
		assertThat(persistedProduct.getBarcode()).isEqualTo(productBarcode);
		assertThat(persistedProduct.getName()).isEqualTo(productName);
		assertThat(persistedProduct.getDescription()).isEqualTo(productDesc);
		assertThat(persistedProduct.getQuantity()).isEqualTo(productQt);
		assertThat(persistedProduct.getCategory().getId()).isEqualTo(category.getId());
		assertThat(persistedProduct.getCategory().getName()).isEqualTo(categoryName);
		assertThat(persistedProduct.getCategory().getDescription()).isEqualTo(categoryDesc);
	}

	@Test
	public void findAll() {
		List<Product> productList = productDao.findAll();
		assertThat(productList.size() > 0);
		assertThat(productList.get(0).getName().equals(PRODUCT_NAME));
	}

	@Test
	public void findByName() {
		List<Product> productList = productDao.findByName(PRODUCT_NAME);
		assertThat(productList.size() > 0);
		assertThat(PRODUCT_NAME.equals(productList.get(0).getName()));
	}

	@Test
	public void findById() {
		Product product = productDao.findById(PRODUCT_BARCODE);
		assertThat(product.getBarcode().equals(PRODUCT_BARCODE));
		assertThat(product.getName().equals(PRODUCT_NAME));
		assertThat(product.getDescription().equals(PRODUCT_DESCRIPTION));
	}

	@Test
	public void delete() {
		Category category = daoTestExtension.inTransaction(() -> {
			Category c = new Category("name2", "desc2");
			return categoryDao.create(c);
		});

		final String productBarcode = "1234567890";
		final String productName = "name1";
		final String productDesc = "desc1";
		final Integer productQt = 10;

		Product persistedProduct = daoTestExtension.inTransaction(() -> {
			Product product = new Product(productBarcode, productName, productDesc, productQt, category);
			return productDao.create(product);
		});

		productDao.delete(persistedProduct);
	}

	@Test
	public void update() {
		final Product product = productDao.findById(PRODUCT_BARCODE);
		final String newDescription = "New Description";
		product.setDescription(newDescription);

		daoTestExtension.inTransaction(() -> productDao.update(product));

		final Product newProduct = productDao.findById(PRODUCT_BARCODE);
		assertThat(newProduct.getName()).isEqualTo(PRODUCT_NAME);
		assertThat(newProduct.getDescription()).isEqualTo(newDescription);
	}

}

package br.com.inventory;

import org.hibernate.SessionFactory;

import br.com.inventory.core.Category;
import br.com.inventory.core.Product;
import br.com.inventory.db.CategoryDao;
import br.com.inventory.db.ProductDao;
import br.com.inventory.resources.CategoryResource;
import br.com.inventory.resources.ProductResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class InventoryApplication extends Application<InventoryConfiguration> {

	public static void main(final String[] args) throws Exception {
		new InventoryApplication().run(args);
	}

	@Override
	public String getName() {
		return "Inventory";
	}

	@Override
	public void initialize(final Bootstrap<InventoryConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(final InventoryConfiguration configuration, final Environment environment) {
		final SessionFactory sessionFactory = hibernate.getSessionFactory();
		final ProductDao productDao = new ProductDao(sessionFactory);
		final CategoryDao categoryDao = new CategoryDao(sessionFactory);

		environment.jersey().register(new ProductResource(productDao));
		environment.jersey().register(new CategoryResource(categoryDao));

	}

	private final HibernateBundle<InventoryConfiguration> hibernate = new HibernateBundle<InventoryConfiguration>(
			Product.class, Category.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(InventoryConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

}

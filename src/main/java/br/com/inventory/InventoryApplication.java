package br.com.inventory;

import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_HEADERS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_METHODS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_ORIGINS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOW_CREDENTIALS_PARAM;

import java.util.EnumSet;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.hibernate.SessionFactory;

import br.com.inventory.core.Category;
import br.com.inventory.core.Product;
import br.com.inventory.db.CategoryDao;
import br.com.inventory.db.ProductDao;
import br.com.inventory.resources.CategoryResource;
import br.com.inventory.resources.ProductResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;

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
		environment.getApplicationContext().setContextPath("/");

		configureCors(configuration, environment);
		
		environment.jersey().register(new ProductResource(productDao));
		environment.jersey().register(new CategoryResource(categoryDao));

		environment.jersey().setUrlPattern("/api/*");

	}

	private final HibernateBundle<InventoryConfiguration> hibernate = new HibernateBundle<InventoryConfiguration>(
			Product.class, Category.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(InventoryConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	private void configureCors(InventoryConfiguration configuration, final Environment environment) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*", "/api/*");
		// Configure CORS parameters
		cors.setInitParameter(ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
		final String allowedOrigins = String.join(",", configuration.getAllowedOrigins());
		cors.setInitParameter(ALLOWED_ORIGINS_PARAM, allowedOrigins);
		cors.setInitParameter(ALLOWED_HEADERS_PARAM, "*");
		cors.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");

		// DO NOT pass a preflight request to down-stream auth filters
		// unauthenticated preflight requests should be permitted by spec
		cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());
	}

}

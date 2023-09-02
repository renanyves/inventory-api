package br.com.inventory.db;

import java.util.List;

import org.hibernate.SessionFactory;

import br.com.inventory.core.Category;
import br.com.inventory.core.Product;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductDao extends AbstractDAO<Product> {

	public ProductDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Product findById(String barcode) {
		return get(barcode);
	}

	public List<Product> findByName(String name) {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Product> query = cb.createQuery(Product.class);
		final Root<Product> from = query.from(Product.class);

		query.where(cb.like(from.get("name"), cb.parameter(String.class, "nameParam")));

		return currentSession().createQuery(query).setParameter("nameParam", "%" + name + "%").list();
	}

	public List<Product> findByCategory(final Category category) {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Product> query = cb.createQuery(Product.class);
		final Root<Product> from = query.from(Product.class);

		query.where(cb.equal(from.get("category").get("id"), cb.parameter(Long.class, "categoryId")));

		return currentSession().createQuery(query).setParameter("categoryId", category.getId()).list();
	}

	public List<Product> findAll() {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Product> query = cb.createQuery(Product.class);
		final Root<Product> from = query.from(Product.class);

		query.orderBy(cb.desc(from.get("barcode")));

		return list(query);
	}

	public void delete(String barcode) {
		currentSession().remove(get(barcode));
	}

	public void update(Product product) {
		currentSession().merge(product);
	}

	public Product create(Product product) {
		return persist(product);
	}

}

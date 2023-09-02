package br.com.inventory.db;

import java.util.List;

import org.hibernate.SessionFactory;

import br.com.inventory.core.Category;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CategoryDao extends AbstractDAO<Category> {

	public CategoryDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Category findById(Long id) {
		return get(id);
	}

	public List<Category> findByName(String name) {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Category> query = cb.createQuery(Category.class);
		final Root<Category> from = query.from(Category.class);

		query.where(cb.like(from.get("name"), cb.parameter(String.class, "nameParam")));

		return currentSession().createQuery(query).setParameter("nameParam", '%' + name + '%').list();
	}

	public List<Category> findAll() {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Category> query = cb.createQuery(Category.class);
		final Root<Category> from = query.from(Category.class);

		query.orderBy(cb.asc(from.get("id")));

		return list(query);
	}

	public void delete(Long id) {
		currentSession().remove(get(id));
	}

	public void update(Category category) {
		currentSession().merge(category);
	}

	public Category create(Category category) {
		return persist(category);
	}

}

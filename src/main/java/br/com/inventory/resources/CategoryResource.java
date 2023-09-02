package br.com.inventory.resources;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import br.com.inventory.core.Category;
import br.com.inventory.db.CategoryDao;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/category")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class CategoryResource {

	private final static Logger logger = LoggerFactory.getLogger(CategoryResource.class);

	private CategoryDao dao;

	public CategoryResource(CategoryDao productDao) {
		this.dao = productDao;
	}

	@GET
	@Timed
	@UnitOfWork
	public List<Category> findAll(@QueryParam("name") Optional<String> name) {
		return name.isPresent() ? dao.findByName(name.get()) : dao.findAll();
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/{id}")
	public Category findById(@PathParam("id") Long id) {
		logger.debug("findById :: {} ", id);
		return dao.findById(id);
	}

	@POST
	@Timed
	@UnitOfWork
	public Category create(@Valid Category category) {
		logger.debug("create :: {} ", category);
		return dao.create(category);
	}

	@PUT
	@Timed
	@UnitOfWork
	public void update(@Valid Category category) {
		dao.update(category);
	}

	@DELETE
	@Timed
	@UnitOfWork
	@Path("/{id}")
	public void delete(@PathParam("id") @NotNull Long id) {
		dao.delete(id);
	}

}

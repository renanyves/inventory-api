package br.com.inventory.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import br.com.inventory.core.Category;
import br.com.inventory.db.CategoryDao;
import io.dropwizard.hibernate.UnitOfWork;

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
	@Path("/{barcode}")
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

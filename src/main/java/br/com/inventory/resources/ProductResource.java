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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;

import br.com.inventory.core.Product;
import br.com.inventory.db.ProductDao;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/product")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ProductResource {

	private ProductDao dao;

	public ProductResource(ProductDao productDao) {
		this.dao = productDao;
	}

	@GET
	@Timed
	@UnitOfWork
	public List<Product> findAll(@QueryParam("name") Optional<String> name) {
		return name.isPresent() ? dao.findByName(name.get()) : dao.findAll();
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/{barcode}")
	public Product findById(@PathParam("barcode") String barcode) {
		return dao.findById(barcode);
	}

	@POST
	@Timed
	@UnitOfWork
	public Product create(@Valid Product product) {
		Product findById = dao.findById(product.getBarcode());
		if (findById == null) {
			return dao.create(product);
		} else {
			throw new WebApplicationException("Barcode already exists.", Status.SEE_OTHER);
		}
	}

	@PUT
	@Timed
	@UnitOfWork
	public void update(@Valid Product product) {
		dao.update(product);
	}

	@DELETE
	@Timed
	@UnitOfWork
	@Path("/{barcode}")
	public void deleteById(@PathParam("barcode") @NotNull String barcode) {
		dao.delete(barcode);
	}

}

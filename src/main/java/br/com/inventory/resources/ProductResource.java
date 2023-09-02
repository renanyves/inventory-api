package br.com.inventory.resources;

import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;

import br.com.inventory.core.Product;
import br.com.inventory.db.ProductDao;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

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

package br.com.inventory;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class InventoryConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();

	@NotEmpty
	@JsonProperty("allowedOrigins")
	private List<String> allowedOrigins;

	public DataSourceFactory getDataSourceFactory() {
		return database;
	}
	
	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}

}

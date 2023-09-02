package br.com.inventory;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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

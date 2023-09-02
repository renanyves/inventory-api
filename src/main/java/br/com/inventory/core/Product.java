package br.com.inventory.core;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {

	public Product() {
		super();
	}

	public Product(String barcode, String name, String description, Integer quantity, Category category) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.category = category;
	}

	@Id
	@Column(length = 25, nullable = false)
	@NotNull
	private String barcode;

	@Column(length = 100, nullable = false)
	@NotNull
	private String name;

	@Column(length = 300, nullable = false)
	@NotNull
	private String description;

	@Column(nullable = false)
	@NotNull
	private Integer quantity;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY"))
	@NotNull
	private Category category;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(barcode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		return Objects.equals(barcode, other.barcode);
	}

	@Override
	public String toString() {
		return "Product [barcode=" + barcode + ", name=" + name + ", description=" + description + ", quantity="
				+ quantity + ", category=" + category + "]";
	}

}

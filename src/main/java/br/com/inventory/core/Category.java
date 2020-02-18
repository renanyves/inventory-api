package br.com.inventory.core;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Category {

	private static final String CATEGORY_ID_GENERATOR = "category_id_generator";

	public Category() {
		super();
	}

	public Category(Long id) {
		super();
		this.id = id;
	}

	public Category(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Category(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Id
	@Column(updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = CATEGORY_ID_GENERATOR)
	@SequenceGenerator(name = CATEGORY_ID_GENERATOR, sequenceName = "category_seq", allocationSize = 1)
	private Long id;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	@Column(length = 200, nullable = true)
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Category))
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
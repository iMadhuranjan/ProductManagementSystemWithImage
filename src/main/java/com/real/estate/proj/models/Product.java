package com.real.estate.proj.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message="The Name is Required")
	private String name;
	
	@NotEmpty(message="The Brand is Required")
	private String brand;
	
	@NotEmpty(message="The Category is Required")
	private List<String> category;
	
	@Min(0)
	private double price;
	
	@Column(columnDefinition = "TEXT")
	@Size(min=10, message="Description should me More than 10 Character")
	@Size(max=2000, message="Description should not exced more than 2000 Character")
	private String description;
	private Date createAt;
	private String imageFileName;
	
	
	public Product() {
		
	}
	
	public Product( String name, String brand, List<String> category, double price, String description, Date createAt,
			String imageFileName) {
		super();
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.price = price;
		this.description = description;
		this.createAt = createAt;
		this.imageFileName = imageFileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", category=" + category + ", price="
				+ price + ", description=" + description + ", createAt=" + createAt + ", imageFileName=" + imageFileName
				+ "]";
	}
	
	
}

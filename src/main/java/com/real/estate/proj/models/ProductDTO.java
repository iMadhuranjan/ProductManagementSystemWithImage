package com.real.estate.proj.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class ProductDTO {

	@NotEmpty(message="The Name is Required")
	private String name;
	
	@NotEmpty(message="The Brand is Required")
	private String brand;
	
	@NotEmpty(message="The Category is Required")
	private List<String> category;
	
	@Min(0)
	private double price;
	
	@Size(min=10, message="Description should me More than 10 Character")
	@Size(max=2000, message="Description should not exced more than 2000 Character")
	private String description;
	
	private MultipartFile imageFileName;

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

	public MultipartFile getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(MultipartFile imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	
}

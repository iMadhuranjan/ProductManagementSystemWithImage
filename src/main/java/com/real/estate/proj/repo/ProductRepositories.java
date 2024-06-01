package com.real.estate.proj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.estate.proj.models.Product;

public interface ProductRepositories extends JpaRepository<Product, Integer>{

}

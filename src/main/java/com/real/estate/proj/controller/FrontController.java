package com.real.estate.proj.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.real.estate.proj.models.Product;
import com.real.estate.proj.models.ProductDTO;
import com.real.estate.proj.repo.ProductRepositories;

import jakarta.validation.Valid;



@Controller
public class FrontController {

	@Autowired
	private ProductRepositories repositories;
	
	public FrontController(ProductRepositories re) {
		this.repositories = re;
	}
	
	@Value("${category}")
	private List<String> category;
	
	@GetMapping("/products")
	public String HomePage(Model model) {
		List<Product> product = repositories.findAll();
		model.addAttribute("product", product);
		return "products";
	}
	
	@GetMapping("/create")
	public String CreateProduct(Model model) {
		model.addAttribute("category", category );
		model.addAttribute("productDTO", new ProductDTO());
		return "createproduct";
	}
	
	@PostMapping("/prodcutProcess")
	public String create(@Valid @ModelAttribute ProductDTO productDTO, BindingResult result) {
		
		if (productDTO.getImageFileName() != null && productDTO.getImageFileName().isEmpty()) {
	        result.addError(new FieldError("productDTO", "imageFileName", "The Image File is Required"));
	    }
		
		if(result.hasErrors()) {
			return "redirect:/create";
		}
		
		
		// Save Image File on Server 
		
		MultipartFile image= productDTO.getImageFileName();
		Date createAt = new  Date();
		String  StorageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
		
		System.out.println(StorageFileName);
		
		try {
			String uploadDir = "public/img";
			Path uploadPath=Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try(InputStream inputstream = image.getInputStream()){
				Files.copy(inputstream, Paths.get(uploadPath + StorageFileName), 
						StandardCopyOption.REPLACE_EXISTING);
			}
			
		}catch(Exception e){
			
		}
		
		Product product = new Product();
		
		product.setName(productDTO.getName());
		product.setBrand(productDTO.getBrand());
		product.setCategory(productDTO.getCategory());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setCreateAt(createAt);
		product.setImageFileName(StorageFileName);
		
		repositories.save(product);
		return "redirect:/products";
		
	}
	
	
		@GetMapping("/update")
		public String edit(Model model, @RequestParam int id){
			
			try {
				Product product = repositories.findById(id).get();
				model.addAttribute("product", product);
				
				ProductDTO dto= new ProductDTO();
			
				dto.setName(product.getName());
				dto.setBrand(product.getBrand());
				dto.setCategory(product.getCategory());
				dto.setDescription(product.getDescription());
				dto.setPrice(product.getPrice());
				
				model.addAttribute("category", category );
				
				model.addAttribute("productDTO", dto);
				
			} catch (Exception e) {
				System.out.println(e);
				return "redirect:/products";
			}
			return "editproduct";
	}
		
		
		@PostMapping("/update")
		public String UpdateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDTO dto, BindingResult bindingResult) {
			
			Product pro=repositories.findById(id).get();
			model.addAttribute("ProductDTO", pro);
			
			if(bindingResult.hasErrors()) {
				return "redirect:/update";
			}
			
			if(!dto.getImageFileName().isEmpty()) {
				String uploadDir = "public/img";
				Path OldimagePath = Paths.get(uploadDir + pro.getImageFileName());
				System.out.println(OldimagePath);
				
				try {
					Files.delete(OldimagePath);
				} catch (Exception e) {
					System.out.println(e);
				}
				
				
				// Save New File
				MultipartFile image= dto.getImageFileName();
				Date createAt = new Date();
				String  StorageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
				
				System.out.println(StorageFileName);
				
				try(InputStream inputstream = image.getInputStream()){
						Files.copy(inputstream, Paths.get(uploadDir + StorageFileName), 
								StandardCopyOption.REPLACE_EXISTING);
					}catch(Exception e) {
						
					}
					
					pro.setImageFileName(StorageFileName);
					
					
					pro.setName(dto.getName());
					pro.setBrand(dto.getBrand());
					pro.setCategory(dto.getCategory());
					pro.setDescription(dto.getDescription());
					pro.setPrice(dto.getPrice());
					pro.setCreateAt(createAt);
									
					
					repositories.save(pro);
				}
			
			return "redirect:/products";
		}
		
		
		@GetMapping("/delete")
		public String delete(@RequestParam int id) {
			Product pro = repositories.findById(id).get();
			
			Path imgpath=Paths.get("public/img" + pro.getImageFileName());
			
			try {
				Files.delete(imgpath);
			} catch (Exception e) {
					System.out.println(e);
			}
			
			repositories.delete(pro);
			return "redirect:/products";
		}
		
}

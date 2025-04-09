package com.john.mystore.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.john.mystore.models.Product;
import com.john.mystore.models.ProductDto;
import com.john.mystore.services.ProductServices;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductServices service;
	
	@GetMapping({"","/"})
	public String showProductList(Model model) {
		List<Product> productsList = service.getProducts();
		model.addAttribute("productsList",productsList);
		return "products/ProductsList";
	}
	@GetMapping("/create")
	public String showCreateProduct(Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto",productDto);
		return "products/CreateProduct";
	}
	@PostMapping("/create")
	public String createProduct(@ModelAttribute ProductDto productDto) {
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCategory(productDto.getCategory());
		product.setPrice(productDto.getPrice());
		service.addProduct(product);
		return "redirect:/products";
	}
	@GetMapping("/edit")
	public String showEditProduct(Model model,@RequestParam("id") int id) {
		Product product = service.findProductById(id);
		model.addAttribute("productDto",product);
		return "products/EditProduct";
	}
	@PostMapping("/edit")
	public String editProduct(@RequestParam("id") int id, @ModelAttribute ProductDto productDto) {
			Product product = service.findProductById(id);
			product.setId(productDto.getId());
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			service.updateProduct(product);
		return "redirect:/products";
	}
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("id") int id) {
		service.deleteProduct(id);
		return "redirect:/products";
	}
}

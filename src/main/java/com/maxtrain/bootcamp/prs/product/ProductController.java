package com.maxtrain.bootcamp.prs.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxtrain.bootcamp.prs.util.JsonResponse;
import com.maxtrain.bootcamp.prs.product.Product;

@CrossOrigin
@Controller
@RequestMapping(path="/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(name="")
	public @ResponseBody JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(productRepository.findAll());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}	
	}
	@GetMapping(path="/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<Product> product = productRepository.findById(id);
			if(!product.isPresent()) {
				return JsonResponse.getErrorInstance("Product not found");
			}
			return JsonResponse.getInstance(product.get());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	private JsonResponse save(Product product) {
		try {
			Product prod = productRepository.save(product);
			return JsonResponse.getInstance(prod);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex, ex.getMessage());
		}
	}
	@PostMapping(path="")
	public @ResponseBody JsonResponse Insert(@RequestBody Product product) {
		return save(product);
	}
	@PutMapping(path="/{id}")
	public @ResponseBody JsonResponse Update(@RequestBody Product product, @PathVariable int id) {
		return save(product);
	}
	@DeleteMapping(path="/{id}")
	public @ResponseBody JsonResponse Delete(@PathVariable int id) {
		try {
			Optional<Product> prod = productRepository.findById(id);
			if(!prod.isPresent()) {
				return JsonResponse.getErrorInstance("Product not found");
			}
			productRepository.deleteById(prod.get().getId());
			return JsonResponse.getInstance(prod);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	
}

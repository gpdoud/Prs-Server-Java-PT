package com.maxtrain.bootcamp.prs.vendor;

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

import com.maxtrain.bootcamp.prs.vendor.Vendor;
import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping(path="/vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepository; 
	
	@GetMapping(name="")
	public @ResponseBody JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(vendorRepository.findAll());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}	
	}
	@GetMapping(path="/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<Vendor> vendor = vendorRepository.findById(id);
			if(!vendor.isPresent()) {
				return JsonResponse.getErrorInstance("Vendor not found");
			}
			return JsonResponse.getInstance(vendor.get());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	private JsonResponse save(Vendor vendor) {
		try {
			Vendor vnd = vendorRepository.save(vendor);
			return JsonResponse.getInstance(vnd);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex, ex.getMessage());
		}
	}
	@PostMapping(path="")
	public @ResponseBody JsonResponse Insert(@RequestBody Vendor vendor) {
		return save(vendor);
	}
	@PutMapping(path="/{id}")
	public @ResponseBody JsonResponse Update(@RequestBody Vendor vendor, @PathVariable int id) {
		return save(vendor);
	}
	@DeleteMapping(path="/{id}")
	public @ResponseBody JsonResponse Delete(@PathVariable int id) {
		try {
			Optional<Vendor> vnd = vendorRepository.findById(id);
			if(!vnd.isPresent()) {
				return JsonResponse.getErrorInstance("Vendor not found");
			}
			vendorRepository.deleteById(vnd.get().getId());
			return JsonResponse.getInstance(vnd);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
}

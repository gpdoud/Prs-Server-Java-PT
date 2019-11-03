package com.maxtrain.bootcamp.prs.requestline;

import java.math.BigDecimal;
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

import com.maxtrain.bootcamp.prs.request.Request;
import com.maxtrain.bootcamp.prs.request.RequestRepository;
import com.maxtrain.bootcamp.prs.requestline.Requestline;
import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping(path="/requestlines")
public class RequestlineController {
	
	@Autowired
	private RequestlineRepository requestlineRepository;
	@Autowired
	private RequestRepository requestRepository;
	
	private void RecalculateRequestTotal(int requestId) {
		Iterable<Requestline> reqlines = requestlineRepository.getRequestlineByRequestId(requestId);
		double total = 0;
		for(Requestline reqline : reqlines) {
			double price = reqline.getProduct().getPrice();
			double qty = reqline.getQuantity();
			total += price * qty;
		}
		Optional<Request> req = requestRepository.findById(requestId);
		Request request = req.get();
		request.setTotal(total);
		requestRepository.save(request);
	}

	@GetMapping(path="")
	public @ResponseBody JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(requestlineRepository.findAll());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}	
	}
	@GetMapping(path="/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<Requestline> requestline = requestlineRepository.findById(id);
			if(!requestline.isPresent()) {
				return JsonResponse.getErrorInstance("Requestline not found");
			}
			return JsonResponse.getInstance(requestline.get());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	private JsonResponse save(Requestline requestline) {
		try {
			Requestline reqline = requestlineRepository.save(requestline);
			return JsonResponse.getInstance(reqline);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex, ex.getMessage());
		}
	}
	@PostMapping(path="")
	public @ResponseBody JsonResponse Insert(@RequestBody Requestline requestline) {
		JsonResponse jr = save(requestline);
		RecalculateRequestTotal(requestline.getRequest().getId());
		return jr;
	}
	@PutMapping(path="/{id}")
	public @ResponseBody JsonResponse Update(@RequestBody Requestline requestline, @PathVariable int id) {
		JsonResponse jr = save(requestline);
		RecalculateRequestTotal(requestline.getRequest().getId());
		return jr;
	}
	@DeleteMapping(path="/{id}")
	public @ResponseBody JsonResponse Delete(@PathVariable int id) {
		try {
			Optional<Requestline> reqline = requestlineRepository.findById(id);
			if(!reqline.isPresent()) {
				return JsonResponse.getErrorInstance("Requestline not found");
			}
			requestlineRepository.deleteById(reqline.get().getId());
			RecalculateRequestTotal(reqline.get().getRequest().getId());
			return JsonResponse.getInstance(reqline);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}

}

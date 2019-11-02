package com.maxtrain.bootcamp.prs.request;

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
import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping(path="/requests")
public class RequestController {

	@Autowired
	private RequestRepository requestRepository;
	
	@GetMapping(path="/reviews/{id}")
	public @ResponseBody JsonResponse getAllReviews(@PathVariable int id) {	
		try {
			Iterable<Request> requests = requestRepository.findAllByUserIdNotAndStatus(id, Request.STATUS_REVIEW);
			return JsonResponse.getInstance(requests);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Error getting reviewed requests", ex);
		}
	}
	
	private @ResponseBody JsonResponse setRequestStatus(Request request, String status) {
		try {
			Optional<Request> req = requestRepository.findById(request.getId());
			if(!req.isPresent()) {
				return JsonResponse.getErrorInstance("Request not found");
			}
			request.setStatus(status);
			requestRepository.save(request);
			return JsonResponse.getInstance(request);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception getting request", ex);
		}
	}
	@PutMapping(path="/review")
	public @ResponseBody JsonResponse setStatusToReview(@RequestBody Request request) {	
		request.setReasonForRejection(null);
		return setRequestStatus(request, Request.STATUS_REVIEW);
	}
	@PutMapping(path="/approve")
	public @ResponseBody JsonResponse setStatusToApproved(@RequestBody Request request) {	
		request.setReasonForRejection(null);
		return setRequestStatus(request, Request.STATUS_APPROVED);
	}
	@PutMapping(path="/reject")
	public @ResponseBody JsonResponse setStatusToRejected(@RequestBody Request request) {		
		return setRequestStatus(request, Request.STATUS_REJECTED);
	}
	
	@GetMapping(path="")
	public @ResponseBody JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(requestRepository.findAll());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}	
	}
	@GetMapping(path="/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<Request> request = requestRepository.findById(id);
			if(!request.isPresent()) {
				return JsonResponse.getErrorInstance("Request not found");
			}
			return JsonResponse.getInstance(request.get());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	private JsonResponse save(Request request) {
		try {
			Request req = requestRepository.save(request);
			return JsonResponse.getInstance(req);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex, ex.getMessage());
		}
	}
	@PostMapping(path="")
	public @ResponseBody JsonResponse Insert(@RequestBody Request request) {
		return save(request);
	}
	@PutMapping(path="/{id}")
	public @ResponseBody JsonResponse Update(@RequestBody Request request, @PathVariable int id) {
		return save(request);
	}
	@DeleteMapping(path="/{id}")
	public @ResponseBody JsonResponse Delete(@PathVariable int id) {
		try {
			Optional<Request> req = requestRepository.findById(id);
			if(!req.isPresent()) {
				return JsonResponse.getErrorInstance("Request not found");
			}
			requestRepository.deleteById(req.get().getId());
			return JsonResponse.getInstance(req);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}

}

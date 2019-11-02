package com.maxtrain.bootcamp.prs.user;

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

@CrossOrigin
@Controller
@RequestMapping(path="/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/authenticate")
	public @ResponseBody JsonResponse authenticate(@RequestBody User user) {
		try {
			User u = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			if(u == null) {
				return JsonResponse.getErrorInstance("User not found");
			}
			return JsonResponse.getInstance(u);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	@GetMapping(path="")
	public @ResponseBody JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(userRepository.findAll());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	@GetMapping(path="/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if(!user.isPresent()) {
				return JsonResponse.getErrorInstance("User not found");
			}
			return JsonResponse.getInstance(user.get());
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
	private JsonResponse save(User user) {
		try {
			User usr = userRepository.save(user);
			return JsonResponse.getInstance(usr);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex, ex.getMessage());
		}
	}
	@PostMapping(path="")
	public @ResponseBody JsonResponse Insert(@RequestBody User user) {
		return save(user);
	}
	@PutMapping(path="/{id}")
	public @ResponseBody JsonResponse Update(@RequestBody User user, @PathVariable int id) {
		return save(user);
	}
	@DeleteMapping(path="/{id}")
	public @ResponseBody JsonResponse Delete(@PathVariable int id) {
		try {
			Optional<User> usr = userRepository.findById(id);
			if(!usr.isPresent()) {
				return JsonResponse.getErrorInstance("User not found");
			}
			userRepository.deleteById(usr.get().getId());
			return JsonResponse.getInstance(usr);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance("Exception:"+ex.getMessage(), ex);
		}
	}
}

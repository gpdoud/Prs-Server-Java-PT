package com.maxtrain.bootcamp.prs.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maxtrain.bootcamp.prs.util.JsonResponse;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsernameAndPassword(String username, String password);
}

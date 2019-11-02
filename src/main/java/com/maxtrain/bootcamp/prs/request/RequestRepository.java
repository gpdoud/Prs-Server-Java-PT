package com.maxtrain.bootcamp.prs.request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Integer> {
	Iterable<Request> findAllByUserIdNotAndStatus(int id, String status);
}

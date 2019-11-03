package com.maxtrain.bootcamp.prs.requestline;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestlineRepository extends CrudRepository<Requestline, Integer> {
	Iterable<Requestline> getRequestlineByRequestId(int requestId);
}

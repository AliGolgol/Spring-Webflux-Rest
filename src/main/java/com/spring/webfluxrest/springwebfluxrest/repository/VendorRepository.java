package com.spring.webfluxrest.springwebfluxrest.repository;

import com.spring.webfluxrest.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String> {

}

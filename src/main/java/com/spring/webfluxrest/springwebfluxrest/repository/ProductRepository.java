package com.spring.webfluxrest.springwebfluxrest.repository;

import com.spring.webfluxrest.springwebfluxrest.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product,String> {

}

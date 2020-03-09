package com.spring.webfluxrest.springwebfluxrest.controller;

import com.spring.webfluxrest.springwebfluxrest.domain.Product;
import com.spring.webfluxrest.springwebfluxrest.repository.ProductRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/api/v1/products")
    Flux<Product> list(){
        return productRepository.findAll();
    }

    @GetMapping("/api/v1/products/{id}")
    Mono<Product> getById(@PathVariable String id){
        return productRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/products")
    Mono<Void> createProduct(@RequestBody Publisher<Product> productPublisher){
        return productRepository.saveAll(productPublisher).then();
    }
}

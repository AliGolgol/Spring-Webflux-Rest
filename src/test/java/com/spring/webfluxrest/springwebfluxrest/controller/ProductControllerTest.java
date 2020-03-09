package com.spring.webfluxrest.springwebfluxrest.controller;

import com.spring.webfluxrest.springwebfluxrest.domain.Product;
import com.spring.webfluxrest.springwebfluxrest.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;


class ProductControllerTest {

    WebTestClient webTestClient;
    ProductRepository productRepository;
    ProductController productController;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productController = new ProductController(productRepository);
        webTestClient = WebTestClient.bindToController(productController).build();
    }

    @Test
    void list() {
        BDDMockito.given(productRepository.findAll())
                .willReturn(Flux.just(Product.builder().name("product1").build(),
                        Product.builder().name("product2").build()));

        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectBodyList(Product.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(productRepository.findById("id"))
                .willReturn(Mono.just(Product.builder().name("product1").build()));

        webTestClient.get()
                .uri("/api/v1/products/id")
                .exchange()
                .expectBody(Product.class);
    }

    @Test
    void testCreateProduct(){
        BDDMockito.given(productRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Product.builder().name("keyword").build()));

        Mono<Product> productMono=Mono.just(Product.builder().name("monitor").build());

        webTestClient.post()
                .uri("/api/v1/products")
                .body(productMono,Product.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void testUpdateProduct() {
        BDDMockito.given(productRepository.save(any(Product.class)))
                .willReturn(Mono.just(Product.builder().build()));

        Mono<Product> productMono=Mono.just(Product.builder().name("case").build());

        webTestClient.put()
                .uri("/api/v1/products/test")
                .body(productMono,Product.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
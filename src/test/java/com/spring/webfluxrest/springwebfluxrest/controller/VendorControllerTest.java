package com.spring.webfluxrest.springwebfluxrest.controller;

import ch.qos.logback.core.db.DBAppenderBase;
import com.spring.webfluxrest.springwebfluxrest.domain.Vendor;
import com.spring.webfluxrest.springwebfluxrest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("ali").lastName("golgol").build(),
                        Vendor.builder().firstName("behrad").lastName("golgol").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        BDDMockito.given(vendorRepository.findById("id"))
                .willReturn(Mono.just(Vendor.builder()
                        .firstName("ali").lastName("golgol").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/id")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void testCreateVendor() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono=Mono.just(Vendor.builder().firstName("ali").build());

        webTestClient.post()
                .uri("/api/v1/vendors/")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void testUpdateVendor() {
        BDDMockito.given(vendorRepository.save(Vendor.builder().build()))
                .willReturn(Mono.just(Vendor.builder().firstName("ali").build()));

        Mono<Vendor> vendorMono=Mono.just(Vendor.builder().lastName("golgol").build());

        webTestClient.put()
                .uri("/api/v1/vendors/id")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
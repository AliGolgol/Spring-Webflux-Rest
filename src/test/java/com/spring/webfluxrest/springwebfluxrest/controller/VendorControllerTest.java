package com.spring.webfluxrest.springwebfluxrest.controller;

import com.spring.webfluxrest.springwebfluxrest.domain.Vendor;
import com.spring.webfluxrest.springwebfluxrest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

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
}
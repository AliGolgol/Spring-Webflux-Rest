package com.spring.webfluxrest.springwebfluxrest.bootstrap;

import com.spring.webfluxrest.springwebfluxrest.domain.Product;
import com.spring.webfluxrest.springwebfluxrest.domain.Vendor;
import com.spring.webfluxrest.springwebfluxrest.repository.ProductRepository;
import com.spring.webfluxrest.springwebfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(ProductRepository productRepository, VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count().block() == 0) {
            System.out.println("Loading data from bootstrap ...");

            productRepository.save(Product.builder()
                    .name("Keyboard").build()).block();
            productRepository.save(Product.builder()
                    .name("Mouse").build()).block();
             productRepository.save(Product.builder()
                    .name("Case").build()).block();
             productRepository.save(Product.builder()
                    .name("Monitor").build()).block();
             productRepository.save(Product.builder()
                    .name("Playstation").build()).block();
             productRepository.save(Product.builder()
                    .name("Glass").build()).block();

            System.out.println("Loaded Products "+productRepository.count().block());

            vendorRepository.save(Vendor.builder()
            .firstName("ali")
            .lastName("golgol").build()).block();

            vendorRepository.save(Vendor.builder()
            .firstName("behrad")
            .lastName("golgol").build()).block();

            vendorRepository.save(Vendor.builder()
            .firstName("bahar")
            .lastName("golgol").build()).block();

            System.out.println("Loaded Vendor "+vendorRepository.count().block());
        }
    }
}

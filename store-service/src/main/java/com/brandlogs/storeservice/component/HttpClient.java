package com.brandlogs.storeservice.component;

import com.brandlogs.storeservice.dto.Variant;
import com.brandlogs.storeservice.dto.Vendor;
import com.brandlogs.storeservice.exception.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class HttpClient {
    private final WebClient.Builder webClientBuilder;

    public HttpClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Vendor getVendorById(long id){
        return webClientBuilder.build()
                .get()
                .uri("lb://vendor-service",uriBuilder
                        -> uriBuilder.path("/api/vendors/view/{id}").build(id))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response-> Mono.error(new ModelNotFoundException("The vendor you entered does not exist")))
                .onStatus(HttpStatus::is5xxServerError, response-> Mono.error(new ModelNotFoundException("vendor service may be down. contact admin")))
                .bodyToMono(Vendor.class).block();
    }

    public Variant getVariantById(long id){
        return webClientBuilder.build()
                .get()
                .uri("lb://product-service",uriBuilder
                        -> uriBuilder.path("/api/products/variants/view/{id}").build(id))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response->{
                    return Mono.error(new ModelNotFoundException("The product you entered does not exist id "+id));
                })
                .onStatus(HttpStatus::is5xxServerError, response->{
                    return Mono.error(new ModelNotFoundException("product service may be down. contact admin"));
                })
                .bodyToMono(Variant.class).block();
    }
}

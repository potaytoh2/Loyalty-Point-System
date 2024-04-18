package com.admin.service;

import java.io.IOException;
import java.util.*;

import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

public class ForwardService {
    protected final String host;
    private final String strip;

    public ForwardService(String host, String strip) {
        this.host = host;
        this.strip = strip;
    }

    public ResponseEntity<String> forward(HttpServletRequest request, String body) throws IOException {
        WebClient.RequestBodySpec spec = WebClient.create(host)
                .method(HttpMethod.valueOf(request.getMethod()))
                .uri(request.getRequestURI().replaceFirst(strip, ""), request.getQueryString());
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            List<String> ls = new ArrayList<>();
            request.getHeaders(header).asIterator().forEachRemaining(value -> ls.add(value));
            spec.header(header, ls.toArray(new String[0]));
        });

        return spec.body(Mono.just(body), String.class)
                .exchangeToMono(response -> response.toEntity(String.class))
                .block();
    }

    public void approveRequest(String uri, String details, String method) {
        WebClient.create(host).method(HttpMethod.valueOf(method)).uri(uri).body(Mono.just(details), String.class)
                .retrieve().bodyToMono(String.class);
    }
}

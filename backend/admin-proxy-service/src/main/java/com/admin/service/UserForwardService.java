package com.admin.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

public class UserForwardService extends ForwardService {
    public UserForwardService(String host, String strip) {
        super(host, strip);
    }

    public List<String> getPermissions(String userId) {
        return WebClient.create(host)
                .get()
                .uri("/permissions/" + userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                }).block();
    }
}

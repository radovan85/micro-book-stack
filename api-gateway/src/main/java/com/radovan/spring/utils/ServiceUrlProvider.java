package com.radovan.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.radovan.spring.services.ConsulServiceDiscovery;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServiceUrlProvider {

    private final Map<String, String> cachedServiceUrls = new ConcurrentHashMap<>();

    @Autowired
    private ConsulServiceDiscovery consulServiceDiscovery;

    public String getServiceUrl(String serviceName) {
        return cachedServiceUrls.computeIfAbsent(serviceName, key -> {
            try {
                // Dohvaćanje URL-a iz Consul registry-ja
                String serviceUrl = consulServiceDiscovery.getServiceUrl(serviceName);
                validateUrl(serviceUrl, serviceName);
                return serviceUrl;
            } catch (RuntimeException e) {
                System.err.println("Failed to retrieve service URL for: " + serviceName + " - " + e.getMessage());
                throw e;
            }
        });
    }

    public String getBookServiceUrl() {
        return getServiceUrl("book-service");
    }

    public String getGenreServiceUrl() {
        return getServiceUrl("genre-service");
    }

    public String getImageServiceUrl() {
        return getServiceUrl("image-service");
    }

    public String getAuthServiceUrl() {
        return getServiceUrl("auth-service");
    }

    private void validateUrl(String url, String serviceName) {
        if (url == null || !url.startsWith("http")) {
            throw new IllegalArgumentException("Invalid URL for " + serviceName + ": " + url);
        }
    }


}

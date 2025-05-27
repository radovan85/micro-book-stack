package com.radovan.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radovan.spring.interceptors.RestTemplateHeaderModifierInterceptor;
import com.radovan.spring.security.AuthorizationHeaderFilter;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableScheduling
@EnableWebMvc
@ComponentScan(basePackages = "com.radovan.spring")
public class SpringMvcConfiguration implements WebMvcConfigurer {

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		// Dodaj interceptor za dinamičko dodavanje Authorization zaglavlja
		restTemplate.setInterceptors(Collections.singletonList(new RestTemplateHeaderModifierInterceptor()));
		return restTemplate;
	}

	/*
	 * @Override public void
	 * configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	 * converters.add(new MappingJackson2HttpMessageConverter()); }
	 */

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// JSON konverter
		converters.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));

		// Plain text konverter za Prometheus metrike
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		stringConverter.setWriteAcceptCharset(false);
		converters.add(stringConverter);
	}

	@Bean
	public Filter authorizationHeaderFilter() {
		return new AuthorizationHeaderFilter();
	}

	@Bean
	@Primary // Ovo govori Spring-u da je ovo glavni MeterRegistry
	public PrometheusMeterRegistry prometheusMeterRegistry() {
		return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
	}

	@Bean
	public MeterRegistry meterRegistry(PrometheusMeterRegistry prometheusRegistry) {
		return prometheusRegistry;
	}

}

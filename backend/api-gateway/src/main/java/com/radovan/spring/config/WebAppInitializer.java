package com.radovan.spring.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.MultipartConfigElement;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final String TMP_FOLDER = System.getProperty("java.io.tmpdir");
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

    @Override
    public void onStartup(ServletContext container) {
        // 1. Registrujte RequestContextListener (obezbeđuje request scope)
        container.addListener(RequestContextListener.class);

        // 2. Registrujte RequestContextFilter (obavezno pre drugih filtera)
        FilterRegistration.Dynamic requestContextFilter = container.addFilter("requestContextFilter",
                new RequestContextFilter());
        requestContextFilter.addMappingForUrlPatterns(null, false, "/*");
        requestContextFilter.setAsyncSupported(true);

        // 3. Registrujte vaš AuthorizationHeaderFilter
        FilterRegistration.Dynamic authFilter = container.addFilter("authorizationHeaderFilter",
                new DelegatingFilterProxy("authorizationHeaderFilter"));
        authFilter.addMappingForUrlPatterns(null, false, "/*");
        authFilter.setAsyncSupported(true);

        // 4. Kreirajte i registrujte DispatcherServlet
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(SpringMvcConfiguration.class);

        ServletRegistration.Dynamic dispatcher = container.addServlet("Spring Initializer",
                new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // 5. Konfigurišite multipart za upload fajlova
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                TMP_FOLDER,
                MAX_UPLOAD_SIZE,
                MAX_UPLOAD_SIZE * 2L,
                MAX_UPLOAD_SIZE / 2
        );
        dispatcher.setMultipartConfig(multipartConfigElement);
    }
}
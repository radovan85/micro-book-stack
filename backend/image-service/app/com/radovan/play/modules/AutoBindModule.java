package com.radovan.play.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.radovan.play.clients.BookServiceClient;
import com.radovan.play.config.ConsulClientRegistrationInitializer;
import com.radovan.play.converter.ImageConverter;
import com.radovan.play.repositories.BookImageRepository;
import com.radovan.play.repositories.impl.BookImageRepositoryImpl;
import com.radovan.play.services.BookImageService;
import com.radovan.play.services.ConsulRegistrationService;
import com.radovan.play.services.ConsulServiceDiscovery;
import com.radovan.play.services.impl.BookImageServiceImpl;
import com.radovan.play.services.impl.ConsulRegistrationServiceImpl;
import com.radovan.play.services.impl.ConsulServiceDiscoveryImpl;
import com.radovan.play.utils.*;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Environment;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class AutoBindModule extends AbstractModule {

    private final Environment environment;
    private final Config config;
    private static final Logger logger = LoggerFactory.getLogger(AutoBindModule.class);

    public AutoBindModule(Environment environment, Config config) {
        this.environment = environment;
        this.config = config;
    }

    @Override
    protected void configure() {
        // Bind repositories and services
        bind(BookImageService.class).to(BookImageServiceImpl.class).asEagerSingleton();
        bind(BookImageRepository.class).to(BookImageRepositoryImpl.class).asEagerSingleton();
        bind(ConsulRegistrationService.class).to(ConsulRegistrationServiceImpl.class).asEagerSingleton();
        bind(ConsulServiceDiscovery.class).to(ConsulServiceDiscoveryImpl.class).asEagerSingleton();
        bind(ServiceUrlProvider.class).asEagerSingleton();

        // Bind Consul initializer to eager singleton
        bind(ConsulClientRegistrationInitializer.class).asEagerSingleton();
        bind(ImageConverter.class).asEagerSingleton();
        bind(BookServiceClient.class).asEagerSingleton();
        bind(FileValidator.class).asEagerSingleton();
        bind(NatsUtils.class).asEagerSingleton();
        bind(NatsSubscriber.class).asEagerSingleton();
        bind(JwtUtil.class).asEagerSingleton();
    }


}
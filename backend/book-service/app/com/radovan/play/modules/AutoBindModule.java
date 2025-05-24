package com.radovan.play.modules;

import com.google.inject.AbstractModule;
import com.radovan.play.config.ConsulClientRegistrationInitializer;
import com.radovan.play.converter.BookConverter;
import com.radovan.play.repositories.BookRepository;
import com.radovan.play.repositories.impl.BookRepositoryImpl;
import com.radovan.play.services.BookService;
import com.radovan.play.services.ConsulRegistrationService;
import com.radovan.play.services.ConsulServiceDiscovery;
import com.radovan.play.services.impl.BookServiceImpl;
import com.radovan.play.services.impl.ConsulRegistrationServiceImpl;
import com.radovan.play.services.impl.ConsulServiceDiscoveryImpl;
import com.radovan.play.utils.*;
import play.Environment;
import com.typesafe.config.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        bind(BookService.class).to(BookServiceImpl.class).asEagerSingleton();
        bind(BookRepository.class).to(BookRepositoryImpl.class).asEagerSingleton();
        bind(ConsulRegistrationService.class).to(ConsulRegistrationServiceImpl.class).asEagerSingleton();
        bind(ConsulServiceDiscovery.class).to(ConsulServiceDiscoveryImpl.class).asEagerSingleton();
        bind(ServiceUrlProvider.class).asEagerSingleton();
        bind(ConsulClientRegistrationInitializer.class).asEagerSingleton();
        bind(NatsSubscriber.class).asEagerSingleton();
        bind(PublicKeyCache.class).asEagerSingleton();
        bind(JwtUtil.class).asEagerSingleton();
        bind(BookConverter.class).asEagerSingleton();
    }
}

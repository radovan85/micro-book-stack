package com.radovan.play.modules;

import com.google.inject.AbstractModule;
import com.radovan.play.config.ConsulClientRegistrationInitializer;
import com.radovan.play.converter.BookGenreConverter;
import com.radovan.play.repositories.BookGenreRepository;
import com.radovan.play.repositories.impl.BookGenreRepositoryImpl;
import com.radovan.play.services.BookGenreService;
import com.radovan.play.services.ConsulRegistrationService;
import com.radovan.play.services.ConsulServiceDiscovery;
import com.radovan.play.services.impl.BookGenreServiceImpl;
import com.radovan.play.services.impl.ConsulRegistrationServiceImpl;
import com.radovan.play.services.impl.ConsulServiceDiscoveryImpl;
import com.radovan.play.utils.*;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Environment;

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
        bind(BookGenreService.class).to(BookGenreServiceImpl.class).asEagerSingleton();
        bind(BookGenreRepository.class).to(BookGenreRepositoryImpl.class).asEagerSingleton();
        bind(ConsulRegistrationService.class).to(ConsulRegistrationServiceImpl.class).asEagerSingleton();
        bind(ConsulServiceDiscovery.class).to(ConsulServiceDiscoveryImpl.class).asEagerSingleton();
        bind(ServiceUrlProvider.class).asEagerSingleton();
        bind(ConsulClientRegistrationInitializer.class).asEagerSingleton();
        bind(NatsUtils.class).asEagerSingleton();
        // Bind JwtUtil koji sada koristi PublicKey
        bind(JwtUtil.class).asEagerSingleton();
        bind(BookGenreConverter.class).asEagerSingleton();
    }





}
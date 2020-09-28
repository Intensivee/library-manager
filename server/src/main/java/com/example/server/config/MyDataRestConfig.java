package com.example.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

//        list off all entities
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> classes = new ArrayList<>();

//        get entities types to array
        entities.forEach(type -> classes.add(type.getJavaType()));
        Class[] domainTypes = classes.toArray(new Class[0]);

//        exposing dynamically all id's
        config.exposeIdsFor(domainTypes);

        }
    }

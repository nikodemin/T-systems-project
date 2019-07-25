package com.t_systems.webstore.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public abstract class AbstractDao {

    @PersistenceUnit
    protected EntityManagerFactory emf;
    protected EntityManager em;

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }
}

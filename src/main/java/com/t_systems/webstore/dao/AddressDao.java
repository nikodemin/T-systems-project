package com.t_systems.webstore.dao;

import com.t_systems.webstore.entity.Address;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Repository
public class AddressDao
{
    @PersistenceUnit
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init()
    {
        em = emf.createEntityManager();
    }

    public void addAddress(Address address)
    {
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
    }
}

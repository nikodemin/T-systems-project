package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDao extends AbstractDao {

    public void addAddress(Address address) {
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
    }
}

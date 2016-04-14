package com.web.chon.core.service;

import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan
 */
@Stateless(mappedName = "ejbsistemaService")
public class SistemaServiceImp implements SistemaService {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;


    @Override
    public Date getFechaSistema() {
        Query query = em.createNativeQuery("SELECT SYSDATE FROM DUAL");
        Date fecha = (Date)query.getSingleResult();

        return fecha;
    }
}

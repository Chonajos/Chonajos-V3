package com.web.chon.ejb;

import com.web.chon.dominio.Correos;
import com.web.chon.negocio.NegocioCatCorreos;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbCatCorreos")
public class EjbCatCorreos implements NegocioCatCorreos {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertCorreo(Correos co) {
        System.out.println("===============================EJB=================================");
        System.out.println(co.toString());
        try {
            Query querySel = em.createNativeQuery("SELECT correo FROM CORREOS WHERE CORREO = '" + co.getCorreo() + "' ");
            List<Object[]> resultList = null;
            resultList = querySel.getResultList();
            if (resultList.isEmpty()) {

                Query query = em.createNativeQuery("INSERT INTO CORREOS (ID_PK,ID_CLIENTE_FK,CORREO,TIPO)VALUES(S_CORREOS.NextVal,?,?,?)");
                query.setParameter(1, co.getId_cliente_fk());
                query.setParameter(2, co.getCorreo());
                query.setParameter(3, co.getTipo());
                return query.executeUpdate();

            } else {
                return 0;
            }

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCorreos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int deleteCorreos(Correos co) {

        try {
            Query query = em.createNativeQuery("DELETE CORREOS WHERE ID_CLIENTE_FK = ?");
            query.setParameter(1, co.getId_cliente_fk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> SearchCorreosbyidClientPk(BigDecimal idClientepk) {

        try {

            Query query = em.createNativeQuery("SELECT * FROM CORREOS WHERE ID_CLIENTE_FK = ?");
            query.setParameter(1, idClientepk);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int updateCorreos(Correos c) {
        try {

            Query query = em.createNativeQuery("UPDATE CORREOS SET CORREO = ?, TIPO = ? WHERE ID_PK= ? ");
            query.setParameter(1, c.getCorreo());
            query.setParameter(2, c.getTipo());
            query.setParameter(3, c.getIdcorreo());
            return query.executeUpdate();
        } catch (Exception ex) {

            Logger.getLogger(EjbCatCorreos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}

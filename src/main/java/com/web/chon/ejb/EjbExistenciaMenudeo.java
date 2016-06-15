/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.negocio.NegocioExistenciaMenudeo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jboss.logging.Logger;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbExistenciaMenudeo")
public class EjbExistenciaMenudeo implements NegocioExistenciaMenudeo {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertaExistenciaMenudeo(ExistenciaMenudeo em) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateExistenciaMenudeo(ExistenciaMenudeo existenciaMenudeo) {
        try {
            Query query = em.createNativeQuery("UPDATE TABLE EXISTENCIAMENUDEO SET KILOS = ?, CANTIDADEMPAQUE = ? WHERE ID_EXMEN_PK = ?");
            query.setParameter(1, existenciaMenudeo.getKilos());
            query.setParameter(1, existenciaMenudeo.getCantidadEmpaque());
            query.setParameter(1, existenciaMenudeo.getIdExMenPk());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error en la busqueda por id", ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getExistenciasMenudeoByIdSucursal(BigDecimal idSucursal) {
        try {
            Query query = em.createNativeQuery("SELECT EXM.ID_EXMEN_PK,EXM.ID_SUBPRODUCTO_FK,EXM.ID_SUCURSAL_FK,EXM.KILOS,EXM.CANTIDADEMPAQUE,EXM.IDTIPOEMPAQUEFK,EXM.IDSTATUSFK,SUB.NOMBRE_SUBPRODUCTO,TE.NOMBRE_EMPAQUE  FROM EXISTENCIAMENUDEO EXM "
                    + "INNER JOIN SUBPRODUCTO SUB ON SUB.ID_SUBPRODUCTO_PK = EXM.ID_SUBPRODUCTO_FK "
                    + "INNER JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK = EXM.IDTIPOEMPAQUEFK "
                    + "WHERE ID_SUCURSAL_FK = ? ");
            query.setParameter(1, idSucursal);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error en la busqueda por id", ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getExistenciasMenudeoById(BigDecimal id) {
        try {
            Query query = em.createNativeQuery("SELECT EXM.ID_EXMEN_PK,EXM.ID_SUBPRODUCTO_FK,EXM.ID_SUCURSAL_FK,EXM.KILOS,EXM.CANTIDADEMPAQUE,EXM.IDTIPOEMPAQUEFK,EXM.IDSTATUSFK,SUB.NOMBRE_SUBPRODUCTO,TE.NOMBRE_EMPAQUE  FROM EXISTENCIAMENUDEO EXM "
                    + "INNER JOIN SUBPRODUCTO SUB ON SUB.ID_SUBPRODUCTO_PK = EXM.ID_SUBPRODUCTO_FK "
                    + "INNER JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK = EXM.IDTIPOEMPAQUEFK "
                    + "WHERE EXM.ID_EXMEN_PK = ? ");
            query.setParameter(1, id);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error en la busqueda por id", ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getExistenciasMenudeo(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

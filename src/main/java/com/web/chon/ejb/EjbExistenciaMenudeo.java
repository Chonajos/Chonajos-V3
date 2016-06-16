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
import java.util.logging.Level;
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
    public int insertaExistenciaMenudeo(ExistenciaMenudeo e) {
        try {

            Query query = em.createNativeQuery("INSERT INTO EXISTENCIAMENUDEO (ID_EXMEN_PK,ID_SUBPRODUCTO_FK,ID_SUCURSAL_FK,KILOS,CANTIDADEMPAQUE,IDTIPOEMPAQUEFK,IDSTATUSFK)VALUES (?,?,?,?,?,?,1)");
            query.setParameter(1, e.getIdExMenPk());
            query.setParameter(2, e.getIdSubProductoPk());
            query.setParameter(3, e.getIdSucursalFk());
            query.setParameter(4, e.getKilos());
            query.setParameter(5, e.getCantidadEmpaque());
            query.setParameter(6, e.getIdTipoEmpaqueFK());
            return query.executeUpdate();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    
    }

    @Override
    public int updateExistenciaMenudeo(ExistenciaMenudeo existenciaMenudeo) {
        try {
            Query query = em.createNativeQuery("UPDATE EXISTENCIAMENUDEO SET KILOS = ?, CANTIDADEMPAQUE = ? WHERE ID_EXMEN_PK = ?");
            query.setParameter(1, existenciaMenudeo.getKilos());
            query.setParameter(2, existenciaMenudeo.getCantidadEmpaque());
            query.setParameter(3, existenciaMenudeo.getIdExMenPk());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error en la modificacion de esxistencias", ex);
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
            Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error en la busqueda por id de sucursal", ex);
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
    
    @Override
    public List<Object[]> getExistenciasRepetidasById(String ID_SUBPRODUCTO_FK, BigDecimal ID_SUCURSAL_FK, BigDecimal IDTIPOEMPAQUEFK) {
        try {
            Query query = em.createNativeQuery("select exm.ID_EXMEN_PK,exm.ID_SUBPRODUCTO_FK,exm.ID_SUCURSAL_FK,exm.KILOS,exm.CANTIDADEMPAQUE,\n" +
"exm.IDTIPOEMPAQUEFK, exm.IDSTATUSFK \n" +
"from EXISTENCIAMENUDEO exm\n" +
"where exm.ID_SUBPRODUCTO_FK = ? and exm.ID_SUCURSAL_FK = ? and exm.IDTIPOEMPAQUEFK = ?");
            query.setParameter(1, ID_SUBPRODUCTO_FK);
            query.setParameter(2, ID_SUCURSAL_FK);
            query.setParameter(3, IDTIPOEMPAQUEFK);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error en la busqueda por id", ex);
            return null;
        }
    }

    @Override
    public int getNextVal() {
          Query query = em.createNativeQuery("SELECT S_EXISTENCIAMENUDEO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    
    }

}

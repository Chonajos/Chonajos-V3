/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.AjusteExistenciaMenudeo;
import com.web.chon.negocio.NegocioAjusteExistenciaMenudeo;
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
 * @author Juan
 */
@Stateless(mappedName = "ejbAjusteExistenciaMenudeo")
public class EjbAjusteExistenciaMenudeo implements NegocioAjusteExistenciaMenudeo {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AjusteExistenciaMenudeo data) {

        try {
            Query query = em.createNativeQuery("INSERT INTO AJUSTE_EXISTENCIA_MENUDEO (ID_AJUSTE_MENUDEO ,ID_EXISTENCIA_MENUDEO_FK ,ID_USUARIO_AJUSTE_FK , ID_USUARIO_APRUEBA_FK , id_tipo_empaque_fk,ID_SUSCURSAL_FK ,FECHA_AJUSTE ,EMPAQUE_ANTERIOR , EMPAQUE_AJUSTADOS, KILOS_ANTERIOR ,KILOS_AJUSTADOS, OBSERVACIONES,MOTIVO_AJUSTE ) VALUES(S_AJUSTE_EXISTENCIA_MENUDEO.NextVal,?,?,?,?,?,?,?,?,?,?,?,?)");

            query.setParameter(1, data.getIdExistenciaMenudeoFK());
            query.setParameter(2, data.getIdUsuarioAjusteFK());
            query.setParameter(3, data.getIdUsuarioApruebaFK());
            query.setParameter(4, data.getIdTipoEmpaqueFK());
            query.setParameter(5, data.getIdSucursalFK());
            query.setParameter(6, data.getFechaAjuste());
            query.setParameter(7, data.getEmpaqueAnterior());
            query.setParameter(8, data.getEmpaqueAjustados());
            query.setParameter(9, data.getKilosAnteior());
            query.setParameter(10, data.getKilosAjustados());
            query.setParameter(11, data.getObservaciones());
            query.setParameter(12, data.getMotivoAjuste());

            return query.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(EjbAjusteExistenciaMenudeo.class.getName()).log(Level.INFO, "Error al Insertar el registro " + data.toString(), ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {

        try {

            Query query = em.createNativeQuery("SELECT * FROM AJUSTE_EXISTENCIA_MENUDEO");

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbAjusteExistenciaMenudeo.class.getName()).log(Level.INFO, "Error al obtener los registros", ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getAllByIdSucursal(BigDecimal idSucursal) {
        try {

            Query query = em.createNativeQuery("SELECT * FROM AJUSTE_EXISTENCIA_MENUDEO WHERE ID_SUSCURSAL_FK = ? ");
            query.setParameter(1, idSucursal);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbAjusteExistenciaMenudeo.class.getName()).log(Level.INFO, "Error al obtener los registros", ex);
            return null;
        }
    }

}

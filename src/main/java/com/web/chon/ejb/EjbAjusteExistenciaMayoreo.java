package com.web.chon.ejb;

import com.web.chon.dominio.AjusteExistenciaMayoreo;
import com.web.chon.negocio.NegocioAjusteExistenciaMayoreo;
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
@Stateless(mappedName = "ejbAjusteExistenciaMayoreo")
public class EjbAjusteExistenciaMayoreo implements NegocioAjusteExistenciaMayoreo {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AjusteExistenciaMayoreo data) {

        try {
            Query query = em.createNativeQuery("INSERT INTO AJUSTE_EXISTENCIA_MAYOREO (ID_AJUSTE_MAYOREO_PK,ID_EXP_FK,ID_USUARIO_AJUSTE_FK,ID_USUARIO_APRUEBA_FK,FECHA_AJUSTE,EMPAQUE_ANTERIOR,EMPAQUE_AJUSTADOS,KILOS_ANTERIOR,KILOS_AJUSTADOS,OBSERVACIONES ,MOTIVO_AJUSTE) VALUES(S_AJUSTE_EXISTENCIA_MAYOREO.NextVal,?,?,?,?,?,?,?,?,?,?)");

            query.setParameter(1, data.getIdExpFk());
            query.setParameter(2, data.getIdUsuarioAjusteFK());
            query.setParameter(3, data.getIdUsuarioApruebaFK());
            query.setParameter(4, data.getFechaAjuste());
            query.setParameter(5, data.getEmpaqueAnterior());
            query.setParameter(6, data.getEmpaqueAjustados());
            query.setParameter(7, data.getKilosAnteior());
            query.setParameter(8, data.getKilosAjustados());
            query.setParameter(9, data.getObservaciones());
            query.setParameter(10, data.getMotivoAjuste());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAjusteExistenciaMayoreo.class.getName()).log(Level.INFO, "Error al Insertar el registro " + data.toString(), ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {

        try {

            Query query = em.createNativeQuery("SELECT * FROM AJUSTE_EXISTENCIA_MAYOREO");

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbAjusteExistenciaMayoreo.class.getName()).log(Level.INFO, "Error al obtener los registros", ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getAllByIdSucursal(BigDecimal idSucursal) {
        try {

            //Aun falta implementar bien este metodo que obtiene por id de sucursal
//            Query query = em.createNativeQuery("SELECT * FROM AJUSTE_EXISTENCIA_MAYOREO WHERE ID_SUSCURSAL_FK = ? ");
//            query.setParameter(1, idSucursal);
//            return query.getResultList();
            return null;

        } catch (Exception ex) {
            Logger.getLogger(EjbAjusteExistenciaMayoreo.class.getName()).log(Level.INFO, "Error al obtener los registros", ex);
            return null;
        }
    }

}

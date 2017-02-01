
package com.web.chon.ejb;

import com.web.chon.dominio.OperacionesCuentas;
import com.web.chon.negocio.NegocioOperacionesCuentas;
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
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbOperacionesCuentas")
public class EjbOperacionesCuentas implements NegocioOperacionesCuentas{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertaOperacion(OperacionesCuentas es) {
        System.out.println("=================================================ejb insert" + es.toString());
            
        try {
            Query query = em.createNativeQuery("INSERT INTO  OPERACIONES_CUENTAS (ID_OPE_CUE_PK,ID_CUENTA_FK,ID_CAJA_ORIGEN_FK,ID_CONCEPTO_FK,FECHA,ID_STATUS_FK,ID_USER_FK,COMENTARIOS,MONTO,E_S) values(?,?,?,?,sysdate,?,?,?,?,?)");
            query.setParameter(1, es.getIdOperacionCuenta());
            query.setParameter(2, es.getIdCuentaFk());
            query.setParameter(3, es.getIdCajaOrigenFk());
            query.setParameter(4, es.getIdConceptoFk());
            query.setParameter(5, es.getIdStatusFk());
            query.setParameter(6, es.getIdUserFk());
            query.setParameter(7, es.getComentarios());
            query.setParameter(8, es.getMonto());
            query.setParameter(9, es.getEntradaSalida());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbOperacionesCuentas.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    
    }

    @Override
    public int updateOperacion(OperacionesCuentas es) {
        try {
            System.out.println("ejb UPDATE" + es.toString());
            Query query = em.createNativeQuery("UPDATE PAGOS_BANCARIOS SET ID_STATUS_FK=? WHERE ID_TRANS_BANCARIAS_PK = ?");
            query.setParameter(1, es.getIdStatusFk());
            query.setParameter(2, es.getIdOperacionCuenta());
            return query.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Errro> "+ex);
            Logger.getLogger(EjbOperacionesCuentas.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public List<Object[]> getOperacionesByIdCuenta(BigDecimal idCuentaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
         try {
            Query query = em.createNativeQuery("select S_OPERACIONES_CUENTAS.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }

    
    }
    
}

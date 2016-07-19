package com.web.chon.ejb;

import com.web.chon.dominio.Credito;
import com.web.chon.negocio.NegocioCredito;
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
 * @author juan
 */
@Stateless(mappedName = "ejbCredito")
public class EjbCredito implements NegocioCredito {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(Credito credito) {

        try {

            Query query = em.createNativeQuery("INSERT INTO  CREDITO (ID_CREDITO_PK ,ID_CLIENTE_FK ,ID_VENTA_MENUDEO ,ID_VENTA_MAYOREO ,ID_USUARIO_CREDITO  ,ESTATUS_CREDITO ,NUMERO_PROMESA_PAGO ,FECHA_INICIO_CREDITO ,FECHA_FIN_CREDITO ,FECHA_PROMESA_FIN_PAGO ,TAZA_INTERES,PLAZOS) "
                    + " VALUES(S_CREDITO.nextval,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, credito.getIdClienteFk());
            query.setParameter(2, credito.getIdVentaMenudeo());
            query.setParameter(3, credito.getIdVentaMayoreo());
            query.setParameter(4, credito.getIdUsuarioCredito());
//            query.setParameter(5, credito.getIdTipoCreditoFk());
            query.setParameter(5, credito.getEstatusCredito());
            query.setParameter(6, credito.getNumeroPromesaPago());
            query.setParameter(7, credito.getFechaInicioCredito());
            query.setParameter(8, credito.getFechaFinCredito());
            query.setParameter(9, credito.getFechaPromesaPago());
            query.setParameter(10, credito.getTazaInteres());
            query.setParameter(11, credito.getPlasos());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(Credito credito) {
        try {

            Query query = em.createNativeQuery("UPDATE CREDITO SET ID_CLIENTE_FK =? ,ID_VENTA_MENUDEO=? ,ID_VENTA_MAYOREO=? ,ID_USUARIO_CREDITO=? ,ID_TIPO_CREDITO_FK=? ,ESTATUS_CREDITO=? ,NUMERO_PROMESA_PAGO=? ,FECHA_INICIO_CREDITO=? ,FECHA_FIN_CREDITO=? ,FECHA_PROMESA_FIN_PAGO=? ,TAZA_INTERES=? WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, credito.getIdClienteFk());
            query.setParameter(2, credito.getIdVentaMenudeo());
            query.setParameter(3, credito.getIdVentaMayoreo());
            query.setParameter(4, credito.getIdUsuarioCredito());
            query.setParameter(5, credito.getIdTipoCreditoFk());
            query.setParameter(6, credito.getEstatusCredito());
            query.setParameter(7, credito.getNumeroPromesaPago());
            query.setParameter(8, credito.getFechaInicioCredito());
            query.setParameter(9, credito.getFechaFinCredito());
            query.setParameter(10, credito.getFechaPromesaPago());
            query.setParameter(11, credito.getTazaInteres());
            query.setParameter(12, credito.getIdCreditoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idCredito) {
        try {

            Query query = em.createNativeQuery("DELETE CREDITO WHERE ID_CREDITO_PK = ?");

            query.setParameter(1, idCredito);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ID_TIPO_CREDITO_FK,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES,PLAZOS  FROM CREDITO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(BigDecimal idCredito) {
        try {

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ID_TIPO_CREDITO_FK,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES,PLAZOS  FROM CREDITO WHERE ID_CREDITO_PK = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getCreditosActivos(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("select credit.ID_CREDITO_PK, stc.NOMBRE_STATUS,credit.FECHA_INICIO_CREDITO,v.FOLIO_SUCURSAL as folio,\n"
                    + "tc.DESCRIPCION, credit.PLAZOS,\n"
                    + "\n"
                    + "(select sum(vp.TOTAL_VENTA) as total from venta v\n"
                    + "inner join VENTA_PRODUCTO vp\n"
                    + "on vp.ID_VENTA_FK = v.ID_VENTA_PK\n"
                    + "where v.ID_VENTA_PK =credit.ID_VENTA_MENUDEO\n"
                    + "group by v.ID_VENTA_PK) \n"
                    + "\n"
                    + "as total,\n"
                    + "(select NVL(sum(ac.MONTO_ABONO),0)from ABONO_CREDITO ac\n"
                    + "where ac.ID_CREDITO_FK= credit.ID_CREDITO_PK and ac.ESTATUS=1) as Total_Abonado\n"
                    + "\n"
                    + ",credit.ID_TIPO_CREDITO_FK,credit.ESTATUS_CREDITO\n"
                    + "\n"
                    + "from credito credit \n"
                    + "inner join venta v\n"
                    + "on v.ID_VENTA_PK = credit.ID_VENTA_MENUDEO\n"
                    + "INNER JOIN TIPO_CREDITO tc\n"
                    + "on tc.ID_TIPO_CREDITO_PK = credit.ID_TIPO_CREDITO_FK\n"
                    + "inner join STATUS_CREDITO stc\n"
                    + "on stc.ID_STATUS_CREDITO_PK = credit.ESTATUS_CREDITO\n"
                    + "where  credit.ESTATUS_CREDITO=1 and credit.ID_CLIENTE_FK= '" + idCliente + "'");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus) {
        try {

            Query query = em.createNativeQuery("UPDATE CREDITO SET ESTATUS_CREDITO = ? WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, estatus);
            query.setParameter(2, idCreditoPk);
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

}

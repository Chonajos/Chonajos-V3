package com.web.chon.ejb;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.negocio.NegocioAbonoCredito;
import java.math.BigDecimal;
import java.util.Date;
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
@Stateless(mappedName = "ejbAbonoCredito")
public class EjbAbonoCredito implements NegocioAbonoCredito {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AbonoCredito abonoCredito) {

        try {
            System.out.println("EJB: =========="+abonoCredito.toString());

            Query query = em.createNativeQuery("INSERT INTO  ABONO_CREDITO (ID_ABONO_CREDITO_PK ,"
                    + "ID_CREDITO_FK "
                    + ",MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK,TIPO_ABONO_FK,ESTATUS,NUMERO_CHEQUE,"
                    + "LIBRADOR,FECHA_COBRO,BANCO_EMISOR,NUMERO_FACTURA,REFERENCIA,CONCEPTO,FECHA_TRANSFERENCIA ) "
                    + " VALUES(?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, abonoCredito.getIdAbonoCreditoPk());
            query.setParameter(2, abonoCredito.getIdCreditoFk());
            query.setParameter(3, abonoCredito.getMontoAbono());
            //fecha
            query.setParameter(4, abonoCredito.getIdUsuarioFk());
            query.setParameter(5, abonoCredito.getIdtipoAbonoFk());
            query.setParameter(6, abonoCredito.getEstatusAbono());
            query.setParameter(7, abonoCredito.getNumeroCheque());
            query.setParameter(8, abonoCredito.getLibrador());
            query.setParameter(9, abonoCredito.getFechaCobro()); 
            query.setParameter(10, abonoCredito.getBanco());
            query.setParameter(11, abonoCredito.getFactura());
            query.setParameter(12, abonoCredito.getReferencia());
            query.setParameter(13, abonoCredito.getConcepto());
            query.setParameter(14, abonoCredito.getFechaTransferencia());
            
  
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(AbonoCredito abonoCredito) {
        try {

            Query query = em.createNativeQuery("UPDATE ABONO_CREDITO SET ID_CREDITO_FK =? ,MONTO_ABONO =? ,FECHA_ABONO =? ,ID_USUARIO_FK =? ,ESTATUS=? WHERE ID_ABONO_CREDITO_PK = ?");
            query.setParameter(1, abonoCredito.getIdCreditoFk());
            query.setParameter(2, abonoCredito.getMontoAbono());
            query.setParameter(3, abonoCredito.getFechaAbono());
            query.setParameter(4, abonoCredito.getIdUsuarioFk());
            query.setParameter(5, abonoCredito.getEstatusAbono());
            query.setParameter(6, abonoCredito.getIdAbonoCreditoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idAbonoCredito) {
        try {

            Query query = em.createNativeQuery("DELETE ABONO_CREDITO WHERE ID_ABONO_CREDITO_PK  = ?");

            query.setParameter(1, idAbonoCredito);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("SELECT ID_ABONO_CREDITO_PK ,ID_CREDITO_FK ,MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK  FROM ABONO_CREDITO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(BigDecimal idAbonoCredito) {
        try {

            Query query = em.createNativeQuery("SELECT ID_ABONO_CREDITO_PK ,ID_CREDITO_FK ,MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK  FROM ABONO_CREDITO WHERE ID_ABONO_CREDITO_PK  = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idAbonoCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_ABONO_CREDITO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getByIdCredito(BigDecimal idAbonoCredito) {
         try {
            Query query = em.createNativeQuery("select * from ABONO_CREDITO abc\n" +
"where abc.ESTATUS = 2 and abc.TIPO_ABONO_FK=3 and abc.ID_CREDITO_FK ='"+idAbonoCredito+"'");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getChequesPendientes(String fechaInicio, String fechaFin) {
        System.out.println("Fecha fin: "+fechaFin);
        try {
            Query query = em.createNativeQuery("select * from ABONO_CREDITO ab WHERE TO_DATE(TO_CHAR(ab.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')\n" +
"< '"+fechaFin+"' and ab.ESTATUS=2 order by ab.FECHA_COBRO asc");
            List<Object[]> resultList = null;
            System.out.println("query:"+query);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

}

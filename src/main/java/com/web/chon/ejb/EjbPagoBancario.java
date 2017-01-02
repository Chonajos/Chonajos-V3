/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.PagosBancarios;
import com.web.chon.negocio.NegocioOperacionesCuentas;
import com.web.chon.negocio.NegocioPagosBancarios;
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
@Stateless(mappedName = "ejbPagoBancario")
public class EjbPagoBancario implements NegocioPagosBancarios{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertaPagoBancario(PagosBancarios pb) {
        System.out.println("Objeto: "+pb.toString());
          try {
              
            Query query = em.createNativeQuery("INSERT INTO  PAGOS_BANCARIOS (ID_TRANS_BANCARIAS_PK,ID_CAJA_FK,ID_CONCEPTO_FK,ID_TIPO_FK,COMENTARIOS,ID_USER_FK,MONTO,FECHA,ID_STATUS_FK,FECHA_TRANSFERENCIA,FOLIO_ELECTRONICO,FECHA_DEPOSITO,ID_CUENTA_FK,CONCEPTO,REFERENCIA,ID_OPERACION_FK,ID_TIPO_TD,ID_LLAVE_FK) values(?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, pb.getIdTransBancariasPk());
            query.setParameter(2, pb.getIdCajaFk());
            query.setParameter(3, pb.getIdConceptoFk());
            query.setParameter(4, pb.getIdTipoFk());
            query.setParameter(5, pb.getComentarios());
            query.setParameter(6, pb.getIdUserFk());
            query.setParameter(7, pb.getMonto());
            query.setParameter(8, pb.getIdStatusFk());
            query.setParameter(9, pb.getFechaTranferencia());
            query.setParameter(10, pb.getFolioElectronico());
            query.setParameter(11, pb.getFechaDeposito());
            query.setParameter(12, pb.getIdCuentaFk());
            query.setParameter(13, pb.getConcepto());
            query.setParameter(14, pb.getReferencia());
            query.setParameter(15, pb.getIdOperacionCajaFk());
            query.setParameter(16, pb.getIdTipoTD());
            query.setParameter(17, pb.getIdLlaveFk());
            
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbOperacionesCuentas.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int updatePagoBancario(PagosBancarios pb) {
       try {
            System.out.println("ejb UPDATE" + pb.toString());
            Query query = em.createNativeQuery("UPDATE PAGOS_BANCARIOS SET ID_STATUS_FK = ? WHERE ID_TRANS_BANCARIAS_PK = ?");
            query.setParameter(1, pb.getIdStatusFk());
            query.setParameter(2, pb.getIdTransBancariasPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getPagosPendientes() {
        Query query = em.createNativeQuery("select pb.*,cj.NOMBRE,con.NOMBRE,ta.NOMBRE_ABONO,usu.NOMBRE_USUARIO,cue.NOMBRE_BANCO from PAGOS_BANCARIOS pb\n" +
"inner join caja cj on cj.ID_CAJA_PK = pb.ID_CAJA_FK\n" +
"inner join conceptos con on con.ID_CONCEPTOS_PK = pb.ID_CONCEPTO_FK\n" +
"inner join TIPO_ABONO ta on ta.ID_TIPO_ABONO_PK = pb.ID_TIPO_FK\n" +
"inner join USUARIO usu on usu.ID_USUARIO_PK = pb.ID_USER_FK\n" +
"inner join CUENTA_BANCARIA cue on cue.ID_CUENTA_BANCARIA_PK = pb.ID_CUENTA_FK\n" +
"where pb.ID_STATUS_FK = 2");
        return query.getResultList();
    }

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_PAGOS_BANCARIOS.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    
    }

    
}

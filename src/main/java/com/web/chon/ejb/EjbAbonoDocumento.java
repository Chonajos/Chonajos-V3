/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.AbonoDocumentos;
import com.web.chon.negocio.NegocioAbonoDocumento;
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
@Stateless(mappedName = "ejbAbonoDocumento")
public class EjbAbonoDocumento implements NegocioAbonoDocumento{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AbonoDocumentos abonoDocumento) {
               try {
            System.out.println("EJB: ==========" + abonoDocumento.toString());

            Query query = em.createNativeQuery("INSERT INTO  ABONO_DOCUMENTOS (ID_ABONO_DOCUMENTO_PK ,"
                    + "ID_DOCUMENTO_FK "
                    + ",MONTO_ABONO ,FECHA_ABONO ,ID_TIPO_ABONO_FK,ESTATUS,NUMERO_CHEQUE,"
                    + "LIBRADOR,FECHA_COBRO,BANCO_EMISOR,NUMERO_FACTURA,REFERENCIA,CONCEPTO,FECHA_TRANSFERENCIA,ID_USUARIO_FK,ID_CLIENTE_FK) "
                    + " VALUES(?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, abonoDocumento.getIdAbonoDocumentoPk());
            query.setParameter(2, abonoDocumento.getIdDocumentoFk());
            query.setParameter(3, abonoDocumento.getMontoAbono());
            //fecha
            query.setParameter(4, abonoDocumento.getIdTipoAbonoFk());
            query.setParameter(5, abonoDocumento.getEstatus());
            query.setParameter(6, abonoDocumento.getNumeroCheque());
            query.setParameter(7, abonoDocumento.getLibrador());
            query.setParameter(8, abonoDocumento.getFechaCobro());
            query.setParameter(9, abonoDocumento.getBanco());
            query.setParameter(10, abonoDocumento.getNumeroFactura());
            query.setParameter(11, abonoDocumento.getReferencia());
            query.setParameter(12, abonoDocumento.getConcepto());
            query.setParameter(13, abonoDocumento.getFechaTransferencia());
            query.setParameter(14, abonoDocumento.getIdUsuarioFk());
            query.setParameter(15, abonoDocumento.getIdClienteFk());
            return query.executeUpdate();

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbAbonoDocumento.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int update(AbonoDocumentos abonoDocumentos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal idAbonoDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getById(BigDecimal idAbonoDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
       Query query = em.createNativeQuery("SELECT S_ABONO_DOCUMENTOS.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getChequesPendientes(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idClienteFk, BigDecimal filtro, BigDecimal filtroStatus) {
        System.out.println("Fecha fin: " + fechaFin);
        System.out.println("IdSucursalEJB: " + idSucursal);
        StringBuffer cadena = new StringBuffer("select ab.*,dc.ID_DOCUMENTO_PK,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO "
                + "||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE, SD.DESCRIPCION, dc.ID_STATUS_FK from ABONO_CREDITO ab inner join USUARIO "
                + "u on u.ID_USUARIO_PK = ab.ID_USUARIO_FK inner join DOCUMENTOS_COBRAR dc on dc.ID_ABONO_FK "
                + "= ab.ID_ABONO_CREDITO_PK inner join CREDITO cre on cre.ID_CREDITO_PK = ab.ID_CREDITO_FK inner "
                + "join cliente cli on cli.ID_CLIENTE = cre.ID_CLIENTE_FK "
                + "inner join STATUS_DOCUMENTOS SD ON SD.ID_STATUS_DOCUMENTO_PK = DC.ID_STATUS_FK ");
        if (filtro.intValue() == 1) {
            cadena.append(" WHERE TO_DATE(TO_CHAR(ab.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')<= '" + fechaInicio + "'");
        cadena.append(" and ab.ESTATUS=1 and ab.TIPO_ABONO_FK=3 and dc.ID_STATUS_FK='"+filtroStatus+"'");
        } else if (filtro.intValue() == 2) {
            cadena.append(" WHERE TO_DATE(TO_CHAR(ab.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')> '" + fechaInicio + "'");
        cadena.append(" and ab.ESTATUS=1 and ab.TIPO_ABONO_FK=3 and dc.ID_STATUS_FK='"+filtroStatus+"'");
        }
        else{
            cadena.append(" where ab.ESTATUS=1 and ab.TIPO_ABONO_FK=3 and dc.ID_STATUS_FK='"+filtroStatus+"'");
        }

        if (idSucursal != null && !idSucursal.equals("")) {
            cadena.append(" and u.ID_SUCURSAL_FK='" + idSucursal + "'");
        }
        if (idClienteFk != null && !idClienteFk.equals("")) {
            cadena.append(" and cli.ID_CLIENTE ='" + idClienteFk + "'");
        }

        cadena.append(" order by ab.FECHA_COBRO asc");
        System.out.println("Query: " + cadena);
        Query query;

        query = em.createNativeQuery(cadena.toString());

        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

    @Override
    public BigDecimal getTotalAbonadoByIdDocumento(BigDecimal idDocumentoFk) {
        System.out.println("IdDocumento: "+idDocumentoFk);
       Query query = em.createNativeQuery("select NVL(sum(ad.MONTO_ABONO),0) as total_abonado from ABONO_DOCUMENTOS ad where ad.ID_DOCUMENTO_FK=?");
       query.setParameter(1, idDocumentoFk);
       return new BigDecimal(Integer.parseInt(query.getSingleResult().toString()));
    
    }
      
}

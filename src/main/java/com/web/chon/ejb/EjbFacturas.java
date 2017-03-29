/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.FacturaPDFDomain;
import com.web.chon.negocio.NeogocioFacturas;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jramirez
 */
@Stateless(mappedName = "ejbFacturas")
public class EjbFacturas implements NeogocioFacturas {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(FacturaPDFDomain factura) {
       
        try {
            Query query = em.createNativeQuery("INSERT INTO FACTURAS "
                    + "(ID_FACTURA_PK,ID_NUMERO_FACTURA,FECHA_TIMBRADO,ID_CLIENTE_FK,"
                    + "ID_SUCURSAL_FK,ID_LLAVE_VENTA_FK,\n" +
"ID_TIPO_LLAVE_FK,OBSERVACIONES,FECHA_EMISION,ID_USUARIO_FK,ID_STATUS_FK,NOMBRE_FACTURA_TIMBRADA,\n" +
"RFC_EMISOR,CADENA_ORIGINAL,IMPORTE,DESCUENTO,IVA) values (?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?)");
            query.setParameter(1, factura.getIdFacturaPk());
            query.setParameter(2, factura.getNumeroFactura());
            query.setParameter(3, factura.getFechaCertificacion());
            query.setParameter(4, factura.getIdClienteFk());
            query.setParameter(5, factura.getIdSucursalFk());
            query.setParameter(6, factura.getIdLlaveFk());
            query.setParameter(7, factura.getIdTipoLlaveFk());
            query.setParameter(8, factura.getComentarios());
            //Fecha Emsión
            //Fichero;
            query.setParameter(9, factura.getIdUsuarioFk());
            query.setParameter(10, factura.getIdStatusFk());
            query.setParameter(11, factura.getNombreArchivoTimbrado());
            query.setParameter(12, factura.getRfcEmisor());
            
            byte[] cad = factura.getCadena().getBytes();
            query.setParameter(13, cad);
            
            query.setParameter(14, factura.getImporte());
            query.setParameter(15, factura.getDescuento());
            query.setParameter(16, factura.getIva1());
            
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbFacturas.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    

    @Override
    public int delete(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getFacturaByIdPk(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getFacturaByIdNumeroFac(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getFacturasBy(BigDecimal idClienteFk, BigDecimal idSucursalFk, BigDecimal idFolioVentaFk, String fechaInicio, String fechaFin) {
//        System.out.println("idCliente: " + idClienteFk);
//        System.out.println("idSucursal: " + idSucursalFk);
//        System.out.println("folioVenta: " + idFolioVentaFk);
//        System.out.println("FechaIni: " + fechaInicio);
//        System.out.println("FechaFin: " + fechaFin);
        Query query;
        int cont = 0;
        StringBuffer cadena = new StringBuffer("select fa.ID_FACTURA_PK,fa.ID_NUMERO_FACTURA,fa.FECHA_TIMBRADO,fa.ID_CLIENTE_FK,fa.ID_SUCURSAL_FK,fa.ID_LLAVE_VENTA_FK, fa.OBSERVACIONES,\n"
                + "fa.FECHA_EMISION,fa.FICHERO,fa.ID_USUARIO_FK,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,\n"
                + "suc.NOMBRE_SUCURSAL,fa.ID_STATUS_FK,fa.NOMBRE_FACTURA_TIMBRADA,fa.RFC_EMISOR,fa.CADENA_ORIGINAL,fa.IMPORTE,fa.DESCUENTO,fa.IVA from FACTURAS fa\n"
                + "inner join CLIENTE cli on cli.ID_CLIENTE = fa.ID_CLIENTE_FK\n"
                + "inner join SUCURSAL suc on suc.ID_SUCURSAL_PK = fa.ID_SUCURSAL_FK ");

        if (!fechaInicio.equals("")) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" TO_DATE(TO_CHAR(fa.FECHA_TIMBRADO,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            cont++;

        }

        if (idSucursalFk != null && idSucursalFk.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" fa.ID_SUCURSAL_FK = '" + idSucursalFk + "' ");
            cont++;

        }

        if (idFolioVentaFk != null && idFolioVentaFk.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" fa.ID_LLAVE_VENTA_FK = '" + idFolioVentaFk + "' ");
            cont++;
        }

        if (idClienteFk != null && idClienteFk.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" fa.ID_CLIENTE_FK  = '" + idClienteFk + "' ");
            cont++;
        }

        cadena.append(" order by fa.FECHA_TIMBRADO");

        query = em.createNativeQuery(cadena.toString());
        //System.out.println("Query: " + query.toString());

        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_Facturas.NextVal from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            return 0;
        }

    }

    @Override
    public int insertarDocumento(BigDecimal id, byte[] fichero) throws SQLException {
       Query querys = em.createNativeQuery("update FACTURAS SET FICHERO = ? WHERE ID_FACTURA_PK = ?");
        querys.setParameter(1, fichero);
        querys.setParameter(2, id);
        return querys.executeUpdate();
    }
     @Override
    public int getLastNumeroFactura() {
        Query query = em.createNativeQuery("select nvl(MAX(ID_NUMERO_FACTURA),0) as NUMERO from FACTURAS ");
        return Integer.parseInt(query.getSingleResult().toString());

    }

}

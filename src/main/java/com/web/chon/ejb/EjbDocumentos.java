package com.web.chon.ejb;

import com.web.chon.dominio.Documento;
import com.web.chon.negocio.NegocioDocumentos;
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
@Stateless(mappedName = "ejbDocumentos")
public class EjbDocumentos implements NegocioDocumentos {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarDocumento(Documento documento) {

        System.out.println("EJBDOCUMENTOS: " + documento.toString());
        try {
            Query query = em.createNativeQuery("INSERT INTO  "
                    + "DOCUMENTOS_COBRAR (ID_DOCUMENTO_PK,ID_TIPO_DOCUMENTO,"
                    + "ID_CLIENTE_FK, ID_STATUS_FK, COMENTARIO,MONTO,NUMERO_CHEQUE"
                    + ",NUMERO_FACTURA,BANCO,LIBRADOR,FECHA_COBRO,ID_FORMA_COBRO_FK,ID_DOCUMENTO_PADRE_FK,ID_TIPO_D,ID_LLAVE,ID_SUCURSAL_FK) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, documento.getIdDocumentoPk());
            query.setParameter(2, documento.getIdTipoDocumento());
            query.setParameter(3, documento.getIdClienteFk());
            query.setParameter(4, documento.getIdStatusFk());
            query.setParameter(5, documento.getComentario());
            query.setParameter(6, documento.getMonto());
            query.setParameter(7, documento.getNumeroCheque());
            query.setParameter(8, documento.getFactura());
            query.setParameter(9, documento.getBanco());
            query.setParameter(10, documento.getLibrador());
            query.setParameter(11, documento.getFechaCobro());
            query.setParameter(12, documento.getIdFormaCobroFk());
            query.setParameter(13, documento.getIdDocumentoPadreFk());
            query.setParameter(14, documento.getIdTipoD());
            query.setParameter(15, documento.getIdLlave());
            query.setParameter(16, documento.getIdSucursalFk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getDocumentoByIdDocumentoPk(BigDecimal idDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getDocumentoByIdAbonoFk(BigDecimal idAbonoFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getDocumentosByIdClienteFk(BigDecimal idClienteFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getDocumentosByIdStatusFk(BigDecimal idStatusFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int nextVal() {
        try {
            Query query = em.createNativeQuery("select S_DOCUMENTOS_COBRAR.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int updateDocumento(Documento documento) {
        try {
            Query query = em.createNativeQuery("UPDATE  "
                    + "DOCUMENTOS_COBRAR SET ID_STATUS_FK = ? "
                    + "  WHERE ID_DOCUMENTO_PK = ?");

            query.setParameter(1, documento.getIdStatusFk());
            query.setParameter(2, documento.getIdDocumentoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getDocumentos(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idClienteFk, BigDecimal filtroFormaPago, BigDecimal filtroStatus, BigDecimal filtroFecha) {
        StringBuffer cadena = new StringBuffer("select dc.ID_DOCUMENTO_PK,dc.ID_TIPO_DOCUMENTO,dc.ID_CLIENTE_FK,dc.ID_STATUS_FK,dc.COMENTARIO,dc.MONTO,dc.NUMERO_CHEQUE,dc.NUMERO_FACTURA,dc.BANCO,dc.LIBRADOR,dc.FECHA_COBRO,dc.ID_FORMA_COBRO_FK,dc.ID_DOCUMENTO_PADRE_FK,dc.ID_TIPO_D,dc.ID_LLAVE,dc.ID_SUCURSAL_FK,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,fp.DESCRIPCION,\n"
                + "sd.DESCRIPCION,suc.NOMBRE_SUCURSAL\n"
                + "from DOCUMENTOS_COBRAR dc\n"
                + "inner join cliente cli on cli.ID_CLIENTE = dc.ID_CLIENTE_FK\n"
                + "inner join FORMAS_PAGO fp on fp.ID_FORMAS_PAGO_PK = dc.ID_FORMA_COBRO_FK\n"
                + "right join SUCURSAL suc on suc.ID_SUCURSAL_PK = dc.ID_SUCURSAL_FK \n" 
                + "inner join STATUS_DOCUMENTOS sd on sd.ID_STATUS_DOCUMENTO_PK = dc.ID_STATUS_FK");
        boolean bandera = false;
        if (filtroFecha.intValue() == 1) {
            cadena.append(" WHERE TO_DATE(TO_CHAR(dc.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')<= '" + fechaInicio + "'");
            bandera = true;
        } else if (filtroFecha.intValue() == 2) {
            cadena.append(" WHERE TO_DATE(TO_CHAR(dc.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')> '" + fechaInicio + "'");
            bandera = true;
        }
        if (filtroFormaPago != null && !filtroFormaPago.equals("")) {
            if (bandera == true) {
                cadena.append(" AND dc.ID_FORMA_COBRO_FK = '" + filtroFormaPago + "'");
            } else {
                cadena.append(" WHERE dc.ID_FORMA_COBRO_FK = '" + filtroFormaPago + "'");
                bandera = true;
            }
        }
        if (filtroStatus != null && !filtroStatus.equals("")) {
            if (bandera == true) {
                cadena.append(" AND dc.ID_STATUS_FK = '" + filtroStatus + "'");
            } else {
                cadena.append(" WHERE dc.ID_STATUS_FK = '" + filtroStatus + "'");
                bandera = true;
            }
        }
        if (idClienteFk != null && !idClienteFk.equals("")) {
            if (bandera == true) {
                cadena.append(" AND dc.ID_CLIENTE_FK = '" + idClienteFk + "'");
            } else {
                cadena.append(" WHERE dc.ID_CLIENTE_FK = '" + idClienteFk + "'");
                bandera = true;
            }
        }
        if (idSucursal != null && !idSucursal.equals("")) {
            if (bandera == true) {
                cadena.append(" AND dc.ID_SUCURSAL_FK = '" + idSucursal + "'");
            } else {
                cadena.append(" WHERE dc.ID_SUCURSAL_FK = '" + idSucursal + "'");
                bandera = true;
            }
        }
        cadena.append(" order by DC.FECHA_COBRO asc");

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
    public int cambiarFormaPago(Documento d) {
        try {
            Query query = em.createNativeQuery("UPDATE  "
                    + "DOCUMENTOS_COBRAR SET ID_FORMA_COBRO_FK = ? "
                    + "  WHERE ID_DOCUMENTO_PK = ?");

            query.setParameter(1, d.getIdFormaCobroFk());
            query.setParameter(2, d.getIdDocumentoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getDocumentoByTipoLlave(BigDecimal idTipo, BigDecimal idLlave) {
       Query query = em.createNativeQuery("select * from DOCUMENTOS_COBRAR dc where dc.ID_TIPO_D = ? and dc.ID_LLAVE = ?");
       query.setParameter(1, idTipo);
       query.setParameter(2, idLlave);
       return query.getResultList();
    }

}

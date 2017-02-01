package com.web.chon.ejb;

import com.web.chon.dominio.OperacionesCaja;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioOperacionesCaja;
import java.sql.SQLException;

/**
 *
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbOperacionesCaja")
public class EjbOperacionesCaja implements NegocioOperacionesCaja {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_OPERACIONES_CAJA.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            //System.out.println("error >" + ex.getMessage());
            return 0;
        }

    }

    @Override
    public int insertaOperacion(OperacionesCaja es) {

        try {
            Query query = em.createNativeQuery("INSERT INTO OPERACIONES_CAJA (ID_OPERACIONES_CAJA_PK,ID_CORTE_CAJA_FK,ID_CAJA_FK,ID_CAJA_DESTINO_FK,ID_CONCEPTO_FK,FECHA,ID_STATUS_FK,ID_USER_FK,COMENTARIOS,MONTO,E_S,ID_CUENTA_DESTINO_FK,ID_SUCURSAL_FK,ID_FORM_PAGO_FK,ID_TIPO_OPERACION_FK) values(?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, es.getIdOperacionesCajaPk());
            query.setParameter(2, es.getIdCorteCajaFk());
            query.setParameter(3, es.getIdCajaFk());
            query.setParameter(4, es.getIdCajaDestinoFk());
            query.setParameter(5, es.getIdConceptoFk());
            query.setParameter(6, es.getIdStatusFk());
            query.setParameter(7, es.getIdUserFk());
            query.setParameter(8, es.getComentarios());
            query.setParameter(9, es.getMonto());
            query.setParameter(10, es.getEntradaSalida());
            query.setParameter(11, es.getIdCuentaDestinoFk());
            query.setParameter(12, es.getIdSucursalFk());
            query.setParameter(13, es.getIdFormaPago());
            query.setParameter(14, es.getIdTipoOperacionFk());
            return query.executeUpdate();

        } catch (Exception ex) {

            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateOperacion(OperacionesCaja es) {

        try {
            //System.out.println("ejb UPDATE" + es.toString());
            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_CORTE_CAJA_FK = ?,ID_CAJA_DESTINO_FK = ?,ID_CONCEPTO_FK = ?,FECHA = ?,ID_STATUS_FK = ?,ID_USER_FK = ?,COMENTARIOS = ?,MONTO=? WHERE ID_OPERACIONES_CAJA_PK = ?");

            query.setParameter(1, es.getIdCorteCajaFk());
            query.setParameter(2, es.getIdCajaFk());
            query.setParameter(3, es.getIdCajaDestinoFk());
            query.setParameter(4, es.getIdConceptoFk());
            query.setParameter(5, es.getFecha());
            query.setParameter(6, es.getIdStatusFk());
            query.setParameter(7, es.getIdUserFk());
            query.setParameter(8, es.getComentarios());
            query.setParameter(9, es.getMonto());
            query.setParameter(10, es.getIdOperacionesCajaPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getOperacionByIdOperacionPK(BigDecimal idOperacionPk) {
        System.out.println("idOperacionPk: " + idOperacionPk);
        //System.out.println("getOperacionByIdOperacionPK : " + idOperacionPk);
        Query query = em.createNativeQuery("SELECT * FROM OPERACIONES_CAJA WHERE ID_OPERACIONES_CAJA_PK = ?");
        query.setParameter(1, idOperacionPk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getOperacionesBy(BigDecimal idCajaFk, BigDecimal idOperacionFk, BigDecimal idConceptoFk, String fechaInicio, String fechaFin, BigDecimal idStatusFk, BigDecimal idUserFk, BigDecimal idCorte, BigDecimal inout, BigDecimal idFormaPago) {
        StringBuffer cadena = new StringBuffer("select opc.*,cj.NOMBRE,con.NOMBRE "
                + "as concepto,tio.NOMBRE as Operacion,u.NOMBRE_USUARIO,sucu.NOMBRE_SUCURSAL,ta.NOMBRE_ABONO from OPERACIONES_CAJA opc\n"
                + "inner join caja cj on cj.ID_CAJA_PK = opc.ID_CAJA_FK\n"
                + "inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n"
                + "inner join USUARIO u on u.ID_USUARIO_PK = opc.ID_USER_FK\n"
                + "inner join TIPO_ABONO ta on ta.ID_TIPO_ABONO_PK = opc.ID_FORM_PAGO_FK\n"
                + "inner join sucursal sucu on sucu.ID_SUCURSAL_PK = opc.ID_SUCURSAL_FK\n "
                + " WHERE opc.ID_CAJA_FK = " + idCajaFk + "\n"
                + "and opc.ID_USER_FK= " + idUserFk + " and TO_DATE(TO_CHAR(opc.FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "'");

        if (idOperacionFk != null && idOperacionFk.intValue() != 0) {
            cadena.append(" and tio.ID_TIPO_OPERACION_PK= '" + idOperacionFk + "' ");
        }
        if (idConceptoFk != null && idConceptoFk.intValue() != 0) {
            cadena.append(" and opc.ID_CONCEPTO_FK = '" + idConceptoFk + "' ");
        }
        if (idStatusFk != null && idStatusFk.intValue() != 0) {
            cadena.append(" and opc.ID_STATUS_FK = '" + idStatusFk + "' ");
        }
        if (inout != null && inout.intValue() != 0) {
            cadena.append(" and opc.E_S =  '" + inout + "' ");
        }
        if (idFormaPago != null && idFormaPago.intValue() != 0) {
            cadena.append(" and opc.ID_FORM_PAGO_FK =  '" + idFormaPago + "' ");
        }

//        if (idCorte.intValue()==1) 
//        {
//            cadena.append(" and opc.ID_CORTE_CAJA_FK is null ");
//        }else
//        {
//             cadena.append(" and opc.ID_CORTE_CAJA_FK is not null ");
//        }
        System.out.println("Cadena: " + cadena);
        Query query;
        query = em.createNativeQuery(cadena.toString());
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTransferenciasEntrantes(BigDecimal idCajaFk) {
        Query query = em.createNativeQuery("select opc.*,cj1.NOMBRE,con.NOMBRE as concepto,tio.NOMBRE as Operacion,u.NOMBRE_USUARIO from OPERACIONES_CAJA opc\n"
                + "inner join caja cj on cj.ID_CAJA_PK = opc.ID_CAJA_DESTINO_FK\n"
                + "inner join caja cj1 on cj1.ID_CAJA_PK = opc.ID_CAJA_FK\n"
                + "inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n"
                + "inner join USUARIO u on u.ID_USUARIO_PK = opc.ID_USER_FK\n"
                + "where opc.ID_STATUS_FK=2 and opc.E_S=2 and opc.ID_CAJA_DESTINO_FK= ?");
        query.setParameter(1, idCajaFk);
        return query.getResultList();
    }

    @Override
    public int updateStatusConceptoOperacion(BigDecimal idOperacionPk, BigDecimal idStatusFk, BigDecimal idConceptoFk) {
        try {

            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_STATUS_FK = ?, ID_CONCEPTO_FK = ? WHERE ID_OPERACIONES_CAJA_PK = ?");

            query.setParameter(1, idStatusFk);
            query.setParameter(2, idConceptoFk);
            query.setParameter(3, idOperacionPk);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES) {
        Query query = em.createNativeQuery("select con.ID_TIPO_OPERACION_FK,tio.NOMBRE,sum(opc.MONTO) from operaciones_caja opc\n"
                + "inner join usuario u on u.ID_USUARIO_PK = opc.ID_USER_FK\n"
                + "inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n"
                + "where opc.ID_STATUS_FK=1 and opc.E_S=? and opc.ID_CORTE_CAJA_FK is null\n"
                + "and opc.ID_USER_FK = ? and opc.ID_CAJA_FK = ? \n"
                + "group by con.ID_TIPO_OPERACION_FK,tio.NOMBRE");
//        System.out.println("===========Consulta=========");
//        System.out.println(query);
//        System.out.println("Variables: " + "Caja: " + idCajaFk + "User: " + idUserFk + " E/S: " + idES);
        query.setParameter(1, idES);
        query.setParameter(2, idUserFk);
        query.setParameter(3, idCajaFk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getOperaciones(BigDecimal idCajaFk, BigDecimal idUserFk) {
        Query query = em.createNativeQuery("select opc.* from operaciones_caja opc\n"
                + "inner join usuario u on u.ID_USUARIO_PK = opc.ID_USER_FK\n"
                + "inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n"
                + "where opc.ID_STATUS_FK=1  and opc.ID_CORTE_CAJA_FK is null\n"
                + "and opc.ID_USER_FK = ? and opc.ID_CAJA_FK = ?");
//        System.out.println("===========Consulta=========");
//        System.out.println(query);
//        System.out.println("Variables: " + "Caja: " + idCajaFk + "User: " + idUserFk);
        query.setParameter(1, idUserFk);
        query.setParameter(2, idCajaFk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idINOUT) {
        Query query = em.createNativeQuery("select * from OPERACIONES_CAJA opc where opc.ID_CONCEPTO_FK = 12\n"
                + "and opc.ID_CAJA_FK = ? and opc.ID_USER_FK = ? and opc.ID_CORTE_CAJA_FK is null and opc.E_S=?");
        query.setParameter(1, idCajaFk);
        query.setParameter(2, idUserFk);
        query.setParameter(3, idINOUT);
        return query.getResultList();
    }

    @Override
    public int updateCorteCaja(BigDecimal idOperacionPk, BigDecimal idCorteCajaFk) {
        try {
            //System.out.println("ejb UPDATE" + idOperacionPk);
            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_CORTE_CAJA_FK = ? WHERE ID_OPERACIONES_CAJA_PK = ?");
            query.setParameter(1, idCorteCajaFk);
            query.setParameter(2, idOperacionPk);
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getDepositosEntrantes(BigDecimal idSucursalFk) {
        StringBuffer cadena = new StringBuffer("select opc.ID_OPERACIONES_CAJA_PK,opc.MONTO,caja.NOMBRE,con.NOMBRE as concepto,tio.NOMBRE as \n"
                + "Operacion,opc.FECHA,u.NOMBRE_USUARIO,cb.NOMBRE_BANCO,cb.CUENTA,opc.COMENTARIOS,\n"
                + "opc.ID_CONCEPTO_FK, opc.ID_CAJA_FK,opc.ID_CUENTA_DESTINO_FK,opc.ID_USER_FK,opc.ID_SUCURSAL_FK\n"
                + "from OPERACIONES_CAJA opc \n"
                + "inner join CUENTA_BANCARIA cb on cb.ID_CUENTA_BANCARIA_PK= opc.ID_CUENTA_DESTINO_FK\n"
                + "inner join caja on caja.ID_CAJA_PK = opc.ID_CAJA_FK\n"
                + "inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n"
                + "inner join USUARIO u on u.ID_USUARIO_PK = opc.ID_USER_FK\n"
                + "where opc.ID_STATUS_FK=2 and opc.ID_CAJA_DESTINO_FK is null and opc.E_S=2 and opc.ID_CONCEPTO_FK = 10");
        if (idSucursalFk != null && idSucursalFk.intValue() != -1) {
            cadena.append(" and opc.ID_SUCURSAL_FK = " + idSucursalFk + "");
        }
        Query query;
        query = em.createNativeQuery(cadena.toString());
        //query.setParameter(1, idCajaFk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getResponsables(BigDecimal idCajaFk) {
        //System.out.println("IDCAJA: "+idCajaFk);
        StringBuffer cadena = new StringBuffer("select u.ID_USUARIO_PK,(u.NOMBRE_USUARIO||' '||u.APATERNO_USUARIO||' '||u.AMATERNO_USUARIO) AS nombreCompleto \n"
                + "from caja cj inner join USUARIO u on u.ID_USUARIO_PK = cj.ID_USER_FK");
        if (idCajaFk != null && idCajaFk.intValue() != -1) {
            cadena.append(" where cj.ID_CAJA_PK = " + idCajaFk + "");
        }
        //System.out.println("CADENA: "+cadena);
        Query query;
        query = em.createNativeQuery(cadena.toString());
        //query.setParameter(1, idCajaFk);
        return query.getResultList();

    }

    @Override
    public List<Object[]> getDetalles(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk) {
        Query query = em.createNativeQuery("select opc.ID_CONCEPTO_FK,con.NOMBRE, opc.ID_SUCURSAL_FK,sucu.NOMBRE_SUCURSAL,sum(opc.MONTO) as monto,opc.ID_CONCEPTO_FK,opc.E_S from OPERACIONES_CAJA opc\n"
                + "inner join conceptos con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join SUCURSAL sucu on sucu.ID_SUCURSAL_PK = opc.ID_SUCURSAL_FK\n"
                + "where opc.E_S = ? and opc.ID_STATUS_FK = ? and opc.ID_CAJA_FK = ? and opc.ID_USER_FK = ? and opc.ID_CORTE_CAJA_FK is null\n"
                + "group by opc.ID_CONCEPTO_FK,con.NOMBRE,opc.ID_SUCURSAL_FK,sucu.NOMBRE_SUCURSAL,opc.E_S");
//        System.out.println("===========Consulta=========");
//        System.out.println(query);
//        System.out.println("Variables: " + "Caja: " + idCajaFk + " User: " + idUserFk +" E/S: "+entrada_salida + " Status: "+idStatusFk);
        query.setParameter(1, entrada_salida);
        query.setParameter(2, idStatusFk);
        query.setParameter(3, idCajaFk);
        query.setParameter(4, idUserFk);

        return query.getResultList();

    }

    @Override
    public List<Object[]> getDetallesCorte(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk, BigDecimal idCorteFk) {
        Query query = em.createNativeQuery("select opc.ID_CONCEPTO_FK,con.NOMBRE, opc.ID_SUCURSAL_FK,sucu.NOMBRE_SUCURSAL,sum(opc.MONTO) as monto,opc.ID_CONCEPTO_FK,opc.E_S from OPERACIONES_CAJA opc\n"
                + "inner join conceptos con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join SUCURSAL sucu on sucu.ID_SUCURSAL_PK = opc.ID_SUCURSAL_FK\n"
                + "where opc.E_S = ? and opc.ID_STATUS_FK = ? and opc.ID_CAJA_FK = ? and opc.ID_USER_FK = ? and opc.ID_CORTE_CAJA_FK =? \n"
                + "group by opc.ID_CONCEPTO_FK,con.NOMBRE,opc.ID_SUCURSAL_FK,sucu.NOMBRE_SUCURSAL,opc.E_S");
//        System.out.println("===========Consulta=========");
//        System.out.println(query);
//        System.out.println("Variables: " + "Caja: " + idCajaFk + " User: " + idUserFk + " E/S: " + entrada_salida + " Status: " + idStatusFk + " IdCorte: " + idCorteFk);
        query.setParameter(1, entrada_salida);
        query.setParameter(2, idStatusFk);
        query.setParameter(3, idCajaFk);
        query.setParameter(4, idUserFk);
        query.setParameter(5, idCorteFk);

        return query.getResultList();

    }

    @Override
    public List<Object[]> getOperacionesByIdCorteCajaFk(BigDecimal idCorteCajaFk, BigDecimal entrada_salida) {
        Query query = em.createNativeQuery("select con.ID_TIPO_OPERACION_FK,tio.NOMBRE,sum(opc.MONTO) from operaciones_caja opc\n"
                + "inner join usuario u on u.ID_USUARIO_PK = opc.ID_USER_FK\n"
                + "inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n"
                + "where opc.ID_STATUS_FK=1  and opc.ID_CORTE_CAJA_FK = ? and opc.E_S = ?\n"
                + "  \n"
                + "group by con.ID_TIPO_OPERACION_FK,tio.NOMBRE");

        query.setParameter(1, idCorteCajaFk);
        query.setParameter(2, entrada_salida);
        return query.getResultList();

    }

    @Override
    public List<Object[]> getOperaciones(BigDecimal idCajaFk, BigDecimal idEntradaSalida, BigDecimal idUsuarioFk) {
        StringBuffer cadena = new StringBuffer("select opcaja.ID_CONCEPTO_FK,opcaja.FECHA,cj.NOMBRE as nombre_caja,cn.NOMBRE as concepto,opcaja.COMENTARIOS, opcaja.MONTO,opcaja.ID_STATUS_FK from OPERACIONES_CAJA opcaja \n"
                + "inner join CONCEPTOS cn on cn.ID_CONCEPTOS_PK = opcaja.ID_CONCEPTO_FK\n"
                + "inner join caja cj on cj.ID_CAJA_PK = opcaja.ID_CAJA_FK\n"
                + "where opcaja.ID_CORTE_CAJA_FK is null");

        if (idCajaFk != null && idCajaFk.intValue() != 0) {
            cadena.append(" and opcaja.ID_CAJA_FK= '" + idCajaFk + "' ");
        }
        if (idEntradaSalida != null && idEntradaSalida.intValue() != 0) {
            cadena.append(" and opcaja.E_S = '" + idEntradaSalida + "' ");
        }
        if (idUsuarioFk != null && idUsuarioFk.intValue() != 0) {
            cadena.append(" and opcaja.ID_USER_FK = '" + idUsuarioFk + "' ");
        }

        Query query;
        query = em.createNativeQuery(cadena.toString());
        return query.getResultList();

    }

    /**
     *
     * @param id
     * @param fichero
     * @throws SQLException
     */
    @Override
    public void insertarDocumento(BigDecimal id, byte[] fichero) throws SQLException {

        Query querys = em.createNativeQuery("update OPERACIONES_CAJA SET FICHERO = ? WHERE ID_OPERACIONES_CAJA_PK = ?");
        querys.setParameter(1, fichero);
        querys.setParameter(2, id);
        querys.executeUpdate();

    }

    @Override
    public List<Object[]> getOperacionesByCategoria(BigDecimal idCategoriaFk, BigDecimal idSucursalFk, BigDecimal idCajaFk, BigDecimal idStatusFk, BigDecimal idConceptoFk, BigDecimal idTipoOperacionFk, String fechaInicio, String fechaFin) {
        StringBuffer cadena = new StringBuffer("select op.ID_OPERACIONES_CAJA_PK,sucu.NOMBRE_SUCURSAL,cj.NOMBRE,op.ID_SUCURSAL_FK,sto.NOMBRE,op.ID_STATUS_FK,\n"
                + "op.FECHA,tip.NOMBRE as OPERACION,cn.NOMBRE AS CONCEPTO,\n"
                + "op.MONTO,op.COMENTARIOS,op.FICHERO,op.ID_CONCEPTO_FK  from OPERACIONES_CAJA op\n"
                + "inner join CONCEPTOS cn on cn.ID_CONCEPTOS_PK = op.ID_CONCEPTO_FK\n"
                + "inner join TIPOS_OPERACION tip on tip.ID_TIPO_OPERACION_PK = op.ID_TIPO_OPERACION_FK\n"
                + "inner join GRUPO_CATEGORIAS gc on gc.ID_GRUPO_CATEGORIAS_PK=tip.ID_GRUPO_FK\n"
                + "inner join SUCURSAL sucu on sucu.ID_SUCURSAL_PK = op.ID_SUCURSAL_FK\n"
                + "inner join STATUS_OPERACIONES sto on sto.ID_STATUS_OPERACIONES_PK=op.ID_STATUS_FK\n"
                + "inner join CAJA cj on cj.ID_CAJA_PK = op.ID_CAJA_FK\n"
                + "where ");

        if (idCategoriaFk != null && idCategoriaFk.intValue() != 0) {
            cadena.append(" gc.ID_GRUPO_CATEGORIAS_PK= '" + idCategoriaFk + "' ");
        }
        if (!fechaInicio.equals("")) {
            cadena.append(" AND TO_DATE(TO_CHAR(op.FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
        }
        if (idSucursalFk != null && idSucursalFk.intValue() != 0) {
            cadena.append(" and op.ID_SUCURSAL_FK='" + idSucursalFk + "' ");
        }
        if (idCajaFk != null && idCajaFk.intValue() != 0) {
            cadena.append(" and op.ID_CAJA_FK= '" + idCajaFk + "' ");
        }
        if (idStatusFk != null && idStatusFk.intValue() != 0) {
            cadena.append(" and op.ID_STATUS_FK= '" + idStatusFk + "' ");
        }
        if (idConceptoFk != null && idConceptoFk.intValue() != 0) {
            cadena.append(" and op.ID_CONCEPTO_FK ='" + idConceptoFk + "' ");
        }
        if (idTipoOperacionFk != null && idTipoOperacionFk.intValue() != 0) {
            cadena.append(" and op.ID_TIPO_OPERACION_FK= '" + idTipoOperacionFk + "' ");
        }
        cadena.append(" order by op.ID_OPERACIONES_CAJA_PK");

        Query query;
        query = em.createNativeQuery(cadena.toString());
        System.out.println("====================================================");
        System.out.println("Query: " + query.toString());
        return query.getResultList();

    }

    @Override
    public List<Object[]> getGenerales(BigDecimal idCajaFk, BigDecimal idEntradaSalida, BigDecimal idUsuarioFk, BigDecimal idStatusFk, BigDecimal idSucursalFk, BigDecimal TIPO) {
        StringBuffer cadena=null;
        if (TIPO != null && TIPO.intValue() == 1) 
        {
            cadena = new StringBuffer("SELECT ta.NOMBRE_ABONO,SUM(OC.MONTO) as monto,ta.ID_TIPO_ABONO_PK FROM OPERACIONES_CAJA OC  \n"
                    + "inner join TIPO_ABONO ta on ta.ID_TIPO_ABONO_PK = oc.ID_FORM_PAGO_FK\n"
                    + "where  oc.ID_CORTE_CAJA_FK is null  ");
        }
        else
        {
            cadena = new StringBuffer("select tio.NOMBRE as operacion,cn.NOMBRE as concepto,ta.NOMBRE_ABONO, sum(oc.MONTO) as monto\n" +
"from OPERACIONES_CAJA oc inner join\n" +
"CONCEPTOS cn on cn.ID_CONCEPTOS_PK = oc.ID_CONCEPTO_FK\n" +
"inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = oc.ID_TIPO_OPERACION_FK\n" +
"inner join TIPO_ABONO ta on ta.ID_TIPO_ABONO_PK = oc.ID_FORM_PAGO_FK\n" +
"where oc.ID_CORTE_CAJA_FK is null");
        }

        if (idCajaFk != null && idCajaFk.intValue() != 0) {
            cadena.append(" and oc.ID_CAJA_FK= " + idCajaFk + "");
        }
        if (idSucursalFk != null && idSucursalFk.intValue() != 0) {
            cadena.append(" and oc.ID_SUCURSAL_FK = " + idSucursalFk + "");
        }
        if (idStatusFk != null && idStatusFk.intValue() != 0) {
            cadena.append(" and oc.ID_STATUS_FK = " + idStatusFk + "");
        }
        if (idEntradaSalida != null && idEntradaSalida.intValue() != 0) {
            cadena.append(" and oc.E_S =  " + idEntradaSalida + "");
        }
        if (idUsuarioFk != null && idUsuarioFk.intValue() != 0) {
            cadena.append(" and oc.ID_USER_FK =  " + idUsuarioFk + "");
        }
        
        if (TIPO != null && TIPO.intValue() == 1) 
        {
           cadena.append(" group by ta.NOMBRE_ABONO,ta.ID_TIPO_ABONO_PK ");
        }
        else
        {
            cadena.append(" group by tio.NOMBRE ,cn.NOMBRE ,ta.NOMBRE_ABONO ");
        }
        System.out.println("idEntradaSalida : "+idEntradaSalida +" idCajaFk:"+idCajaFk+ " idUsuarioFk:  "+idUsuarioFk+" idStatusFk: "+idStatusFk + " idSucursalFk: "+idSucursalFk +" TIPO :"+TIPO);
        
        Query query;
        query = em.createNativeQuery(cadena.toString());
        System.out.println("====================================================");
        System.out.println("Query: " + query.toString());
        return query.getResultList();

    }

    @Override
    public int updateCortebyIdCaja(BigDecimal idCajaFk, BigDecimal idCorteFk) {
        try {
            System.out.println("Parametros: "+idCajaFk + "  "+idCorteFk);
            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_CORTE_CAJA_FK = ? WHERE ID_CAJA_FK = ? and ID_STATUS_FK=1 and ID_CORTE_CAJA_FK is null");
            query.setParameter(1, idCorteFk);
            query.setParameter(2, idCajaFk);
            System.out.println("Query: "+query.toString());
            return query.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error> "+ex.toString());
            Logger.getLogger(EjbOperacionesCaja.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

}

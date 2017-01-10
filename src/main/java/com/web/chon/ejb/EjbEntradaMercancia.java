/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.negocio.NegocioEntradaMercancia;
import com.web.chon.util.TiempoUtil;
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
 * @author freddy
 */
@Stateless(mappedName = "ejbEntradaMercancia")
public class EjbEntradaMercancia implements NegocioEntradaMercancia {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMercancia(EntradaMercancia entrada) {
        //System.out.println("EJB_INSERTA_ENTRADAMERCANCIA");
        try {
            System.out.println("Entrada: " + entrada.toString());
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMERCANCIA (ID_EM_PK,ID_PROVEDOR_FK,MOVIMIENTO,"
                    + "FECHA,REMISION,ID_SUCURSAL_FK,IDENTIFICADOR,ID_STATUS_FK,KILOSTOTALES,KILOSTOTALESPROVEDOR,"
                    + "COMENTARIOS,FECHAREMISION,CARROSUCURSAL,ID_USUARIO_FK,CANTIDADTOTAL,CANTIDADTOTALPROVEDOR,"
                    + "FECHA_PAGO)VALUES (?,?,?,sysdate,?,?,?,1,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, entrada.getIdEmPK());
            query.setParameter(2, entrada.getIdProvedorFK());
            query.setParameter(3, entrada.getMovimiento());
            //fecha
            query.setParameter(4, entrada.getRemision());
            query.setParameter(5, entrada.getIdSucursalFK());
            query.setParameter(6, entrada.getFolio());
            //status entrada
            query.setParameter(7, entrada.getKilosTotales());
            query.setParameter(8, entrada.getKilosTotalesProvedor());
            query.setParameter(9, entrada.getComentariosGenerales());
            query.setParameter(10, entrada.getFechaRemision());
            query.setParameter(11, entrada.getIdCarroSucursal());
            query.setParameter(12, entrada.getIdUsuario());
            query.setParameter(13, entrada.getCantidadEmpaquesReales());
            query.setParameter(14, entrada.getCantidadEmpaquesProvedor());
            query.setParameter(15, entrada.getFechaPago());
            

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public int buscaMaxMovimiento(EntradaMercancia entrada) {
        //System.out.println("EJB_BUSCA_MAX_MOVIMIENTO");
        try {
            //System.out.println("Entrada: " + entrada.toString());
            Query query = em.createNativeQuery("select max(movimiento) from ENTRADAMERCANCIA where ID_PROVEDOR_FK = ? AND FECHA BETWEEN ? AND ?");
            query.setParameter(1, entrada.getIdProvedorFK());
            //query.setParameter(2, entrada.getIdSucursalFK());
            query.setParameter(2, entrada.getFechaFiltroInicio());
            query.setParameter(3, entrada.getFechaFiltroFin());
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            //Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_ENTRADAMERCANCIA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor,BigDecimal carro) {
        int cont = 0;
        StringBuffer query = new StringBuffer("SELECT EMA.ID_EM_PK,EMA.ID_PROVEDOR_FK,EMA.MOVIMIENTO,EMA.FECHA,EMA.REMISION,EMA.ID_SUCURSAL_FK,EMA.IDENTIFICADOR,\n" +
"EMA.ID_STATUS_FK,EMA.KILOSTOTALES,EMA.KILOSTOTALESPROVEDOR,EMA.COMENTARIOS,EMA.FECHAREMISION,PRO.\n" +
"NOMBRE_PROVEDOR ||' '|| PRO.A_PATERNO_PROVE ||' '|| \n" +
"PRO.A_MATERNO_PROVE AS NOMBRE_PROVEDOR, SUC.NOMBRE_SUCURSAL,EMA.CARROSUCURSAL,EMA.COMENTARIOS,EMA.CANTIDADTOTAL,\n" +
"EMA.CANTIDADTOTALPROVEDOR,EMA.FECHA_PAGO,usu.NOMBRE_USUARIO ||' '|| usu.APATERNO_USUARIO ||' '|| usu.AMATERNO_USUARIO as recibidor,EMA.id_usuario_fk FROM ENTRADAMERCANCIA EMA\n" +
"LEFT JOIN PROVEDORES PRO\n" +
"ON EMA.ID_PROVEDOR_FK = PRO.ID_PROVEDOR_PK\n" +
"LEFT JOIN SUCURSAL SUC \n" +
"ON EMA.ID_SUCURSAL_FK = SUC.ID_SUCURSAL_PK\n" +
"inner join USUARIO usu on usu.ID_USUARIO_PK = EMA.ID_USUARIO_FK");
        if (fechaInicio != null) {
            cont++;
            query.append(" WHERE TO_DATE(TO_CHAR(EMA.FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + TiempoUtil.getFechaDDMMYY(fechaInicio) + "' AND '" + TiempoUtil.getFechaDDMMYY(fechaFin) + "' ");
        }

        if (idSucursal != null && !idSucursal.equals(new BigDecimal(-1))) {
            if (cont == 0) {
                cont++;
                query.append(" WHERE ");
            } else {
                query.append(" AND ");
            }
            query.append(" EMA.ID_SUCURSAL_FK =" + idSucursal);
        }
        if (carro != null && !carro.equals(new BigDecimal(-1))) {
            if (cont == 0) {
                cont++;
                query.append(" WHERE ");
            } else {
                query.append(" AND ");
            }
            query.append(" EMA.CARROSUCURSAL =" + carro);
        }

        if (idProvedor != null && idProvedor != new BigDecimal(-1)) {
            if (cont == 0) {
                query.append(" WHERE ");
            } else {
                query.append(" AND ");
            }
            query.append(" EMA.ID_PROVEDOR_FK =" + idProvedor);
        }

        query.append(" ORDER BY EMA.ID_EM_PK");

        return em.createNativeQuery(query.toString()).getResultList();

    }

    @Override
    public List<Object[]> getSubEntradaByNombre(String clave) {
        //System.out.println("Ejb: " + clave);
        Query query = em.createNativeQuery("SELECT ID_EM_PK,IDENTIFICADOR FROM ENTRADAMERCANCIA WHERE UPPER(IDENTIFICADOR) LIKE '%" + clave + "%'");

        return query.getResultList();
    }

    @Override
    public List<Object[]> getEntradaById(BigDecimal id) {
        Query query = em.createNativeQuery("SELECT ID_EM_PK,IDENTIFICADOR FROM ENTRADAMERCANCIA WHERE ID_EM_PK = ?");
        query.setParameter(1, id);

        return query.getResultList();
    }

    @Override
    public int getCarroSucursal(BigDecimal idSucursal) {
        Query query = em.createNativeQuery("select count(*) from ENTRADAMERCANCIA where ID_SUCURSAL_FK=?");
        query.setParameter(1, idSucursal);
        return Integer.parseInt(query.getSingleResult().toString());

    }

    @Override
    public int deleteEntradaMercancia(EntradaMercancia entrada) {
        //System.out.println("EJB_DELETE EntradaMercancia");
        try {
            Query query = em.createNativeQuery("delete from ENTRADAMERCANCIA em where em.ID_EM_PK =?");
            query.setParameter(1, entrada.getIdEmPK());
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateEntradaMercancia(EntradaMercancia entrada) {

        System.out.println("------------------EJB_UPDATE_ENTRADAMERCANCIA---------------------");
        try {
            System.out.println("Entrada: " + entrada);
            Query query = em.createNativeQuery("UPDATE ENTRADAMERCANCIA SET ID_PROVEDOR_FK=?,MOVIMIENTO=?,"
                    + "FECHA=?,REMISION=?,ID_SUCURSAL_FK=?,IDENTIFICADOR=?,ID_STATUS_FK=?,KILOSTOTALES=?,KILOSTOTALESPROVEDOR=?,"
                    + "COMENTARIOS=?,FECHAREMISION=?,ID_USUARIO_FK=?,CARROSUCURSAL=?,CANTIDADTOTAL=? WHERE ID_EM_PK = ?");
            query.setParameter(1, entrada.getIdProvedorFK());
            query.setParameter(2, entrada.getMovimiento());
            query.setParameter(3, entrada.getFecha());
            query.setParameter(4, entrada.getRemision());
            query.setParameter(5, entrada.getIdSucursalFK());
            query.setParameter(6, entrada.getFolio());
            query.setParameter(7, entrada.getIdStatusFk());
            query.setParameter(8, entrada.getKilosTotales());
            query.setParameter(9, entrada.getKilosTotalesProvedor());
            query.setParameter(10, entrada.getComentariosGenerales());
            query.setParameter(11, entrada.getFechaRemision());
            query.setParameter(12, entrada.getIdUsuario());
            query.setParameter(13, entrada.getIdCarroSucursal());
            query.setParameter(14, entrada.getCantidadEmpaquesReales());
            query.setParameter(15, entrada.getIdEmPK());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getEntradaByIdEmPFk(BigDecimal idEmPFk) {
        //System.out.println("EJB_getEntradaByIdEmPFk: "+idEmPFk);
        Query query = em.createNativeQuery("select em.* from ENTRADAMERCANCIA em\n"
                + "inner join ENTRADAMERCANCIAPRODUCTO emp\n"
                + "on emp.ID_EM_FK = em.ID_EM_PK\n"
                + "where emp.ID_EMP_PK= ? ");
        query.setParameter(1, idEmPFk);
        return query.getResultList();

    }

    @Override
    public List<Object[]> getEntradaByIdPk(BigDecimal idPk) {
        //System.out.println("EJB_getEntradaByIdPkk: "+idPk);
        Query query = em.createNativeQuery("select em.* from ENTRADAMERCANCIA em\n"
                + "where em.ID_EM_PK = ?");
        query.setParameter(1, idPk);
        return query.getResultList();

    }

    @Override
    public List<Object[]> getCarrosByIdSucursalAndIdProvedor(BigDecimal idSucursal, BigDecimal idProvedor, BigDecimal carro) {

        try {
            StringBuffer txtQuery = new StringBuffer("SELECT ENM.CARROSUCURSAL,ENM.IDENTIFICADOR,ENM.FECHA, PRO.NOMBRE_PROVEDOR||' '||PRO.A_PATERNO_PROVE||' '||PRO.A_MATERNO_PROVE AS NOMBRE_PROVEDOR FROM ENTRADAMERCANCIA ENM "
                    + "INNER JOIN PROVEDORES PRO ON PRO.ID_PROVEDOR_PK = ENM.ID_PROVEDOR_FK");

            int cont = 0;
            if (idProvedor != null) {
                cont++;
                txtQuery.append(" WHERE ENM.ID_PROVEDOR_FK=" + idProvedor);
            }

            if (idSucursal != null) {

                cont++;
                if (cont != 0) {
                    txtQuery.append(" AND ENM.ID_SUCURSAL_FK=" + idSucursal);
                } else {
                    txtQuery.append(" WHERE ENM.ID_SUCURSAL_FK =" + idSucursal);
                }
            }
            
            if (carro != null) {

                if (cont != 0) {
                    txtQuery.append(" AND ENM.CARROSUCURSAL =" + carro);
                } else {
                    txtQuery.append(" WHERE ENM.CARROSUCURSAL =" + carro);
                }
            }

            txtQuery.append(" ORDER BY ENM.CARROSUCURSAL ASC");

            Query query = em.createNativeQuery(txtQuery.toString());

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}

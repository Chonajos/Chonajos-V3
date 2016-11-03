/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Sucursal;
import com.web.chon.negocio.NegocioCatSucursales;
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
@Stateless(mappedName = "ejbCatSucursales")
public class EjbCatSucursales implements NegocioCatSucursales {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getSucursales() {
        try {

            System.out.println("EJB_GET_CLIENTE");
            Query query = em.createNativeQuery("select * from sucursal");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getSucursalId(BigDecimal idSucursal) {

        try {

            Query query = em.createNativeQuery("SELECT * FROM SUCURSAL WHERE ID_SUCURSAL_PK = ?");
            query.setParameter(1, idSucursal);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteSucursal(BigDecimal idSucursal) {

        try {

            System.out.println("Id SUCURSAL a eliminar: " + idSucursal);
            Query query = em.createNativeQuery("UPDATE SUCURSAL  SET STATUS_SUCURSAL = ? WHERE ID_SUCURSAL_PK = ?");
            query.setParameter(1, 2);
            query.setParameter(2, idSucursal);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateSucursal(Sucursal sucu) {
        System.out.println("EJB_UPDATE_SUCURSAL");
        try {
            System.out.println("insert : " + sucu);
            Query query = em.createNativeQuery("UPDATE SUCURSAL SET NOMBRE_SUCURSAL = ?,CALLE_SUCURSAL = ?, CP_SUCURSAL = ?, TELEFONO_SUCURSAL = ?, NUM_INT = ?, NUM_EXT = ?, STATUS_SUCURSAL=? WHERE ID_SUCURSAL_PK=?");
            query.setParameter(1, sucu.getNombreSucursal());
            query.setParameter(2, sucu.getCalleSucursal());
            query.setParameter(3, sucu.getCpSucursal());
            query.setParameter(4, sucu.getTelefonoSucursal());
            query.setParameter(5, sucu.getNumInt());
            query.setParameter(6, sucu.getNumExt());
            query.setParameter(7, sucu.getIdStatusSucursalfk());
            query.setParameter(8, sucu.getIdSucursalPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int insertSucursal(Sucursal sucu) {
        System.out.println("EJB_INSERTA_SUCURSAL");
        try {
            System.out.println("insert : " + sucu);
            Query query = em.createNativeQuery("INSERT INTO SUCURSAL (ID_SUCURSAL_PK,NOMBRE_SUCURSAL,CALLE_SUCURSAL,CP_SUCURSAL,TELEFONO_SUCURSAL,NUM_INT,NUM_EXT,STATUS_SUCURSAL)"
                    + "VALUES(S_SUCURSAL.NextVal,?,?,?,?,?,?,?)");
            query.setParameter(1, sucu.getNombreSucursal());
            query.setParameter(2, sucu.getCalleSucursal());
            query.setParameter(3, sucu.getCpSucursal());
            query.setParameter(4, sucu.getTelefonoSucursal());
            query.setParameter(5, sucu.getNumInt());
            query.setParameter(6, sucu.getNumExt());
            query.setParameter(7, sucu.getIdStatusSucursalfk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_SUCURSAL.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Long getSizeListSucursales() {

        try {
            Query query = em.createNativeQuery("select count(id_sucursal_pk) from sucursal");
            BigDecimal value = (BigDecimal) query.getSingleResult();
            System.out.printf(value.toString());
//            Object[] o= (Object[]) query.getSingleResult();
            return value.longValue();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0l;
        }
    }

    @Override
    public List<Object[]> getSucursalesDetalle(int first, int pageSize) {
        try {
            System.out.println("EJB_getSucursalesDetalle");

            Query query = em.createNativeQuery("select * from (select s.*,sta.DESCRIPCION_STATUS,en.ID_ENTIDAD_PK,en.NOMBRE_ENTIDAD,m.ID_MUNICIPIO_PK,m.NOMBRE_MUNICIPIO,cp.NOMBRE_COLONIA,cp.CODIGO_POSTAL, "
                    + "row_number() over (order by s.ID_SUCURSAL_PK ASC) rn "
                    + "from sucursal s "
                    + "LEFT join STATUS sta "
                    + "on s.STATUS_SUCURSAL = sta.ID_PK "
                    + "LEFT JOIN CODIGOS_POSTALES cp "
                    + "on s.CP_SUCURSAL=cp.ID_PK "
                    + "LEFT JOIN Municipios m "
                    + "on cp.ID_MUNICIPIO_FK=m.id_municipio_pk "
                    + "LEFT JOIN ENTIDAD en "
                    + "on en.ID_ENTIDAD_PK=m.ID_ENTIDAD_FK) "
                    + "where rn between ? and ? + ?");
            query.setParameter(1, first);
            query.setParameter(2, first);
            query.setParameter(3, pageSize);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}

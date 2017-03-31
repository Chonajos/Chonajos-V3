/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.DatosFacturacion;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioDatosFacturacion;

/**
 *
 * @author jramirez
 */
@Stateless(mappedName = "ejbDatosFacturacion")
public class EjbDatosFacturacion implements NegocioDatosFacturacion {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getByIdCliente(BigDecimal idClienteFk) {
        //System.out.println("IDCLIENTE: " + idClienteFk);
        try {
            Query query = em.createNativeQuery("select df.ID_DATOS_FACTURACION_PK,df.ID_CLIENTE_FK,df.ID_SUCURSAL_FK,df.RAZON_SOCIAL ,df.RFC,df.CALLE,df.NUM_INT,df.NUM_EXT "
                    + ",df.PAIS,df.LOCALIDAD,df.ID_CP_FISCAL,df.TELEFONO,df.CORREO,df.REGIMEN "
                    + ",df.FIELD,df.RUTA_LLAVE_PRIVADA, "
                    + "df.RUTA_CERTIFICADO,df.RUTA_LLAVE_PRIVADA_CANCEL,df.RUTA_CERTIFICADO_CANCEL,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,cp.CODIGO_POSTAL,cp.NOMBRE_COLONIA,mun.NOMBRE_MUNICIPIO, en.NOMBRE_ENTIDAD "
                    + "from DATOS_FACTURACION df "
                    + "inner join CLIENTE cli on cli.ID_CLIENTE = df.ID_CLIENTE_FK "
                    + "inner join CODIGOS_POSTALES cp on cp.ID_PK = df.ID_CP_FISCAL "
                    + "inner join MUNICIPIOS mun on mun.ID_MUNICIPIO_PK = cp.ID_MUNICIPIO_FK "
                    + "inner join entidad en on en.ID_ENTIDAD_PK = mun.ID_ENTIDAD_FK "
                    + "where df.ID_CLIENTE_FK = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idClienteFk);
            //System.out.println("Query: " + query.toString());
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getByIdSucursal(BigDecimal idSucursalFk) {
        try {
            Query query = em.createNativeQuery("select df.ID_DATOS_FACTURACION_PK,df.ID_CLIENTE_FK,df.ID_SUCURSAL_FK,df.RAZON_SOCIAL ,df.RFC,df.CALLE,df.NUM_INT,df.NUM_EXT "
                    + ",df.PAIS,df.LOCALIDAD,df.ID_CP_FISCAL,df.TELEFONO,df.CORREO,df.REGIMEN "
                    + ",df.FIELD,df.RUTA_LLAVE_PRIVADA, "
                    + "df.RUTA_CERTIFICADO,df.RUTA_LLAVE_PRIVADA_CANCEL,df.RUTA_CERTIFICADO_CANCEL,df.CLAVE_PUBLICA, "
                    + "cp.CODIGO_POSTAL,cp.NOMBRE_COLONIA,mun.NOMBRE_MUNICIPIO, en.NOMBRE_ENTIDAD from DATOS_FACTURACION df "
                    + "inner join CODIGOS_POSTALES cp on cp.ID_PK = df.ID_CP_FISCAL "
                    + "inner join MUNICIPIOS mun on mun.ID_MUNICIPIO_PK = cp.ID_MUNICIPIO_FK "
                    + "inner join entidad en on en.ID_ENTIDAD_PK = mun.ID_ENTIDAD_FK "
                    + "");
            List<Object[]> resultList = null;

            query.setParameter(1, idSucursalFk);
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int delete(BigDecimal idDatosFacturacion) {
        try {

            Query query = em.createNativeQuery("DELETE DATOS_FACTURACION WHERE ID_DATOS_FACTURACION_PK = ?");
            query.setParameter(1, idDatosFacturacion);
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int insert(DatosFacturacion df) {
        try {

            Query query = em.createNativeQuery("INSERT INTO  DATOS_FACTURACION (ID_DATOS_FACTURACION_PK,ID_CLIENTE_FK,ID_SUCURSAL_FK,RAZON_SOCIAL,RFC,CALLE,NUM_INT,NUM_EXT,PAIS,LOCALIDAD,ID_CP_FISCAL,TELEFONO,CORREO,REGIMEN,FIELD,RUTA_LLAVE_PRIVADA,RUTA_CERTIFICADO,RUTA_LLAVE_PRIVADA_CANCEL,RUTA_CERTIFICADO_CANCEL,CLAVE_PUBLICA) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            query.setParameter(1, getNextVal());
            query.setParameter(2, df.getIdClienteFk());
            query.setParameter(3, df.getIdSucursalFk());
            query.setParameter(4, df.getRazonSocial());
            query.setParameter(5, df.getRfc());
            query.setParameter(6, df.getCalle());
            query.setParameter(7, df.getNumInt());
            query.setParameter(8, df.getNumExt());
            query.setParameter(9, df.getPais());
            query.setParameter(10, df.getLocalidad());
            query.setParameter(11, df.getIdCodigoPostalFk());
            query.setParameter(12, df.getTelefono());
            query.setParameter(13, df.getCorreo());
//            query.setParameter(14, df.getRegimen());
            query.setParameter(14, "Regimen General de Ley");
            query.setParameter(15, df.getField());
            query.setParameter(16, df.getRuta_llave_privada());
            query.setParameter(17, df.getRuta_certificado());
            query.setParameter(18, df.getRuta_llave_privada_cancel());
            query.setParameter(19, df.getRuta_certificado_cancel());
            query.setParameter(20, df.getClavePublica());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int update(DatosFacturacion df) {
        try {

            Query query = em.createNativeQuery("UPDATE DATOS_FACTURACION SET ID_SUCURSAL_FK = ?,RAZON_SOCIAL = ?,RFC = ?,CALLE = ? "
                    + ",NUM_INT = ?,NUM_EXT = ?,PAIS = ?,LOCALIDAD = ?,ID_CP_FISCAL = ?,TELEFONO = ?,CORREO = ?,REGIMEN = ?,FIELD = ?, "
                    + "RUTA_LLAVE_PRIVADA = ?,RUTA_CERTIFICADO = ?,RUTA_LLAVE_PRIVADA_CANCEL = ?,RUTA_CERTIFICADO_CANCEL = ?,CLAVE_PUBLICA = ? "
                    + "WHERE ID_DATOS_FACTURACION_PK = ?");

            query.setParameter(1, df.getIdSucursalFk());
            query.setParameter(2, df.getRazonSocial());
            query.setParameter(3, df.getRfc());
            query.setParameter(4, df.getCalle());
            query.setParameter(5, df.getNumInt());
            query.setParameter(6, df.getNumExt());
            query.setParameter(7, df.getPais());
            query.setParameter(8, df.getLocalidad());
            query.setParameter(9, df.getIdCodigoPostalFk());
            query.setParameter(10, df.getTelefono());
            query.setParameter(11, df.getCorreo());
            query.setParameter(12, df.getRegimen());
            query.setParameter(13, df.getField());
            query.setParameter(14, df.getRuta_llave_privada());
            query.setParameter(15, df.getRuta_certificado());
            query.setParameter(16, df.getRuta_llave_privada_cancel());
            query.setParameter(17, df.getRuta_certificado_cancel());

            query.setParameter(18, df.getClavePublica());
            query.setParameter(19, df.getIdDatosFacturacionPk());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("SELECT S_DATOS_FACTURACION.nextval FROM DUAL");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {
            Query query = em.createNativeQuery("SELECT DF.ID_DATOS_FACTURACION_PK,DF.ID_SUCURSAL_FK,DF.RAZON_SOCIAL ,DF.RFC,DF.CALLE,DF.NUM_INT,DF.NUM_EXT "
                    + ",DF.PAIS,DF.LOCALIDAD,DF.ID_CP_FISCAL,DF.TELEFONO,DF.CORREO,DF.REGIMEN "
                    + ",DF.FIELD,DF.RUTA_LLAVE_PRIVADA, "
                    + "DF.RUTA_CERTIFICADO,DF.RUTA_LLAVE_PRIVADA_CANCEL,DF.RUTA_CERTIFICADO_CANCEL,DF.CLAVE_PUBLICA, "
                    + "CP.CODIGO_POSTAL,CP.NOMBRE_COLONIA,mun.NOMBRE_MUNICIPIO, EN.NOMBRE_ENTIDAD,mun.ID_MUNICIPIO_PK,mun.ID_ENTIDAD_FK FROM DATOS_FACTURACION DF "
                    + "INNER JOIN CODIGOS_POSTALES CP ON CP.ID_PK = DF.ID_CP_FISCAL "
                    + "INNER JOIN MUNICIPIOS mun ON mun.ID_MUNICIPIO_PK = CP.ID_MUNICIPIO_FK "
                    + "INNER JOIN ENTIDAD EN ON EN.ID_ENTIDAD_PK = mun.ID_ENTIDAD_FK");

            List<Object[]> resultList = null;

            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getByRfc(String rfc) {
        try {
            Query query = em.createNativeQuery("SELECT DF.ID_DATOS_FACTURACION_PK,ID_CLIENTE_FK,DF.ID_SUCURSAL_FK,DF.RAZON_SOCIAL ,DF.RFC,DF.CALLE,DF.NUM_INT,DF.NUM_EXT "
                    + ",DF.PAIS,DF.LOCALIDAD,DF.ID_CP_FISCAL,DF.TELEFONO,DF.CORREO,DF.REGIMEN "
                    + ",DF.FIELD,DF.RUTA_LLAVE_PRIVADA, "
                    + "DF.RUTA_CERTIFICADO,DF.RUTA_LLAVE_PRIVADA_CANCEL,DF.RUTA_CERTIFICADO_CANCEL,DF.CLAVE_PUBLICA, "
                    + "CP.CODIGO_POSTAL,CP.NOMBRE_COLONIA,mun.NOMBRE_MUNICIPIO, EN.NOMBRE_ENTIDAD,mun.ID_MUNICIPIO_PK,mun.ID_ENTIDAD_FK FROM DATOS_FACTURACION DF "
                    + "INNER JOIN CODIGOS_POSTALES CP ON CP.ID_PK = DF.ID_CP_FISCAL "
                    + "INNER JOIN MUNICIPIOS mun ON mun.ID_MUNICIPIO_PK = CP.ID_MUNICIPIO_FK "
                    + "INNER JOIN ENTIDAD EN ON EN.ID_ENTIDAD_PK = mun.ID_ENTIDAD_FK WHERE RFC=?");

            query.setParameter(1, rfc);

            List<Object[]> resultList = null;

            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}

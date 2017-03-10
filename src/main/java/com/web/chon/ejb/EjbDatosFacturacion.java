/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.negocio.NegocioFacturacion;
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
 * @author jramirez
 */
@Stateless(mappedName = "ejbDatosFacturacion")
public class EjbDatosFacturacion implements NegocioFacturacion {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getDatosFacturacionByIdCliente(BigDecimal idClienteFk) {
        System.out.println("IDCLIENTE: "+idClienteFk);
        try {
            Query query = em.createNativeQuery("select df.ID_DATOS_FACTURACION_PK,df.ID_CLIENTE_FK,df.ID_SUCURSAL_FK,df.RAZON_SOCIAL ,df.RFC,df.CALLE,df.NUM_INT,df.NUM_EXT\n" +
",df.PAIS,df.LOCALIDAD,df.ID_CP_FISCAL,df.TELEFONO,df.CORREO,df.REGIMEN\n" +
",df.FIELD,df.RUTA_LLAVE_PRIVADA,\n" +
"df.RUTA_CERTIFICADO,df.RUTA_LLAVE_PRIVADA_CANCEL,df.RUTA_CERTIFICADO_CANCEL,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,cp.CODIGO_POSTAL,cp.NOMBRE_COLONIA,mun.NOMBRE_MUNICIPIO, en.NOMBRE_ENTIDAD \n" +
"from DATOS_FACTURACION df \n" +
"inner join CLIENTE cli on cli.ID_CLIENTE = df.ID_CLIENTE_FK\n" +
"inner join CODIGOS_POSTALES cp on cp.ID_PK = df.ID_CP_FISCAL\n" +
"inner join MUNICIPIOS mun on mun.ID_MUNICIPIO_PK = cp.ID_MUNICIPIO_FK\n" +
"inner join entidad en on en.ID_ENTIDAD_PK = mun.ID_ENTIDAD_FK\n" +
"where df.ID_CLIENTE_FK = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idClienteFk);
            System.out.println("Query: "+query.toString());
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    @Override
    public List<Object[]> getDatosFacturacionByIdSucursal(BigDecimal idSucursalFk) {
        try {
            Query query = em.createNativeQuery("select df.ID_DATOS_FACTURACION_PK,df.ID_CLIENTE_FK,df.ID_SUCURSAL_FK,df.RAZON_SOCIAL ,df.RFC,df.CALLE,df.NUM_INT,df.NUM_EXT\n" +
",df.PAIS,df.LOCALIDAD,df.ID_CP_FISCAL,df.TELEFONO,df.CORREO,df.REGIMEN\n" +
",df.FIELD,df.RUTA_LLAVE_PRIVADA,\n" +
"df.RUTA_CERTIFICADO,df.RUTA_LLAVE_PRIVADA_CANCEL,df.RUTA_CERTIFICADO_CANCEL,df.CLAVE_PUBLICA,\n" +
"cp.CODIGO_POSTAL,cp.NOMBRE_COLONIA,mun.NOMBRE_MUNICIPIO, en.NOMBRE_ENTIDAD from DATOS_FACTURACION df \n" +
"inner join CODIGOS_POSTALES cp on cp.ID_PK = df.ID_CP_FISCAL\n" +
"inner join MUNICIPIOS mun on mun.ID_MUNICIPIO_PK = cp.ID_MUNICIPIO_FK\n" +
"inner join entidad en on en.ID_ENTIDAD_PK = mun.ID_ENTIDAD_FK\n" +
"where df.ID_SUCURSAL_FK = ?");
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
    public int deleteDatosFacturacion(String idProducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertarDatosFacturacion(DatosFacturacion df) {
        try {

            Query query = em.createNativeQuery("INSERT INTO  DATOS_FACTURACION (ID_DATOS_FACTURACION_PK,ID_CLIENTE_FK,ID_SUCURSAL_FK,RAZON_SOCIAL,RFC,CALLE,NUM_INT,NUM_EXT,PAIS,LOCALIDAD,ID_CP_FISCAL,TELEFONO,CORREO,REGIMEN,FIELD,RUTA_LLAVE_PRIVADA,RUTA_CERTIFICADO,RUTA_LLAVE_PRIVADA_CANCEL,RUTA_CERTIFICADO_CANCEL) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, df.getIdDatosFacturacionPk());
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int updateDatosFacturacion(DatosFacturacion df) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
       try {
            Query query = em.createNativeQuery("select S_DATOS_FACTURACION.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }
    
}

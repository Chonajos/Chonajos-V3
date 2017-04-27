package com.web.chon.ejb;

import com.web.chon.dominio.Cliente;
import com.web.chon.negocio.NegocioCatCliente;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fredy
 */
@Stateless(mappedName = "ejbCatCliente")
public class EjbCatCliente implements NegocioCatCliente {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    private final static Logger logger = LoggerFactory.getLogger(EjbCatCliente.class);

    @Override
    public List<Object[]> getClientes() {
        try {
            Query query = em.createNativeQuery("SELECT CLI.ID_CLIENTE,CLI.NOMBRE,CLI.APELLIDO_PATERNO,CLI.APELLIDO_MATERNO,CLI.EMPRESA,CLI.CALLE,CLI.SEXO,CLI.TELEFONO_CELULAR,CLI.TELEFONO_FIJO,CLI.EXTENSION,CLI.NUM_INT,CLI.NUM_EXT,CLI.ID_CP,CLI.NEXTEL,CLI.RAZON,CLI.RFC,CLI.STATUS,CLI.FECHA_ALTA,CLI.DIAS_CREDITO,CLI.MONTO_CREDITO,CLI.TIPO_PERSONA,CLI.LOCALIDAD,CLI.PAIS,CLI.CORREO ,CP.CODIGO_POSTAL,CP.NOMBRE_COLONIA,MUN.NOMBRE_MUNICIPIO,EN.NOMBRE_ENTIDAD,CP.ID_PK, MUN.ID_MUNICIPIO_PK,EN.ID_ENTIDAD_PK FROM CLIENTE CLI LEFT JOIN CODIGOS_POSTALES CP ON CP.ID_PK = CLI.ID_CP LEFT JOIN MUNICIPIOS MUN ON MUN.ID_MUNICIPIO_PK = CP.ID_MUNICIPIO_FK LEFT JOIN ENTIDAD EN ON EN.ID_ENTIDAD_PK = MUN.ID_ENTIDAD_FK ORDER BY CLI.NOMBRE ASC");
            List<Object[]> resultList = null;
            System.out.println("Query: " + query.toString());
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Object[]> getClienteById(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("SELECT CLI.ID_CLIENTE,CLI.NOMBRE,CLI.APELLIDO_PATERNO,CLI.APELLIDO_MATERNO,CLI.EMPRESA,CLI.CALLE,CLI.SEXO,CLI.TELEFONO_CELULAR,CLI.TELEFONO_FIJO,CLI.EXTENSION,CLI.NUM_INT,CLI.NUM_EXT,CLI.ID_CP,CLI.NEXTEL,CLI.RAZON,CLI.RFC,CLI.STATUS,CLI.FECHA_ALTA,CLI.DIAS_CREDITO,CLI.MONTO_CREDITO,CLI.TIPO_PERSONA,CLI.LOCALIDAD,CLI.PAIS,CLI.CORREO ,CP.CODIGO_POSTAL,CP.NOMBRE_COLONIA,MUN.NOMBRE_MUNICIPIO,EN.NOMBRE_ENTIDAD FROM CLIENTE CLI LEFT JOIN CODIGOS_POSTALES CP ON CP.ID_PK = CLI.ID_CP LEFT JOIN MUNICIPIOS MUN ON MUN.ID_MUNICIPIO_PK = CP.ID_MUNICIPIO_FK LEFT JOIN ENTIDAD EN ON EN.ID_ENTIDAD_PK = MUN.ID_ENTIDAD_FK  WHERE CLI.ID_CLIENTE = ?");
            query.setParameter(1, idCliente);

            return query.getResultList();

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }

    @Override
    public int deleteCliente(BigDecimal idCliente) {
        try {

            //System.out.println("Id Cliente a eliminar: " + idCliente);
            Query query = em.createNativeQuery("UPDATE ClIENTE  SET STATUS = ? WHERE ID_CLIENTE = ?");
            query.setParameter(1, 2);
            query.setParameter(2, idCliente);

            return query.executeUpdate();

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int updateCliente(Cliente clie) {
        try {
            System.out.println("Cliente a modificar :" + clie.toString());
            Query query = em.createNativeQuery("UPDATE CLIENTE SET NOMBRE = ?,APELLIDO_PATERNO = ?,APELLIDO_MATERNO = ?,EMPRESA = ?,CALLE = ?,SEXO = ?,TELEFONO_CELULAR = ?,TELEFONO_FIJO = ?,EXTENSION = ?,NUM_INT = ?,NUM_EXT = ?,ID_CP = ?,NEXTEL = ?,RAZON = ?,RFC = ?,STATUS = ?,DIAS_CREDITO= ?,MONTO_CREDITO = ?,TIPO_PERSONA = ?,LOCALIDAD = ?,PAIS = ?,CORREO = ?  WHERE ID_CLIENTE = ? ");
            query.setParameter(1, clie.getNombre());
            query.setParameter(2, clie.getPaterno());
            query.setParameter(3, clie.getMaterno());
            query.setParameter(4, clie.getEmpresa());
            query.setParameter(5, clie.getCalle());
            query.setParameter(6, clie.getSexo());
            query.setParameter(7, clie.getTelefonoCelular());
            query.setParameter(8, clie.getTelefonoOficina());
            query.setParameter(9, clie.getExt());
            query.setParameter(10, clie.getNumInterior());
            query.setParameter(11, clie.getNumExterior());
            query.setParameter(12, clie.getIdColoniaFk() == new BigDecimal(0) ? new BigDecimal(100000) : clie.getIdColoniaFk());
            query.setParameter(13, clie.getNextel());
            query.setParameter(14, clie.getRazonSocial());
            query.setParameter(15, clie.getRfc());
            query.setParameter(16, clie.getIdStatusFk());
            query.setParameter(17, clie.getDiasCredito());
            query.setParameter(18, clie.getLimiteCredito());
            query.setParameter(19, clie.getTipoPersona());
            query.setParameter(20, clie.getLocalidad());
            query.setParameter(21, clie.getPais());
            query.setParameter(22, clie.getCorreo());
            query.setParameter(23, clie.getIdClientePk());

            return query.executeUpdate();

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }

    }

    @Override
    public int insertCliente(Cliente clie) {

        //System.out.println("EJB_INSERTA_CLIENTE");
        try {

            //System.out.println("insert : " + clie.toString());
            Query query = em.createNativeQuery("INSERT INTO CLIENTE (ID_CLIENTE,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,EMPRESA,CALLE,SEXO,TELEFONO_CELULAR,TELEFONO_FIJO,EXTENSION,NUM_INT,NUM_EXT,ID_CP,NEXTEL,RAZON,RFC,STATUS,FECHA_ALTA,DIAS_CREDITO,MONTO_CREDITO,TIPO_PERSONA,LOCALIDAD,PAIS,CORREO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?)");
            query.setParameter(1, clie.getIdClientePk());
            query.setParameter(2, clie.getNombre());
            query.setParameter(3, clie.getPaterno());
            query.setParameter(4, clie.getMaterno());
            query.setParameter(5, clie.getEmpresa());
            query.setParameter(6, clie.getCalle());
            query.setParameter(7, clie.getSexo());
            query.setParameter(8, clie.getTelefonoCelular());
            query.setParameter(9, clie.getTelefonoOficina());
            query.setParameter(10, clie.getExt());
            query.setParameter(11, clie.getNumInterior());
            query.setParameter(12, clie.getNumExterior());
            query.setParameter(13, clie.getIdCodigoPostalFk() == new BigDecimal(0) ? new BigDecimal(100000) : clie.getIdCodigoPostalFk());
            query.setParameter(14, clie.getNextel());
            query.setParameter(15, clie.getRazonSocial());
            query.setParameter(16, clie.getRfc());
            query.setParameter(17, clie.getIdStatusFk());
            query.setParameter(18, clie.getDiasCredito());
            query.setParameter(19, clie.getLimiteCredito());
            query.setParameter(20, clie.getTipoPersona());
            query.setParameter(21, clie.getLocalidad());
            query.setParameter(22, clie.getPais());
            query.setParameter(23, clie.getCorreo());
            return query.executeUpdate();
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }

    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_CLIENTE.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getClienteByNombreCompleto(String nombreCliente) {
        Query query = em.createNativeQuery("SELECT CLI.ID_CLIENTE,CLI.NOMBRE,CLI.APELLIDO_PATERNO,CLI.APELLIDO_MATERNO,CLI.EMPRESA,CLI.CALLE,CLI.SEXO,CLI.TELEFONO_CELULAR,CLI.TELEFONO_FIJO,CLI.EXTENSION,CLI.NUM_INT,CLI.NUM_EXT,CLI.ID_CP,CLI.NEXTEL,CLI.RAZON,CLI.RFC,CLI.STATUS,CLI.FECHA_ALTA,CLI.DIAS_CREDITO,CLI.MONTO_CREDITO,CLI.TIPO_PERSONA,CLI.LOCALIDAD,CLI.PAIS,CLI.CORREO ,CP.CODIGO_POSTAL,CP.NOMBRE_COLONIA,MUN.NOMBRE_MUNICIPIO,EN.NOMBRE_ENTIDAD FROM CLIENTE CLI LEFT JOIN CODIGOS_POSTALES CP ON CP.ID_PK = CLI.ID_CP LEFT JOIN MUNICIPIOS MUN ON MUN.ID_MUNICIPIO_PK = CP.ID_MUNICIPIO_FK LEFT JOIN ENTIDAD EN ON EN.ID_ENTIDAD_PK = MUN.ID_ENTIDAD_FK   WHERE UPPER(TRIM(NOMBRE) ||' '|| TRIM(APELLIDO_PATERNO) ||' '|| TRIM(APELLIDO_MATERNO) )  LIKE UPPER('%" + nombreCliente + "%') and STATUS =1");

        return query.getResultList();
    }

    @Override
    public List<Object[]> getClienteCreditoById(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("select c.ID_CLIENTE,c.NOMBRE, c.APELLIDO_PATERNO, c.APELLIDO_MATERNO,c.MONTO_CREDITO,sum(vp.TOTAL_VENTA) as Credito_Utilizado ,\n"
                    + "NVL((select sum(vmp.TOTAL_VENTA) as Credito_Utilizado from CREDITO cre\n"
                    + "inner join VENTA_MAYOREO vm\n"
                    + "on vm.ID_VENTA_MAYOREO_PK = cre.ID_VENTA_MAYOREO\n"
                    + "inner join VENTAMAYOREOPRODUCTO vmp\n"
                    + "on vmp.ID_VENTA_MAYOREO_FK = vm.ID_VENTA_MAYOREO_PK\n"
                    + "inner join cliente c\n"
                    + "on c.ID_CLIENTE = cre.ID_CLIENTE_FK\n"
                    + "where cre.ESTATUS_CREDITO = 1 and cre.ID_CLIENTE_FK= '" + idCliente + "'),0) as credito_utilizado_mayoreo from CREDITO cre\n"
                    + "inner join venta v\n"
                    + "on v.ID_VENTA_PK = cre.ID_VENTA_MENUDEO\n"
                    + "inner join VENTA_PRODUCTO vp\n"
                    + "on vp.ID_VENTA_FK = v.ID_VENTA_PK\n"
                    + "inner join cliente c\n"
                    + "on c.ID_CLIENTE = cre.ID_CLIENTE_FK\n"
                    + "where cre.ESTATUS_CREDITO = 1  and cre.ID_CLIENTE_FK= '" + idCliente + "'\n"
                    + "group by c.ID_CLIENTE,c.NOMBRE, c.APELLIDO_PATERNO, c.APELLIDO_MATERNO,c.MONTO_CREDITO");
            query.setParameter(1, idCliente);

            //System.out.println("Query: " + query);
            return query.getResultList();

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Object[]> getCreditoClienteByIdCliente(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("SELECT C.ID_CLIENTE, TRIM(C.NOMBRE) ||' '|| TRIM(C.APELLIDO_PATERNO) ||' '|| TRIM(C.APELLIDO_MATERNO) AS NOMBRE_COMPLETO,NVL(C.MONTO_CREDITO,0) "
                    + "AS MONTO_CREDITO, "
                    + "NVL((SELECT SUM(CRE.MONTO_CREDITO) FROM CREDITO CRE WHERE CRE.ID_CLIENTE_FK = C.ID_CLIENTE AND CRE.ESTATUS_CREDITO = 1 ),0) "
                    + "AS CREDITO_UTILIZADO, "
                    + "NVL((SELECT SUM(DC.MONTO) AS DOCUMENTOS FROM DOCUMENTOS_COBRAR DC WHERE DC.ID_STATUS_FK=1 AND DC.ID_CLIENTE_FK=C.ID_CLIENTE),0) AS Documentos , "
                    + "(SELECT AVG(T1.DIFERENCIA) FROM(SELECT CR.FECHA_INICIO_CREDITO,NVL(CR.FECHA_FIN_CREDITO,SYSDATE)-CR.FECHA_INICIO_CREDITO AS DIFERENCIA FROM CREDITO CR "
                    + "WHERE ID_CLIENTE_FK = ? ORDER BY CR.FECHA_INICIO_CREDITO DESC) T1 WHERE ROWNUM <=3 ) AS PROMEDIO_RECUPERACION_3, "
                    + "(SELECT AVG(NVL(CR.FECHA_FIN_CREDITO,SYSDATE)-CR.FECHA_INICIO_CREDITO) AS DIFERENCIA FROM CREDITO CR "
                    + "WHERE ID_CLIENTE_FK = C.ID_CLIENTE) AS PROMEDIO_RECUPERACION "
                    + "FROM CLIENTE C "
                    + "WHERE C.ID_CLIENTE = ? AND C.STATUS =1");
            query.setParameter(1, idCliente);
            query.setParameter(2, idCliente);
            return query.getResultList();

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Object[]> getClientesActivos() {
        try {

            //System.out.println("EJB_GET_CLIENTE");
            Query query = em.createNativeQuery("SELECT CLI.ID_CLIENTE,CLI.NOMBRE,CLI.APELLIDO_PATERNO,CLI.APELLIDO_MATERNO,CLI.EMPRESA,CLI.CALLE,CLI.SEXO,CLI.TELEFONO_CELULAR,CLI.TELEFONO_FIJO,CLI.EXTENSION,CLI.NUM_INT,CLI.NUM_EXT,CLI.ID_CP,CLI.NEXTEL,CLI.RAZON,CLI.RFC,CLI.STATUS,CLI.FECHA_ALTA,CLI.DIAS_CREDITO,CLI.MONTO_CREDITO,CLI.TIPO_PERSONA,CLI.LOCALIDAD,CLI.PAIS,CLI.CORREO ,CP.CODIGO_POSTAL,CP.NOMBRE_COLONIA,MUN.NOMBRE_MUNICIPIO,EN.NOMBRE_ENTIDAD FROM CLIENTE CLI LEFT JOIN CODIGOS_POSTALES CP ON CP.ID_PK = CLI.ID_CP LEFT JOIN MUNICIPIOS MUN ON MUN.ID_MUNICIPIO_PK = CP.ID_MUNICIPIO_FK LEFT JOIN ENTIDAD EN ON EN.ID_ENTIDAD_PK = MUN.ID_ENTIDAD_FK WHERE CLI.STATUS=1 ORDER BY CLI.NOMBRE ASC");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Object[]> getClienteByIdSubProducto(String idSubProducto, BigDecimal idSucursal) {
        try {

            Query query = em.createNativeQuery("SELECT CL.NOMBRE ||' '|| CL.APELLIDO_PATERNO ||' '||CL.APELLIDO_MATERNO AS NOMBRE_CLIENTE ,CO.CORREO,SUM(VMP.CANTIDAD_EMPAQUE) AS TOTAL_EMPAQUE "
                    + "FROM CLIENTE CL "
                    + "LEFT JOIN CORREOS CO ON CO.ID_CLIENTE_FK = CL.ID_CLIENTE "
                    + "LEFT JOIN VENTA_MAYOREO VM ON CL.ID_CLIENTE  = VM.ID_CLIENTE_FK "
                    + "INNER JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                    + "WHERE  VMP.ID_SUBPRODUCTO_FK = ? AND VM.ID_SUCURSAL_FK = ? AND VM.ID_STATUS_FK != 4 AND CL.STATUS !=2 "
                    + "GROUP BY CL.NOMBRE ||' '|| CL.APELLIDO_PATERNO ||' '||CL.APELLIDO_MATERNO ,CO.CORREO ORDER BY TOTAL_EMPAQUE DESC");

            query.setParameter(1, idSubProducto);
            query.setParameter(2, idSucursal);

            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Object[]> getReporteClienteVentasUtilidad(BigDecimal idCliente, String fechaInicio, String fechaFin, BigDecimal idSucursal) {
        try {
            StringBuffer sQuery;
            if (idSucursal == null) {
                sQuery = new StringBuffer("SELECT TRIM(CLI.NOMBRE)||' '|| TRIM(CLI.APELLIDO_PATERNO)||' '|| TRIM(CLI.APELLIDO_MATERNO) AS NOMBRE_COMPLETO,CLI.ID_CLIENTE, "
                        + "NVL(SUM(VP.CANTIDAD_EMPAQUE*VP.PRECIO_PRODUCTO),0) AS TOTAL_MENUDEO_CONTADO, "
                        + "NVL(SUM(VPC.CANTIDAD_EMPAQUE*VPC.PRECIO_PRODUCTO),0) AS TOTAL_MENUDEO_CREDITO, "
                        + "(SELECT NVL(SUM(VP1.CANTIDAD_EMPAQUE*VP1.PRECIO_PRODUCTO),0)- NVL(SUM(VP1.CANTIDAD_EMPAQUE*MTP.COSTOREAL),0) FROM VENTA V1 "
                        + "INNER JOIN VENTA_PRODUCTO VP1 ON V1.ID_VENTA_PK = VP1.ID_VENTA_FK "
                        + "INNER JOIN MANTENIMIENTO_PRECIO MTP ON MTP.ID_SUBPRODUCTO_FK = VP1.ID_SUBPRODUCTO_FK "
                        + "WHERE V1.ID_CLIENTE_FK = CLI.ID_CLIENTE AND MTP.ID_SUCURSAL_FK = V1.ID_SUCURSAL_FK AND V1.STATUS_FK != 4) AS UTILIDAD_MENUDEO, "
                        + "(SELECT NVL(SUM(VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO),0)  FROM VENTA_MAYOREO VM "
                        + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + "WHERE VM.ID_STATUS_FK != 4 AND VM.ID_TIPO_VENTA_FK = 1 AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE) AS TOTAL_MAYOREO_CONTADO, "
                        + "(SELECT NVL(SUM(VMPC.KILOS_VENDIDOS*VMPC.PRECIO_PRODUCTO),0)  FROM VENTA_MAYOREO VM "
                        + "LEFT JOIN VENTAMAYOREOPRODUCTO VMPC ON VMPC.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + "WHERE VM.ID_STATUS_FK != 4 AND VM.ID_TIPO_VENTA_FK = 2 AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE) AS TOTAL_MAYOREO_CREDITO, "
                        + "(SELECT NVL(SUM(VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO),0)-SUM(VMP.CANTIDAD_EMPAQUE*EXP.KILOSPROMPROD*EXP.CONVENIO) FROM VENTA_MAYOREO VM "
                        + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + "LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                        + "WHERE VM.ID_STATUS_FK != 4  AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND EXP.ID_TIPO_CONVENIO_FK = 1) AS UTILIDAD_MAYOREO_COSTO, "
                        + "(SELECT NVL(SUM((VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO)*EXP.CONVENIO/100),0) FROM VENTA_MAYOREO VM "
                        + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + "LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                        + "WHERE VM.ID_STATUS_FK != 4  AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND EXP.ID_TIPO_CONVENIO_FK = 2) AS UTILIDAD_MAYOREO_COMISION "
                        + ",(SELECT NVL(SUM(VMP.KILOS_VENDIDOS*EXP.CONVENIO),0) FROM VENTA_MAYOREO VM "
                        + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + "LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                        + "WHERE VM.ID_STATUS_FK != 4  AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND EXP.ID_TIPO_CONVENIO_FK = 3) AS UTILIDAD_MAYOREO_PACTO "
                        + ",(SELECT NVL(AVG(NVL(CR.FECHA_FIN_CREDITO,SYSDATE)-CR.FECHA_INICIO_CREDITO),0) FROM CREDITO CR WHERE  CR.ID_CLIENTE_FK = CLI.ID_CLIENTE) AS DIAS_RECUPERACION "
                        + ",(SELECT NVL(SUM(ABC.MONTO_ABONO),0)  FROM ABONO_CREDITO ABC "
                        + "INNER JOIN CREDITO CR ON CR.ID_CREDITO_PK = ABC.ID_CREDITO_FK WHERE ABC.ESTATUS = 1 AND CR.ID_CLIENTE_FK = CLI.ID_CLIENTE ) AS RECUPERACION "
                        + ",(SELECT ROUND(AVG(NVL(FECHA_FIN_CREDITO,SYSDATE)-FECHA_INICIO_CREDITO),2)FROM CREDITO) AS PROMEDIO_GENERAL "
                        + "FROM CLIENTE CLI "
                        + "LEFT JOIN VENTA V  ON V.ID_CLIENTE_FK =CLI.ID_CLIENTE "
                        + "LEFT JOIN VENTA_PRODUCTO VP ON VP.ID_VENTA_FK = V.ID_VENTA_PK AND V.STATUS_FK != 4 AND V.TIPO_VENTA = 1 "
                        + "LEFT JOIN VENTA_PRODUCTO VPC ON VPC.ID_VENTA_FK = V.ID_VENTA_PK AND V.STATUS_FK != 4 AND V.TIPO_VENTA = 2 ");
            } else {

                sQuery = new StringBuffer(" SELECT TRIM(CLI.NOMBRE)||' '|| TRIM(CLI.APELLIDO_PATERNO)||' '|| TRIM(CLI.APELLIDO_MATERNO) AS NOMBRE_COMPLETO,CLI.ID_CLIENTE, "
                        + " NVL(SUM(VP.CANTIDAD_EMPAQUE*VP.PRECIO_PRODUCTO),0) AS TOTAL_MENUDEO_CONTADO, "
                        + " NVL(SUM(VPC.CANTIDAD_EMPAQUE*VPC.PRECIO_PRODUCTO),0) AS TOTAL_MENUDEO_CREDITO, "
                        + " (SELECT NVL(SUM(VP1.CANTIDAD_EMPAQUE*VP1.PRECIO_PRODUCTO),0)- NVL(SUM(VP1.CANTIDAD_EMPAQUE*MTP.COSTOREAL),0) FROM VENTA V1 "
                        + " INNER JOIN VENTA_PRODUCTO VP1 ON V1.ID_VENTA_PK = VP1.ID_VENTA_FK "
                        + " INNER JOIN MANTENIMIENTO_PRECIO MTP ON MTP.ID_SUBPRODUCTO_FK = VP1.ID_SUBPRODUCTO_FK "
                        + " WHERE V1.ID_CLIENTE_FK = CLI.ID_CLIENTE AND MTP.ID_SUCURSAL_FK = V1.ID_SUCURSAL_FK AND V1.STATUS_FK != 4 AND V1.ID_SUCURSAL_FK = "+idSucursal+") AS UTILIDAD_MENUDEO, "
                        + " (SELECT NVL(SUM(VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO),0)  FROM VENTA_MAYOREO VM "
                        + " LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + " WHERE VM.ID_STATUS_FK != 4 AND VM.ID_TIPO_VENTA_FK = 1 AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND VM.ID_SUCURSAL_FK = "+idSucursal+") AS TOTAL_MAYOREO_CONTADO, "
                        + " (SELECT NVL(SUM(VMPC.KILOS_VENDIDOS*VMPC.PRECIO_PRODUCTO),0)  FROM VENTA_MAYOREO VM "
                        + " LEFT JOIN VENTAMAYOREOPRODUCTO VMPC ON VMPC.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + " WHERE VM.ID_STATUS_FK != 4 AND VM.ID_TIPO_VENTA_FK = 2 AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND VM.ID_SUCURSAL_FK = "+idSucursal+") AS TOTAL_MAYOREO_CREDITO, "
                        + " (SELECT NVL(SUM(VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO),0)-SUM(VMP.CANTIDAD_EMPAQUE*EXP.KILOSPROMPROD*EXP.CONVENIO) FROM VENTA_MAYOREO VM "
                        + " LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + " LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                        + " WHERE VM.ID_STATUS_FK != 4  AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND EXP.ID_TIPO_CONVENIO_FK = 1 AND VM.ID_SUCURSAL_FK = "+idSucursal+") AS UTILIDAD_MAYOREO_COSTO, "
                        + " (SELECT NVL(SUM((VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO)*EXP.CONVENIO/100),0) FROM VENTA_MAYOREO VM "
                        + " LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + " LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                        + " WHERE VM.ID_STATUS_FK != 4  AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND EXP.ID_TIPO_CONVENIO_FK = 2 AND VM.ID_SUCURSAL_FK = "+idSucursal+") AS UTILIDAD_MAYOREO_COMISION "
                        + " ,(SELECT NVL(SUM(VMP.KILOS_VENDIDOS*EXP.CONVENIO),0) FROM VENTA_MAYOREO VM "
                        + " LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                        + " LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                        + " WHERE VM.ID_STATUS_FK != 4  AND VM.ID_CLIENTE_FK = CLI.ID_CLIENTE AND EXP.ID_TIPO_CONVENIO_FK = 3 AND VM.ID_SUCURSAL_FK = "+idSucursal+") AS UTILIDAD_MAYOREO_PACTO "
                        + " ,(SELECT NVL(AVG(NVL(CR.FECHA_FIN_CREDITO,SYSDATE)-CR.FECHA_INICIO_CREDITO),0) FROM CREDITO CR WHERE  CR.ID_CLIENTE_FK = CLI.ID_CLIENTE AND CR.ID_SUCURSAL_FK ="+idSucursal+"  ) AS DIAS_RECUPERACION "
                        + " ,(SELECT NVL(SUM(ABC.MONTO_ABONO),0)  FROM ABONO_CREDITO ABC "
                        + " INNER JOIN CREDITO CR ON CR.ID_CREDITO_PK = ABC.ID_CREDITO_FK WHERE ABC.ESTATUS = 1 AND CR.ID_CLIENTE_FK = CLI.ID_CLIENTE AND CR.ID_SUCURSAL_FK = 3 ) AS RECUPERACION, "
                        + " (SELECT ROUND(AVG(NVL(FECHA_FIN_CREDITO,SYSDATE)-FECHA_INICIO_CREDITO),2)FROM CREDITO C WHERE C.ID_SUCURSAL_FK ="+idSucursal+") AS PROMEDIO_GENERAL "
                        + " FROM CLIENTE CLI "
                        + " LEFT JOIN VENTA V  ON V.ID_CLIENTE_FK =CLI.ID_CLIENTE AND V.ID_SUCURSAL_FK ="+idSucursal
                        + " LEFT JOIN VENTA_PRODUCTO VP ON VP.ID_VENTA_FK = V.ID_VENTA_PK AND V.STATUS_FK != 4 AND V.TIPO_VENTA = 1 "
                        + " LEFT JOIN VENTA_PRODUCTO VPC ON VPC.ID_VENTA_FK = V.ID_VENTA_PK AND V.STATUS_FK != 4 AND V.TIPO_VENTA = 2 ");

            }

            if (idCliente != null) {
                sQuery.append(" WHERE CLI.ID_CLIENTE = " + idCliente);
            }
            sQuery.append("GROUP BY TRIM(CLI.NOMBRE)||' '|| TRIM(CLI.APELLIDO_PATERNO)||' '|| TRIM(CLI.APELLIDO_MATERNO),CLI.ID_CLIENTE "
                    + " ORDER BY CLI.ID_CLIENTE DESC ");
            Query query = em.createNativeQuery(sQuery.toString());

            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;

        }
    }

    @Override
    public List<Object[]> getClienteByIdAbono(BigDecimal folioAbono) {
        try {
            Query query = em.createNativeQuery("SELECT CLI.ID_CLIENTE, CLI.NOMBRE ||' '|| CLI.APELLIDO_PATERNO ||' '|| CLI.APELLIDO_MATERNO AS NOMBRE_COMPLETO, SUM(AC.MONTO_ABONO),CLI.RFC FROM CLIENTE CLI "
                    + "INNER JOIN CREDITO CR ON CR.ID_CLIENTE_FK = CLI.ID_CLIENTE "
                    + "INNER JOIN ABONO_CREDITO AC ON AC.ID_CREDITO_FK = CR.ID_CREDITO_PK "
                    + "WHERE AC.NUMERO_ABONO = ? GROUP BY CLI.ID_CLIENTE,CLI.NOMBRE ,CLI.APELLIDO_PATERNO,CLI.APELLIDO_MATERNO,CLI.RFC ");

            query.setParameter(1, folioAbono);

            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;

        }
    }

}

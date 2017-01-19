
package com.web.chon.ejb;

import com.web.chon.dominio.Cliente;
import com.web.chon.negocio.NegocioCatCliente;
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
 * @author fredy
 */
@Stateless(mappedName = "ejbCatCliente")
public class EjbCatCliente implements NegocioCatCliente {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getClientes() {
        try {

            Query query = em.createNativeQuery("select c.* ,en.ID_ENTIDAD_PK, en.NOMBRE_ENTIDAD ,en1.ID_ENTIDAD_PK, "
                    + "en1.NOMBRE_ENTIDAD,m.NOMBRE_MUNICIPIO,m1.NOMBRE_MUNICIPIO,cp.NOMBRE_COLONIA, "
                    + "cp.CODIGO_POSTAL,cp1.NOMBRE_COLONIA,cp1.CODIGO_POSTAL,m.ID_MUNICIPIO_PK,m.ID_MUNICIPIO_PK,cp.ID_PK,cp1.ID_PK "
                    + "from Cliente c "
                    + "LEFT JOIN CODIGOS_POSTALES cp "
                    + "on c.ID_CP=cp.ID_PK "
                    + "LEFT JOIN Municipios m "
                    + "on cp.ID_MUNICIPIO_FK=m.id_municipio_pk "
                    + "LEFT JOIN ENTIDAD en "
                    + "on en.ID_ENTIDAD_PK=m.ID_ENTIDAD_FK "
                    + "LEFT JOIN CODIGOS_POSTALES cp1 "
                    + "on c.ID_CP_FISCAL=cp1.ID_PK "
                    + "LEFT JOIN Municipios m1 "
                    + "on cp.ID_MUNICIPIO_FK=m1.id_municipio_pk "
                    + "LEFT JOIN ENTIDAD en1 "
                    + "on en1.ID_ENTIDAD_PK=m1.ID_ENTIDAD_FK "
                    + "order by c.NOMBRE");
            
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getClienteById(int idCliente) {
        try {

            Query query = em.createNativeQuery("SELECT * FROM ClIENTE WHERE ID_CLIENTE = ?");
            query.setParameter(1, idCliente);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteCliente(int idCliente) {
        try {

            //System.out.println("Id Cliente a eliminar: " + idCliente);
            Query query = em.createNativeQuery("UPDATE ClIENTE  SET STATUS = ? WHERE ID_CLIENTE = ?");
            query.setParameter(1, 2);
            query.setParameter(2, idCliente);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateCliente(Cliente clie) {

        try {
            System.out.println("Cliente a modificar :" + clie.toString());
            Query query = em.createNativeQuery("UPDATE CLIENTE SET NOMBRE = ?, APELLIDO_PATERNO = ?, APELLIDO_MATERNO = ?, EMPRESA = ?, CALLE = ?, SEXO = ?, FECHA_NACIMIENTO = ?,TELEFONO_MOVIL = ?, TELEFONO_FIJO = ?, EXTENSION = ?, NUM_INT = ? , NUM_EXT = ?, CLAVECELULAR = ?, LADACELULAR = ?, ID_CP= ?,CALLEFISCAL = ?,NUMINTFIS = ?,NUMEXTFIS = ?, ID_CP_FISCAL = ?, NEXTEL = ?,RAZON = ?,RFC = ?, LADAOFICINA = ?,CLAVEOFICINA = ?,NEXTELCLAVE = ?, STATUS =? ,DIAS_CREDITO = ?,MONTO_CREDITO = ?, TIPO_PERSONA = ?  WHERE ID_CLIENTE = ? ");
            query.setParameter(1, clie.getNombre());
            query.setParameter(2, clie.getPaterno());
            query.setParameter(3, clie.getMaterno());
            query.setParameter(4, clie.getEmpresa());
            query.setParameter(5, clie.getCalle());
            query.setParameter(6, clie.getSexo());
            query.setParameter(7, clie.getFecha_nacimiento());
            query.setParameter(8, clie.getTel_movil());
            query.setParameter(9, clie.getTel_fijo());
            query.setParameter(10, clie.getExt());
            //query.setParameter(12, clie.getEmail());
            query.setParameter(11, clie.getNum_int());
            query.setParameter(12, clie.getNum_ext());
            //query.setParameter(15, clie.getColonia());
            query.setParameter(13, clie.getClavecelular());
            query.setParameter(14, clie.getLadacelular());
            query.setParameter(15, clie.getID_CP() == new BigDecimal(0) ? new BigDecimal(100000) : clie.getID_CP());
            query.setParameter(16, clie.getCalleFiscal());
            query.setParameter(17, clie.getNum_int_fiscal());
            query.setParameter(18, clie.getNum_ext_fiscal());
            //query.setParameter(22, clie.getColoniaFiscal());
            query.setParameter(19, clie.getID_CP_FISCAL() == new BigDecimal(0) ? new BigDecimal(100000) : clie.getID_CP_FISCAL());
            query.setParameter(20, clie.getNextel());
            query.setParameter(21, clie.getRazon_social());
            query.setParameter(22, clie.getRfcFiscal());
            query.setParameter(23, clie.getLadaoficina());
            query.setParameter(24, clie.getClaveoficina());
            query.setParameter(25, clie.getNextelclave());
            query.setParameter(26, clie.getStatus_cliente());//activo
            query.setParameter(27, clie.getDiasCredito());
            query.setParameter(28, clie.getLimiteCredito());
            query.setParameter(29, clie.getTipoPersona());
            query.setParameter(30, clie.getId_cliente());

            return query.executeUpdate();

        } catch (Exception ex) {

            System.out.println("error ");
            System.out.println("error >> " + ex.getMessage().toString());
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int insertCliente(Cliente clie) {

        //System.out.println("EJB_INSERTA_CLIENTE");
        try {
            //System.out.println("insert : " + clie.toString());
            Query query = em.createNativeQuery("INSERT INTO CLIENTE (ID_CLIENTE,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMPRESA, CALLE, SEXO, FECHA_NACIMIENTO,TELEFONO_MOVIL, TELEFONO_FIJO, EXTENSION,  NUM_INT, NUM_EXT,  CLAVECELULAR, LADACELULAR,ID_CP,CALLEFISCAL,NUMINTFIS,NUMEXTFIS,ID_CP_FISCAL,NEXTEL,RAZON,RFC,LADAOFICINA,CLAVEOFICINA,NEXTELCLAVE,STATUS,DIAS_CREDITO,MONTO_CREDITO,FECHA_ALTA,TIPO_PERSONA) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)");
            query.setParameter(1, clie.getId_cliente());
            query.setParameter(2, clie.getNombre());
            query.setParameter(3, clie.getPaterno());
            query.setParameter(4, clie.getMaterno());
            query.setParameter(5, clie.getEmpresa());
            query.setParameter(6, clie.getCalle());
            query.setParameter(7, clie.getSexo());
            query.setParameter(8, clie.getFecha_nacimiento());
            query.setParameter(9, clie.getTel_movil());
            query.setParameter(10, clie.getTel_fijo());
            query.setParameter(11, clie.getExt());
            //query.setParameter(12, clie.getEmail());
            query.setParameter(12, clie.getNum_int());
            query.setParameter(13, clie.getNum_ext());
            //query.setParameter(15, clie.getColonia());
            query.setParameter(14, clie.getClavecelular());
            query.setParameter(15, clie.getLadacelular());
            query.setParameter(16, clie.getID_CP() == new BigDecimal(0) ? new BigDecimal(100000) : clie.getID_CP());
            query.setParameter(17, clie.getCalleFiscal());
            query.setParameter(18, clie.getNum_int_fiscal());
            query.setParameter(19, clie.getNum_ext_fiscal());
            //query.setParameter(22, clie.getColoniaFiscal());
            query.setParameter(20, clie.getID_CP_FISCAL() == new BigDecimal(0) ? new BigDecimal(100000) : clie.getID_CP_FISCAL());
            query.setParameter(21, clie.getNextel());
            query.setParameter(22, clie.getRazon_social());
            query.setParameter(23, clie.getRfcFiscal());
            query.setParameter(24, clie.getLadaoficina());
            query.setParameter(25, clie.getClaveoficina());
            query.setParameter(26, clie.getNextelclave());
            query.setParameter(27, 1);//activo
            query.setParameter(28, clie.getDiasCredito());
            query.setParameter(29, clie.getLimiteCredito());
            query.setParameter(30, clie.getTipoPersona());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
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
        Query query = em.createNativeQuery("SELECT * FROM CLIENTE WHERE UPPER(TRIM(NOMBRE) ||' '|| TRIM(APELLIDO_PATERNO) ||' '|| TRIM(APELLIDO_MATERNO) )  LIKE UPPER('%" + nombreCliente + "%') and STATUS =1");

        return query.getResultList();
    }

    @Override
    public List<Object[]> getClienteCreditoById(int idCliente) {
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
            //System.out.println("error query :" + ex.toString());
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getCreditoClienteByIdCliente(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("select c.ID_CLIENTE, TRIM(c.NOMBRE) ||' '|| TRIM(c.APELLIDO_PATERNO) ||' '|| TRIM(c.APELLIDO_MATERNO) AS NOMBRE_COMPLETO,NVL(c.MONTO_CREDITO,0) "
                    + "AS MONTO_CREDITO, "
                    + "NVL((SELECT SUM(CRE.MONTO_CREDITO) FROM CREDITO CRE WHERE CRE.ID_CLIENTE_FK =c.ID_CLIENTE AND CRE.ESTATUS_CREDITO = 1 ),0) "
                    + "AS CREDITO_UTILIZADO, "
                    + "(select sum(dc.monto) as DOCUMENTOS from DOCUMENTOS_COBRAR dc where dc.ID_STATUS_FK=1 and dc.ID_CLIENTE_FK=c.ID_CLIENTE) as Documentos "
                    + "FROM CLIENTE c "
                    + "WHERE c.ID_CLIENTE = ? AND c.STATUS =1");
            query.setParameter(1, idCliente);
            return query.getResultList();

        } catch (Exception ex) {

            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getClientesActivos() {
        try {

            //System.out.println("EJB_GET_CLIENTE");
            Query query = em.createNativeQuery("select c.* ,en.ID_ENTIDAD_PK, en.NOMBRE_ENTIDAD ,en1.ID_ENTIDAD_PK, en1.NOMBRE_ENTIDAD,m.NOMBRE_MUNICIPIO,m1.NOMBRE_MUNICIPIO,cp.NOMBRE_COLONIA,cp.CODIGO_POSTAL,cp1.NOMBRE_COLONIA,cp1.CODIGO_POSTAL,m.ID_MUNICIPIO_PK,m.ID_MUNICIPIO_PK,cp.ID_PK,cp1.ID_PK "
                    + "from Cliente c "
                    + "INNER JOIN CODIGOS_POSTALES cp "
                    + "on c.ID_CP=cp.ID_PK "
                    + "INNER JOIN Municipios m "
                    + "on cp.ID_MUNICIPIO_FK=m.id_municipio_pk "
                    + "INNER JOIN ENTIDAD en "
                    + "on en.ID_ENTIDAD_PK=m.ID_ENTIDAD_FK "
                    + "INNER JOIN CODIGOS_POSTALES cp1 "
                    + "on c.ID_CP_FISCAL=cp1.ID_PK "
                    + "INNER JOIN Municipios m1 "
                    + "on cp.ID_MUNICIPIO_FK=m1.id_municipio_pk "
                    + "INNER JOIN ENTIDAD en1 "
                    + "on en1.ID_ENTIDAD_PK=m1.ID_ENTIDAD_FK AND c.STATUS =1");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Cliente;
import com.web.chon.negocio.NegocioCatCliente;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
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
    public List<Object[]> getClientes() 
    {
        try {

            System.out.println("EJB_GET_CLIENTE");
            Query query = em.createNativeQuery("select c.* ,en.ID_ENTIDAD_PK, en.NOMBRE_ENTIDAD ,en1.ID_ENTIDAD_PK, en1.NOMBRE_ENTIDAD,m.NOMBRE_MUNICIPIO,m1.NOMBRE_MUNICIPIO\n" +
"from Cliente c\n" +
"INNER JOIN CODIGOS_POSTALES cp\n" +
"on c.ID_CP=cp.ID_PK\n" +
"INNER JOIN Municipios m\n" +
"on cp.ID_MUNICIPIO_FK=m.id_municipio_pk\n" +
"INNER JOIN ENTIDAD en\n" +
"on en.ID_ENTIDAD_PK=m.ID_ENTIDAD_FK\n" +
"INNER JOIN CODIGOS_POSTALES cp1\n" +
"on c.ID_CP=cp1.ID_PK\n" +
"INNER JOIN Municipios m1\n" +
"on c.ID_CP_FISCAL=m1.id_municipio_pk\n" +
"INNER JOIN ENTIDAD en1\n" +
"on en1.ID_ENTIDAD_PK=m1.ID_ENTIDAD_FK");
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

            System.out.println("Id Cliente a eliminar: " + idCliente);
            Query query = em.createNativeQuery("DELETE ClIENTE WHERE ID_CLIENTE = ?");
            query.setParameter(1, idCliente);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateCliente(Cliente clie) {

        try {
            System.out.println("Cliente a modificar :" + clie.getNombre());
            Query query = em.createNativeQuery("UPDATE CLIENTE SET NOMBRE = ?, APELLIDO_PATERNO = ?, APELLIDO_MATERNO = ?, EMPRESA = ?, CALLE = ?, SEXO = ?, FECHA_NACIMIENTO = ?,TELEFONO_MOVIL = ?, TELEFONO_FIJO = ?, EXTENSION = ?, EMAIL = ?, NUM_INT = ? , NUM_EXT = ?, COLONIA = ?, CLAVECELULAR = ?, LADACELULAR = ?, ID_CP= ?,CALLEFISCAL = ?,NUMINTFIS = ?,NUMEXTFIS = ?,COLONIAFISCAL = ?, ID_CP_FISCAL = ?, NEXTEL = ?,RAZON = ?,RFC = ?, LADAOFICINA = ?,CLAVEOFICINA = ?,NEXTELCLAVE = ? WHERE ID_CLIENTE = ? ");
            query.setParameter(1, clie.getNombre());
            query.setParameter(2, clie.getPaterno());
            query.setParameter(3, clie.getMaterno());
            query.setParameter(4, clie.getEmpresa());
            query.setParameter(5, clie.getCalle());
            //query.setParameter(6, clie.getCp());
           
            query.setParameter(6, clie.getSexo());
            query.setParameter(7, clie.getFecha_nacimiento());
            query.setParameter(8, clie.getTel_movil());
            query.setParameter(9, clie.getTel_fijo());
            query.setParameter(10, clie.getExt());
            query.setParameter(11, clie.getEmail());
            query.setParameter(12, clie.getNum_int());
            query.setParameter(13, clie.getNum_ext());
            query.setParameter(14, clie.getColonia());
            query.setParameter(15, clie.getClavecelular());
            query.setParameter(16, clie.getLadacelular());
            query.setParameter(17, clie.getID_CP());
            query.setParameter(18, clie.getCalleFiscal());
            query.setParameter(19, clie.getNum_int_fiscal());
            query.setParameter(20, clie.getNum_ext_fiscal());
            query.setParameter(21, clie.getColoniaFiscal());
            //query.setParameter(24, clie.getEstadoFiscal());
            query.setParameter(22, clie.getID_CP_FISCAL());
            query.setParameter(23, clie.getNextel());
            query.setParameter(24, clie.getRazon_social());
            query.setParameter(25, clie.getRfcFiscal());
            //query.setParameter(27, clie.getCpFiscal());
            query.setParameter(26, clie.getLadaoficina());
            query.setParameter(27, clie.getClaveoficina());
            query.setParameter(28, clie.getNextelclave());
            query.setParameter(29, clie.getId_cliente());
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int insertCliente(Cliente clie) {
        System.out.println("EJB_INSERTA_CLIENTE");
        try {

            Query querySel = em.createNativeQuery("SELECT NOMBRE FROM CLIENTE WHERE EMAIL = '" + clie.getEmail() + "' ");
            List<Object[]> resultList = null;

            resultList = querySel.getResultList();

            if (resultList.isEmpty()) 
            {
                
                
                System.out.println("insert : "+clie );
                Query query = em.createNativeQuery("INSERT INTO CLIENTE (ID_CLIENTE,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMPRESA, CALLE, SEXO, FECHA_NACIMIENTO,TELEFONO_MOVIL, TELEFONO_FIJO, EXTENSION, EMAIL, NUM_INT, NUM_EXT, COLONIA, CLAVECELULAR, LADACELULAR,ID_CP,CALLEFISCAL,NUMINTFIS,NUMEXTFIS,COLONIAFISCAL,ID_CP_FISCAL,NEXTEL,RAZON,RFC,LADAOFICINA,CLAVEOFICINA,NEXTELCLAVE) "
                        + "VALUES (S_CLIENTE.NextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
                query.setParameter(11, clie.getEmail());
                query.setParameter(12, clie.getNum_int());
                query.setParameter(13, clie.getNum_ext());
                query.setParameter(14, clie.getColonia());
                query.setParameter(15, clie.getClavecelular());
                query.setParameter(16, clie.getLadacelular());
                query.setParameter(17, clie.getID_CP());
                query.setParameter(18, clie.getCalleFiscal());
                query.setParameter(19, clie.getNum_int_fiscal());
                query.setParameter(20, clie.getNum_ext_fiscal());
                query.setParameter(21, clie.getColoniaFiscal());         
                query.setParameter(22, clie.getID_CP_FISCAL());
                query.setParameter(23, clie.getNextel());
                query.setParameter(24, clie.getRazon_social());
                query.setParameter(25, clie.getRfcFiscal());               
                query.setParameter(26, clie.getLadaoficina());
                query.setParameter(27, clie.getClaveoficina());
                query.setParameter(28, clie.getNextelclave());

                return query.executeUpdate();

            } else {

                return 0;

            }

        } catch (Exception ex) {
            Logger.getLogger(EjbCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getClienteByNombreCompleto(String nombreCliente) {
        Query query = em.createNativeQuery("SELECT * FROM CLIENTE WHERE UPPER(NOMBRE ||' '|| APELLIDO_PATERNO ||' '|| APELLIDO_MATERNO )  LIKE UPPER('%" + nombreCliente + "%')");

        return query.getResultList();
    }

}

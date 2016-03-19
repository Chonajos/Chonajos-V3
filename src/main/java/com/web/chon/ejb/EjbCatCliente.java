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
    public List<Object[]> getClientes() {
        try {

            System.out.println("EJB_GET_CLIENTE");
            Query query = em.createNativeQuery("SELECT * FROM CLIENTE");
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
            Query query = em.createNativeQuery("UPDATE CLIENTE SET NOMBRE=?, APELLIDO_PATERNO=?, APELLIDO_MATERNO=?, EMPRESA=?, CALLE=?, CP=?, ESTADO=?, SEXO=?, FECHA_NACIMIENTO=?,TELEFONO_MOVIL=?, TELEFONO_FIJO=?, EXTENSION=?, EMAIL=?, NUM_INT=?, NUM_EXT=?, COLONIA=?, CLAVECELULAR=?, LADACELULAR=?, DEL_MUN=?,CALLEFISCAL=?,NUMINTFIS=?,NUMEXTFIS=?,COLONIAFISCAL=?,ESTADOFISCAL=?,DEL_MUN_FISCAL=?,NEXTEL=?,RAZON=?,RFC=?,CPFISCAL=? LADAOFICINA=?,CLAVEOFICINA=?,NEXTELCLAVE=?WHERE ID_CLIENTE = ? ");
            query.setParameter(1, clie.getNombre());
            query.setParameter(2, clie.getPaterno());
            query.setParameter(3, clie.getMaterno());
            query.setParameter(4, clie.getEmpresa());
            query.setParameter(5, clie.getCalle());
            query.setParameter(6, clie.getCp());
            query.setParameter(7, clie.getEstado());
            query.setParameter(8, clie.getSexo());
            query.setParameter(9, clie.getFecha_nacimiento());
            query.setParameter(10, clie.getTel_movil());
            query.setParameter(11, clie.getTel_fijo());
            query.setParameter(12, clie.getExt());
            query.setParameter(13, clie.getEmail());
            query.setParameter(14, clie.getNum_int());
            query.setParameter(15, clie.getNum_ext());
            query.setParameter(16, clie.getColonia());
            query.setParameter(17, clie.getClavecelular());
            query.setParameter(18, clie.getLadacelular());
            query.setParameter(19, clie.getDel_Mun());
            query.setParameter(20, clie.getCalleFiscal());
            query.setParameter(21, clie.getNum_int_fiscal());
            query.setParameter(22, clie.getNum_ext_fiscal());
            query.setParameter(23, clie.getColoniaFiscal());
            query.setParameter(24, clie.getEstadoFiscal());
            query.setParameter(25, clie.getDel_mun_fiscal());
            query.setParameter(26, clie.getNextel());
            query.setParameter(27, clie.getRazon_social());
            query.setParameter(28, clie.getRfcFiscal());
            query.setParameter(29, clie.getCpFiscal());
            query.setParameter(30, clie.getLadaoficina());
            query.setParameter(31, clie.getClaveoficina());
            query.setParameter(32, clie.getNextelclave());
            query.setParameter(33, clie.getId_cliente());

            return query.executeUpdate();

        } catch (Exception ex) {
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
                
                //System.out.println("insert : " + usuario.getNombreUsuario() + " " + usuario.getRfcUsuario());
                Query query = em.createNativeQuery("INSERT INTO CLIENTE (ID_CLIENTE,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMPRESA, CALLE, CP, ESTADO, SEXO, FECHA_NACIMIENTO,TELEFONO_MOVIL, TELEFONO_FIJO, EXTENSION, EMAIL, NUM_INT, NUM_EXT, COLONIA, CLAVECELULAR, LADACELULAR, DEL_MUN,CALLEFISCAL,NUMINTFIS,NUMEXTFIS,COLONIAFISCAL,ESTADOFISCAL,DEL_MUN_FISCAL,NEXTEL,RAZON,RFC,CPFISCAL,LADAOFICINA,CLAVEOFICINA,NEXTELCLAVE) "
                        + "VALUES (S_CLIENTE.NextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                query.setParameter(1, clie.getNombre());
                query.setParameter(2, clie.getPaterno());
                query.setParameter(3, clie.getMaterno());
                query.setParameter(4, clie.getEmpresa());
                query.setParameter(5, clie.getCalle());
                query.setParameter(6, clie.getCp());
                query.setParameter(7, clie.getEstado());
                query.setParameter(8, clie.getSexo());
                query.setParameter(9, clie.getFecha_nacimiento());
                query.setParameter(10, clie.getTel_movil());
                query.setParameter(11, clie.getTel_fijo());
                query.setParameter(12, clie.getExt());
                query.setParameter(13, clie.getEmail());
                query.setParameter(14, clie.getNum_int());
                query.setParameter(15, clie.getNum_ext());
                query.setParameter(16, clie.getColonia());
                query.setParameter(17, clie.getClavecelular());
                query.setParameter(18, clie.getLadacelular());
                query.setParameter(19, clie.getDel_Mun());
                query.setParameter(20, clie.getCalleFiscal());
                query.setParameter(21, clie.getNum_int_fiscal());
                query.setParameter(22, clie.getNum_ext_fiscal());
                query.setParameter(23, clie.getColoniaFiscal());
                query.setParameter(24, clie.getEstadoFiscal());
                query.setParameter(25, clie.getDel_mun_fiscal());
                query.setParameter(26, clie.getNextel());
                query.setParameter(27, clie.getRazon_social());
                query.setParameter(28, clie.getRfcFiscal());
                query.setParameter(29, clie.getCpFiscal());
                query.setParameter(30, clie.getLadaoficina());
                query.setParameter(31, clie.getClaveoficina());
                query.setParameter(32, clie.getNextelclave());

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

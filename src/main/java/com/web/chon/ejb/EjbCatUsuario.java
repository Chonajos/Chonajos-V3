/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Usuario;
import com.web.chon.negocio.NegocioCatUsuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author juan
 */
@Stateless(mappedName = "ejbCatUsuario")
public class EjbCatUsuario implements NegocioCatUsuario {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getUsuarios() {
        try {

            System.out.println("EJB_GET");

            Query query = em.createNativeQuery("SELECT * FROM USUARIO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Object[] getUsuarioById(int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteUsuario(int idUsuario) {
        try {

            System.out.println("Id Usuario a eliminar: " + idUsuario);
            Query query = em.createNativeQuery("DELETE Usuario WHERE ID_USUARIO_PK = ?");
            query.setParameter(1, idUsuario);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateUsuario(Usuario usuario) {

        try {

            System.out.println("Usuario a modificar :" + usuario.getNombreUsuario() + " desc " + usuario.getRfcUsuario() + " " + usuario.getIdUsuarioPk().intValue());
            Query query = em.createNativeQuery("UPDATE Usuario SET NOMBRE_USUARIO = ?, APATERNO_USUARIO = ?, AMATERNO_USUARIO = ?, "
                    + "CLAVE_USUARIO = ?, CONTRASENA_USUARIO = ?, RFC_USUARIO = ? WHERE ID_USUARIO_PK = ? ");
            query.setParameter(1, usuario.getNombreUsuario());
            query.setParameter(2, usuario.getApaternoUsuario());
            query.setParameter(3, usuario.getAmaternoUsuario());
            query.setParameter(4, usuario.getClaveUsuario());
            query.setParameter(5, usuario.getContrasenaUsuario());
            query.setParameter(6, usuario.getRfcUsuario());
            query.setParameter(7, usuario.getIdUsuarioPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int insertUsuario(Usuario usuario) {
        System.out.println("ejb");
        try {

            Query querySel = em.createNativeQuery("SELECT CLAVE_USUARIO FROM USUARIO WHERE CLAVE_USUARIO = '" + usuario.getClaveUsuario() + "' ");

            List<Object[]> resultList = null;
            resultList = querySel.getResultList();

            if (resultList.isEmpty()) {
                
                System.out.println("insert : " + usuario.getNombreUsuario() + " " + usuario.getRfcUsuario());
                Query query = em.createNativeQuery("INSERT INTO USUARIO (ID_USUARIO_PK,NOMBRE_USUARIO, APATERNO_USUARIO, AMATERNO_USUARIO, "
                        + "CLAVE_USUARIO, CONTRASENA_USUARIO, RFC_USUARIO) VALUES (S_USUARIO.NextVal,?,?,?,?,?,?)");
                query.setParameter(1, usuario.getNombreUsuario());
                query.setParameter(2, usuario.getApaternoUsuario());
                query.setParameter(3, usuario.getAmaternoUsuario());
                query.setParameter(4, usuario.getClaveUsuario());
                query.setParameter(5, usuario.getContrasenaUsuario());
                query.setParameter(6, usuario.getRfcUsuario());
                
                return query.executeUpdate();
                
            } else {

                return 0;

            }


        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int insertarUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

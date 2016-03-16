/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Usuario;
import com.web.chon.negocio.NegocioLogin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author marcogante
 */
@Stateless(mappedName = "ejbUsuario")
public class SessionUsuario implements NegocioLogin {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public Usuario validarLogin(Usuario obj) throws Exception {
        Usuario usuario = new Usuario();
        try {

            Query query = em.createNativeQuery("SELECT * FROM USUARIO WHERE CLAVE_USUARIO = ? AND CONTRASENA_USUARIO =?");

            query.setParameter(1, obj.getClaveUsuario().trim());
            query.setParameter(2, obj.getContrasenaUsuario().trim());

            List<Object[]> lista = query.getResultList();

            if (lista.size() > 0) {

                for (Object[] object : lista) {
                    usuario.setNombreUsuario(object[1] == null ? "" : object[1].toString());
                    usuario.setApaternoUsuario(object[2] == null ? "" : object[2].toString());
                    usuario.setAmaternoUsuario(object[3] == null ? "" : object[3].toString());
                    usuario.setMensaje("Usuario encontrado");
                    usuario.setStatus(true);

                }
            } else {

                usuario.setMensaje("Usuario no encontrado");
                usuario.setStatus(false);
            }

            return usuario;
        } catch (Exception ex) {
            System.out.println("Error" + ex.getMessage());
            return usuario;
        }
    }
}

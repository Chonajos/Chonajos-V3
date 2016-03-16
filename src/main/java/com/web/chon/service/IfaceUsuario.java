
package com.web.chon.service;

import com.web.chon.dominio.Usuario;

/**
 *
 * @author marcogante
 */
public interface IfaceUsuario {
    Usuario validarLogin(Usuario obj) throws Exception;
}

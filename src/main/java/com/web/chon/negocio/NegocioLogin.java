package com.web.chon.negocio;

import com.web.chon.dominio.Usuario;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioLogin {
    Usuario validarLogin(Usuario obj) throws Exception;
}

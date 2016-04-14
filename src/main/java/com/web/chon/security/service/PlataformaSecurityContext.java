package com.web.chon.security.service;

import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import java.util.Date;

/**
 *
 * @author Juan
 */
public interface PlataformaSecurityContext {

	public UsuarioDominio getUsuarioAutenticado();
	
	public Date getFechaSistema();
	
}

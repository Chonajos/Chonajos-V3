package com.web.chon.security.service;

import com.web.chon.core.eception.UsuarioNoAutenticadoException;
import com.web.chon.core.service.SistemaService;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.service.ServiceAnalisisMercado;
import com.web.chon.util.Utilidades;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
/**
 * Wrapper de spring security {@link SecurityContext} para obtener el usuario
 * autenticado {@link Usuarios}.
 */
@Service
public class SpringSecurityPlataformaSecurityContext implements PlataformaSecurityContext, Serializable {

    private static final long serialVersionUID = 1L;

    SistemaService ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (SistemaService) Utilidades.getEJBRemote("ejbsistemaService", SistemaService.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(SistemaService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public UsuarioDominio getUsuarioAutenticado() {

        UsuarioDominio usuarioActual = null;

        final SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            final Authentication auth = context.getAuthentication();
            if (auth != null) {
                usuarioActual = (UsuarioDominio) auth.getPrincipal();
            }
        }

        if (usuarioActual == null) {
            throw new UsuarioNoAutenticadoException();
        }

        usuarioActual.setUsuId(usuarioActual.getUsuId().toUpperCase());

        return usuarioActual;
    }

    @Override
    public Date getFechaSistema() {
        getEjb();
        return ejb.getFechaSistema();
    }

}

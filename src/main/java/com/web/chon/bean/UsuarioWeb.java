package com.web.chon.bean;

import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan
 */
@Component
@Scope("session")
public class UsuarioWeb implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String TIME_ZONE_DF = "America/Mexico_City";

    @Autowired
    PlataformaSecurityContext plataformaSecurityContext;

    public UsuarioDominio getUsuario() {
        return plataformaSecurityContext.getUsuarioAutenticado();
    }

    public String getTimeZone() {
        return TIME_ZONE_DF;
    }

}

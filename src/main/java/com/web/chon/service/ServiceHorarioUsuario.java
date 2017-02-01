package com.web.chon.service;

import com.web.chon.dominio.HorarioUsuario;
import com.web.chon.negocio.NegocioHorarioUsuario;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service
public class ServiceHorarioUsuario implements IfaceHorarioUsuario {

    NegocioHorarioUsuario ejb;

    private void getEjb() {

        if (ejb == null) {
            try {
                ejb = (NegocioHorarioUsuario) Utilidades.getEJBRemote("ejbHorarioUsuario", NegocioHorarioUsuario.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceHorarioUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public int insert(HorarioUsuario horarioUsuario) {
        getEjb();
        
        return ejb.insert(horarioUsuario);
        
    }

    @Override
    public int update(HorarioUsuario horarioUsuario) {
        
        getEjb();
        
        return ejb.update(horarioUsuario);
    }

}

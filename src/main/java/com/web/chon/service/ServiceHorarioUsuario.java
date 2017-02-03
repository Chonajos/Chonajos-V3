package com.web.chon.service;

import com.web.chon.dominio.HorarioUsuario;
import com.web.chon.negocio.NegocioHorarioUsuario;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    public HorarioUsuario getByIdUsuario(BigDecimal id) {

        getEjb();
        HorarioUsuario horarioUsuario = new HorarioUsuario();
        List<HorarioUsuario> lstHorarioUsuario = new ArrayList<HorarioUsuario>();
        List<Object[]> lstObject = new ArrayList<Object[]>();

        lstObject = ejb.getByIdUsuario(id);

        for (Object[] object : lstObject) {
            
            horarioUsuario.setIdHorarioUsuario(object[0] == null ? null : new BigDecimal(object[0].toString()));
            horarioUsuario.setIdUsuario(object[1] == null ? null : new BigDecimal(object[1].toString()));
            horarioUsuario.setHoraEntrada(object[2] == null ? null : (Date) object[2]);
            horarioUsuario.setHoraSalida(object[3] == null ? null : (Date) object[3]);
            horarioUsuario.setFechaInicio(object[4] == null ? null : (Date) object[4]);
            horarioUsuario.setFechaFin(object[5] == null ? null : (Date) object[5]);
            
        }

        return horarioUsuario;
    }

}

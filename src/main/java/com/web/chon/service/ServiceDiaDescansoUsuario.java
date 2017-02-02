package com.web.chon.service;

import com.web.chon.dominio.DiaDescansoUsuario;
import com.web.chon.negocio.NegocioDiaDescansoUsuario;
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
public class ServiceDiaDescansoUsuario implements IfaceDiaDescansoUsuario {

    NegocioDiaDescansoUsuario ejb;

    private void getEjb() {

        if (ejb == null) {
            try {
                ejb = (NegocioDiaDescansoUsuario) Utilidades.getEJBRemote("ejbDiaDescansoUsuario", NegocioDiaDescansoUsuario.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceHorarioUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public int insert(DiaDescansoUsuario diaDescansoUsuario) {
        getEjb();

        String dias = "";
        for (int i = 0; i < diaDescansoUsuario.getDiasSelecionados().length; i++) {
            dias += diaDescansoUsuario.getDiasSelecionados()[i] + ",";
        }

        if (dias.length() > 1) {
            dias = dias.substring(0, dias.length() - 1);

            diaDescansoUsuario.setDia(dias);
        }

        return ejb.insert(diaDescansoUsuario);
    }

    @Override
    public int update(DiaDescansoUsuario diaDescansoUsuario) {
        getEjb();

        String dias = "";
        for (int i = 0; i < diaDescansoUsuario.getDiasSelecionados().length; i++) {
            dias += diaDescansoUsuario.getDiasSelecionados()[i] + ",";
        }

        if (dias.length() > 1) {
            dias = dias.substring(0, dias.length() - 1);

            diaDescansoUsuario.setDia(dias);
        }

        return ejb.update(diaDescansoUsuario);
    }

    @Override
    public DiaDescansoUsuario getByIdUsuario(BigDecimal id) {
        getEjb();
        DiaDescansoUsuario diaDescansoUsuario = new DiaDescansoUsuario();

        List<Object[]> lstObject = new ArrayList<Object[]>();

        lstObject = ejb.getByIdUsuario(id);

        for (Object[] object : lstObject) {

            diaDescansoUsuario.setIdDiaDescansoUsuario(object[0] == null ? null : new BigDecimal(object[0].toString()));
            diaDescansoUsuario.setIdUsuario(object[1] == null ? null : new BigDecimal(object[1].toString()));
            diaDescansoUsuario.setFechaInicio(object[2] == null ? null : (Date) object[2]);
            diaDescansoUsuario.setFechaFin(object[3] == null ? null : (Date) object[3]);
            diaDescansoUsuario.setDia(object[4] == null ? null : object[4].toString());

            if (diaDescansoUsuario.getDia() != null) {
                String[] diasDescanso = diaDescansoUsuario.getDia().split(",");
                diaDescansoUsuario.setDiasSelecionados(diasDescanso);
            }

        }
        return diaDescansoUsuario;
    }

}

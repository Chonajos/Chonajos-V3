package com.web.chon.service;

import com.web.chon.dominio.DiaDescansoUsuario;
import com.web.chon.negocio.NegocioDiaDescansoUsuario;
import com.web.chon.util.Utilidades;
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

        String dias ="";
        for (int i = 0; i < diaDescansoUsuario.getDiasSelecionados().length; i++) {
            System.out.println("dia selecionado "+diaDescansoUsuario.getDiasSelecionados()[i]);
            dias += diaDescansoUsuario.getDiasSelecionados()[i] + ",";
        }

        dias = dias.substring(0, dias.length() - 1);

        diaDescansoUsuario.setDia(dias);

        return ejb.insert(diaDescansoUsuario);
    }

    @Override
    public int update(DiaDescansoUsuario diaDescansoUsuario) {
        getEjb();

        String dias = null;
        for (int i = 0; i < diaDescansoUsuario.getDiasSelecionados().length; i++) {
            dias = diaDescansoUsuario.getDiasSelecionados()[i] + ",";
        }

        dias = dias.substring(0, dias.length() - 1);

        diaDescansoUsuario.setDia(dias);

        return ejb.update(diaDescansoUsuario);
    }

}

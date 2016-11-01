/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.PagosBancarios;
import com.web.chon.negocio.NegocioPagosBancarios;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServicePagosBancarios implements IfacePagosBancarios {
NegocioPagosBancarios ejb;
    private void getEjb() 
    {
        if (ejb == null) {
            try {
                ejb = (NegocioPagosBancarios) Utilidades.getEJBRemote("ejbPagoBancario", NegocioPagosBancarios.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServicePagosBancarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    @Override
    public int insertaPagoBancario(PagosBancarios pb) {
        getEjb();
        return ejb.insertaPagoBancario(pb);
    }

    @Override
    public int updatePagoBancario(PagosBancarios pb) {
        getEjb();
        return ejb.updatePagoBancario(pb);
    
    }

    @Override
    public ArrayList<PagosBancarios> getPagosPendientes() {
        getEjb();
        BigDecimal  i = new BigDecimal(1);
        ArrayList<PagosBancarios> listaOperaciones = new ArrayList<PagosBancarios>();
        List<Object[]> lstObject = ejb.getPagosPendientes();
        for (Object[] obj : lstObject) 
        {
            PagosBancarios op = new PagosBancarios();
            op.setIdTransBancariasPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdConceptoFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdTipoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setComentarios(obj[4] == null ? null : obj[4].toString());
            op.setIdUserFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setMonto(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setFecha(obj[7] == null ? null : (Date)obj[7]);
            op.setIdStatusFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            op.setFechaTranferencia((obj[9] == null ? null : (Date)obj[9]));
            op.setFolioElectronico(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setFechaDeposito((obj[11] == null ? null : (Date)obj[11]));
            op.setIdCuentaFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setIdCuentaFk(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            op.setConcepto(obj[14] == null ? null : obj[14].toString());
            op.setReferencia(obj[15] == null ? null : obj[15].toString());
            op.setNombreCaja(obj[16] == null ? null : obj[16].toString());
            op.setNombreConcepto(obj[17] == null ? "" : obj[17].toString());
            op.setNombreTipoAbono(obj[18] == null ? "" : obj[18].toString());
            op.setNombreUsuario(obj[19] == null ? null : obj[19].toString());
            op.setNombreBanco(obj[20] == null ? null : obj[20].toString());
            op.setNumero(i);
            i = i.add(new BigDecimal(1), MathContext.UNLIMITED);
            
            if(op.getIdStatusFk().intValue()==1)
            {
                op.setNombreStatus("APLICADO");
            }
            else
            {
                op.setNombreStatus("PENDIENTE");
            }

            listaOperaciones.add(op);
        }
        return listaOperaciones;
    
    }

    @Override
    public int getNextVal() {
        getEjb();
       return ejb.getNextVal();
    }
    
}
